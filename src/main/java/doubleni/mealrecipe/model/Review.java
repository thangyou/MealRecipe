package doubleni.mealrecipe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String userId;

    private Long recipeId;

    private String reviewContext;

    private String reviewImage;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp reviewCreated;

    private double reviewRating;

//    //다대일 관계
//    //한 개의 User이 여러 개의 Review를 가질 수 있지만, 각각의 Review는 하나의 User에만 속할 수 있는 관계
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    @JsonIgnore
//    private User user;

}
