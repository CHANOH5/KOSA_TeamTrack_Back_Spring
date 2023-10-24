package com.my.util;

import java.util.Comparator;

import com.my.rank.dto.RankDTO;

public class ValueComparator implements Comparator<RankDTO>{

	@Override
	public int compare(RankDTO dto1, RankDTO dto2) {
//		return dto2.getTotalScore().compareTo(dto1.getTotalScore());
		return Double.compare(dto2.getTotalScore(), dto1.getTotalScore());
	}

}
