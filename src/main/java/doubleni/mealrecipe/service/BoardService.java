package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.MD5Generator;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.*;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.BoardRepository;
import doubleni.mealrecipe.repository.FileRepository;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final FileService fileService;

    /* TEST */

    // ==========================================================

    /* 모든 게시글(리스트) 조회 */
    public List<BoardRes> getBoards() throws BaseException {
        List<BoardRes> boards =
                boardRepository.findAll()
                        .stream()
                        .map(BoardRes::new)
                        .collect(Collectors.toList());

        if (boards.isEmpty()) {
            throw new BaseException(BOARD_NOT_EXISTS);
        }
        return boards;
    }

    /* 나의 게시글 조회 */
    public List<BoardRes> getMyBoards(Long idx) throws BaseException {
        try {
            Optional<User> userOptional = userRepository.findById(idx);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<BoardRes> myBoards =
                        boardRepository.findBoardByUser(user)
                                .stream()
                                .map(BoardRes::new)
                                .toList();
                return myBoards;
            } else {
                    // 사용자를 찾지 못한 경우 에러 처리
                    throw new BaseException(USERS_NOT_EXISTS);
                }
        } catch (Exception exception) {
            throw new BaseException(BOARD_NOT_EXISTS);
        }


    }

    /* boardId로 게시글 조회 */
    public Board getBoardByBoardId(Long boardId, Long idx) throws BaseException {
        Optional<Board> findBoard = this.boardRepository.findByBoardId(boardId);
        if (findBoard.isPresent()) {
            Board board = findBoard.get();
            if (!idx.equals(findBoard.get().getUser().getId())) {
                board.setHits(board.getHits()+1); // 조회수 증가
            }
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
    public BoardRes add(BoardReq addBoard, MultipartFile file, Long idx) throws BaseException {
        try {
            Optional<User> optUser = userRepository.findById(idx);

            if (optUser.isPresent()) {
                User user = optUser.get();
                Board board = addBoard.toEntity(user);

                if (file != null) {
                String origFilename = file.getOriginalFilename();
                String filename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch(Exception e){
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                file.transferTo(new File(filePath));

                FileReq fileDto = new FileReq();
                fileDto.setOrigFilename(origFilename);
                fileDto.setFilename(filename);
                fileDto.setFilePath(filePath);

                Long fileId = fileService.saveFile(fileDto);
                board.setFileId(fileId);
                } else {
                    board.setFileId(0L);
                }

                boardRepository.save(board);

                return new BoardRes(board);
            } else {
                throw new BaseException(POST_BOARD_FAILS);
            }

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /* 게시글 수정 */
    @Transactional
    public BoardRes updateBoard(Long boardId, BoardReq update) throws BaseException {

        try {
            Optional<Board> findBoard = boardRepository.findById(boardId);
            if (findBoard.isPresent()) {
                Board board = findBoard.get();

                System.out.println("--- update 전 ---");

                if (update.getTitle() != null) { // 제목 변경
                    System.out.println("title : " + board.getTitle());
                    board.setTitle(update.getTitle());
                }
                if (update.getContent() != null) { // 내용 변경
                    System.out.println("content : " + board.getContent());
                    board.setContent(update.getContent());
                }
                if (update.getFileId() != null) { // 파일 변경
                    System.out.println("file id : " + board.getFileId());
                    board.setFileId(update.getFileId());
                }

                boardRepository.save(board);

                return new BoardRes(board);
            } else {
                throw new BaseException(UPDATE_FAIL_BOARD);
            }
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
                if (filedId != 0) {
                    fileRepository.deleteById(filedId);
                } else {
                    throw new BaseException(DELETE_FAIL_FILES);
                }
                boardRepository.deleteByBoardId(boardId);
                ResponseEntity.ok("-> 게시글 삭제 완료");
            } else {
                throw new BaseException(BOARD_NOT_EXISTS);
            }
        } catch (Exception exception){
            throw new BaseException(DELETE_FAIL_BOARD);
        }
    }

    // ================================================================================

//    public Page<Board> findAll(PageRequest pageRequest) {
//        return boardRepository.findAll(pageRequest);
//    }
//
//    public Page<Board> findByTitle(PageRequest pageRequest) {
//        return boardRepository.findByTitle("1", pageRequest);
//    }


}
