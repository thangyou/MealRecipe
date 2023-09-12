package doubleni.mealrecipe.model;

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
    private boolean selected; // 포함 여부
    public Ingredient() {}
    public Ingredient(String ingredient_name, Long ingredient_price, boolean selected){
        this.ingredient_name = ingredient_name;
        this.ingredient_price = ingredient_price;
        this.selected = selected;

    }

    public Long getIngredient_id() {
        return ingredient_id;
    }
    public String getIngredient_name() {
        return ingredient_name;
    }
    public Long getIngredient_price() {
        return ingredient_price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setIngredient_id(Long ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public void setIngredient_price(Long ingredient_price) {
        this.ingredient_price = ingredient_price;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
