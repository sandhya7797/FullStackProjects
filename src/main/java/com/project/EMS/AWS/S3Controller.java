package com.project.EMS.AWS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class S3Controller {

    @Autowired
    private com.project.EMS.AWS.S3Service s3Service;

    @PostMapping("/createBucket")
    public ResponseEntity<String> createBucket(@RequestParam String bucketName) {
        String createdBucketName = s3Service.createBucket(bucketName);
        return ResponseEntity.status(HttpStatus.OK).body("Bucket created: " + createdBucketName);
    }

    @PostMapping("/setBucketPolicy")
    public ResponseEntity<Void> setBucketPolicy(@RequestParam String bucketName, @RequestBody String policy) {
        s3Service.setBucketPolicy(bucketName, policy);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/upload", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> uploadFile(@RequestBody byte[] fileBytes, @RequestHeader("Content-Type") String contentType, @RequestParam String fileName) throws IOException {
        String res = s3Service.uploadFile(fileBytes, fileName, contentType);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("key") String key) {
        byte[] data = s3Service.downloadFile(key);
        return ResponseEntity.ok()
                .body(data);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("key") String key) {
        // Implement delete logic in S3Service if needed
        String response = s3Service.deleteFile(key);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/deleteBucket")
    public ResponseEntity<Void> deleteBucket(@RequestParam String bucketName) {
        s3Service.deleteBucket(bucketName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
