package com.verve.javatest.controller;

import java.util.Objects;
import java.util.Optional;

import com.google.gson.Gson;
import com.verve.javatest.dao.SupplyDao;
import com.verve.javatest.dao.TagsDao;
import com.verve.javatest.entity.Tags;

import io.javalin.http.Handler;

public class TagsController {
	
	public static Handler createTags = ctx -> {
		Gson gson = new Gson();
		Tags tags = gson.fromJson(ctx.body(), Tags.class);
		TagsDao dao = TagsDao.instance();
		Boolean createStatus = dao.createTags(tags);
		if(createStatus) {
			ctx.html(tags.name + " created with id as " + tags.id);
		} else {
			ctx.html("The Tags can not be created");
		}
	};
	
	public static Handler updateTags = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		Gson gson = new Gson();
		Tags tags = gson.fromJson(ctx.body(), Tags.class);
		TagsDao dao = TagsDao.instance();
		dao.updateTags(id, tags);
		
		if(tags.supplySources != null) {
			SupplyDao supplyDao = SupplyDao.instance();
			supplyDao.associateTags(tags.id, tags.supplySources);
		}
		
		ctx.result("Tags at id " + tags.id + " is updated");
	};
	
	public static Handler fetchAllTagss = ctx -> {
		TagsDao dao = TagsDao.instance();
		Iterable<Tags> allTags = dao.getAllTags();
		ctx.json(allTags);
	};

	public static Handler fetchById = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		TagsDao dao = TagsDao.instance();
		Optional<Tags> tags = dao.getTagsById(id);
		if(tags == null) {
			ctx.html("Not Found");
		} else {
			ctx.json(tags.get());
		}
	};
}
