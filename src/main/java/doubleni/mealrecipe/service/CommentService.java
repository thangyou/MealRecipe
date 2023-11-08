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

    public List<Comment> findAll(Long boardId) {
        return commentRepository.findAllByBoard_BoardId(boardId);
    }

    public void addComment(Long boardId, CommentReq req, Long id) {
        Board board = boardRepository.findById(boardId).get();
        User user = userRepository.findById(id).get();
        board.commentChange(board.getCommentCnt() + 1);
        commentRepository.save(req.toEntity(board, user));
//        try {
//            Optional<User> user = userRepository.findById(id);
//            if (user.isPresent()) {
//                Optional<Board> board = boardRepository.findByBoardId(boardId);
//                    board.commentChange(board.getCommentCnt() + 1);
//                    commentRepository.save(req.toEntity(board, user));
//                }
//                ResponseEntity.ok("-> 게시글 등록 완료");
//            } else {
//                throw new BaseException(USERS_EMPTY_USER_ID);
//            }
//        } catch (Exception exception) {
//            throw new BaseException(POST_BOARD_FAILS); // Comment 에러 코드 추가
//        }
    }


    @Transactional
    public Long updateComment(Long commentId, String newBody, Long id) throws BaseException {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        Optional<User> findUser = userRepository.findById(id);
        Long writer = findComment.get().getUser().getId();
        Long user = findUser.get().getId();
        System.out.println("writer : " + writer + ", user : " + user);
        // comment 의 user id 값과 로그인 한 user id 값 잘 받아옴
        // 아래 코드만 수정 하면 될 듯
        if (writer.equals(user)) {
            Comment comment = findComment.get();
            comment.update(newBody);

            return comment.getBoard().getBoardId();
        } else { // (!writer.equals(user))
            throw new BaseException(UPDATE_FAIL_COMMENT);
        }

//        if (findComment.isEmpty() || findUser.isEmpty()) {
//            return null;
//        }
//        if (!findComment.get().getUser().equals(findUser.get())) {
//            return null;
//        }
//        if ((!findComment.get().getUser().getId().equals(findUser.get().getId()))) { // 이거 안돼
//            // id = 2인 user가 작성한 comment id는 2번임
//            // 근데 comment 1번 수정하니까
//            // null 값 출력 되쟈냐 이거 고쳐
//            System.out.println(findUser.get().getId() + "가 작성한 댓글이 아닌뎅..?");
//            return null;
//        }
//        Comment comment = findComment.get();
//        comment.update(newBody);
//
//        return comment.getBoard().getBoardId();
    }

    public Long deleteComment(Long commentId, Long id) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        Optional<User> findUser = userRepository.findById(id);
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
