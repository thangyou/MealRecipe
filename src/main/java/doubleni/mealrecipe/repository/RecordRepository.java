package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<Record> findByUser (User user);
}
