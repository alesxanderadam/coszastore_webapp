package alticshaw.com.coszastore.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceImp {
    void init();
    boolean save(MultipartFile file);
    Resource loadAsResource(String Filename);
    boolean deleteAll();
    boolean deleteByName(String filename);
    boolean isImage(MultipartFile image);
}
