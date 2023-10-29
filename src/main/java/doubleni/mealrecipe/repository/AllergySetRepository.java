package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.opinion.AllergySet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergySetRepository extends JpaRepository<AllergySet, Long> {
    List<AllergySet> findAllByUserId (Long id);

}

