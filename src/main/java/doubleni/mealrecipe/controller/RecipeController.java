package doubleni.mealrecipe.controller;

import doubleni.mealrecipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class RecipeController {
    private Logger logger = Logger.getLogger(String.valueOf(RecipeController.class));
    private RecipeService recipeService;

    public RecipeController() {
    } // constructor

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


}
