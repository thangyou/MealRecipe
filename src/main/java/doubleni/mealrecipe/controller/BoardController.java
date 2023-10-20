package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.AddBoardReq;
import doubleni.mealrecipe.model.DTO.BoardReq;
import doubleni.mealrecipe.model.DTO.BoardRes;
import doubleni.mealrecipe.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 모든 게시글(리스트) 조회 api
     * [GET] /board/List
     *
     * @return BaseResponse<List>
     */
    /* 조회 */
    @GetMapping("/list")
    @ApiOperation(value="게시글 조회 API", notes="모든 게시글(리스트) 조회")
    public ResponseEntity<List<BoardRes>> getBoards() {
        try {
            List<BoardRes> positions = boardService.getBoards();
            return ResponseEntity.ok(positions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * boardId로 게시글 조회 api
     * [GET] /board/boardId={boardId}
     *
     * @return BaseResponse<Board> or <BoardRes>
     */
    @GetMapping("/boardId={boardId}")
    @ApiOperation(value="게시글 조회 API", notes="boardId 게시글 조회")
    public ResponseEntity<?> getBoardByBoardId(@PathVariable long boardId) {
        try {
            Board board = boardService.getBoardById(boardId);
            return ResponseEntity.ok().body(new BoardRes(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
   }

    // ==================================================================================

    /**
     * 게시글 정렬 api
     * [GET] /board/
     *
     * @return BaseResponse<List>
     */
    /* 정렬 */
    // 조회수(hits), 좋아요 수(likeCnt), 댓글 수(commentCnt)



    // ==================================================================================

    /**
     * 키워드(in 작성자) 리스트 검색 api
     * 키워드(in 제목) 리스트 검색 api
     * 키워드(in 본문) 리스트 검색 api
     * [GET] /board/search-board-of?keyword={keyword}
     *
     *  @return BaseResponse<List>
     */
    /* 검색 */

    // http://localhost:8080/board/search-board-of-?keyword={keyword}
    // 검색 조건 : 작성자, 제목, 본문
//    @GetMapping("/search/writer={writer}")
//    @ApiOperation(value="게시글 검색 API", notes="작성자 검색")
//    public ResponseEntity<?> getBoardByUserId(@PathVariable String writer) {
//        try {
//            List<Board> board_list = boardService.searchBoardByEmailOrNickname(writer);
//            return ResponseEntity.ok().body(board_list);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
    @GetMapping("/search-board-of-title") // failed
    @ApiOperation(value = "게시글 검색 API", notes = "제목 검색")
    public ResponseEntity<?> searchBoardByTitle(@RequestParam("keyword") String keyword) {
        try {
            List<BoardRes> boardList  = boardService.searchBoardByTitle(keyword);
            return ResponseEntity.ok(boardList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search-board-of-content") // failed
    @ApiOperation(value = "게시글 검색 API", notes = "본문 검색")
    public ResponseEntity<?> searchBoardByContent(@RequestParam("keyword") String keyword) {
        try {
            List<BoardRes> boardList  = boardService.searchBoardByContent(keyword);
            return ResponseEntity.ok(boardList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // ==================================================================================

    /**
     * 게시글 추가 api
     * [POST] /board/add
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/add")
    @ApiOperation(value = "게시글 등록 API", notes = "게시글 등록")
    public ResponseEntity<String> save(@RequestBody AddBoardReq req) {
        try {
            boardService.save(req);
            return ResponseEntity.ok("게시글 저장 완료 !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 게시글 수정 api
     * [PATCH] /board/update
     *
     * @return BaseResponse<Board>
     */
    // http://localhost:8080/board/update?boardId={boardId}
    @PatchMapping("/update")
    @ApiOperation(value = "게시글 수정 API", notes = "게시글 수정")
    public ResponseEntity<Board> updateBoard(@RequestParam("boardId") Long boardId, @RequestBody BoardReq req) {
        try {
                boardService.updateBoard(boardId, req);
                System.out.println(boardId + "번 게시글 수정 완료");
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
    }

    /**
     * 게시글 삭제 api
     * [DELETE] /board/delete
     *
     * @return BaseResponse<String>
     */
    // http://localhost:8080/board/delete?boardId={boardId}
    @DeleteMapping("/delete")
    @ApiOperation(value = "게시글 삭제 API", notes = "게시글 삭제")
    public ResponseEntity<?> deleteBoard(@RequestParam("boardId") Long boardId) {
        try {
            boardService.deleteBoard(boardId);
            return ResponseEntity.ok(boardId + "번 게시글 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }




}
