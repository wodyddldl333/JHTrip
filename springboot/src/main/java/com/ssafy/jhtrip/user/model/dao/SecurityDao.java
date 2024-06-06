package com.ssafy.jhtrip.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jhtrip.user.model.dto.Security;
import com.ssafy.jhtrip.user.model.dto.User;

import java.util.Optional;


@Mapper
public interface SecurityDao {
	
	Optional<Security> getSalt(String id);

	int regist(Security security);

	int update(Security security);
	
}
