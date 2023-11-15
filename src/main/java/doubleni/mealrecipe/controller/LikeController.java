package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.BoardLike;
import doubleni.mealrecipe.model.DTO.BoardLikeRes;
import doubleni.mealrecipe.model.DTO.RecipeLikeRes;
import doubleni.mealrecipe.model.RecipeLike;
import doubleni.mealrecipe.service.BoardService;
import doubleni.mealrecipe.service.LikeService;
import doubleni.mealrecipe.service.RecipeService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@Controller
@Api(tags = "Likes", description = "사용자 레시피 좋아요(저장)")
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final JwtService jwtService;

    @GetMapping("/all-board")
    @ApiOperation(value = "모든 게시판 좋아요 조회", notes = "Board 좋아요 레시피(리스트) 출력")
    @ApiResponses(value={@ApiResponse(code =3000, message = "값을 불러오는데 실패하였습니다."),
    @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요.")})
    public BaseResponse<List<BoardLikeRes>> getAllBoardLike() {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            List<BoardLikeRes> likes = likeService.getAllBoardLike();
            return new BaseResponse<>(likes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/users-board")
    @ApiOperation(value = "사용자가 저장한 모든 게시판 조회", notes = "Board 좋아요 레시피(리스트) 출력")
    @ApiResponses(value={@ApiResponse(code =3000, message = "값을 불러오는데 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 2011, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2080, message = "좋아요가 존재 하지 않읍니다.")})
    public BaseResponse<List<BoardLikeRes>> getBoardLikeByUserId() throws BaseException {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            List<BoardLikeRes> myBoardLikes = likeService.getBoardLike(idx);
            return new BaseResponse<>(myBoardLikes);
        } catch (Exception e) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }

    @PostMapping("/board/add")
    @ApiOperation(value = "Board 좋아요(저장)", notes = "사용자가 Board를 좋아요(저장)한다.")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 4050, message = "좋아요 추가 실패"),
            @ApiResponse(code = 2081, message = "이미 좋아요한 게시글입니다.")})
    public BaseResponse<BoardLikeRes> addBoardLike(@RequestParam("boardId") Long boardId) {
        try {
            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            BoardLikeRes boardLikeRes = likeService.addBoardLike(idx, boardId);
            return new BaseResponse<>(boardLikeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

//    public BaseResponse<String> addBoardLike(@RequestParam("id") Long id,
//                                        @RequestParam("board") Long boardId) {
//        try {
//            Long idx = jwtService.getUserIdx();
//            if (idx != id) {
//                return new BaseResponse<>(INVALID_USER_JWT);
//            } else if (id == 0) {
//                return new BaseResponse<>(USERS_EMPTY_USER_ID);
//            }
//
//            likeService.addBoardLike(id, boardId);
//            return new BaseResponse<>(boardId + "번 게시글 좋아요!");
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

    @DeleteMapping("/board/delete")
    @ApiOperation(value = "Board 좋아요(저장) 삭제")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 2080, message = "좋아요가 존재 하지 않읍니다."),
            @ApiResponse(code = 4051, message = "좋아요 삭제 실패")})
    public BaseResponse<String> deleteBoardLike(@RequestParam("boardId") Long boardId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            likeService.deleteBoardLike(boardId, idx);
            return new BaseResponse<>(boardId + "번 게시글 좋아요 취소!");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/check-board-like")
    @ApiOperation(value = "사용자 Board 좋아요 확인", notes = "게시글 좋아요 여부 반환")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 2080, message = "좋아요가 존재 하지 않읍니다.")})
    public BaseResponse<Long> checkBoardLike(@RequestParam("boardId") Long boardId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            Long checkLike = likeService.checkBoardLike(idx, boardId);
            return new BaseResponse<>(checkLike);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // ================================================================================
    @GetMapping("/all-recipe")
    @ApiOperation(value = "모든 레시피 좋아요 조회", notes = "사용자가 저장한 모든 레시피(리스트) 출력")
    @ApiResponses(value={@ApiResponse(code =3000, message = "값을 불러오는데 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요.")})
    public BaseResponse<List<RecipeLikeRes>> getAllRecipeLike() {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            List<RecipeLikeRes> likes = likeService.getAllRecipeLike();
            return new BaseResponse<>(likes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @GetMapping("/users-recipe")
    @ApiOperation(value = "사용자가 저장한 모든 레시피 조회", notes = "Recipe 좋아요 레시피(리스트) 출력")
    @ApiResponses(value={@ApiResponse(code =3000, message = "값을 불러오는데 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요.")})
    public BaseResponse<List<RecipeLikeRes>> getRecipeLikeByUserId() {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            List<RecipeLikeRes> myRecipeLikes = likeService.getRecipeLike(idx);
            return new BaseResponse<>(myRecipeLikes);
        } catch (Exception e) {
            return new BaseResponse<>(RESPONSE_ERROR);
        }
    }

    @PostMapping("/recipe/add")
    @ApiOperation(value = "Recipe 좋아요(저장)")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 4050, message = "좋아요 추가 실패"),
            @ApiResponse(code = 2082, message = "이미 좋아요한 레시피입니다.")})
    public BaseResponse<RecipeLikeRes> addRecipeLike(@RequestParam("rcpId") Long rcpId) {
        try {
            Long idx = jwtService.getUserIdx();

            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            RecipeLikeRes recipeLikeRes = likeService.addRecipeLike(idx, rcpId);
            return new BaseResponse<>(recipeLikeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @DeleteMapping("/recipe/delete")
    @ApiOperation(value = "Recipe 좋아요(저장) 삭제")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 2080, message = "좋아요가 존재 하지 않읍니다."),
            @ApiResponse(code = 4051, message = "좋아요 삭제 실패")})
    public BaseResponse<String> deleteRecipeLike(@RequestParam("rcpId") Long rcpId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            likeService.deleteRecipeLike(idx, rcpId);
            return new BaseResponse<>(rcpId + "번 레시피 좋아요 취소!");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // rcpid값 넘겨서 좋아요 눌렀는지 여부 반환하는 api를 하나더 만드는거
    @GetMapping("/check-recipe-like")
    @ApiOperation(value = "사용자 Recipe 좋아요 확인", notes = "레시피 좋아요 여부 반환")
    @ApiResponses(value={@ApiResponse(code =4000,message = "데이터베이스 연결에 실패하였습니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 2080, message = "좋아요가 존재 하지 않읍니다.")})
    public BaseResponse<Long> checkRecipeLike(@RequestParam("rcpId") Long rcpId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            Long checkLike = likeService.checkRecipeLike(idx, rcpId);
            return new BaseResponse<>(checkLike);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
