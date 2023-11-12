package doubleni.mealrecipe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    //private String userId;

    //private Long recipeId;

    private String reviewContext;

    private String reviewImageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp reviewCreated;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp modifiedDate;

    private Double reviewRating;

    //다대일 관계
    //한 개의 User이 여러 개의 Review를 가질 수 있지만, 각각의 Review는 하나의 User에만 속할 수 있는 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonIgnore
    private User user;

    //하나의 recipe는 여러개의 review를 가질 수 있지만, 각각의 review는 하나의 recipe에만 속할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rcpId")
    @JsonIgnore
    private Recipe recipe;


}
