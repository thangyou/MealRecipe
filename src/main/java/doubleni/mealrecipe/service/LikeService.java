package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.*;
import doubleni.mealrecipe.model.DTO.BoardLikeRes;
import doubleni.mealrecipe.model.DTO.RecipeLikeRes;
import doubleni.mealrecipe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    public List<BoardLikeRes> getBoardLike() {
        List<BoardLikeRes> boardLikes = boardLikeRepository.findAll()
                .stream()
                .map(BoardLikeRes::new)
                .toList();

        if (boardLikes.isEmpty()) {
            throw new IllegalStateException();
        }
        return boardLikes;
    }

    @Transactional
    public void addBoardLike(Long loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        User loginUser = userRepository.findById(loginId).get();
        User boardUser = board.getUser();

        // 자신이 누른 좋아요가 아니라면
        if (!boardUser.equals(loginUser)) {
            boardUser.likeChange(boardUser.getReceivedLikeCnt() + 1);
        }
        board.likeChange(board.getLikeCnt() + 1);

        boardLikeRepository.save(BoardLike.builder()
                .user(loginUser)
                .board(board)
                .build());
    }

    @Transactional
    public void deleteBoardLike(Long loginId, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        User loginUser = userRepository.findById(loginId).get();
        User boardUser = board.getUser();

        // 자신이 누른 좋아요가 아니라면
        if (!boardUser.equals(loginUser)) {
            boardUser.likeChange(boardUser.getReceivedLikeCnt() - 1);
        }
        board.likeChange(board.getLikeCnt() - 1);

        boardLikeRepository.deleteByUserIdAndBoard_BoardId(loginId, boardId);
    }

    // ================================================================================

    public List<RecipeLikeRes> getRecipeLike() {
        List<RecipeLikeRes> recipeLikes = recipeLikeRepository.findAll()
                .stream()
                .map(RecipeLikeRes::new)
                .toList();

        if (recipeLikes.isEmpty()) {
            throw new IllegalStateException();
        }
        return recipeLikes;
    }


    @Transactional
    public void addRecipeLike(Long loginId, Long rcpId) {
        Recipe recipe = recipeRepository.findById(rcpId).get();
        User loginUser = userRepository.findById(loginId).get();

        recipe.likeChange(recipe.getLikeCnt() + 1);

        recipeLikeRepository.save(RecipeLike.builder()
                .user(loginUser)
                .recipe(recipe)
                .build());
    }

    @Transactional
    public void deleteRecipeLike(Long loginId, Long rcpId) {
        Recipe recipe = recipeRepository.findById(rcpId).get();
        recipe.likeChange(recipe.getLikeCnt() + 1);

        recipeLikeRepository.deleteByUserIdAndRecipe_RcpId(loginId, rcpId);
    }

}
