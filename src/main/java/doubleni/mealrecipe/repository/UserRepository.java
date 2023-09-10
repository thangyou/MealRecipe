package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Long userId);
}
