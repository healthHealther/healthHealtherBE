package com.health.healther.dto.board;

import com.health.healther.domain.model.Comment;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponseDto {

    private Long commentId;

    private String nickName;

    private String comment;

    public static CommentListResponseDto from(Comment comment) {
        return CommentListResponseDto.builder()
                .commentId(comment.getId())
                .nickName(comment.getMember().getNickName())
                .comment(comment.getContext())
                .build();
    }
}
