package com.health.healther.dto.space;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

import com.health.healther.constant.SpaceType;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.model.SpaceKind;
import com.health.healther.dto.coupon.CouponReservationListResponseDto;
import com.health.healther.dto.review.ReviewDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpaceListResponseDto {
	private Long spaceId;

	private String title;

	private int price;

	private String images;

	private Set<SpaceType> spaceTypes;

	private boolean existCoupon;

	private long reviewAverage;

	@Builder
	public SpaceListResponseDto(
			SpaceKind spaceKind,
			boolean existCoupon,
			List<ReviewDto> reviewList
	) {
		Space space = spaceKind.getSpace();
		String imageUrl = null;

		this.spaceId = space.getId();
		this.title = space.getTitle();
		this.price = space.getPrice();

		if (space.getImages() != null && space.getImages().size() > 0) {
			imageUrl = space.getImages().get(0).getImageUrl();
		}
		this.images = imageUrl;

		this.spaceTypes = new HashSet<>(
				space.getSpaceKinds().stream()
						.map(kind -> kind.getSpaceType())
						.collect(Collectors.toList())
		);
		this.existCoupon = existCoupon;

		long average = 0;

		OptionalDouble optionalDouble = reviewList.stream()
				.mapToInt(review -> review.getGrade()).average();

		if (optionalDouble.isPresent()) {
			average = Math.round(optionalDouble.getAsDouble());
		}
		this.reviewAverage = average;
	}
}
