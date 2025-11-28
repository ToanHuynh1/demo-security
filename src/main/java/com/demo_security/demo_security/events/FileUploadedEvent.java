package com.demo_security.demo_security.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadedEvent {
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String uploadedBy;
}