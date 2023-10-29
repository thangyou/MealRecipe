package doubleni.mealrecipe.model.opinion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OpinionReq {
    private List<String> foodList;
    private List<String> allergyList;
}
