package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardId(Long boardId);
//    Optional<Board> findByEmailOrNickname(String keyword);


    List<Board> findByTitleContaining(@Param("keyword") String keyword);
    List<Board> findByDescContaining(@Param("keyword") String keyword);

}
