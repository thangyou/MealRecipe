package doubleni.mealrecipe.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import doubleni.mealrecipe.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRecord {
    private Long recordId;

    private List<String> recordNum;

    private User user;
}
