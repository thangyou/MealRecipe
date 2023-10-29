package doubleni.mealrecipe.model;

import doubleni.mealrecipe.model.DTO.FileReq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "files")
public class Files {
    // 업로드한 파일의 정보
    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long fileId; // 파일 ID == Board.fileId

    @Column(nullable = false)
    private String origFilename; // 원본 파일명

    @Column(nullable = false)
    private String filename; // Server에 저장되는 파일명

    @Column(nullable = false)
    private String filePath; // Server에 저장되는 경로

    @Builder
    public Files(Long fileId, String origFilename, String filename, String filePath) {
        this.fileId = fileId;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

    public void updateFile(FileReq req) {
//        this.fileId = req.getFileId();
        this.origFilename = req.getOrigFilename();
        this.filename = req.getFilename();
        this.filePath = req.getFilePath();
    }
}
