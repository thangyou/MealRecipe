package doubleni.mealrecipe.model;

import doubleni.mealrecipe.model.DTO.BoardReq;
import doubleni.mealrecipe.model.DTO.GetBoardRes;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name = "board")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long boardId;
    private String title; // 제목
    private String desc; // 본문

//    @Enumerated(EnumType.STRING)
//    private String category; // 카테고리

    /**
     *  USER @OneToMany 1:N
     *  BOARD - 레시피 게시판
     *  COMMENT - 댓글
     *  LIKE - 좋아요
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

//    @OneToMany(mappedBy = "board", orphanRemoval = true)
//    private List<Like> likes;       // 좋아요
//    private Integer likeCnt;        // 좋아요 수
//
//    @OneToMany(mappedBy = "board", orphanRemoval = true)
//    private List<Comment> comments; // 댓글
//    private Integer commentCnt;     // 댓글 수

//    @OneToOne(fetch = FetchType.LAZY)
//    private UploadImage uploadImage;

    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Board(String title, String desc) {
//        this.user = user;
        this.title = title;
        this.desc = desc;
    }

    public void updateBoard(BoardReq req) {
        this.title = req.getTitle();
        this.desc = req.getDesc();
    }


}
