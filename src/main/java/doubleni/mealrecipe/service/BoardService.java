package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.BoardImage;
import doubleni.mealrecipe.model.DTO.AddBoardReq;
import doubleni.mealrecipe.model.DTO.BoardImageReq;
import doubleni.mealrecipe.model.DTO.BoardReq;
import doubleni.mealrecipe.model.DTO.BoardRes;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.BoardImageRepository;
import doubleni.mealrecipe.repository.BoardRepository;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final BoardImageRepository boardImageRepository;

    public Page<Board> findAll(PageRequest pageRequest) {
        return boardRepository.findAll(pageRequest);
    }

    public Page<Board> findByTitle(PageRequest pageRequest) {
        return boardRepository.findByTitle("1", pageRequest);
    }


//    @PostConstruct
//    public void initializing() {
//        for (int i = 0; i < 50; i++) {
//            Board board = Board.builder()
//                    .title(String.valueOf(i))
//                    .content("Korea")
//                    .build();
//            boardRepository.save(board);
//        }
//    }


    /* 모든 게시글(리스트) 조회 */
//    @Transactional(readOnly = true)
//    public List<BoardRes> getBoards() {
//        List<BoardRes> boards =
//                boardRepository.findAll()
//                        .stream()
//                        .map(BoardRes::new)
//                        .collect(Collectors.toList());
//
//        if (boards.isEmpty()) {
//            throw new IllegalStateException();
//        }
//        return boards;
//    }

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
    @Transactional
    public void save(AddBoardReq addBoard, BoardImageReq imageReq) {
        Board board = boardRepository.save(addBoard.toEntity());
        boardRepository.save(board);

        if (imageReq.getFiles() != null && !imageReq.getFiles().isEmpty()) {
            for (MultipartFile file : imageReq.getFiles()) {
                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();

                File destinationFile = new File("\\src\\main\\resources\\static\\files\\" + imageFileName);

                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                BoardImage image = BoardImage.builder()
//                        .url("/boardImages/" + imageFileName)
                        .url("/src/main/resources/static/files/" + imageFileName)
                        .board(board)
                        .build();

                boardImageRepository.save(image);
            }
        }
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
