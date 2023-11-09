package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Comment;
import doubleni.mealrecipe.model.DTO.BoardRes;
import doubleni.mealrecipe.model.DTO.CommentReq;
import doubleni.mealrecipe.model.DTO.CommentRes;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.BoardRepository;
import doubleni.mealrecipe.repository.CommentRepository;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public List<CommentRes> getComments(Long boardId) {
        List<CommentRes> comments =
                commentRepository.findAllByBoard_BoardId(boardId)
                        .stream()
                        .map(CommentRes::new)
                        .collect(Collectors.toList());

        return comments;
    }

    public List<CommentRes> getMyCommentById(Long idx) {
        List<CommentRes> mycomments =
                commentRepository.findAllByUser_Id(idx)
                        .stream()
                        .map(CommentRes::new)
                        .toList();
        return mycomments;
    }

    public CommentRes addComment(Long boardId, CommentReq req, Long idx) {
        Board board = boardRepository.findById(boardId).get();
        User user = userRepository.findById(idx).get();

        board.commentChange(board.getCommentCnt() + 1);
        Comment comment = commentRepository.save(req.toEntity(board, user));

        return new CommentRes(comment);
    }


    @Transactional
    public CommentRes updateComment(Long commentId, String newBody, Long idx) throws BaseException {
        try {
            Optional<Comment> findComment = commentRepository.findById(commentId);
            if (findComment.isPresent()) {
                if (idx.equals(findComment.get().getUser().getId())) {
                    Comment comment = findComment.get();
                    comment.update(newBody);
                    return new CommentRes(comment);
                } else {
                    throw new BaseException(INVALID_USER_JWT);
                }
            } else {
                throw new BaseException(COMMENT_NOT_EXISTS);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Long deleteComment(Long commentId, Long idx) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        Optional<User> findUser = userRepository.findById(idx);
        if (findComment.isEmpty() || findUser.isEmpty() ||
                (!findComment.get().getUser().equals(findUser.get()))) {
            return null;
        }
        // 댓글 삭제 하면 board 의 commentCnt 값도 1씩 줄어야 함.
        Board board = findComment.get().getBoard();
        board.commentChange(board.getCommentCnt() - 1);

        commentRepository.delete(findComment.get());
        return board.getBoardId();
    }


}
