package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRecipeResponse {
    private int count;
    private double reviewAverage;
    private List<GetReviewRes> reviews;


    public ReviewRecipeResponse(List<GetReviewRes> reviews, Recipe recipe) {
        this.count = reviews.size();
        this.reviewAverage = recipe.getAverageRating();
        this.reviews = reviews;
    }

}
