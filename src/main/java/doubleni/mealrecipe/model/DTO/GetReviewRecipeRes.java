package doubleni.mealrecipe.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewRecipeRes {
    private Long reviewId;
    private String reviewContext;
    private String reviewImageUrl;
    private Timestamp reviewCreated;
    private Timestamp reviewModified;
    private double reviewRating;
    private Long userId;
    private String nickName;
    private Long recipeId;
    private String recipeName;

    private double reviewAverage;
}