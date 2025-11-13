package com.demo_security.demo_security.service.file;

import com.demo_security.demo_security.model.UploadedFile;
import com.demo_security.demo_security.repository.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.demo_security.demo_security.service.common.GenericSearchService;
import com.demo_security.demo_security.payload.file.FileSearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Sort;
 
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private UploadedFileRepository uploadedFileRepository;

    @Value("${aws.s3.bucket-name:demo-security-uploads}")
    private String bucketName;

    @Value("${aws.s3.region:us-east-1}")
    private String region;

    @Value("${aws.s3.access-key:}")
    private String accessKey;

    @Value("${aws.s3.secret-key:}")
    private String secretKey;

    private S3Client s3Client;

    @Autowired
    public void initializeS3Client() {
        if (accessKey != null && !accessKey.isEmpty() && secretKey != null && !secretKey.isEmpty()) {
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
            this.s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                    .build();
        }
    }

    public Page<UploadedFile> searchFiles(FileSearchCriteria criteria, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<UploadedFile> spec = Specification.where(null);
        if (criteria.getFilename() != null && !criteria.getFilename().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("filename"), "%" + criteria.getFilename() + "%"));
        }
        // Thêm các điều kiện filter khác nếu cần
        return GenericSearchService.search((JpaSpecificationExecutor<UploadedFile>) uploadedFileRepository, spec, pageable, f -> f);
    }

    @Override
    public UploadedFile uploadFile(MultipartFile file) {
        try {
            if (s3Client == null) {
                throw new RuntimeException("AWS S3 credentials not configured. Please set AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY environment variables.");
            }

            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            
            // Upload to S3
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType(file.getContentType())
                    .build();
            
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            
            String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, uniqueFileName);

            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setFileName(file.getOriginalFilename());
            uploadedFile.setFileUrl(fileUrl);
            uploadedFile.setPublicId(uniqueFileName);
            uploadedFile.setFileType(file.getContentType());
            uploadedFile.setFileSize(file.getSize());
            uploadedFile.setUploadedAt(LocalDateTime.now());
            return uploadedFileRepository.save(uploadedFile);
        } catch (IOException e) {
            throw new RuntimeException("File upload to S3 failed", e);
        }
    }

    @Override
    public List<UploadedFile> getAllFiles() {
        return uploadedFileRepository.findAll();
    }

    @Override
    public UploadedFile getFile(Long id) {
        return uploadedFileRepository.findById(id).orElse(null);
    }

    @Override
    public Page<UploadedFile> getFiles(Pageable pageable) {
        return uploadedFileRepository.findAll(pageable);
    }

    @Override
    public void deleteFile(Long id) {
        UploadedFile file = getFile(id);
        if (file != null && s3Client != null) {
            try {
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getPublicId())
                        .build();
                
                s3Client.deleteObject(deleteObjectRequest);
                uploadedFileRepository.deleteById(id);
            } catch (Exception e) {
                throw new RuntimeException("File delete from S3 failed", e);
            }
        }
    }
}
