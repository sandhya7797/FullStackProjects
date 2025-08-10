package com.project.EMS.AWS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(S3Client s3) {
        this.s3 = s3;
    }

    public String createBucket(String bucketName) {
        s3.createBucket(b -> b.bucket(bucketName));
        return bucketName;
    }

    public void setBucketPolicy(String bucketName, String bucketPolicy) {
        PutBucketPolicyRequest bucketPolicyRequest = PutBucketPolicyRequest.builder()
                .bucket(bucketName)
                .policy(bucketPolicy)
                .build();

        s3.putBucketPolicy(bucketPolicyRequest);
    }


    public String uploadFile(byte[] file, String fileName, String contentType) throws IOException {
        String key = fileName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        s3.putObject(request, RequestBody.fromBytes(file));

        return key;
    }

    public byte[] downloadFile(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> object = s3.getObjectAsBytes(request);
        return object.asByteArray();
    }

    public String deleteFile(String key) {
        // Implement delete logic if needed
        s3.deleteObject(b -> b.bucket(bucketName).key(key));
        return key;
    }

    public void deleteBucket(String bucketName) {
        // Implement bucket deletion logic if needed
        s3.deleteBucket(b -> b.bucket(bucketName));

    }

}
