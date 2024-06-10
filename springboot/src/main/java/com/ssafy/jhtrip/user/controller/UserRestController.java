package com.ssafy.jhtrip.user.controller;


import com.ssafy.jhtrip.user.model.dto.ResetToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.jhtrip.user.model.dto.User;
import com.ssafy.jhtrip.user.model.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserRestController {

	private final UserService userService;

	@Value("${file.upload-dir}")
	private String uploadDir;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User userInfo) {
		log.info("Login attempt for user ID: {}", userInfo.getId());
		User loginUser = userService.login(userInfo);
		return ResponseEntity.ok(loginUser);
	}

	@PostMapping("/regist")
	private ResponseEntity<?> regist(@Valid @RequestBody User user) {
		log.info("Registration request received for user with ID: {}", user.getId());
		int result = userService.regist(user);
		log.info("User registered successfully: {}", user.getId());
		return ResponseEntity.ok(result);
	}

	// 회원 정보 수정
	@PutMapping("/update/{token}")
	private ResponseEntity<?> resetPassword(@RequestBody User user, @PathVariable String token) {
		log.info("update request received for user with ID: {}", user.getId());
		int result = userService.resetPassword(user, token);
		log.info("User reset password successfully: {}", user.getId());
		return ResponseEntity.ok(result);
	}

	@PutMapping("/update")
	private ResponseEntity<?> modifyUserInfo(@RequestPart("user") User user,
											 @RequestPart(value = "file", required = false) MultipartFile file) {
		log.info("modify update request received for user with ID: {}", user.getId());
		int result = userService.modifyUserInfo(user, file);
		log.info("User updated successfully: {}", user.getId());
		return ResponseEntity.ok(result);
	}

	// 회원 탈퇴
	@DeleteMapping("/delete/{id}")
	private ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
		int result = userService.deleteUser(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> requestPasswordReset(@RequestBody User user) {
		// 비밀번호 재설정 토큰 생성 및 이메일 전송
		userService.generatePasswordResetToken(user);
		return ResponseEntity.ok(user.getId());
	}

	@GetMapping("/reset-password/{token}")
	public ResponseEntity<?> resetPassword(@PathVariable String token) {
		ResetToken resetToken = userService.isTokenVaild(token);

		// 프론트에서 유효한 토큰이 전달되면 비밀번호 변경 페이지와 연결
        return ResponseEntity.ok(Objects.requireNonNullElse(resetToken, "Not Valid Token"));
	}

	@GetMapping("/uploads/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		try {
			String fileName = userService.getFileName(id);
			Path path = Paths.get(uploadDir + "/" + fileName);
			byte[] fileContent = Files.readAllBytes(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG); // 또는 적절한 이미지 타입으로 설정
			return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
