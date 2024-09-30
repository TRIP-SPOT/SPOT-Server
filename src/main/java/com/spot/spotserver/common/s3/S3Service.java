package com.spot.spotserver.common.s3;

import com.spot.spotserver.common.payload.ErrorCode;
import com.spot.spotserver.common.s3.exception.FileConversionException;
import com.spot.spotserver.common.s3.exception.S3UploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new FileConversionException(ErrorCode.FILE_CONVERSION_FAILED));

        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeTempFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .acl("public-read")
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(uploadFile));

            return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(fileName)).toExternalForm();
        } catch (S3Exception e) {
            log.error("S3에 파일 업로드 실패: {}", e.awsErrorDetails().errorMessage(), e);
            throw new S3UploadException(ErrorCode.S3_UPLOAD_FAILED);
        }
    }

    private void removeTempFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("로컬 임시 파일이 삭제되었습니다.");
        } else {
            log.info("로컬 임시 파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            log.error("MultipartFile의 파일 이름이 null이거나 빈 문자열입니다.");
            return Optional.empty();
        }
        // 특수 문자 제거
        String safeFileName = originalFilename.replaceAll("[\\\\/:*?\"<>|]", "_");

        // 임시 파일 생성
        Path tempFilePath = Files.createTempFile(null, safeFileName);
        File convertFile = tempFilePath.toFile();

        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("파일 쓰기 실패: {}", e.getMessage(), e);
            Files.deleteIfExists(tempFilePath); // 예외 발생 시 임시 파일 삭제
            throw new FileConversionException(ErrorCode.FILE_CONVERSION_FAILED);
        }
        return Optional.of(convertFile);
    }
}