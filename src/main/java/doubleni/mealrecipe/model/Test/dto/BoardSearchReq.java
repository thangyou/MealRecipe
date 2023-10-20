package doubleni.mealrecipe.model.Test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardSearchReq {
    private String sortType;
    private String searchType;
    private String keyword;
}
