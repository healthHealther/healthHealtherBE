package com.health.healther.domain.model;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE BOARD SET BOARD.DELETED_AT = CURRENT_TIMESTAMP WHERE BOARD.BOARD_ID = ?")
@Getter
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOARD_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@OneToMany
	@JoinColumn
	private List<Comment> comments = new ArrayList<>();

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "LIKE_COUNT")
	private int likeCount;
  
  public void likeBoard() {
		this.likeCount += 1;
	}
  
	public void deleteBoardLike() {
		this.likeCount -= 1;
    
  }
}
