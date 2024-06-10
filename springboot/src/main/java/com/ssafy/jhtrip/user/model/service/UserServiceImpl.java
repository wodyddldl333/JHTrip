package com.ssafy.jhtrip.user.model.service;

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
import java.util.Objects;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.ssafy.jhtrip.exception.*;
import com.ssafy.jhtrip.user.model.dao.ResetTokenDao;
import com.ssafy.jhtrip.user.model.dto.ResetToken;
import org.springframework.beans.factory.annotation.Value;
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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserDao userDao;
    private final SecurityDao securityDao;
    private final ResetTokenDao resetTokenDao;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public UserServiceImpl(UserDao userDao, SecurityDao securityDao, ResetTokenDao resetTokenDao, JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.userDao = userDao;
        this.securityDao = securityDao;
        this.resetTokenDao = resetTokenDao;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Transactional
    @Override
    public User login(User userInfo) {
        Security security = getSalt(userInfo.getId());
        byte[] nsalt = Base64.getDecoder().decode(security.getSalt());
        String hashedPassword = hashPassword(userInfo.getPass(), nsalt, security.getIteration());
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
    public int resetPassword(User user, String token) {
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
            log.info("ResetPassword for user: {}", user.getId());
            userDao.modifyUser(user);

            // 보안 정보 수정
            Security security = new Security(user.getId(), Base64.getEncoder().encodeToString(salt), randomNumber, "SHA-256");
            log.info("Reset security details for user: {}", user.getId());
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
        String filename =  StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
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

    public String findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new NotFindUserId("failed to find user with email: " + email));
    }

    @Transactional
    @Override
    public void generatePasswordResetToken(User user) {
        String id = findByEmail(user.getEmail());
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
            throw new ResetTokenException("Error occurred while saving reset token");
        }

        // 이메일 전송
        String resetLink = "http://localhost:5173/reset-password/" + token;
        sendEmail(user.getEmail(), "비밀번호 초기화 링크입니다.", resetLink);
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

    public Security getSalt(String id) {
        return securityDao.getSalt(id)
                .orElseThrow(() -> new LoginException("Not exist Id"));
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
    private void sendEmail(String to, String subject, String resetLink) {
        Context context = new Context();
        context.setVariable("resetLink", resetLink);
        String htmlContent = templateEngine.process("email-template", context);
        System.out.println(htmlContent);
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true);
        };
        javaMailSender.send(preparator);
    }
}