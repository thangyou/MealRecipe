package doubleni.mealrecipe.repository;


import doubleni.mealrecipe.model.dto.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeRepositoryImpl{

    private final EntityManager em;

    public Recipe findByName(String rcpNm) {
        return em.createQuery("SELECT u FROM Recipe u WHERE u.rcp_nm = :rcpNm", Recipe.class)
                .setParameter("rcpNm", rcpNm)
                .getSingleResult();
    }

}
