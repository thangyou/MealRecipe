package doubleni.mealrecipe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class RecipeAPI {
    private String total_count;
    private ArrayList<Recipe> recipes; // row
}
