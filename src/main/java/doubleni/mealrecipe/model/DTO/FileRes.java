package doubleni.mealrecipe.model.DTO;

import doubleni.mealrecipe.model.ImageFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileRes {
    private Long fileId;
    private String origFilename;
    private String filename;
    private String filePath;

    public FileRes(ImageFile file) {
        this.fileId = file.getFileId();
        this.origFilename = file.getOrigFilename();
        this.filename = file.getFilename();
        this.filePath = file.getFilePath();
    }
}
