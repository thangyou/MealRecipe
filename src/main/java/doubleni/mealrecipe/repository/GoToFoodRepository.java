package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.opinion.GoToFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoToFoodRepository extends JpaRepository<GoToFood, Long> {

}

