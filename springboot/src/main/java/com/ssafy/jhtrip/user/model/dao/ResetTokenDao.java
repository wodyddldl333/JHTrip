package com.ssafy.jhtrip.user.model.dao;

import com.ssafy.jhtrip.user.model.dto.ResetToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ResetTokenDao {
    int save(ResetToken resetToken);

    Optional<ResetToken> loadToken(String token);
}
