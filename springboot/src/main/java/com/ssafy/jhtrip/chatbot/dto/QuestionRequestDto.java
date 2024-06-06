package com.ssafy.jhtrip.chatbot.dto;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class QuestionRequestDto implements Serializable {
	private String question;
}
