package doubleni.mealrecipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import doubleni.mealrecipe.Repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    RecipeRepository recipeRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        recipeRepository.deleteAll();
    }

    @DisplayName("getRecipes : 레시피 목록 조회 성공")
    @Test
    void getRecipes() throws Exception {
        // given

        // when

        // then
    }

    @Test
    void getRecipe() {
    }

    @Test
    void newRecipe() {
    }
}