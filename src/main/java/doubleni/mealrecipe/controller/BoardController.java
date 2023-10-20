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
    /**
     * 조회 - get
     * 검색 - search(Controller & Service) / find(Repository)
     * 생성 - save
     * 수정 - update
     * 삭제 - delete
     */

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

    /* 조회 */
    @GetMapping("/list")
    @ApiOperation(value="게시글 조회 API", notes="게시글 리스트 조회")
    public ResponseEntity<List<GetBoardRes>> getBoards() {
        try {
            List<GetBoardRes> positions = boardService.getBoards();
            return ResponseEntity.ok(positions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/boardId={boardId}")
    @ApiOperation(value="게시글 조회 API", notes="Board ID 게시글 조회")
    public ResponseEntity<GetBoardRes> getBoardByBoardId(@PathVariable long boardId) {
        Board board = boardService.findByBoardId(boardId);
        return ResponseEntity.ok().body(new GetBoardRes(board));
    }

    @GetMapping("/userEmail={userEmail}")
    @ApiOperation(value="게시글 조회 API", notes="User ID 게시글 조회")
    public ResponseEntity<GetBoardRes> getBoardByUserId(@PathVariable String userEmail) {
        Board board = boardService.findByUserEmail(userEmail);
        return ResponseEntity.ok().body(new GetBoardRes(board));
    }

    /* 검색 */
    @GetMapping("/search")
    @ApiOperation(value = "게시글 검색 API", notes = "키워드로 게시판 검색")
    public ResponseEntity<?> searchBoardByKeyword(@RequestParam("keyword") String keyword) {
        try {
            List<GetBoardRes> boardList  = boardService.searchBoardByKeyword(keyword);
            return ResponseEntity.ok(boardList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
