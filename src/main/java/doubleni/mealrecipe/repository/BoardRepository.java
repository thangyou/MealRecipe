package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitle(String title, Pageable pageable);
    Optional<Board> findByBoardId(Long boardId);
//    List<Board> findByNickname(String writer);

    // 검색
    List<Board> findByUserNicknameContaining(@Param("keyword") String keyword);
    List<Board> findByTitleContaining(@Param("keyword") String keyword);
    List<Board> findByContentContaining(@Param("keyword") String keyword);
    void deleteByBoardId(Long BoardId);

    // 정렬
    List<Board> findAllByOrderByHitsDesc(); // 조회수
    List<Board> findAllByOrderByLikeCntDesc(); // 좋아요
    List<Board> findAllByOrderByCommentCntDesc(); // 댓글



}
