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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE IMAGE SET IMAGE.DELETED_AT = CURRENT_TIMESTAMP WHERE IMAGE.IMAGE_ID = ?")
@Getter
public class Image extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMAGE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	private Image(
			Space space,
			String imageUrl
	) {
		this.space = space;
		this.imageUrl = imageUrl;
	}

	public static Image of(
			Space space,
			String imageUrl
	) {
		return new Image(
				space,
				imageUrl
		);
	}
}
