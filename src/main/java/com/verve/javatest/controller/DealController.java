package com.verve.javatest.controller;

import java.util.Objects;
import java.util.Optional;

import com.google.gson.Gson;
import com.verve.javatest.dao.DealDao;
import com.verve.javatest.entity.Deal;

import io.javalin.http.Handler;

public class DealController {
	
	public static Handler createDeal = ctx -> {
		Gson gson = new Gson();
		Deal deal = gson.fromJson(ctx.body(), Deal.class);
		DealDao dao = DealDao.instance();
		dao.createDeal(deal);
		ctx.result(deal.name + " created with id as " + deal.id);
	};
	
	public static Handler fetchAllDeals = ctx -> {
		DealDao dao = DealDao.instance();
		Iterable<Deal> allDeals = dao.getAllDeals();
		ctx.json(allDeals);
	};
	
	public static Handler fetchDealById = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		DealDao dealDao = DealDao.instance();
		Optional<Deal> deal = dealDao.getDealById(id);
		if(deal == null) {
			ctx.html("Not Found");
		} else {
			ctx.json(deal.get());
		}
	};
}
