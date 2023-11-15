package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard_BoardId(Long boardId);
    List<Comment> findAllByUser_Id(Long userId);
}
