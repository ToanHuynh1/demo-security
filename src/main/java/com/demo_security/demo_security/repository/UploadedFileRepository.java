package com.demo_security.demo_security.repository;

import com.demo_security.demo_security.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
}
