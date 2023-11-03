package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.ImageFile;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileReq {
    private Long fileId;
    private String origFilename;
    private String filename;
    private String filePath;

    public ImageFile toEntity() {
        return ImageFile.builder()
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
