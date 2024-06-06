package com.ssafy.jhtrip.notice.service;

import java.util.List;

import com.ssafy.jhtrip.exception.NoticeDetailException;
import com.ssafy.jhtrip.exception.NoticeRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ssafy.jhtrip.notice.SearchCondition;
import com.ssafy.jhtrip.notice.dao.NoticeDao;
import com.ssafy.jhtrip.notice.dto.Notice;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService{
	
	private final NoticeDao noticeDao;

	public NoticeServiceImpl(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public List<Notice> search(SearchCondition searchCondition) {
		return noticeDao.search(searchCondition);
	}

	@Override
	@Transactional
	public Notice select(int no) {
		try {
			noticeDao.updateHit(no);
			return noticeDao.select(no)
					.orElseThrow(() -> new NoticeDetailException("notice no {} Not Fount"));
		} catch (Exception e) {
			log.error("Error occurred while Detail Notice");
			throw new NoticeDetailException(e.getMessage());
		}

	}
	
	@Override
	public int insert(Notice notice) {
		try {
			return noticeDao.insert(notice);
		} catch (Exception e) {
			log.error("Error occurred while register notice: {}", e.getMessage());
			throw new NoticeRegistrationException(e.getMessage());
		}
	}
	@Override
	public int update(Notice notice) {
		return noticeDao.update(notice);
	}

	@Override
	public int delete(int no) {
		return noticeDao.delete(no);
	}

	@Override
	public int count() {
		return noticeDao.selectCount();
	}

}
