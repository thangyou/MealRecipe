package doubleni.mealrecipe.model.Recipe;

import javax.persistence.*;

@Entity
@Table(name = "Ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long ingredient_id; // ID
    private String ingredient_name; // 재료명
    private Long ingredient_price; // 재료 가격

}
