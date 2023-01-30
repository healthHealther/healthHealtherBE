package com.health.healther.dto.board;

import lombok.*;

import javax.validation.constraints.NotBlank;

public class CommentRegisterDto {

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestDto {

        @NotBlank
        private String context;

    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto {

        public ResponseDto(String context) {
            this.commentLength = context.length();
        }

        private int commentLength;
    }
}
