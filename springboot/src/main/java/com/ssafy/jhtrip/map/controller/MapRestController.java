package com.ssafy.jhtrip.map.controller;

import java.util.List;

import com.ssafy.jhtrip.map.dto.Sido;
import com.ssafy.jhtrip.map.dto.TripContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.jhtrip.map.dto.MapDto;
import com.ssafy.jhtrip.map.dto.SearchKeyword;
import com.ssafy.jhtrip.map.service.MapService;

@RestController
@Slf4j
@RequestMapping("/map")
public class MapRestController {

	private MapService mapService;

	public MapRestController(MapService mapService) {
		this.mapService = mapService;
	}

//	 검색 조건 객체를 생성하여 검색 조건 설정하기
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody SearchKeyword keyword) {
		System.out.println(keyword);
		List<MapDto> list = mapService.search(keyword);
		if (list.size() != 0) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/main")
	public ResponseEntity<?> mainTrip() {
		log.info("Main Trip");
		List<TripContent> contents = mapService.loadMain();
		return ResponseEntity.ok(contents);
	}

	// 실시간 인기 여행지
	@GetMapping("/mainpopular")
	public ResponseEntity<?> mainPop() {
		log.info("Main Trip");
		List<TripContent> contents = mapService.loadMainPop();
		return ResponseEntity.ok(contents);
	}

	@GetMapping("/sido")
	public ResponseEntity<?> getsido() {
		log.info("getSidoList");
		List<Sido> sidos = mapService.getSido();
		return ResponseEntity.ok(sidos);
	}

	@GetMapping("/getInfo/{id}")
	public ResponseEntity<?> getInfo(@PathVariable int id) {
		log.info("getInfo");
		MapDto map = mapService.getByContenetId(id);
		return ResponseEntity.ok(map);
	}
}
