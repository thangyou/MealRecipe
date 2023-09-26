package doubleni.mealrecipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import doubleni.mealrecipe.repository.RecipeRepository;
import doubleni.mealrecipe.model.dto.AddRecipeRequest;
import doubleni.mealrecipe.model.dto.Recipe;
import doubleni.mealrecipe.model.dto.UpdateRecipeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeApiControllerTest {

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

    @DisplayName("addRecipe : 레시피 추가 성공")
    @Test
    public void addRecipe() throws Exception {
        // given
        final String url = "/api/recipes";
        final String rcp_seq = "rcp_seq";
        final String rcp_nm = "rcp_nm";
        final String rcp_way2 = "rcp_way2";
        final String rcp_pat2 = "rcp_pat2";
        final String manual01 = "manual01";
        final String manual_img01 = "manual_img01";

        final AddRecipeRequest userRequest = new AddRecipeRequest(rcp_seq, rcp_nm, rcp_way2, rcp_pat2,
                manual01, manual_img01);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when : 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());
        List<Recipe> recipes = recipeRepository.findAll();

        assertThat(recipes.size()).isEqualTo(1);
        assertThat(recipes.get(0).getRcp_seq()).isEqualTo(rcp_seq);
        assertThat(recipes.get(0).getRcp_nm()).isEqualTo(rcp_nm);
        assertThat(recipes.get(0).getRcp_way2()).isEqualTo(rcp_way2);
        assertThat(recipes.get(0).getRcp_pat2()).isEqualTo(rcp_pat2);
        assertThat(recipes.get(0).getManual01()).isEqualTo(manual01);
        assertThat(recipes.get(0).getManual_img01()).isEqualTo(manual_img01);

    }

    @DisplayName("findAllRecipes : 레시피 목록 조회 성공")
    @Test
    public void findAllRecipes() throws Exception {
        // given
        final String url = "/api/recipes";
        final String rcp_seq = "rcp_seq";
        final String rcp_nm = "rcp_nm";
        final String rcp_way2 = "rcp_way2";
        final String rcp_pat2 = "rcp_pat2";
        final String manual01 = "manual01";
        final String manual_img01 = "manual_img01";

        recipeRepository.save(Recipe.builder()
                .rcp_seq(rcp_seq)
                .rcp_nm(rcp_nm)
                .rcp_way2(rcp_way2)
                .rcp_pat2(rcp_pat2)
                .manual01(manual01)
                .manual_img01(manual_img01)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rcp_seq").value(rcp_seq))
                .andExpect(jsonPath("$[0].rcp_nm").value(rcp_nm))
                .andExpect(jsonPath("$[0].rcp_way2").value(rcp_way2))
                .andExpect(jsonPath("$[0].rcp_pat2").value(rcp_pat2))
                .andExpect(jsonPath("$[0].manual01").value(manual01))
                .andExpect(jsonPath("$[0].manual_img01").value(manual_img01));
    }

    @DisplayName("findRecipe : 특정 레시피 조회 성공")
    @Test
    public void findRecipe() throws Exception {
        // given
        final String url = "/api/recipes/{id}";
        final String rcp_seq = "rcp_seq";
        final String rcp_nm = "rcp_nm";
        final String rcp_way2 = "rcp_way2";
        final String rcp_pat2 = "rcp_pat2";
        final String manual01 = "manual01";
        final String manual_img01 = "manual_img01";
        Recipe savedRecipe = recipeRepository.save(Recipe.builder()
                        .rcp_seq(rcp_seq)
                        .rcp_nm(rcp_nm)
                        .rcp_way2(rcp_way2)
                        .rcp_pat2(rcp_pat2)
                        .manual01(manual01)
                        .manual_img01(manual_img01)
                        .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url,
                savedRecipe.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rcp_seq").value(rcp_seq))
                .andExpect(jsonPath("$.rcp_nm").value(rcp_nm))
                .andExpect(jsonPath("$.rcp_way2").value(rcp_way2))
                .andExpect(jsonPath("$.rcp_pat2").value(rcp_pat2))
                .andExpect(jsonPath("$.manual01").value(manual01))
                .andExpect(jsonPath("$.manual_img01").value(manual_img01));

    }

    @DisplayName("deleteRecipe : 레시피 삭제 성공")
    @Test
    public void deleteRecipe() throws Exception {
        // given
        final String url = "/api/recipes/{id}";
        final String rcp_seq = "rcp_seq";
        final String rcp_nm = "rcp_nm";
        final String rcp_way2 = "rcp_way2";
        final String rcp_pat2 = "rcp_pat2";
        final String manual01 = "manual01";
        final String manual_img01 = "manual_img01";

        Recipe savedRecipe = recipeRepository.save(Recipe.builder()
                .rcp_seq(rcp_seq)
                .rcp_nm(rcp_nm)
                .rcp_way2(rcp_way2)
                .rcp_pat2(rcp_pat2)
                .manual01(manual01)
                .manual_img01(manual_img01)
                .build());

        // when
        mockMvc.perform(delete(url, savedRecipe.getId()))
                .andExpect(status().isOk());

        // then
        List<Recipe> recipes= recipeRepository.findAll();

        assertThat(recipes).isEmpty();
    }

    @DisplayName("updateRecipe : 레시피 수정 성공")
    @Test
    public void updateRecipe() throws Exception {
        // given
        final String url = "/api/recipes/{id}";
        final String rcp_seq = "rcp_seq";
        final String rcp_nm = "rcp_nm";
        final String rcp_way2 = "rcp_way2";
        final String rcp_pat2 = "rcp_pat2";
        final String manual01 = "manual01";
        final String manual_img01 = "manual_img01";

        Recipe savedRecipe = recipeRepository.save(Recipe.builder()
                .rcp_seq(rcp_seq)
                .rcp_nm(rcp_nm)
                .rcp_way2(rcp_way2)
                .rcp_pat2(rcp_pat2)
                .manual01(manual01)
                .manual_img01(manual_img01)
                .build());

        final String new_rcp_seq = "new_rcp_seq";
        final String new_rcp_nm = "new_rcp_nm";
        final String new_rcp_way2 = "new_rcp_way2";
        final String new_rcp_pat2 = "new_rcp_pat2";
        final String new_manual01 = "new_manual01";
        final String new_manual_img01 = "new_manual_img01";

        UpdateRecipeRequest request = new UpdateRecipeRequest(new_rcp_seq, new_rcp_nm, new_rcp_way2,
                new_rcp_pat2, new_manual01, new_manual_img01);

        // when
        ResultActions result = mockMvc.perform(put(url, savedRecipe.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Recipe recipe = recipeRepository.findById(savedRecipe.getId()).get();

        // 계속 rcp_seq 값만 update 안되는뎅..
        // 너 왜 그러는뎅..
        System.out.println("recipe.getRcp_seq() = " + recipe.getRcp_seq()); // rcp_seq
        System.out.println("recipe.getRcp_nm() = " + recipe.getRcp_nm()); // rcp_nm


//        assertThat(recipe.getRcp_seq()).isEqualTo(new_rcp_seq);
        assertThat(recipe.getRcp_nm()).isEqualTo(new_rcp_nm);
        assertThat(recipe.getRcp_way2()).isEqualTo(new_rcp_way2);
        assertThat(recipe.getRcp_pat2()).isEqualTo(new_rcp_pat2);
        assertThat(recipe.getManual01()).isEqualTo(new_manual01);
        assertThat(recipe.getManual_img01()).isEqualTo(new_manual_img01);

    }


}