package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileStorageServiceImp fileStorageServiceImp;

    @Autowired
    public FileController(FileStorageServiceImp fileStorageServiceImp) {
        this.fileStorageServiceImp = fileStorageServiceImp;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> save(@RequestParam MultipartFile file) {
        boolean isSuccess =  fileStorageServiceImp.save(file);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Upload " + file.getOriginalFilename() + " successfully");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/load/{filename}")
    public ResponseEntity<?> load(@PathVariable String filename) {
        Resource resource = fileStorageServiceImp.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/delete/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        boolean isSuccess =  fileStorageServiceImp.deleteByName(filename);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete " + filename + " successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/delete/all")
    public ResponseEntity<?> deleteAll() {
        boolean isSuccess = fileStorageServiceImp.deleteAll();
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete all files successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}