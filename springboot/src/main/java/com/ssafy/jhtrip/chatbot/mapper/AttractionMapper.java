package com.ssafy.jhtrip.chatbot.mapper;

import com.ssafy.jhtrip.chatbot.dto.AttractionList;
import com.ssafy.jhtrip.chatbot.dto.Qustionkeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AttractionMapper {

    List<AttractionList> getAttractionListBySido(Qustionkeyword qustionkeyword);

    Optional<String> findBycode(@Param("sidoCode") int sidoCode);
}
