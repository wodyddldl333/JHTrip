package com.ssafy.jhtrip.board.dao;

import java.util.List;

import com.ssafy.jhtrip.board.dto.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jhtrip.board.dto.Board;
import com.ssafy.jhtrip.board.dto.Comments;
import com.ssafy.jhtrip.board.dto.SearchCondition;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoardDao {

	int delete(int no);
	int update(Board board);
	List<Board> search(SearchCondition searchCondition);
	int insert(Board board);
	Board select(int no);
	int commentWrite(Comments comments);
	List<Comments> getComments(int no);
	int updateComment(Comments comments);
	int deleteComment(int no);
	void updateHit(int no);

    int insertFile(FileInfo fileInfo);

	int getBoardNo();

	FileInfo getFileInfo(int no);

	@Select("select count(*) from board;")
	int selectCount();
}
