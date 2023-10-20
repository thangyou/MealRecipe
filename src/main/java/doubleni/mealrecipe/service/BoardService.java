package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.AddBoardReq;
import doubleni.mealrecipe.model.DTO.BoardReq;
import doubleni.mealrecipe.model.DTO.GetBoardRes;
import doubleni.mealrecipe.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final BoardRepository boardRepository;

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

    /* 게시글 목록 조회 */
    @Transactional(readOnly = true)
    public List<GetBoardRes> getBoards() {
        List<GetBoardRes> boards =
                boardRepository.findAll()
                        .stream()
                        .map(GetBoardRes::new)
                        .collect(Collectors.toList());

        if (boards.isEmpty()) {
            throw new IllegalStateException();
        }
        return boards;
    }

    /* board id로 게시글 조회 */
    public Board findByBoardId(long boardId) {
        return boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + boardId));
    }

    /* email or nickname 으로 게시글 조회 */
    public Board findByUserEmail(String keyword) {
        return boardRepository.findByEmailOrNickname(keyword)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + keyword));
    }


    /* keyword로 게시글 검색 */
    public List<GetBoardRes> searchBoardByKeyword(String keyword) {
            List<GetBoardRes> boardList = boardRepository.findByTitleContaining(keyword)
                    .stream()
                    .map(GetBoardRes::new)
                    .toList();

            if (boardList.isEmpty()) {
                boardList = boardRepository.findByDescContaining(keyword)
                        .stream()
                        .map(GetBoardRes::new)
                        .toList();
            } else {
                throw new IllegalStateException();
            }
            return boardList;
    }






}
