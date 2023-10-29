package doubleni.mealrecipe.service;

import doubleni.mealrecipe.config.MD5Generator;
import doubleni.mealrecipe.config.exception.BaseException;
import doubleni.mealrecipe.model.BaseEntity;
import doubleni.mealrecipe.model.Board;
import doubleni.mealrecipe.model.DTO.FileReq;
import doubleni.mealrecipe.model.Files;
import doubleni.mealrecipe.repository.FileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FileReq fileReq) {
        return fileRepository.save(fileReq.toEntity()).getFileId();

    }

    @Transactional
    public void updateFile(Long fileId, MultipartFile files) {
        Optional<Files> findFile = fileRepository.findByFileId(fileId);

        if (findFile.isPresent()) {

            FileReq updateFile = new FileReq();
//            updateFile.setFileId(fileId);

            System.out.println("--- update 전 ---");
            System.out.println("OrigFilename : " + findFile.get().getOrigFilename());
            System.out.println("Filename : " + findFile.get().getFilename());
            System.out.println("FilePath : " + findFile.get().getFilePath());

            try {
                String origFilename = files.getOriginalFilename();
                String filename = new MD5Generator(origFilename).toString();
                String savePath = System.getProperty("user.dir") + "\\files";
                 if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch(Exception e){
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                files.transferTo(new File(filePath));

                updateFile.setOrigFilename(origFilename);
                updateFile.setFilename(filename);
                updateFile.setFilePath(filePath);

                findFile.get().updateFile(updateFile);
                updateFile.setFileId(fileId);

                System.out.println("--- update 후 ---");
                System.out.println("OrigFilename : " + updateFile.getOrigFilename());
                System.out.println("Filename : " + updateFile.getFilename());
                System.out.println("FilePath : " + updateFile.getFilePath());


                ResponseEntity.ok("-> 파일 수정 완료");

            } catch (IOException | NoSuchAlgorithmException exception) {
                ResponseEntity.badRequest().body(exception.getMessage());
            }

        } else {
//            findFile.get().updateFile(req);
            ResponseEntity.badRequest().body("not found : " + fileId);
        }
    }

    @Transactional
    public FileReq getFile(Long fileId) {
        Files files = fileRepository.findByFileId(fileId).get();

        return FileReq.builder()
                .fileId(fileId)
                .origFilename(files.getOrigFilename())
                .filename(files.getFilename())
                .filePath(files.getFilePath())
                .build();
    }

}
