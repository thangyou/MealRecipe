package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    void deleteByUserIdAndBoard_BoardId(Long userId, Long boardId);

}
