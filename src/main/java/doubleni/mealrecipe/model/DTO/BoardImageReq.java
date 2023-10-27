package doubleni.mealrecipe.model.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardImageReq {
    private List<MultipartFile> files;
}
