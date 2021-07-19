package com.verve.javatest.controller;

import java.util.Objects;
import java.util.Optional;

import com.google.gson.Gson;
import com.verve.javatest.dao.SupplyDao;
import com.verve.javatest.dao.TagsDao;
import com.verve.javatest.entity.Supply;

import io.javalin.http.Handler;

public class SupplyController {
	
	public static Handler createSupply = ctx -> {
		Gson gson = new Gson();
		Supply supply = gson.fromJson(ctx.body(), Supply.class);
		SupplyDao dao = SupplyDao.instance();
		dao.createSupply(supply);
		
		TagsDao tagsDao = TagsDao.instance();
		tagsDao.associateSupply(supply.id, supply.tags);
		
		ctx.result(supply.name + " created with id as " + supply.id);
	};
	
	public static Handler updateSupply = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		Gson gson = new Gson();
		Supply supply = gson.fromJson(ctx.body(), Supply.class);
		SupplyDao dao = SupplyDao.instance();
		dao.updateSupply(id, supply);
		
		if(supply.tags != null) {
			TagsDao tagsDao = TagsDao.instance();
			tagsDao.associateSupply(supply.id, supply.tags);
		}
		
		ctx.result("Supply at id " + id + " is updated");
	};
	
	public static Handler fetchAllSupplies = ctx -> {
		SupplyDao dao = SupplyDao.instance();
		Iterable<Supply> allSupplies = dao.getAllSupplies();
		ctx.json(allSupplies);
	};

	public static Handler fetchById = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		SupplyDao dao = SupplyDao.instance();
		Optional<Supply> supply = dao.getSupplyById(id);
		if(supply == null) {
			ctx.html("Not Found");
		} else {
			ctx.json(supply.get());
		}
	};
}
