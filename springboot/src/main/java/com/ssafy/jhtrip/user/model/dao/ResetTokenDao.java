package com.ssafy.jhtrip.user.model.dao;

import com.ssafy.jhtrip.user.model.dto.ResetToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * 비밀번호 찾기 시 비밀번호 초기화를 위한 Reset Token 발급 인터페이스
 *
 * @author wodyddldl333
 * @version v2
 */
@Mapper
public interface ResetTokenDao {
    /**
     * 발급된 ResetToken을 저장.
     *
     * @param resetToken 비밀번호를 초기화하는 회원과 만료기간, 토큰 값이 담겨있는 객체
     */
    int saveToken(ResetToken resetToken);

    /**
     * 저장된 ResetToken을 불러옴
     *
     * @param token ResetToken Value
     * @return 토큰이 가진 정보 객체 (회원 ID, 토큰 값, 유효기간)
     */
    Optional<ResetToken> loadToken(String token);
}
