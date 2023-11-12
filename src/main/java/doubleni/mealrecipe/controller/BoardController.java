package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.MD5Generator;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Comment;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.service.BoardService;
import doubleni.mealrecipe.service.CommentService;
import doubleni.mealrecipe.service.FileService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@Controller
@Api(tags = "Board", description = "사용자 커뮤니티 게시판")
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

    /**
     * 모든 게시글(리스트) 조회 api
     * [GET] /board/list
     *
     * @return BaseResponse<List>
     */
    @GetMapping("/list")
    @ApiOperation(value="모든 게시글 조회", notes="모든 게시글(리스트)을 조회한다.")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<List<BoardRes>> getBoards() {
        try {
            List<BoardRes> boards = boardService.getBoards();
            return new BaseResponse<>(boards);
        } catch (Exception e) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }

    /**
     * 나의 게시글 조회 api
     * [GET] /board/myboard?userId={userId}
     *
     * @return BaseResponse<List>
     */
    @GetMapping("/myboard")
    @ApiOperation(value="나의 게시글 조회", notes="내가 작성한 게시글(리스트)을 조회한다.")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<List<BoardRes>> getMyBoards() {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            List<BoardRes> myBoards = boardService.getMyBoards(idx);
            return new BaseResponse<>(myBoards);
        } catch (Exception e) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }

    /**
     * boardId로 게시글 조회 api
     * [GET] /board/boardId={boardId}
     *
     * @return BaseResponse<Board> or <BoardRes>
     */
    @GetMapping("/boardId={boardId}")
    @ApiOperation(value="게시글 조회", notes="boardId로 게시글을 조회한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =2000,message = "입력값을 확인해주세요."),
            @ApiResponse(code =2063,message = "존재 하지 않거나 삭제된 게시글 입니다."),
            @ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
   public BaseResponse<BoardRes> getBoardByBoardId(@PathVariable Long boardId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            Board board = boardService.getBoardByBoardId(boardId, idx);

            if (board == null) {
                return new BaseResponse<>(BOARD_NOT_EXISTS);
            }
            BoardRes boardRes = new BoardRes(board);
            return new BaseResponse<>(boardRes);
        } catch (Exception exception) {
            return new BaseResponse<>(RESPONSE_ERROR);
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
    @GetMapping("/list-order-by-hits")
    @ApiOperation(value="게시판 조건 정렬 API", notes="조회수 높은 순 게시판 정렬")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<List<BoardRes>> getBoardByOrderByHitsDesc() {
        try {
            List<BoardRes> getBoardRes  = boardService.getBoardByOrderByHitsDesc();
            return new BaseResponse<>(getBoardRes);
        } catch (Exception e) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }

    @GetMapping("/list-order-by-like")
    @ApiOperation(value="게시판 조건 정렬 API", notes="좋아요 높은 순 게시판 정렬")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<List<BoardRes>> getBoardByOrderByLikeCntDesc() {
        try {
            List<BoardRes> getBoardRes  = boardService.getBoardByOrderByLikeCntDesc();
            return new BaseResponse<>(getBoardRes);
        } catch (Exception e) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }

    @GetMapping("/list-order-by-comment")
    @ApiOperation(value="게시판 조건 정렬 API", notes="댓글 수 많은 순 게시판 정렬")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<List<BoardRes>> getBoardByOrderByCommentCntDesc() {
        try {
            List<BoardRes> getBoardRes  = boardService.getBoardByOrderByCommentCntDesc();
            return new BaseResponse<>(getBoardRes);
        } catch (Exception e) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }


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
    @GetMapping("/search-board-of-writer")
    @ApiOperation(value="작성자 검색", notes="작성자로 게시글을 검색한다.")
    public ResponseEntity<List<BoardRes>> getBoardByUserId(@RequestParam("writer") String writer) {
        try {
            List<BoardRes> board_list = boardService.searchBoardByUserNickname(writer);
            return ResponseEntity.ok().body(board_list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/search-board-of-title")
    @ApiOperation(value = "제목 검색", notes = "제목으로 게시글을 검색한다.")
    public ResponseEntity<List<BoardRes>> searchBoardByTitle(@RequestParam("keyword") String keyword) {
        try {
            List<BoardRes> boardList  = boardService.searchBoardByTitle(keyword);
            return ResponseEntity.ok(boardList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search-board-of-content")
    @ApiOperation(value = "본문 검색", notes = "본문으로 게시글을 검색한다.")
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
     * @return BaseResponse<BoardRes>
     */
    @PostMapping("/add")
    @ApiOperation(value = "게시글 등록", notes = "게시글을 등록한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다. \n Body에서 files, title, content 입력하면 됨")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code=2060,message = "제목을 입력해주세요."),
            @ApiResponse(code=2061,message = "내용을 입력해주세요."),
            @ApiResponse(code=2064,message = "게시글 등록을 실패하였습니다.")
    })
    public BaseResponse<BoardRes> add(BoardReq req, @RequestPart(value = "files", required = false) MultipartFile file)
    {
        try {
            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            BoardRes boardRes = boardService.add(req, file, idx);
            return new BaseResponse<>(boardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 수정 api
     * [PATCH] /board/update
     *
     * @return BaseResponse<BoardRes>
     */
    @PatchMapping("/update") // 이것도 BoardRes 출력하도록 수정하기
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다.")})
    public BaseResponse<BoardRes> updateBoard(@RequestParam("boardId") Long boardId, BoardReq req,
                                              @RequestPart(value = "files", required = false) MultipartFile files) {
        try {
                Long idx = jwtService.getUserIdx();
                if (idx == 0) {
                    return new BaseResponse<>(USERS_EMPTY_USER_ID);
                }
                System.out.println("게시글 수정 API : " + boardId);
                BoardRes boardRes = boardService.updateBoard(boardId, req);
                System.out.println("updateBoard() 완료");

                if (files != null) {
                    Board board = boardService.getBoardByBoardId(boardId, idx);
                    System.out.println(">> 새로운 파일 저장");
                    Long fileId = board.getFileId();
                    if (fileId != 0) {
                        System.out.println(fileId + "번 파일 수정하기");
                        System.out.println("--- update 전 ---");
                        System.out.println("getOrigFilename : " + fileService.getFile(fileId).getOrigFilename());
                        System.out.println("getFilePath : " + fileService.getFile(fileId).getFilePath());
                        System.out.println("getFilename : " + fileService.getFile(fileId).getFilename());
                    } else {
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

                        fileId = fileService.saveFile(fileDto);
                        boardRes.setFileId(fileId);
                    }
                    fileService.updateFile(fileId, files);
                    System.out.println("updateFile() 완료");
                } else {
                    System.out.println(">> 수정할 파일 없죠");
                }
                return new BaseResponse<>(boardRes);
            } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 게시글 삭제 api
     * [DELETE] /board/delete
     *
     * @return BaseResponse<String>
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    public BaseResponse<String> deleteBoard(@RequestParam("boardId") Long boardId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            boardService.deleteBoard(boardId);
            return new BaseResponse<>(boardId + "번 게시글 삭제 완료 !");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // =================================================================================================================


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

    //   @GetMapping("/display") // 작성 중
//   public BaseResponse<FileReq> getPost(@RequestParam long boardId) {
//       Board board = boardService.getBoardById(boardId);
//       BoardRes res = new BoardRes(board);
//       Long filedId = board.getFileId();
//       FileReq fileReq = fileService.getFile(filedId);
//
//       // 파일 경로
//       String filePath = fileReq.getFilePath();
//
//
//       return new BaseResponse<>(fileReq);
//   }

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

//    public BaseResponse<String> save(@RequestParam(value = "files", required = false) MultipartFile files,
//                                 BoardReq boardDto, @RequestParam Long id) {
//        try {
//            Long idx = jwtService.getUserIdx();
//            if (idx != id) {
//                return new BaseResponse<>(INVALID_USER_JWT);
//            } else if (id == 0) {
//                return new BaseResponse<>(USERS_EMPTY_USER_ID);
//            }
//
//            System.out.println(files);
//            System.out.println(boardDto.getTitle());
//            System.out.println(boardDto.getContent());
//
//            if (files != null) {
//                String origFilename = files.getOriginalFilename();
//                String filename = new MD5Generator(origFilename).toString();
//                String savePath = System.getProperty("user.dir") + "\\files";
//                if (!new File(savePath).exists()) {
//                    try {
//                        new File(savePath).mkdir();
//                    } catch(Exception e){
//                        e.getStackTrace();
//                    }
//                }
//                String filePath = savePath + "\\" + filename;
//                files.transferTo(new File(filePath));
//
//                FileReq fileDto = new FileReq();
//                fileDto.setOrigFilename(origFilename);
//                fileDto.setFilename(filename);
//                fileDto.setFilePath(filePath);
//
//                Long fileId = fileService.saveFile(fileDto);
//                boardDto.setFileId(fileId);
//            } else {
//                boardDto.setFileId(0L);
//            }
//
//            if (boardDto.getTitle() == null) {
//                return new BaseResponse<>(POST_BOARD_EMPTY_TITLE);
//            }
//
//            if (boardDto.getContent() == null) {
//                return new BaseResponse<>(POST_BOARD_EMPTY_CONTENT);
//            }
//
//            boardService.save(boardDto, id);
//            return new BaseResponse<>("게시글 저장 완료 !");
//        } catch(BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        } catch (IOException | NoSuchAlgorithmException exception) {
//            return new BaseResponse<>(exception.getMessage());
//        }
//    }

}
