package com.demo_security.demo_security.service.file;

import com.demo_security.demo_security.model.UploadedFile;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileService {
    UploadedFile uploadFile(MultipartFile file);
    List<UploadedFile> getAllFiles();
    Page<UploadedFile> getFiles(Pageable pageable);
    UploadedFile getFile(Long id);
    void deleteFile(Long id);
}
