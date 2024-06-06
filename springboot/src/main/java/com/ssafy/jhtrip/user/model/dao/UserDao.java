package com.ssafy.jhtrip.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jhtrip.user.model.dto.Security;
import com.ssafy.jhtrip.user.model.dto.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.swing.text.html.Option;
import java.util.Optional;


@Mapper
public interface UserDao {
	
	public Optional<User> login(User user);

	public int regist(User user);

	public int modifyUser(User user);
	
	public int deleteUser(String id);

	public Optional<String> findByEmail(String email);

	@Update("update members set profile_uri = #{newFileName} where id = #{id}")
	int updateProfile(@Param("id") String id, @Param("newFileName") String newFileName);

	@Select("select profile_uri from members where id = #{id}")
	Optional<String> getFileName(@Param("id") String id);
}
