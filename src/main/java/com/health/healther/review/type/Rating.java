package com.health.healther.review.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rating {

    PERFECT("매우 훌륭해요",5),
    GREAT("훌륭해요",4),
    GOOD("만족해요",3),
    BAD("그저 그래요",2),
    VERY_BAD("최악이에요",1);

    private final String comment;
    private final Integer grade;

}
