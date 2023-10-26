package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.opinion.AllergySet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AllergySetRepository extends JpaRepository<AllergySet, Long> {

}

