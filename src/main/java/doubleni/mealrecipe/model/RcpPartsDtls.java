//package doubleni.mealrecipe.model;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
////@Builder
//@Table(name = "Rcp_Parts_Dtls")
//@Entity
//public class RcpPartsDtls {
//
//    @Id
//    @Column(name = "rcp_id")
//    private Long rcpId;
//
//    @ElementCollection
//    @Column(name = "rcp_parts_dtls")
//    private List<String> rcpPartsDtls; // 재료 정보
//
//    @OneToOne
//    @MapsId
//    @Setter
//    @JoinColumn(name = "rcp_id")
//    private Recipe recipe;
//
//
//
//}
