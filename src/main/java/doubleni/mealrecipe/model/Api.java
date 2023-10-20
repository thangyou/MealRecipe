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

    @Column(name = "rcp_nm", nullable = false)
    private String rcpNm; // 레시피명

    @Column(name = "ingredient", nullable = false)
    private String ingredient; // 재료 정보

}