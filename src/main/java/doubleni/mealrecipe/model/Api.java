package doubleni.mealrecipe.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "api")
@Entity
public class Api {
    // 식약처 공공 데이터 레시피
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String rcpSeq; // 일련 번호
    private String rcpNm; // 레시피명

    private int infoPro; // 단백질
    private int infoFat; // 지방

    @Column(name = "ingredient", nullable = false)
    private String ingredient; // 재료 정보

}