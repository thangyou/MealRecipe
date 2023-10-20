package doubleni.mealrecipe.model.Test.service;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Test.dto.AddBoardRequest;
import doubleni.mealrecipe.model.Test.repository.TestRepository;
import doubleni.mealrecipe.model.Test.entity.TestBoard;
import doubleni.mealrecipe.model.Test.entity.UploadImage;
import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class TestService {

    private final TestRepository boardRepository;
    private final UserRepository userRepository;
//    private final LikeRepository likeRepository;
//    private final CommentRepository commentRepository;
    private final UploadImageService uploadImageService;

    /**
     * getBoardList()
     *  - return : 글 list
     *  - 검색을 했다면 return : 제목 or 작성자 (in 키워드) 글
     */

//    public Page<Board> getBoardList(PageRequest pageRequest, String searchType, String keyword) {
//        if (searchType != null && keyword != null) {
//            if (searchType.equals("title")) {
//                return boardRepository.findAllByTitleContains(keyword, pageRequest);
//            } else {
//                return boardRepository.findAllByUserNicknameContains(keyword, pageRequest);
//            }
//        }
////        return boardRepository.findAll(pageRequest);
//    }

    /**
     * addBoard()
     *  - Board 저장 -> 이미지가 있다면 이미지 저장 후 저장된 Board에 해당 이미지 정보 추가 삽입
     */
//    @Transactional
//    public Long writeBoard(AddBoardRequest req, String email, Authentication auth) throws IOException {
//        User loginUser = userRepository.findByEmail(email).get();
//
//        TestBoard savedBoard = boardRepository.save(req.toEntity(loginUser));
//
//        UploadImage uploadImage = uploadImageService.saveImage(req.getUploadImage(), savedBoard);
//        if (uploadImage != null) {
//            savedBoard.setUploadImage(uploadImage);
//        }
//
//        return savedBoard.getBoardId();
//    }
}
