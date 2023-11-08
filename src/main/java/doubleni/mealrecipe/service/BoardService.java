package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.MD5Generator;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.config.exception.BaseResponse;
import doubleni.mealrecipe.config.exception.BaseResponseStatus;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.BoardRepository;
import doubleni.mealrecipe.repository.CommentRepository;
import doubleni.mealrecipe.repository.FileRepository;
import doubleni.mealrecipe.repository.UserRepository;
import doubleni.mealrecipe.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static doubleni.mealrecipe.config.exception.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    /**
     * 조회 - get
     * 검색 - search(Controller & Service) / find(Repository)
     * 생성 - save
     * 수정 - update
     * 삭제 - delete
     */

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    /* TEST */

    // ==========================================================

//    public Page<Board> findAll(PageRequest pageRequest) {
//        return boardRepository.findAll(pageRequest);
//    }
//
//    public Page<Board> findByTitle(PageRequest pageRequest) {
//        return boardRepository.findByTitle("1", pageRequest);
//    }


    /* 모든 게시글(리스트) 조회 */
    @Transactional(readOnly = true)
    public List<BoardRes> getBoards() {
        List<BoardRes> boards =
                boardRepository.findAll()
                        .stream()
                        .map(BoardRes::new)
                        .collect(Collectors.toList());

        if (boards.isEmpty()) {
            throw new IllegalStateException();
        }
        return boards;
    }

    /* board id로 게시글 조회 */
    public Board getBoardById(Long boardId) { // 내 글 불러 오는 겨
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + boardId));
    }
    public Board getBoardByBoardId(Long boardId) throws BaseException { // 조회수 증가
        Optional<Board> findBoard = this.boardRepository.findByBoardId(boardId);
        if (findBoard.isPresent()) {
            Board board = findBoard.get();
            board.setHits(board.getHits()+1);
            this.boardRepository.save(board);
            return board;
        } else {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    /* 게시글 정렬 */
    public List<BoardRes> getBoardByOrderByHitsDesc() throws BaseException { // 조회 수
        List<BoardRes> getBoards =
                boardRepository.findAllByOrderByHitsDesc()
                        .stream()
                        .map(BoardRes::new)
                        .toList();

        if (getBoards.isEmpty()) {
            throw new BaseException(DATABASE_ERROR);
        }
        return getBoards;
    }

    public List<BoardRes> getBoardByOrderByLikeCntDesc() throws BaseException { // 좋아요
        List<BoardRes> getBoards =
                boardRepository.findAllByOrderByLikeCntDesc()
                        .stream()
                        .map(BoardRes::new)
                        .toList();

        if (getBoards.isEmpty()) {
            throw new BaseException(DATABASE_ERROR);
        }
        return getBoards;
    }

    public List<BoardRes> getBoardByOrderByCommentCntDesc() throws BaseException { // 댓글 수
        List<BoardRes> getBoards =
                boardRepository.findAllByOrderByCommentCntDesc()
                        .stream()
                        .map(BoardRes::new)
                        .toList();

        if (getBoards.isEmpty()) {
            throw new BaseException(DATABASE_ERROR);
        }
        return getBoards;
    }




    /* 게시글 검색 */
    public List<BoardRes> searchBoardByUserNickname(String writer) {
        List<BoardRes> boardList = boardRepository.findByUserNicknameContaining(writer)
                .stream()
                .map(BoardRes::new)
                .toList();

        if (boardList.isEmpty()) {
            throw new IllegalStateException();
        }
        return boardList;
    }

    public List<BoardRes> searchBoardByTitle(String keyword) {
        List<BoardRes> boardList = boardRepository.findByTitleContaining(keyword)
                .stream()
                .map(BoardRes::new)
                .toList();

        if (boardList.isEmpty()) {
            throw new IllegalStateException();
        }
        return boardList;
    }

    public List<BoardRes> searchBoardByContent(String keyword) {
        List<BoardRes> boardList = boardRepository.findByContentContaining(keyword)
                .stream()
                .map(BoardRes::new)
                .toList();

        if (boardList.isEmpty()) {
            throw new IllegalStateException();
        }

        return boardList;
    }


    // ==================================================================================


    /* 게시글 추가 */
    @Transactional
    public void save(BoardReq addBoard, Long id) throws BaseException {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                boardRepository.save(addBoard.toEntity(user.get()));
                ResponseEntity.ok("-> 게시글 등록 완료");
            } else {
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
        } catch (Exception exception) {
            throw new BaseException(POST_BOARD_FAILS);
        }


    }

    /* 게시글 수정 */
    @Transactional
    public void updateBoard(long boardId, BoardReq update) throws BaseException {

        try {
            Optional<Board> findBoard = boardRepository.findById(boardId);
            System.out.println("--- update 전 ---");
            System.out.println("title : " + findBoard.get().getTitle());
            System.out.println("content : " + findBoard.get().getContent());
            System.out.println("file id : " + findBoard.get().getFileId());

            findBoard.get().updateBoard(update);
            System.out.println("--- update 후 ---");
            System.out.println("title : " + findBoard.get().getTitle());
            System.out.println("content : " + findBoard.get().getContent());
            System.out.println("file id : " + findBoard.get().getFileId());

            ResponseEntity.ok("-> 게시글 수정 완료");

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /* 게시글 삭제 */
    public void deleteBoard(Long boardId) throws BaseException {
        try {
            Optional<Board> findBoard = boardRepository.findById(boardId);
            if (findBoard.isPresent()) {
                Long filedId = findBoard.get().getFileId();
                fileRepository.deleteById(filedId);
                boardRepository.deleteByBoardId(boardId);
                ResponseEntity.ok("-> 게시글 삭제 완료");
            } else {
                ResponseEntity.badRequest().body("not found : " + boardId);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }


    }


}
