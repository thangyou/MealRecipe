package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.MD5Generator;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.service.BoardService;
import doubleni.mealrecipe.service.FileService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@Controller
@Api(tags = "Board", description = "사용자 레시피 게시판")
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
    private final JwtService jwtService;
    private final FileService fileService;

    /* TEST */


    // ==============================================================================

    /* Page 이용 */
//    @GetMapping("/all-page")
//    @ApiOperation(value = "게시글 조회 API", notes = "모든 게시글(리스트) 조회")
//    public Page<Board> getAllBoards() {
//        PageRequest pageRequest = PageRequest.of(0,4);
//        return boardService.findAll(pageRequest);
//    }
//
//    @GetMapping("/list-page")
//    @ApiOperation(value = "게시글 조회 API", notes = "원하는 페이지 게시글 조회")
//    public Page<Board> getAllBoardsWithPageByQueryMethod(@RequestParam("page") Integer page,
//                                                         @RequestParam("size") Integer size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return boardService.findByTitle(pageRequest);
//    }


    /**
     * 모든 게시글(리스트) 조회 api
     * [GET] /board/list
     *
     * @return BaseResponse<List>
     */
    @GetMapping("/list")
    @ApiOperation(value="모든 게시글 조회", notes="모든 게시글(리스트) 조회")
    public ResponseEntity<List<BoardRes>> getBoards() {
        try {
            List<BoardRes> boards = boardService.getBoards();
            return ResponseEntity.ok(boards);
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
    @ApiOperation(value="게시글 조회", notes="boardId 게시글 조회")
    public ResponseEntity<BoardRes> getBoardByBoardId(@PathVariable long boardId) {
        try {
            Board board = boardService.getBoardById(boardId);
            return ResponseEntity.ok().body(new BoardRes(board));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
   }

   @GetMapping("/display") // 작성 중
   public BaseResponse<FileReq> getPost(@RequestParam long boardId) {
       Board board = boardService.getBoardById(boardId);
       BoardRes res = new BoardRes(board);
       Long filedId = board.getFileId();
       FileReq fileReq = fileService.getFile(filedId);

       // 파일 경로
       String filePath = fileReq.getFilePath();


       return new BaseResponse<>(fileReq);
   }

//    @GetMapping("/board/detail")
//    public ResponseEntity<BaseResponse<BoardDetailRes>> getBoardDetail(@RequestParam("boardId") Long boardId) {
//        try {
//            // 게시글 상세 정보 가져오기
//            Board board = boardService.getBoardById(boardId);
//
//            // 파일 정보 가져오기 (만약 fileId가 null이면 file은 null이 됨)
//            FileRes file = null;
//            if (board.getFileId() != null) {
//                file = fileService.getFile(board.getFileId());
//            }
//
//            return ResponseEntity.ok(new BaseResponse<>("게시글 상세 정보 조회 성공", boardDetailRes));
//        } catch (BaseException exception) {
//            return ResponseEntity.ok(new BaseResponse<>(exception.getStatus()));
//        }
//    }


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
    @GetMapping("/search-board-of-writer")
    @ApiOperation(value="작성자 검색", notes="작성자 검색")
    public ResponseEntity<List<BoardRes>> getBoardByUserId(@RequestParam("writer") String writer) {
        try {
            List<BoardRes> board_list = boardService.searchBoardByUserNickname(writer);
            return ResponseEntity.ok().body(board_list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/search-board-of-title")
    @ApiOperation(value = "제목 검색", notes = "제목 검색")
    public ResponseEntity<List<BoardRes>> searchBoardByTitle(@RequestParam("keyword") String keyword) {
        try {
            List<BoardRes> boardList  = boardService.searchBoardByTitle(keyword);
            return ResponseEntity.ok(boardList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search-board-of-content")
    @ApiOperation(value = "본문 검색", notes = "본문 검색")
    public ResponseEntity<List<BoardRes>> searchBoardByContent(@RequestParam("keyword") String keyword) {
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
    @ApiOperation(value = "게시글 등록", notes = "게시글 등록")
//    public BaseResponse<String> save(@RequestBody BoardReq req, @RequestParam Long id) {
//        try {
//            Long idx = jwtService.getUserIdx();
//            if (idx != id) {
//                return new BaseResponse<>(INVALID_USER_JWT);
//            } else if (id == 0) {
//                return new BaseResponse<>(USERS_EMPTY_USER_ID);
//            }
//            boardService.save(req, id);
//            return new BaseResponse<>("게시글 저장 완료 !");
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
    public BaseResponse<String> save(@RequestParam(value = "files", required = false) MultipartFile files,
                                 BoardReq boardDto, @RequestParam Long id) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            String savePath = System.getProperty("user.dir") + "\\files";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch(Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileReq fileDto = new FileReq();
            fileDto.setOrigFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDto);
            boardDto.setFileId(fileId);
            boardService.save(boardDto, id);
            return new BaseResponse<>("게시글 저장 완료 !");
        } catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        } catch (IOException | NoSuchAlgorithmException exception) {
            return new BaseResponse<>(exception.getMessage());
        }
    }

    /** 파일 다운로드 **/
//    @GetMapping("/file-download") // fail 왜 갑자기 이질ㄹ랄
//    @ApiOperation(value = "파일 다운로드", notes = "파일 다운로드")
//    public ResponseEntity<Resource> fileDownload(@RequestParam("fileId") Long fileId) throws IOException {
//        FileReq fileDto = fileService.getFile(fileId);
//        Path path = Paths.get(fileDto.getFilePath());
//        Resource resource = new InputStreamResource(Files.newInputStream(path));
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getOrigFilename() + "\"")
//                .body(resource);
//    }

    /**
     * 게시글 수정 api
     * [PATCH] /board/update
     *
     * @return BaseResponse<Board>
     */
    // http://localhost:8080/board/update?boardId={boardId}
    @PatchMapping("/update")
    @ApiOperation(value = "게시글 수정", notes = "게시글 수정")
    public BaseResponse<String> updateBoard(@RequestParam("boardId") Long boardId,
                                       BoardReq req, @RequestParam Long id,
                                       @RequestParam(value = "files", required = false) MultipartFile files) {
        try
        {
                Long idx = jwtService.getUserIdx();
                if (idx != id) {
                    return new BaseResponse<>(INVALID_USER_JWT);
                } else if (id == 0) {
                    return new BaseResponse<>(USERS_EMPTY_USER_ID);
                }
                System.out.println("게시글 수정 API : " + boardId);
                boardService.updateBoard(boardId, req);
                System.out.println("updateBoard() 완료");
                Board board = boardService.getBoardById(boardId);
                Long fileId = board.getFileId();
                if (files != null) {
                    System.out.println(">> 새로운 파일 저장");
                    System.out.println(fileId + "번 파일 수정하기");
                    System.out.println("--- update 전 ---");
                    System.out.println("getOrigFilename : " + fileService.getFile(fileId).getOrigFilename());
                    System.out.println("getFilePath : " + fileService.getFile(fileId).getFilePath());
                    System.out.println("getFilename : " + fileService.getFile(fileId).getFilename());

                    fileService.updateFile(fileId, files);

                }
                System.out.println(">> 수정할 파일 없죠");
                System.out.println("getFilename : " + fileService.getFile(fileId).getFilename());

                System.out.println("updateFile() 완료");

                return new BaseResponse<>(boardId + "번 게시글 수정 완료 !");
            } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
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
    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제")
    public BaseResponse<String> deleteBoard(@RequestParam("boardId") Long boardId, @RequestParam Long id) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            boardService.deleteBoard(boardId);
            return new BaseResponse<>(boardId + "번 게시글 삭제 완료 !");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }




}
