package com.ssafy.jhtrip.user.model.service;

import com.ssafy.jhtrip.user.model.dto.ResetToken;
import com.ssafy.jhtrip.user.model.dto.Security;
import com.ssafy.jhtrip.user.model.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	
	public User login(User user, Security security);

	public int regist(User user);

	public int modifyUser(User user, String token);

	public int deleteUser(String id);

	public Security getSalt(String id);

	String findByEmail(String email);

	void generatePasswordResetToken(String id, String email);

	ResetToken isTokenVaild(String token);

	public int modifyUserInfo(User user, MultipartFile file);

	String getFileName(String id);
}
