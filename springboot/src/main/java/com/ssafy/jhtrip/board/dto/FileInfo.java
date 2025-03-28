package com.ssafy.jhtrip.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

    private int boardNo;

    private String saveFolder;

    private String originalFile;

    private String saveFile;
}
