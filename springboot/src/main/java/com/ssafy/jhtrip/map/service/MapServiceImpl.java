package com.ssafy.jhtrip.map.service;

import java.util.List;

import com.ssafy.jhtrip.map.dto.MapDto;
import com.ssafy.jhtrip.map.dto.SearchKeyword;
import com.ssafy.jhtrip.map.dto.Sido;
import com.ssafy.jhtrip.map.dto.TripContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ssafy.jhtrip.map.dao.MapDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MapServiceImpl implements MapService {

	private MapDao mapDao;

	public MapServiceImpl(MapDao mapDao) {
		this.mapDao = mapDao;
	}
	@Override
	public List<TripContent> loadMain() {
		return mapDao.loadMain();
	}

	@Override
	@Transactional
	public List<MapDto> search(SearchKeyword keyword) {
		try {
			mapDao.updateSearch(keyword);
			return mapDao.search(keyword);
		} catch (Exception e) {
			log.error("Error occured while Searching : {}", e);
			// TODO : 예외 정의하기
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Sido> getSido() {
		return mapDao.getSido();
	}

	@Override
	public List<TripContent> loadMainPop() {
		return mapDao.loadPopular();
	}

	@Override
	public MapDto getByContenetId(int contenetId) {
		return mapDao.selectbyContentId(contenetId)
				.orElseThrow(() -> new RuntimeException("Error occured while getByContenetId"));
	}
}
