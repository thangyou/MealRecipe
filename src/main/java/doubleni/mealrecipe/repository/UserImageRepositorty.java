package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.User;
import doubleni.mealrecipe.model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepositorty extends JpaRepository<UserImage, Long> {

    Optional<UserImage> findByUser(User user);
}
