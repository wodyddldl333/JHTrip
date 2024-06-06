package com.ssafy.jhtrip.user.model.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.ssafy.jhtrip.exception.*;
import com.ssafy.jhtrip.user.model.dao.ResetTokenDao;
import com.ssafy.jhtrip.user.model.dto.ResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.jhtrip.user.model.dao.SecurityDao;
import com.ssafy.jhtrip.user.model.dao.UserDao;
import com.ssafy.jhtrip.user.model.dto.Security;
import com.ssafy.jhtrip.user.model.dto.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final JavaMailSender javaMailSender;
    private final UserDao userDao;
    private final SecurityDao securityDao;
    private final ResetTokenDao resetTokenDao;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public UserServiceImpl(UserDao userDao, SecurityDao securityDao, ResetTokenDao resetTokenDao, JavaMailSender javaMailSender) {
        this.userDao = userDao;
        this.securityDao = securityDao;
        this.resetTokenDao = resetTokenDao;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public User login(User user, Security security) {
        byte[] nsalt = Base64.getDecoder().decode(security.getSalt());
        String hashedPassword = hashPassword(user.getPass(), nsalt, security.getIteration());
        User userInfo = new User();
        userInfo.setId(user.getId());
        userInfo.setPass(hashedPassword);
        return userDao.login(userInfo)
                .orElseThrow(() -> new LoginException("Login failed"));
    }

    @Transactional
    @Override
    public int regist(User user) {
        if (!user.isPassVal()) {
            throw new UserRegistrationException("Passwords do not match");
        }
        byte[] salt = generateSalt();
        int randomNumber = generateRandInt();
        String hashedPassword = hashPassword(user.getPass(), salt, randomNumber);
        user.setPass(hashedPassword);

        // 사용자 정보 등록
        userDao.regist(user);

        // 보안 정보 등록
        Security security = new Security(user.getId(), Base64.getEncoder().encodeToString(salt), randomNumber, "SHA-256");
        return securityDao.regist(security);
    }

    @Transactional
    @Override
    public int modifyUser(User user, String token) {
        isTokenVaild(token);
        if (!user.isPassVal()) {
            throw new UserModificationException("Passwords do not match");
        }
        try {
            // 비밀번호 해싱 및 보안 정보 생성
            byte[] salt = generateSalt();
            int randomNumber = generateRandInt();
            String hashedPassword = hashPassword(user.getPass(), salt, randomNumber);
            user.setPass(hashedPassword);

            // 사용자 정보 수정
            log.info("Modifying user information for user: {}", user.getId());
            userDao.modifyUser(user);

            // 보안 정보 수정
            Security security = new Security(user.getId(), Base64.getEncoder().encodeToString(salt), randomNumber, "SHA-256");
            log.info("Updating security details for user: {}", user.getId());
            return securityDao.update(security);
        } catch (Exception e) {
            log.error("Error while modifying user", e);
            throw new UserModificationException("Error while modifying user");
        }
    }

    @Transactional
    @Override
    public int modifyUserInfo(User user, MultipartFile file) {
        // 이미지 업로드
        int result = 0;
        if (file != null) {
            result = upload(user.getId(), file);
        }
        // 비밀번호 변경
        if (!"".equals(user.getNewPw())) {
            String newPassword = user.getPass();
            Security security = securityDao.getSalt(user.getId())
                    .orElseThrow(() -> new LoginException("존재하지 않는 아이디"));
            byte[] nsalt = Base64.getDecoder().decode(security.getSalt());
            user.setPass(hashPassword(user.getPass(), nsalt, security.getIteration()));
            userDao.login(user)
                    .orElseThrow(() -> new LoginException("현재 비밀번호가 틀립니다."));
            try {
                // 비밀번호 해싱 및 보안 정보 생성
                byte[] salt = generateSalt();
                int randomNumber = generateRandInt();
                user.setPass(hashPassword(user.getNewPw(), salt, randomNumber));

                log.info("Modifying user information for user: {}", user.getId());
                userDao.modifyUser(user);

                Security newsecurity = new Security(user.getId(), Base64.getEncoder().encodeToString(salt), randomNumber, "SHA-256");
                log.info("Updating security details for user: {}", user.getId());
                return securityDao.update(newsecurity);
            } catch (Exception e) {
                log.error("Error while modifying user", e);
                throw new UserModificationException("Error while modifying user");
            }
        }
        return result;
    }

    @Override
    public String getFileName(String id) {
        return userDao.getFileName(id)
                .orElseThrow(() -> new RuntimeException("profile Not Found"));
    }

    private int upload(String id, MultipartFile file) {
        String filename =  StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "-" + filename;
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path path = Paths.get(uploadDir + "/" + newFileName);
            Files.copy(file.getInputStream(), path);
            return userDao.updateProfile(id, newFileName);
        } catch (Exception e) {
            log.error("Failed to upload file", e);
            throw new RuntimeException("Failed to upload file");
        }
    }

    @Override
    public int deleteUser(String id) {
        try {
            return userDao.deleteUser(id);
        } catch (Exception e) {
            throw new UserModificationException("Error while deleting user", e);
        }
    }

    @Override
    public Security getSalt(String id) {
        return securityDao.getSalt(id)
                .orElseThrow(() -> new LoginException("Not exist Id"));
    }

    @Override
    public String findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new NotFindUserId("failed to find user with email: " + email));
    }

    private int generateRandInt() {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[4]; // 4바이트 크기의 난수 생성
        random.nextBytes(randomBytes);
        return Math.abs(ByteBuffer.wrap(randomBytes).getInt()) % 100 + 50;
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16바이트 크기의 솔트 사용
        random.nextBytes(salt);
        return salt;
    }

    // 비밀번호 해싱 및 키 스트레칭 함수
    private static String hashPassword(String password, byte[] salt, int iterations) {
        int keyLength = 256; // 해시 값의 길이
        char[] passwordChars = password.toCharArray();

        try {
            PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashedPassword = keyFactory.generateSecret(spec).getEncoded();

            // Base64 인코딩하여 저장
            return Base64.getEncoder().encodeToString(hashedPassword);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error occurred while hashing password: {}", e.getMessage());
            throw new RuntimeException("Error occurred while hashing password", e);
        }
    }

    //SMTP 프로토콜
    public void sendEmail(String to, String subject, String text) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
        };
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
        javaMailSender.send(preparator);
    }

    @Transactional
    @Override
    public void generatePasswordResetToken(String id, String email) {
        String token = UUID.randomUUID().toString(); // 랜덤한 토큰 생성

        ResetToken resetToken = new ResetToken();
        resetToken.setUserId(id);
        resetToken.setToken(token);
        resetToken.setExpiry(LocalDateTime.now().plusMinutes(15).toString());

        try {
            // 생성된 토큰을 데이터베이스에 저장
            resetTokenDao.save(resetToken);
        } catch (Exception e) {
            log.error("Error occurred while saving reset token: {}", e.getMessage());
            throw new ResetTokenExecption("Error occurred while saving reset token");
        }

        // 이메일 전송
        String path = "http://localhost:5173/reset-password/" + token;
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Password Reset</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>\n" +
                "      <div style=\"margin: 0 auto; max-width: 600px; padding: 10px;\">\n" +
                "        <div style=\"font-size: 25px; text-align: center; padding: 10px;\">\n" +
                "          🔒 비밀번호를 재설정 해주세요.\n" +
                "        </div>\n" +
                "        <div style=\"border: 2px solid #CCCCCC; padding: 30px; border-radius: 10px;\">\n" +
                "          <div style=\"text-align: center; font-size: 20px; font-weight: bold; margin-bottom: 10px;\">\n" +
                "            🚀 Enjoy Trip 비밀번호 재설정\n" +
                "          </div>\n" +
                "          <div style=\"margin-bottom: 20px; font-size: 16px; line-height: 1.5; color: #333333;\">\n" +
                "            비밀번호를 분실하였다면 아래의 버튼을 눌러 비밀번호 재설정을 진행해주세요. 비밀번호 변경 URL은 메일이 발송된 후 15분 동안 유효합니다.\n" +
                "          </div>\n" +
                "          <div style=\"text-align: center; padding: 20px;\">\n" +
                "            <a href=\"" + path + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; font-weight: bold; color: #FFFFFF; background-color: #007BFF; border-radius: 5px; text-decoration: none;\">🔑 Reset your password</a>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        sendEmail(email, "비밀번호 초기화 링크입니다.", htmlContent);
    }

    @Override
    public ResetToken isTokenVaild(String token) {
        ResetToken resetToken = resetTokenDao.loadToken(token)
                .orElseThrow(() -> new NotTokenValidation("Token is not valid"));
        String expiry = resetToken.getExpiry();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expiryTime = LocalDateTime.parse(expiry, formatter);
        if (expiryTime.isBefore(LocalDateTime.now())) {
            throw new NotTokenValidation("Token is not valid");
        }
        return resetToken;
    }
}
