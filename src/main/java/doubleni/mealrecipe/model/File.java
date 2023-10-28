package doubleni.mealrecipe.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class File {
    // 업로드한 파일의 정보
    @Id
    @GeneratedValue
    private Long id; // 파일 ID == Board.fileId

    @Column(nullable = false)
    private String origFilename; // 원본 파일명

    @Column(nullable = false)
    private String filename; // Server에 저장되는 파일명

    @Column(nullable = false)
    private String filePath; // Server에 저장되는 경로

    @Builder
    public File(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
