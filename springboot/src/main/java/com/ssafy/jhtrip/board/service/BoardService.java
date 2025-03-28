package com.ssafy.jhtrip.board.service;

import java.util.List;

import com.ssafy.jhtrip.board.dto.Board;
import com.ssafy.jhtrip.board.dto.Comments;
import com.ssafy.jhtrip.board.dto.FileInfo;
import com.ssafy.jhtrip.board.dto.SearchCondition;

public interface BoardService {

	int delete(int no);

	int update(Board board);

	List<Board> search(SearchCondition searchCondition);

	int insert(Board board);

	Board select(int no);

	int commentWrite(Comments comments);

	List<Comments> getComments(int no);

	int updateComment(Comments comments);

	int deleteComment(int no);

	int insertFileInfo(FileInfo fileInfo);

    int getCount();
}
