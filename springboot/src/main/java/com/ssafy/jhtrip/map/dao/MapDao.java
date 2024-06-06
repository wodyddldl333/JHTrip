package com.ssafy.jhtrip.map.dao;

import java.util.List;
import java.util.Optional;

import com.ssafy.jhtrip.map.dto.MapDto;
import com.ssafy.jhtrip.map.dto.SearchKeyword;
import com.ssafy.jhtrip.map.dto.Sido;
import com.ssafy.jhtrip.map.dto.TripContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface MapDao {
    List<TripContent> loadMain();
    List<MapDto> search(SearchKeyword keyword);
    List<Sido> getSido();
    List<TripContent> loadPopular();

    void updateSearch(SearchKeyword searchKeyword);

    Optional<MapDto> selectbyContentId(@Param("contentId") int contentId);
}
