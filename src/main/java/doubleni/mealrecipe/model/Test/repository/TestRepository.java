package doubleni.mealrecipe.model.Test.repository;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Test.entity.TestBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestBoard, Long> {

    // TEST ===================================================================

    // 조회
//    Page<Board> findAll(PageRequest pageRequest); // 게시글을 페이지에 맞게

    // 검색
    Page<Board> findAllByTitleContains(String keyword, PageRequest pageRequest); // 제목 in keyword
    Page<Board> findAllByUserNicknameContains(String keyword, PageRequest pageRequest); // 작성자 in keyword

    // User
    List<Board> findByUserNickname(String nickname);
    List<Board> findByUserEmail(String email);

    // =========================================================================
}
