package com.ssafy.jhtrip.board.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.ssafy.jhtrip.board.dto.FileInfo;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.ssafy.jhtrip.board.dto.Board;
import com.ssafy.jhtrip.board.dto.Comments;
import com.ssafy.jhtrip.board.dto.SearchCondition;
import com.ssafy.jhtrip.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardRestController {

    private final BoardService boardService;

    private static final String UPLOADED_FOLDER = "upload/";

    public BoardRestController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 전체 리스트 업
    @GetMapping("/board")
    public ResponseEntity<?> boardList(@ModelAttribute SearchCondition condition) {
        log.info("boardList called with searchCondition - {}", condition);
        List<Board> boards = boardService.search(condition);
        if (boards != null && !boards.isEmpty()) {
            log.info("boardList found {} boards", boards.size());
            return ResponseEntity.ok(boards);
        } else {
            log.info("boardList found no boards");
            return ResponseEntity.noContent().build();
        }
    }

    // 게시글 상세보기
    @GetMapping("/board/{no}")
    public ResponseEntity<?> boardDetail(@PathVariable(value = "no") int no) {
        log.info("boardDetail called with no - {}", no);
        Board board = boardService.select(no);
        if (board != null) {
            log.info("boardDetail found board - {}", board);
            return ResponseEntity.ok(board);
        } else {
            log.info("boardDetail found no board with no - {}", no);
            return ResponseEntity.noContent().build();
        }
    }

//    // 게시글 작성
//    @PostMapping("/board")
//    public ResponseEntity<?> insert(@Valid @RequestBody Board board) {
//        log.info("insert called with board - {}", board);
//        int result = boardService.insert(board);
//        log.info("insert created {} records", result);
//        return ResponseEntity.ok(result);
//    }

    // 게시글 수정
    @PutMapping("/board")
    public ResponseEntity<?> updateBoard(@Valid @RequestBody Board board) {
        log.info("updateBoard called with board - {}", board);
        int result = boardService.update(board);
        log.info("updateBoard updated {} records", result);
        return ResponseEntity.ok(result);
    }

    // 게시글 삭제
    @DeleteMapping("/board/{no}")
    public ResponseEntity<?> deleteBoard(@PathVariable(value = "no") int no) {
        log.info("deleteBoard called with no - {}", no);
        int result = boardService.delete(no);
        log.info("deleteBoard deleted {} records", result);
        return ResponseEntity.ok(result);
    }

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<?> commentWrite(@Valid @RequestBody Comments comments) {
        log.info("commentWrite called with comments - {}", comments);
        int result = boardService.commentWrite(comments);
        log.info("commentWrite created {} records", result);
        return ResponseEntity.ok(result);
    }

    // 댓글 불러오기
    @GetMapping("/comment/{no}")
    public ResponseEntity<?> getComments(@PathVariable(value = "no") int no) {
        log.info("getComments called with no - {}", no);
        List<Comments> comments = boardService.getComments(no);
        if (comments != null && !comments.isEmpty()) {
            log.info("getComments found {} comments", comments.size());
            return ResponseEntity.ok(comments);
        } else {
            log.info("getComments found no comments with no - {}", no);
            return ResponseEntity.noContent().build();
        }
    }

    // 댓글 수정하기
    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody Comments comments) {
        log.info("updateComment called with comments - {}", comments);
        int result = boardService.updateComment(comments);
        log.info("updateComment updated {} records", result);
        return ResponseEntity.ok(result);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{no}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "no") int no) {
        log.info("deleteComment called with no - {}", no);
        int result = boardService.deleteComment(no);
        log.info("deleteComment deleted {} records", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/board")
    @Transactional
    public ResponseEntity<?> insertable(@Valid @RequestPart("board") Board board,
                                        @RequestPart(value = "file", required = false) MultipartFile file) {
        log.info("insert called with board - {}", board);
        if (file != null) {
            try {
                FileInfo fileInfo = new FileInfo();
                Path uploadPath = Paths.get(UPLOADED_FOLDER);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                byte[] bytes = file.getBytes();
                String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + filename);
                fileInfo.setOriginalFile(file.getOriginalFilename());
                fileInfo.setSaveFile(filename);
                boardService.insert(board);
                int result = boardService.insertFileInfo(fileInfo);
                Files.write(path, bytes);
                return ResponseEntity.ok(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            int result = boardService.insert(board);
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOADED_FOLDER).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        log.info("getCount called");
        int result = boardService.getCount();
        log.info("getCount called");
        return ResponseEntity.ok(result);
    }
}
