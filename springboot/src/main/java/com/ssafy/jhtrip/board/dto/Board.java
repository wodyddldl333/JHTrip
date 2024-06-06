package com.ssafy.jhtrip.board.dto;

import jakarta.validation.constraints.NotBlank;

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
	
	public Board() {}

	public Board(int no, String title, String content, String userId, int hit, String createdAt) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.hit = hit;
		this.createdAt = createdAt;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public String toString() {
		return "Board{" +
				"no=" + no +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", userId='" + userId + '\'' +
				", hit=" + hit +
				", createdAt='" + createdAt + '\'' +
				", fileInfo=" + fileInfo +
				'}';
	}
}
