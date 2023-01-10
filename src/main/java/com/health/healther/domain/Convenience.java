package com.health.healther.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.health.healther.constant.ConvenienceType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT is null")
@SQLDelete(sql = "UPDATE SPACE SET SAPCE.DELETED_AT = CURRENT_TIMESTAMP WHERE SPACE.SAPCE_ID = ?")
@Getter
public class Convenience {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONVENIENCE_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "SPACE_ID")
	private Space space;

	@Enumerated(EnumType.STRING)
	private ConvenienceType convenienceType;
}
