package com.health.healther.review.domain.model;

import javax.persistence.*;

import com.health.healther.domain.model.BaseEntity;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Space;
import com.health.healther.review.domain.dto.ReviewCreateRequestDto;
import com.health.healther.review.domain.repository.ReviewRepository;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE REVIEW SET REVIEW.DELETED_AT = CURRENT_TIMESTAMP WHERE REVIEW.REVIEW_ID = ?")
@Getter
@Builder
@Entity
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REVIEW_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@Column(name = "TITLE")
	private String title;

	@Lob
	@Column(name = "CONTENT")
	private String content;

	@Column(name = "GRADE")
	private int grade;

	public void updateReview(String content, int grade) {

		this.content = content;
		this.grade = grade;
	}


}
