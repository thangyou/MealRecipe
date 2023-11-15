package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.BoardLike;
import doubleni.mealrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    List<BoardLike> findBoardLikesByUser(User user);
    void deleteByUserIdAndBoard_BoardId(Long userId, Long boardId);
    BoardLike findBoardLikeByUserAndBoard_BoardId(User user, Long boardId);



}
