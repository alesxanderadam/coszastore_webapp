package alticshaw.com.coszastore.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageServiceImp {
    void init();

    String save(MultipartFile file);

    List<String> uploadAndStoreMultipleImages(List<MultipartFile> file);

    Resource loadAsResource(String Filename);

    boolean deleteAll();

    boolean deleteByName(String filename);

    boolean isImage(MultipartFile image);
    String getImageDirectoryPath();
    String getOtherFilesDirectoryPath();
}
