package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.AddBoardReq;
import doubleni.mealrecipe.model.DTO.BoardReq;
import doubleni.mealrecipe.model.DTO.BoardRes;
import doubleni.mealrecipe.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final BoardRepository boardRepository;

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
//    public List<Board> searchBoardByNickname(String writer) {
//        return boardRepository.findByNickname(writer);
//    }

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
    public void save(AddBoardReq addBoard) {
        boardRepository.save(addBoard.toEntity());
    }

    /* 게시글 수정 */
    @Transactional
    public void updateBoard(long boardId, BoardReq update) {
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(EntityNotFoundException::new);
//        board.updateBoard(req);
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
        boardRepository.deleteById(boardId);
    }







}
