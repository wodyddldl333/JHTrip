package com.ssafy.jhtrip.board.service;

import java.util.List;

import com.ssafy.jhtrip.board.dto.FileInfo;
import com.ssafy.jhtrip.exception.BoardDetailException;
import com.ssafy.jhtrip.exception.BoardRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ssafy.jhtrip.board.dao.BoardDao;
import com.ssafy.jhtrip.board.dto.Board;
import com.ssafy.jhtrip.board.dto.Comments;
import com.ssafy.jhtrip.board.dto.SearchCondition;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	private final BoardDao boardDao;

	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public int delete(int no) {
		return boardDao.delete(no);
	}

	@Override
	public int update(Board board) {
		return boardDao.update(board);
	}

	@Override
	public List<Board> search(SearchCondition searchCondition) {
		return boardDao.search(searchCondition);
	}

	@Override
	public int insert(Board board) throws BoardRegistrationException {
		try {
			return boardDao.insert(board);
		} catch (Exception e) {
			log.error("Error occurred while regist board: {}", e.getMessage());
			throw new BoardRegistrationException(e.getMessage());
		}
	}

	@Transactional
	@Override
	public Board select(int no) {
		try {
			boardDao.updateHit(no);
			Board board = boardDao.select(no);
			FileInfo fileInfo = boardDao.getFileInfo(no);
			if (fileInfo != null) {
				board.setFileInfo(fileInfo);
			}
			return board;
		} catch (Exception e) {
			log.error("Error occurred while boardDetail: {}", e.getMessage());
			throw new BoardDetailException(e.getMessage());
		}

	}

	@Override
	public int commentWrite(Comments comments) {
		return boardDao.commentWrite(comments);
	}

	@Override
	public List<Comments> getComments(int no) {
		return boardDao.getComments(no);
	}

	@Override
	public int updateComment(Comments comments) {
		return boardDao.updateComment(comments);
	}

	@Override
	public int deleteComment(int no) {
		return boardDao.deleteComment(no);
	}

	@Override
	public int insertFileInfo(FileInfo fileInfo) {
		int boardNo = boardDao.getBoardNo();
		fileInfo.setBoardNo(boardNo);
		return boardDao.insertFile(fileInfo);
	}

	@Override
	public int getCount() {
		return boardDao.selectCount();
	}

}
