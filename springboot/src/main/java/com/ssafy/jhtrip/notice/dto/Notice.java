package com.ssafy.jhtrip.notice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Notice {

	private int no;
	@NotBlank
	private String userId;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private int hit;
	private String createdAt;

}
