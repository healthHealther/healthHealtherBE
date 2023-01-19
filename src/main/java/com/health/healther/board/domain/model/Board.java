package com.health.healther.board.domain.model;

import javax.persistence.*;

import com.health.healther.domain.model.BaseEntity;
import com.health.healther.domain.model.Comment;
import com.health.healther.domain.model.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE BOARD SET BOARD.DELETED_AT = CURRENT_TIMESTAMP WHERE BOARD.BOARD_ID = ?")
@Getter
@Builder
@Entity
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOARD_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@OneToMany(mappedBy = "board")
	private List<Comment> comments = new ArrayList<>();

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "LIKE_COUNT")
	private int likeCount;
}
