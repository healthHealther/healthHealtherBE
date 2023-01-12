package com.health.healther.review.domain.entity;


import com.health.healther.review.type.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REVIEW")
@Where(clause = "isDeleted = false")
@SQLDelete(sql = "UPDATE REVIEW SET is_deleted = true where id = ?")
@Entity

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID", nullable = false)
    private Long id;


    @Column(name = "REVIEW_TITLE", nullable = false) // 제목은 필수
    private String title;

    @Column(name = "REVIEW_CONTENT")
    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "SPACE_ID")
//    private Space space;

    @Column(name = "GRADE")
    private Rating rating;

    @Column(name = "IS_DELETED")
    private boolean isDeleted = Boolean.FALSE;


}
