package com.demo_security.demo_security.controller.file;

import com.demo_security.demo_security.model.UploadedFile;
import com.demo_security.demo_security.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import  org.springframework.data.domain.Pageable;
import  org.springframework.data.domain.PageRequest;

@RestController
@Tag(name = "File", description = "File management endpoints")
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadedFile> uploadFile(@RequestParam("file") MultipartFile file) {
        UploadedFile uploadedFile = fileService.uploadFile(file);
        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping
    public ResponseEntity<?> getFiles(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(fileService.getFiles(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UploadedFile> getFile(@PathVariable Long id) {
        UploadedFile file = fileService.getFile(id);
        if (file == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}
