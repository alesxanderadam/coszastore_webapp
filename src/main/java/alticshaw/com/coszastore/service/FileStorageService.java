package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.exception.CustomException;
import alticshaw.com.coszastore.exception.FileStorageException;
import alticshaw.com.coszastore.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class    FileStorageService implements FileStorageServiceImp {
    @Value("${path.root.directory}")
    private String directory;

    @Override
    public void init() {
        try {
            if (!Files.exists(imagePath())) {
                Files.createDirectories(imagePath());
            }

            if (!Files.exists(otherFilesPath())) {
                Files.createDirectories(otherFilesPath());
            }
        } catch (Exception e) {
            throw new FileStorageException("Unable to create storage directory for files");
        }
    }

    @Override
    public boolean deleteByName(String filename) {
        try {
            Path imageFile = imagePath().resolve(filename);
            Path otherFiles = otherFilesPath().resolve(filename);

            if (Files.exists(imageFile)) {
                Files.delete(imageFile);
            } else if (Files.exists(otherFiles)) {
                Files.delete(otherFiles);
            } else {
                throw new FileStorageException("A file of that name is not exist.");
            }
            return true;
        } catch (Exception e) {
            throw new FileStorageException("Could not delete " + filename);
        }

    }


    @Override
    public String save(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String filename = file.getOriginalFilename();
            if (filename == null || filename.trim().isEmpty()) {
                throw new FileStorageException("Filename can not be null or empty");
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
            String savedFileName = now.format(formatter) + "_" + filename;

            if (isImage(file)) {
                Files.copy(inputStream, imagePath().resolve(savedFileName));
            } else {
                Files.copy(inputStream, otherFilesPath().resolve(savedFileName));
            }
            return savedFileName;
        } catch (Exception e) {
            throw new CustomException("Unable to upload file.");
        }
    }

    @Override
    public List<String> uploadAndStoreMultipleImages(List<MultipartFile> files) {
        List<String> savedFileNames = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                try (InputStream inputStream = file.getInputStream()) {
                    String filename = file.getOriginalFilename();
                    if (filename == null || filename.trim().isEmpty()) {
                        throw new FileStorageException("Filename can not be null or empty");
                    }

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                    String savedFileName = now.format(formatter) + "_" + filename;

                    if (isImage(file)) {
                        Files.copy(inputStream, imagePath().resolve(savedFileName));
                    } else {
                        Files.copy(inputStream, otherFilesPath().resolve(savedFileName));
                    }

                    savedFileNames.add(savedFileName);
                } catch (Exception e) {
                    throw new CustomException("Unable to upload files. " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new CustomException("Unable to upload files. " + e.getMessage()); //Server error -> 500
        }

        return savedFileNames;
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path imageFile = imagePath().resolve(filename);
            Path otherFiles = otherFilesPath().resolve(filename);
            Resource imageResource = new UrlResource(imageFile.toUri());
            Resource otherFilesResource = new UrlResource(otherFiles.toUri());

            if (imageResource.exists() || imageResource.isReadable()) {
                return imageResource;
            } else if (otherFilesResource.exists() || otherFilesResource.isReadable()) {
                return otherFilesResource;
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

    @Override
    public boolean isImage(MultipartFile image) {
        try (InputStream inputStream = image.getInputStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return bufferedImage != null;
        } catch (IOException e) {
            throw new FileStorageException(image.getOriginalFilename() + " is not an image or something wrong happen!");
        }
    }

    private Path imagePath() {
        return Paths.get(this.directory + "\\images");
    }

    private Path otherFilesPath() {
        return Paths.get(directory + "\\others");
    }

    public String getImageDirectoryPath() {
        return this.directory + "\\images";
    }

    public String getOtherFilesDirectoryPath() {
        return this.directory + "\\ others";
    }

}
