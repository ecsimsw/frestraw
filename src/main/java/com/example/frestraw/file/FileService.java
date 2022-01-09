package com.example.frestraw.file;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class FileService {

    private static final String ROOT_PATH = "/resources";
    private static final String DEFAULT_FILE_NAME = "default.png";
    private static final String PHOTO_UPLOAD_FOLDER = "card-photos";

    public String saveImageFile(MultipartFile multipartFile) throws IOException {
        if (Objects.isNull(multipartFile)) {
            return ROOT_PATH + "/" + DEFAULT_FILE_NAME;
        }
        final String imageName = LocalDateTime.now() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        FileUploadUtil.saveFile(PHOTO_UPLOAD_FOLDER, imageName, multipartFile);
        return ROOT_PATH + "/" + imageName;
    }
}