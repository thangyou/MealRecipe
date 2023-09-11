package doubleni.mealrecipe.model.Recipe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
//import java.util.HashSet;

@Getter
@Setter
@Entity
@Table(name = "Recipe")
public class Recipe {
    // getter
    // 레시피 = 메뉴
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipe_id; // ID
    private String recipe_name; // 이름
    private String recipe_image; // 이미지
    @Column(columnDefinition = "LONGTEXT")
    private String recipe_description; // 설명
    private String recipe_type; // 종류
    private int recipe_cookTime; // 조리 시간

    // 재료 어케하노 리스트?
    //    private Set<Ingredient> ingredientSet = new HashSet<>();
    private int review_count; // 평점
    private double average_rating; //


    public Recipe(String recipe_name, String recipe_image, String recipe_description,
                  int recipe_cookTime, int review_count, double average_rating, String recipe_type){
        this.recipe_name = recipe_name;
        this.recipe_image = recipe_image;
        this.recipe_description = recipe_description;
        this.recipe_type = recipe_type;
        this.recipe_cookTime = recipe_cookTime;
        this.average_rating = average_rating;
        this.review_count = review_count;
    }

    public Recipe() {}

    // setter
    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public void setRecipe_image(String recipe_image) {
        this.recipe_image = recipe_image;
    }

    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    public void setRecipe_type(String recipe_type) {
        this.recipe_type = recipe_type;
    }

    public void setRecipe_cookTime(int recipe_cookTime) {
        this.recipe_cookTime = recipe_cookTime;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }
}
