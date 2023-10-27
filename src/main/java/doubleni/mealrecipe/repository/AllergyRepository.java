package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.opinion.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    Allergy findByAllergyName(String allergyName);

    Allergy findAllByAllergyId(Long allergyId);
}

