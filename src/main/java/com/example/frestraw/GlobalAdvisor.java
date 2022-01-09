package com.example.frestraw;

import com.example.frestraw.card.CardDuplicateException;
import com.example.frestraw.file.UploadFileException;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvisor {

    @ExceptionHandler(CardDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleCardDuplicateException(CardDuplicateException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("선택하신 이메일이 이미 사용 중입니다."));
    }

    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<ErrorResponse> handleUploadFileException(UploadFileException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("사진을 업로드하는 도중 오류가 발생했습니다."));
    }
}
