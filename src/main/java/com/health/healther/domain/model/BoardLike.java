package com.health.healther.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE BOARD_LIKE SET BOARD_LIKE.DELETED_AT = CURRENT_TIMESTAMP WHERE BOARD_LIKE.BOARD_LIKE_ID = ?")
@Getter
public class BoardLike extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOARD_LIKE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOARD_ID")
	private Board board;

	@Column(name = "IS_LIKED")
	private boolean isLiked;
}
