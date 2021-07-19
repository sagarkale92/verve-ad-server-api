package com.verve.javatest.controller;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

import com.verve.javatest.dao.AdDao;
import com.verve.javatest.dao.SupplyDao;
import com.verve.javatest.dao.TagsDao;
import com.verve.javatest.entity.Supply;
import com.verve.javatest.entity.Tags;

import io.javalin.http.Handler;

public class AdController {
	
	public static Handler fetchAdBySupplyId = ctx -> {
		int supplyId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("supplyId")));
		
		SupplyDao supplyDao = SupplyDao.instance();
		Supply supply = supplyDao.getSupplyById(supplyId).get();
		Set<Integer> tagsIds = supply.tags;
		Integer[] tagsIdsArr = tagsIds.toArray(new Integer[tagsIds.size()]);
		
		Random random = new Random();
		int tagsId = tagsIdsArr[random.nextInt(tagsIds.size())];
		
		TagsDao tagsDao = TagsDao.instance();
		Tags tags = tagsDao.getTagsById(tagsId).get();
		
		AdDao adDao = AdDao.instance();
		String result = adDao.getAdByTags(tags);
		
		ctx.result(result);
	};
	
}
