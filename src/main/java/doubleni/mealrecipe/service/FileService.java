package doubleni.mealrecipe.service;

import doubleni.mealrecipe.model.DTO.FileReq;
import doubleni.mealrecipe.model.File;
import doubleni.mealrecipe.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FileReq fileDto) {
        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FileReq getFile(Long id) {
        File file = fileRepository.findById(id).get();

        return FileReq.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
    }
}
