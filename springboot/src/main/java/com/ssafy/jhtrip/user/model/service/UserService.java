package com.ssafy.jhtrip.user.model.service;

import com.ssafy.jhtrip.user.model.dto.ResetToken;
import com.ssafy.jhtrip.user.model.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	
	User login(User user);

	int regist(User user);

	int resetPassword(User user, String token);

	int deleteUser(String id);

	void generatePasswordResetToken(User user);

	ResetToken isTokenVaild(String token);

	int modifyUserInfo(User user, MultipartFile file);

	String getFileName(String id);
}
