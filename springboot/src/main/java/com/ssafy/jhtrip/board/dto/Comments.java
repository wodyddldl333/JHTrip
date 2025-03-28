package com.ssafy.jhtrip.board.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    private int id;

    @Min(value = 1, message = "boardNo는 필수 값입니다")
    private int boardNo;

    @NotBlank
    private String userId;

    @NotBlank
    private String comment;

    private String createdAt;
}