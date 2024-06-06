package com.ssafy.jhtrip.board.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Comments {
    private int id;
    @Min(value = 1, message = "boardNo는 필수 값입니다")
    private int boardNo;
    @NotBlank
    private String userId;
    @NotBlank
    private String comment;
    private String createdAt;
    public Comments() {}
    public Comments(int id, int boardNo, String userId, String comment, String createdAt) {
        this.id = id;
        this.boardNo = boardNo;
        this.userId = userId;
        this.comment = comment;
        this.createdAt = createdAt;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getBoardNo() {
        return boardNo;
    }
    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        return "Comments [id=" + id + ", boardNo=" + boardNo + ", userId=" + userId + ", comment=" + comment
                + ", createdAt=" + createdAt + "]";
    }
    
    
}