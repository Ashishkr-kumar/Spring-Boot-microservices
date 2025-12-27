package com.aws.s3.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aws.s3.model.FileDetails;
import com.aws.s3.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
    
    @Autowired
    private FileService fileService;

    /**
     * Handles the file upload request to an S3 bucket.
     * This endpoint is triggered by a POST request to "/upload".
     *
     * @param file The file to be uploaded, passed as a multipart form data.
     * @return A {@link ResponseEntity} containing a success message upon successful upload.
     * @throws Exception if an error occurs during the file upload to the S3 service,
     *                   such as issues with AWS credentials or network problems.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        fileService.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    /**
     * Handles the file download request from an S3 bucket.
     * This endpoint is triggered by a GET request to "/download".
     *
     * @param fileName The name of the file to be downloaded, passed as a request parameter.
     * @return A {@link ResponseEntity} containing the file's data as a byte array.
     *         The response includes a "Content-Disposition" header to prompt the browser
     *         to download the file with the given name.
     * @throws Exception if an error occurs during the file retrieval from the S3 service,
     *                   such as the file not being found or an issue with AWS credentials.
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String fileName) throws Exception {
        byte[] data = fileService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(data);
    }

    /**
     * Handles the request to list all files in the S3 bucket.
     *
     * @return A {@link ResponseEntity} containing a list of file details, including names and sizes.
     */
    @GetMapping("/list")
    public ResponseEntity<List<FileDetails>> listFiles() {
        return ResponseEntity.ok(fileService.listFiles());
    }

    /**
     * Handles the file deletion request from the S3 bucket.
     *
     * @param fileName The name of the file to be deleted.
     * @return A {@link ResponseEntity} with a success message.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        fileService.deleteFile(fileName);
        return ResponseEntity.ok("File deleted successfully");
    }
}