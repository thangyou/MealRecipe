package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.AddBoardReq;
import doubleni.mealrecipe.model.DTO.BoardReq;
import doubleni.mealrecipe.model.DTO.GetBoardRes;
import doubleni.mealrecipe.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/add")
    @ApiOperation(value = "게시글 등록 API", notes = "게시글 등록")
    public ResponseEntity<String> save(@RequestBody AddBoardReq req) {
        try {
            boardService.save(req);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // http://localhost:8080/board/update?boardId={boardId}
    @PatchMapping("/update")
    @ApiOperation(value = "게시글 수정 API", notes = "게시글 수정")
    public ResponseEntity<Board> updateBoard(@RequestParam("boardId") Long boardId, @RequestBody BoardReq req) {
        try {
                boardService.updateBoard(boardId, req);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
    }

    // http://localhost:8080/board/delete?boardId={boardId}
    @DeleteMapping("/delete")
    @ApiOperation(value = "게시글 삭제 API", notes = "게시글 삭제")
    public ResponseEntity<?> deleteBoard(@RequestParam("boardId") Long boardId) {
        try {
            boardService.deleteBoard(boardId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @ApiOperation(value="게시글 조회 API", notes="게시글 리스트 조회")
    public ResponseEntity<List<GetBoardRes>> getBoards() {
        List<GetBoardRes> positions = new ArrayList<>();
        try {
            positions = boardService.getBoards();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{boardId}")
    @ApiOperation(value="게시글 조회 API", notes="게시글 조회")
    public ResponseEntity<GetBoardRes> getBoard(@PathVariable long boardId) {
        Board board = boardService.findById(boardId);
        return ResponseEntity.ok().body(new GetBoardRes(board));
    }


//     http://localhost:8080/board/search?keyword={keyword}
//    @GetMapping("/search")
//    @ApiOperation(value = "게시글 검색 API", notes = "키워드로 게시판 검색")
//    public ResponseEntity<List<Board>> searchBoards(@RequestParam("keyword") String keyword) {
//        List<Board> boardList = new ArrayList<>();
//
//        try {
//            boardList = boardService.getBoardsSearchBy(keyword);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(boardList);
//    }


}
