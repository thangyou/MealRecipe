package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.model.BoardLike;
import doubleni.mealrecipe.model.DTO.BoardLikeRes;
import doubleni.mealrecipe.model.DTO.RecipeLikeRes;
import doubleni.mealrecipe.service.BoardService;
import doubleni.mealrecipe.service.LikeService;
import doubleni.mealrecipe.service.RecipeService;
import doubleni.mealrecipe.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.INVALID_USER_JWT;
import static doubleni.mealrecipe.config.exception.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RestController
@RequiredArgsConstructor
@Controller
@Api(tags = "Likes", description = "사용자 레시피 찜")
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final BoardService boardService;
    private final RecipeService recipeService;
    private final JwtService jwtService;

    @GetMapping("/users-board")
    @ApiOperation(value = "사용자가 저장한 모든 게시판 조회", notes = "Board 좋아요 레시피(리스트) 출력")
    public BaseResponse<List<BoardLikeRes>> getBoardLikeByUserId(@RequestParam("id") Long id) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            List<BoardLikeRes> likes = likeService.getBoardLike();
            return new BaseResponse<>(likes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/board/add")
    @ApiOperation(value = "Board 좋아요(저장)")
    public BaseResponse<String> addBoardLike(@RequestParam("id") Long id,
                                        @RequestParam("board") Long boardId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            likeService.addBoardLike(id, boardId);
            return new BaseResponse<>(boardId + "번 게시글 좋아요!");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @DeleteMapping("/board/delete")
    @ApiOperation(value = "Board 좋아요(저장) 삭제")
    public BaseResponse<String> deleteBoardLike(@RequestParam("id") Long id,
                                @RequestParam("board") Long boardId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            likeService.deleteBoardLike(id, boardId);
            return new BaseResponse<>(boardId + "번 게시글 좋아요 취소!");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // ================================================================================
    @GetMapping("/users-recipe")
    @ApiOperation(value = "사용자가 저장한 모든 레시피 조회", notes = "Recipe 좋아요 레시피(리스트) 출력")
    public BaseResponse<List<RecipeLikeRes>> getRecipeLikeByUserId(@RequestParam("id") Long id) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }

            List<RecipeLikeRes> likes = likeService.getRecipeLike();
            return new BaseResponse<>(likes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/recipe/add")
    @ApiOperation(value = "Recipe 좋아요(저장)")
    public BaseResponse<String> addRecipeLike(@RequestParam("id") Long id,
                                        @RequestParam("recipe") Long rcpId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            likeService.addRecipeLike(id, rcpId);
            return new BaseResponse<>(rcpId + "번 레시피 좋아요!");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @DeleteMapping("/recipe/delete")
    @ApiOperation(value = "Recipe 좋아요(저장) 삭제")
    public BaseResponse<String> deleteRecipeLike(@RequestParam("id") Long id,
                                              @RequestParam("recipe") Long rcpId) {
        try {
            Long idx = jwtService.getUserIdx();
            if (idx != id) {
                return new BaseResponse<>(INVALID_USER_JWT);
            } else if (id == 0) {
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            likeService.deleteRecipeLike(id, rcpId);
            return new BaseResponse<>(rcpId + "번 게시글 좋아요 취소!");
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
