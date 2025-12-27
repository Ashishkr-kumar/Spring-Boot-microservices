package com.aws.s3.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.s3.model.FileDetails;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

/**
 * Service class for handling file operations with an AWS S3 bucket.
 * This class provides methods to upload and download files from the S3 bucket
 * specified in the application properties.
 */
@Service
public class FileService {
    
    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public void uploadFile(MultipartFile file) throws Exception 
   {
        s3Client.putObject(builder -> builder.bucket(bucketName).key(file.getOriginalFilename()).build(),
                RequestBody.fromBytes(file.getBytes()));
    }

    /**
     * Downloads a file from an AWS S3 bucket.
     * <p>
     * The file is identified by its key (filename) in the S3 bucket.
     *
     * @param fileName The name of the file to download.
     * @return A byte array containing the contents of the downloaded file.
     * @throws Exception if the download from S3 fails.
     */
    public byte[] downloadFile(String fileName) throws Exception {
        return s3Client.getObjectAsBytes(builder -> builder.bucket(bucketName).key(fileName).build()).asByteArray(); 
    }

    /**
     * Lists all files in the S3 bucket.
     *
     * @return A list of file names.
     */
    public List<FileDetails> listFiles() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder().bucket(bucketName).build();
        return s3Client.listObjectsV2(listObjectsV2Request).contents().stream()
                .map(s3Object -> new FileDetails(s3Object.key(), s3Object.size()))
                .collect(Collectors.toList());
    }

    /**
     * Deletes a file from the S3 bucket.
     *
     * @param fileName The name of the file to delete.
     */
    public void deleteFile(String fileName) {
        DeleteObjectRequest deleteReq = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteReq);
    }
}
