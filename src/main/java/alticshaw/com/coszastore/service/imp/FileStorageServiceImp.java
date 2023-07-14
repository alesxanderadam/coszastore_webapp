package alticshaw.com.coszastore.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceImp {
    void init();
    void save(MultipartFile file);
    Resource loadAsResource(String Filename);
    void deleteAll();
    void deleteByName(String filename);
}
