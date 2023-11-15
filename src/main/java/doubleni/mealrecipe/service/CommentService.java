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

    public List<CommentRes> getComments(Long boardId) throws BaseException {
        try {
            Optional<Board> findBoard = this.boardRepository.findByBoardId(boardId);
            if (findBoard.isPresent()) {
                List<CommentRes> comments =
                        commentRepository.findAllByBoard_BoardId(boardId)
                                .stream()
                                .map(CommentRes::new)
                                .collect(Collectors.toList());
                return comments;
            } else {
                throw new BaseException(BOARD_NOT_EXISTS);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }



    }

    public List<CommentRes> getMyCommentById(Long idx) throws BaseException {
        try {
            Optional<User> userOptional = userRepository.findById(idx);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<CommentRes> myComments =
                        commentRepository.findAllByUser_Id(idx)
                                .stream()
                                .map(CommentRes::new)
                                .toList();

                if (myComments.isEmpty()) {
                    throw new BaseException(SHOW_FAIL_COMMENT);
                }
                return myComments;

            } else {
                // 사용자를 찾지 못한 경우 에러 처리
                throw new BaseException(USERS_NOT_EXISTS);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public CommentRes addComment(Long boardId, CommentReq req, Long idx) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);
            Optional<Board> optBoard = boardRepository.findByBoardId(boardId);

            if (optUser.isPresent()) {
                User user = optUser.get();

                if (optBoard.isEmpty()) {
                    throw new BaseException(BOARD_NOT_EXISTS);
                }
                Board board = optBoard.get();
                board.commentChange(board.getCommentCnt() + 1);
                Comment comment = commentRepository.save(req.toEntity(board, user));
                return new CommentRes(comment);
            } else {
                throw new BaseException(USERS_NOT_EXISTS);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
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

    public Long deleteComment(Long commentId, Long idx) throws BaseException {
        try {
            Optional<User> findUser = userRepository.findById(idx);
            Optional<Comment> findComment = commentRepository.findById(commentId);

            if (findUser.isPresent()) {
                User user = findUser.get();
                if (findComment.isEmpty()) {
                    throw new BaseException(COMMENT_NOT_EXISTS);
                }
                User writer = findComment.get().getUser();
                Board board = findComment.get().getBoard();

                if (!user.equals(writer)) {
                  throw new BaseException(INVALID_USER_JWT);
                }
                // 댓글 삭제 하면 board 의 commentCnt 값도 1씩 줄어야 함.
                board.commentChange(board.getCommentCnt() - 1);

                commentRepository.delete(findComment.get());
                return board.getBoardId();
            } else {
                throw new BaseException(USERS_NOT_EXISTS);
            }

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
