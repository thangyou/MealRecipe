package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.Files;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileReq {
    private Long fileId;
    private String origFilename;
    private String filename;
    private String filePath;

    public Files toEntity() {
        return Files.builder()
                .fileId(fileId)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
    }

    @Builder
    public FileReq(Long fileId, String origFilename, String filename, String filePath) {
        this.fileId = fileId;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
