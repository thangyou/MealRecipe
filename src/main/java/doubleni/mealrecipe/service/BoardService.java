package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.BoardReq;
import doubleni.mealrecipe.model.DTO.BoardRes;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.BoardRepository;
import doubleni.mealrecipe.repository.FileRepository;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public void savePost(BoardReq boardDto,Long id) {
        Optional<User> user = userRepository.findById(id);
        boardRepository.save(boardDto.toEntity(user.get()));
    }

    // ==========================================================

    public Page<Board> findAll(PageRequest pageRequest) {
        return boardRepository.findAll(pageRequest);
    }

    public Page<Board> findByTitle(PageRequest pageRequest) {
        return boardRepository.findByTitle("1", pageRequest);
    }


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
    public Board getBoardById(long boardId) {
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + boardId));
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
//    public void save(BoardReq addBoard, Long id) {
//        Optional<User> user = userRepository.findById(id);
//        Board board = boardRepository.save(addBoard.toEntity(user.get()));
//    }

    /* 게시글 수정 */
    @Transactional
    public void updateBoard(long boardId, BoardReq update) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isPresent()) {
            findBoard.get().updateBoard(update);

            ResponseEntity.ok("-> 게시글 수정 완료");
        } else {
            ResponseEntity.badRequest().body("not found : " + update.getBoardId());
        }
    }

    /* 게시글 삭제 */
    public void deleteBoard(long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isPresent()) {
            boardRepository.deleteByBoardId(boardId);
            fileRepository.deleteById(boardId);
            ResponseEntity.ok("-> 게시글 삭제 완료");
        } else {
            ResponseEntity.badRequest().body("not found : " + boardId);
        }

    }




}
