package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@NoArgsConstructor
@AllArgsConstructor
@RestController
public class RecipeController {
    private RecipeService recipeService;

}
