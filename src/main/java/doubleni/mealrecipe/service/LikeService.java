package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.*;
import doubleni.mealrecipe.model.DTO.BoardLikeRes;
import doubleni.mealrecipe.model.DTO.RecipeLikeRes;
import doubleni.mealrecipe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    public List<BoardLikeRes> getAllBoardLike() throws BaseException {
       List<BoardLikeRes> boardLikes = boardLikeRepository.findAll()
                .stream()
                .map(BoardLikeRes::new)
                .toList();

        if (boardLikes.isEmpty()) {
            throw new IllegalStateException();
        }
        return boardLikes;
    }

    public List<BoardLikeRes> getBoardLike(Long idx) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);
            if (optUser.isPresent()) {
                User user = optUser.get();
                return boardLikeRepository.findBoardLikesByUser(user)
                        .stream().map(BoardLikeRes::new).toList();
            } else {
                throw new BaseException(USERS_NOT_EXISTS);
            }
        } catch (Exception e) {
            throw new BaseException(LIKE_NOT_EXISTS);
        }
    }

    @Transactional
    public BoardLikeRes addBoardLike(Long idx, Long boardId) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);

            if (optUser.isPresent()) {
                User user = optUser.get();
                Board board = boardRepository.findByBoardId(boardId).get();
                User boardWriter = board.getUser();

                // 게시글 1개당 좋아요 1번만 가능하도록 추가
                // 이미 좋아요 누른 게시글인지 확인하기
                BoardLike checkBoardLike  = boardLikeRepository.findBoardLikeByUserAndBoard_BoardId(user, boardId);

                if (checkBoardLike == null) {
                System.out.println("boardWriter : " + boardWriter.getId() + ", login user : " + user.getId());

                    if (!boardWriter.equals(user)) { // 자신이 누른 좋아요가 아니라면
                        boardWriter.likeChange(boardWriter.getReceivedLikeCnt() + 1);
                    }
                    board.likeChange(board.getLikeCnt() + 1);

                    BoardLike boardLike = boardLikeRepository.save(BoardLike.builder()
                            .user(user)
                            .board(board)
                            .build());

                    return new BoardLikeRes(boardLike);
                } else {
                    throw new BaseException(DATABASE_ERROR);
                }
            } else {
                throw new BaseException(ADD_FAIL_LIKE);
            }
        } catch (Exception exception) {
            throw new BaseException(LIKE_BOARD_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void deleteBoardLike(Long boardId, Long idx) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);
            Optional<Board> findBoard = boardRepository.findById(boardId);

            if (findBoard.isPresent()) {
                User user = optUser.get();
                Board board = findBoard.get();
                User boardWriter = board.getUser();

                // 자신이 누른 좋아요가 아니라면
                if (!boardWriter.equals(user)) {
                    boardWriter.likeChange(boardWriter.getReceivedLikeCnt() - 1);
                }
                board.likeChange(board.getLikeCnt() - 1);

                boardLikeRepository.deleteByUserIdAndBoard_BoardId(idx, boardId);
            }
        } catch (Exception exception){
            throw new BaseException(DELETE_FAIL_LIKE);
        }
    }

    // ================================================================================

    public List<RecipeLikeRes> getAllRecipeLike() {
        List<RecipeLikeRes> recipeLikes = recipeLikeRepository.findAll()
                .stream()
                .map(RecipeLikeRes::new)
                .toList();

        if (recipeLikes.isEmpty()) {
            throw new IllegalStateException();
        }
        return recipeLikes;
    }

    public List<RecipeLikeRes> getRecipeLike(Long idx) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);
            if (optUser.isPresent()) {
                User user = optUser.get();
                return recipeLikeRepository.findRecipeLikesByUser(user)
                        .stream().map(RecipeLikeRes::new).toList();
            } else {
                throw new BaseException(USERS_NOT_EXISTS);
            }
        } catch (Exception e) {
            throw new BaseException(LIKE_NOT_EXISTS);
        }
    }


    @Transactional
    public RecipeLikeRes addRecipeLike(Long idx, Long rcpId) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);
            Optional<Recipe> optRecipe = recipeRepository.findById(rcpId);

            if (optUser.isPresent() && optRecipe.isPresent()) {
                User user = optUser.get();
                Recipe recipe = optRecipe.get();

                RecipeLike checkRecipeLike = recipeLikeRepository.findRecipeLikeByUserAndRecipe_RcpId(user, rcpId);
                if (checkRecipeLike == null) {
                    recipe.likeChange(recipe.getLikeCnt() + 1);
                    RecipeLike recipeLike = recipeLikeRepository.save(RecipeLike.builder()
                            .user(user)
                            .recipe(recipe)
                            .checkLike(1L)
                            .build());

                    return new RecipeLikeRes(recipeLike);
                } else {
                    throw new BaseException(ADD_FAIL_LIKE);
                }
            } else {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch(Exception exception) {
            throw new BaseException(LIKE_RECIPE_ALREADY_EXISTS);
        }
    }
//        Recipe recipe = recipeRepository.findById(rcpId).get();
//        User loginUser = userRepository.findById(loginId).get();
//
//        recipe.likeChange(recipe.getLikeCnt() + 1);
//
//        recipeLikeRepository.save(RecipeLike.builder()
//                .user(loginUser)
//                .recipe(recipe)
//                .build());
//    }

    @Transactional
    public void deleteRecipeLike(Long idx, Long rcpId) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);
            Optional<Recipe> optRecipe = recipeRepository.findById(rcpId);

            if (optUser.isPresent() && optRecipe.isPresent()) {
                User user = optUser.get();
                Recipe recipe = optRecipe.get();
                RecipeLike checkRecipeLike = recipeLikeRepository.findRecipeLikeByUserAndRecipe_RcpId(user, rcpId);
                if (checkRecipeLike != null) {
                    recipe.likeChange(recipe.getLikeCnt() - 1);
                    recipeLikeRepository.deleteByUserIdAndRecipe_RcpId(idx, rcpId);
                } else {
                    throw new BaseException(LIKE_NOT_EXISTS);
                }
            } else {
                throw new BaseException(DELETE_FAIL_LIKE);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public Long checkRecipeLike(Long idx, Long rcpId) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);
            Optional<Recipe> optRecipe = recipeRepository.findById(rcpId);

            if (optUser.isPresent() && optRecipe.isPresent()) {
                User user = optUser.get();
                RecipeLike checkRecipeLike = recipeLikeRepository.findRecipeLikeByUserAndRecipe_RcpId(user, rcpId);
                return checkRecipeLike.getCheckLike();
            } else {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch(Exception exception) {
            throw new BaseException(LIKE_NOT_EXISTS);
        }
    }




}
