package doubleni.mealrecipe.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private int count;
    private List<GetReviewRecipeRes> reviews;


    public ReviewResponse(List<GetReviewRecipeRes> reviews) {
        this.count = reviews.size();
        this.reviews = reviews;
    }

}