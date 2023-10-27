package doubleni.mealrecipe.model;

import doubleni.mealrecipe.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class UploadImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;    // 원본 파일명
    private String savedFilename;        // 서버에 저장된 파일명

//    @OneToOne(mappedBy = "uploadImage", fetch = FetchType.LAZY)
//    private Board board;
}