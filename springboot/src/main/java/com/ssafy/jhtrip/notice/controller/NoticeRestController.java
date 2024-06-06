package com.ssafy.jhtrip.notice.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.jhtrip.notice.SearchCondition;
import com.ssafy.jhtrip.notice.dto.Notice;
import com.ssafy.jhtrip.notice.service.NoticeService;


@RestController
@Slf4j
@RequestMapping("/notice")
public class NoticeRestController {

	private final NoticeService noticeService;

	public NoticeRestController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@GetMapping("/notice")
	public ResponseEntity<?> noticeList(@ModelAttribute SearchCondition condition) {
		log.info("noticeList called with SearchCondition - {}", condition);
		List<Notice> notices = noticeService.search(condition);
		if (notices != null && !notices.isEmpty()) {
			log.info("noticeList found {} notices", notices.size());
			return ResponseEntity.ok(notices);
		} else {
			log.warn("noticeList Not Found");
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/notice/{no}")
	public ResponseEntity<?> noticeDetail(@PathVariable(value = "no") int no) {
		log.info("noticeDetail called with no - {}", no);
		Notice notice = noticeService.select(no);
		if (notice != null) {
			log.info("Fount Notice : {}", notice);
			return ResponseEntity.ok(notice);
		} else {
			log.warn("Notice Not Found with no : {}", no);
			return ResponseEntity.noContent().build();
		}
	}

	@PostMapping("/notice")
	public ResponseEntity<?> writeNotice(@Valid @RequestBody Notice notice) {
		log.info("write notice : {}", notice);
		int result = noticeService.insert(notice);
		log.info("write notice {} records", notice);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/notice")
	public ResponseEntity<?> updateNotice(@Valid @RequestBody Notice notice) {
		log.info("update Notice - {}", notice);
		int result = noticeService.update(notice);
		log.info("update notice {} records ", result);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/notice/{no}")
	public ResponseEntity<?> deleteNotice(@PathVariable(value = "no") int no) {
		log.info("delete Notice with no : {}", no);
		int result = noticeService.delete(no);
		log.info("delete notice {} records ", result);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/count")
	public ResponseEntity<?> count() {
		log.info("count called ");
		int result = noticeService.count();
		log.info("count called ");
		return ResponseEntity.ok(result);
	}
}
