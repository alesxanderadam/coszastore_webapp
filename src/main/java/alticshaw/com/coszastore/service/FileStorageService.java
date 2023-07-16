package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.exception.FileStorageException;
import alticshaw.com.coszastore.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService implements FileStorageServiceImp {
    @Value("${path.root.directory}")
    private String directory;

    @Override
    public void init() {
        Path directoryPath = Paths.get(directory);
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
        } catch (Exception e) {
            throw new FileStorageException("Unable to create storage directory for files");
        }
    }

    @Override
    public boolean deleteByName(String filename) {
        try {
            Path file = Paths.get(this.directory).resolve(filename);
            if (!Files.exists(file)) {
                throw new FileStorageException("A file of that name is not exist.");
            }
            Files.delete(file);
            return true;
        } catch (Exception e) {
            throw new FileStorageException("Could not delete " + filename);
        }

    }


    @Override
    public boolean save(MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            if (filename == null) {
                throw new FileStorageException("Filename can not be null.");
            }
            Files.copy(file.getInputStream(), Paths.get(this.directory).resolve(file.getOriginalFilename()));
            return true;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new FileStorageException("A file of that name already exists.");
            }
            throw new FileStorageException("Unable to upload file.");
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = Paths.get(this.directory).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new FileStorageException("Could not load the file!");
        }
    }

    @Override
    public boolean deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(this.directory).toFile());
        init();
        return true;
    }
}
