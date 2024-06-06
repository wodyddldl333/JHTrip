package com.ssafy.jhtrip.notice.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jhtrip.notice.SearchCondition;
import com.ssafy.jhtrip.notice.dto.Notice;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoticeDao {

	List<Notice> search(SearchCondition searchCondition);
	Optional<Notice> select(int no);

	void updateHit(int no);

	int insert(Notice notice);

	int update(Notice notice);

	int delete(int no);

	@Select("select count(*) from notice;")
	int selectCount();
}
