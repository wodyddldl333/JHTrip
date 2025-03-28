package com.ssafy.jhtrip.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class Board {

	private int no;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@NotBlank
	private String userId;

	private int hit;

	private String createdAt;

	private FileInfo fileInfo;
}
