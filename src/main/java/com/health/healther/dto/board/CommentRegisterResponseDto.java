package com.health.healther.dto.board;


import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentRegisterResponseDto {

    private int commentLength;

    public CommentRegisterResponseDto(String context) {

        this.commentLength = context.length();

    }
}
