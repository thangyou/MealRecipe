package doubleni.mealrecipe.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewRes {
    private Long reviewId;

    private String userId;

    private Long recipeId;

    private String reviewContext;

    private String reviewImage;

    private Timestamp reviewCreated;

    private double reviewRating;
}
