package com.ssafy.jhtrip.board.dao;

import java.util.List;

import com.ssafy.jhtrip.board.dto.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jhtrip.board.dto.Board;
import com.ssafy.jhtrip.board.dto.Comments;
import com.ssafy.jhtrip.board.dto.SearchCondition;

/**
 * 게시판 관련 DB 작업을 처리하는 DAO 인터페이스입니다.
 * MyBatis의 매퍼 인터페이스로 구현됩니다.
 *
 * @author wodyddldl333
 * @version v1
 * */
@Mapper
public interface BoardDao {

	/**
	 * 게시글 목록 조회
	 *
	 * @param searchCondition 검색조건 (limit, nextpage)
	 * @return 검색조건에 알맞은 게시글 목록 응답
	 * */
	List<Board> search(SearchCondition searchCondition);

	/**
	 * 게시글 상세조회
	 *
	 * @param no 게시글 ID
	 * @return 해당하는 게시글 Board
	 * */
	Board select(int no);

	/**
	 * 게시글 작성
	 *
	 * @param board 작성된 게시글
	 * */
	int insert(Board board);

	/**
	 * 게시글 삭제
	 *
	 * @param no 게시글 ID
	 * */
	int delete(int no);

	/**
	 * 게시글 수정
	 *
	 * @param board 수정하는 게시글 DTO
	 * */
	int update(Board board);

	/**
	 * 댓글 목록 조회
	 *
	 * @param no 게시글 ID
	 * @return 게시글 목록
	 * */
	List<Comments> getComments(int no);

	/**
	 * 댓글 작성
	 *
	 * @param comments 작성된 댓글
	 * */
	int commentWrite(Comments comments);

	/**
	 * 댓글 수정
	 *
	 * @param comments 수정된 댓글
	 * */
	int updateComment(Comments comments);

	/**
	 * 댓글 삭제
	 *
	 * @param no 게시글 ID
	 * */
	int deleteComment(int no);

	/**
	 * 조회수 증가
	 *
	 * @param no 게시글 ID
	 * */
	void updateHit(int no);

	/**
	 * 첨부파일 첨부
	 *
	 * @param fileInfo 첨부파일 정보
	 * */
    int insertFile(FileInfo fileInfo);

	/**
	 * 게시글 ID 가져오기
	 * */
	int getBoardNo();

	/**
	 * 첨부파일 정보 조회
	 *
	 * @param no 게시글 ID*/
	FileInfo getFileInfo(int no);

	/**
	 * 게시글 개수 조회
	 */
	int selectCount();
}
