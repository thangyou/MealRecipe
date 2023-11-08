package doubleni.mealrecipe.model;

import doubleni.mealrecipe.model.DTO.BoardReq;
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
@Setter
@ToString
@Table(name = "board")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer hits; // 조회수

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 작성자

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    /**
     *  BOARD @OneToMany 1:N
     *  COMMENT - 댓글
     *  LIKE - 좋아요
     */
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<BoardLike> boardLikes;       // 좋아요
    private Integer likeCnt;        // 좋아요 수

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> comments; // 댓글
    private Integer commentCnt;     // 댓글 수

    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 조건 검색 ***********************************************
//    private String keyfilter;
//    private String keyword;
//    private String startdate;
//    private String enddate;
    // **********************************************************


    @Builder
    public Board(Long boardId, String title, String content, User user, Long fileId,
                 Integer likeCnt, Integer hits, Integer commentCnt) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.user = user;
        this.fileId = fileId;
        this.likeCnt = likeCnt;
        this.hits = hits;
        this.commentCnt = commentCnt;
    }

    public void updateBoard(BoardReq req) {
        this.title = req.getTitle();
        this.content = req.getContent();
    }

//    @OneToOne(mappedBy = "files")
//    private ImageFile file;
//
//    public ImageFile getFile(ImageFile file) {
//        return this.file = file;
//    }

    public void likeChange(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public void commentChange(Integer commentCnt) {
        this.commentCnt = commentCnt;
    }



}
