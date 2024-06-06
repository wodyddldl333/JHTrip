package com.ssafy.jhtrip.map.service;

import java.util.List;

import com.ssafy.jhtrip.map.dto.MapDto;
import com.ssafy.jhtrip.map.dto.SearchKeyword;
import com.ssafy.jhtrip.map.dto.Sido;
import com.ssafy.jhtrip.map.dto.TripContent;

public interface MapService {

    List<TripContent> loadMain();

    List<MapDto> search(SearchKeyword keyword);

    List<Sido> getSido();

    List<TripContent> loadMainPop();

    MapDto getByContenetId(int contenetId);

}
