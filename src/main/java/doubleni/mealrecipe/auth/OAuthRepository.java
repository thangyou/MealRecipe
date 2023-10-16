package doubleni.mealrecipe.auth;

import doubleni.mealrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndType(String email, String type);

}
