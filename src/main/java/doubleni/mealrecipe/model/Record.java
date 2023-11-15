package doubleni.mealrecipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ElementCollection
    @CollectionTable(name = "record_number", joinColumns = @JoinColumn(name = "recordId"))
    @Column(name = "recordNum")
    private List<String> recordNum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonIgnore
    private User user;



}
