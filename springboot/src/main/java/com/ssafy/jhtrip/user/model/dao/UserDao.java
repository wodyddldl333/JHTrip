package com.ssafy.jhtrip.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jhtrip.user.model.dto.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

/**
 * 사용자 관련 DB 작업을 처리하는 DAO 인터페이스입니다.
 * MyBatis의 매퍼 인터페이스로 구현됩니다.
 *
 * @author wodyddldl333
 * @version v1
 */
@Mapper
public interface UserDao {

	/**
	 * 사용자의 로그인 정보를 확인합니다.
	 *
	 * @param user 로그인 시도하는 사용자 객체
	 * @return 로그인 성공 시 사용자 정보(Optional), 실패 시 Optional.empty()
	 */
	Optional<User> login(User user);

	/**
	 * 새로운 사용자를 등록합니다.
	 *
	 * @param user 등록할 사용자 정보
	 * @return 등록된 레코드 수 (성공 시 1)
	 */
	int regist(User user);

	/**
	 * 사용자 정보를 수정합니다.
	 *
	 * @param user 수정할 사용자 정보
	 * @return 수정된 레코드 수 (성공 시 1)
	 */
	int modifyUser(User user);

	/**
	 * 사용자 정보를 삭제합니다.
	 *
	 * @param id 삭제할 사용자 ID
	 * @return 삭제된 레코드 수 (성공 시 1)
	 */
	int deleteUser(String id);

	/**
	 * 이메일로 사용자를 조회합니다.
	 *
	 * @param email 검색할 이메일 주소
	 * @return 해당 이메일을 가진 사용자 ID(Optional), 없으면 Optional.empty()
	 */
	Optional<String> findByEmail(String email);

	/**
	 * 사용자의 프로필 이미지를 업데이트합니다.
	 *
	 * @param id 사용자 ID
	 * @param newFileName 새 프로필 이미지 파일명
	 * @return 업데이트된 레코드 수 (성공 시 1)
	 */
	@Update("update members set profile_uri = #{newFileName} where id = #{id}")
	int updateProfile(@Param("id") String id, @Param("newFileName") String newFileName);

	/**
	 * 사용자 ID로 프로필 이미지 파일명을 조회합니다.
	 *
	 * @param id 사용자 ID
	 * @return 프로필 이미지 파일명(Optional), 없으면 Optional.empty()
	 */
	@Select("select profile_uri from members where id = #{id}")
	Optional<String> getFileName(@Param("id") String id);
}

