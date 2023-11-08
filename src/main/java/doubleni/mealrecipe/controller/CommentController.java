package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Comment;
import doubleni.mealrecipe.model.DTO.BoardRes;
import doubleni.mealrecipe.model.DTO.CommentReq;
import doubleni.mealrecipe.model.DTO.CommentRes;
import doubleni.mealrecipe.service.BoardService;
import doubleni.mealrecipe.service.CommentService;
import doubleni.mealrecipe.service.FileService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@Controller
@Api(tags = "Comment", description = "댓글")
@RequestMapping("/comment")
public class CommentController {

    private final JwtService jwtService;
    private final CommentService commentService;

    @ApiOperation(value="게시판 댓글 추가", notes="게시글의 댓글을 추가한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다."),
            @ApiResponse(code = 4040, message = "댓글 등록 실패")})
    @PostMapping("/add")
    public BaseResponse<String> addComments(@RequestParam("boardId") Long boardId, @RequestParam("id") Long id,
                                            CommentReq req) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            commentService.addComment(boardId, req, id);
            return new BaseResponse<>("댓글 작성 완료 !");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PatchMapping("/update")
    @ApiOperation(value = "게시판 댓글 수정", notes = "댓글을 수정한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다."),
            @ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 4041, message = "댓글 수정 실패")
    })
    public BaseResponse<String> updateComment(@RequestParam("commentId") Long commentId, @RequestParam("id") Long id,
                                            CommentReq req) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            Long boardId = commentService.updateComment(commentId, req.getBody(), id);
            return new BaseResponse<>(boardId + "번 게시글의 댓글 수정 완료 !");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "게시판 댓글 삭제", notes = "댓글을 삭제한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다."),
            @ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 4042, message = "댓글 삭제 실패")
    })
    public BaseResponse<String> deleteComment(@RequestParam("commentId") Long commentId, @RequestParam("id") Long id) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            Long boardId = commentService.deleteComment(commentId, id);
            return new BaseResponse<>(boardId + "번 게시글의 댓글 삭제 완료 !");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/boardId={boardId}")
    @ApiOperation(value="댓글 조회", notes="boardId로 게시글의 댓글을 조회한다. \n headers = {\"X-ACCESS-TOKEN\": jwt}; 설정해주기(jwt는 로그인하면 반환되는 jwt이다.")
    @ApiResponses(value={@ApiResponse(code =2000,message = "입력값을 확인해주세요."),
            @ApiResponse(code =2063,message = "존재 하지 않거나 삭제된 게시글 입니다."),
            @ApiResponse(code =3000,message = "값을 불러오는데 실패하였습니다.")})
    public BaseResponse<List<CommentRes>> getCommentByBoardId(@PathVariable Long boardId) {
        if (boardId == null) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            List<CommentRes> commentRes = commentService.getComments(boardId);
            return new BaseResponse<>(commentRes);
        } catch (Exception exception) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }



}
