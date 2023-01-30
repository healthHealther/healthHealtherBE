package com.health.healther.dto.board;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardIsLikedResponseDto {
    private boolean isLiked;

    public static BoardIsLikedResponseDto of(boolean isLiked) {
        return BoardIsLikedResponseDto.builder()
                .isLiked(isLiked)
                .build();
    }
}
