package com.ssafy.jhtrip.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jhtrip.user.model.dto.Security;
import com.ssafy.jhtrip.user.model.dto.User;

import java.util.Optional;

/**
 * 비밀번호 해싱에 대한 솔트값을 저장하고 불러오는 인터페이스
 *
 * @author wodyddldl333
 * @version v2
 */
@Mapper
public interface SecurityDao {

	/**
	 * 해당 회원에 대한 솔트값을 가져옵니다.
	 *
	 * @param id 로그인을 하는 회원 ID
	 * @return Security 객체 반환
	 */
	Optional<Security> getSalt(String id);

	/**
	 * 솔트 값을 저장합니다.
	 *
	 * @param security 로그인을 하는 회원과 솔트 값이 담긴 객체
	 */
	int regist(Security security);

	/**
	 * 비밀번호 변경 시 솔트 값 업데이트 로직
	 *
	 * @param security 로그인을 하는 회원과 솔트 값이 담긴 객체
	 */
	int update(Security security);
	
}
