package com.ssafy.jhtrip.notice.service;

import java.util.List;

import com.ssafy.jhtrip.notice.SearchCondition;
import com.ssafy.jhtrip.notice.dto.Notice;

public interface NoticeService {

	
	List<Notice> search(SearchCondition searchCondition);
	Notice select(int no);
	int insert(Notice notice);
	int update(Notice notice);
	int delete(int no);

    int count();
}
