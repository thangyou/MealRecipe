package doubleni.mealrecipe.model.Test.entity;

import doubleni.mealrecipe.model.Test.dto.TestBoardReq;
import doubleni.mealrecipe.model.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Table(name = "test_board")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TestBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long boardId;
    private String title; // 제목
    private String content; // 본문
    private int hits; // 조회수

//    @Enumerated(EnumType.STRING)
//    private String category; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 작성자

    /**
     *  BOARD @OneToMany 1:N
     *  COMMENT - 댓글
     *  LIKE - 좋아요
     */
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Like> likes;       // 좋아요
    private Integer likeCnt;        // 좋아요 수

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> comments; // 댓글
    private Integer commentCnt;     // 댓글 수

    /**
     * BOARD @OneToOne 1:1
     */
    @OneToOne(fetch = FetchType.LAZY)
    private UploadImage uploadImage;

    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 조건 검색 ***********************************************
    private String keyfilter;
    private String keyword;
    private String startdate;
    private String enddate;
    // **********************************************************

//    @Builder
//    public TestBoard(User user, String title, String desc) {
//        this.user = user;
//        this.title = title;
//        this.desc = desc;
//    }

    public void updateTestBoard(TestBoardReq req) {
        this.title = req.getTitle();
        this.content= req.getContent();
    }

    public void likeChange(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public void commentChange(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }

    public void setUploadImage(UploadImage uploadImage) {
        this.uploadImage = uploadImage;
    }


}
