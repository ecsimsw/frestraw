package com.example.frestraw;

import com.example.frestraw.card.CardDuplicateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvisor {

    @ExceptionHandler(CardDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleBindingException(CardDuplicateException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("선택하신 이메일이 이미 사용 중입니다."));
    }
}
