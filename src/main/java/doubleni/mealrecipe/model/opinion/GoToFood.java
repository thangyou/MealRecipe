package doubleni.mealrecipe.model.opinion;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class GoToFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long GoToFoodId;

    private Long id;

    private Long foodId;
}
