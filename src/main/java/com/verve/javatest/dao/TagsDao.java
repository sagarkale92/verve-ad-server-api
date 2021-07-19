package com.verve.javatest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.verve.javatest.entity.Tags;

public class TagsDao {
	private List<Tags> tagsList = new ArrayList<>();
	public static TagsDao tagsDaoInstance;
	
	private TagsDao() {
		
	}
	
	/**
	 * Singleton instance of a class
	 * @return
	 */
	public static TagsDao instance() {
		if(tagsDaoInstance == null) {
			tagsDaoInstance = new TagsDao();
		}
		
		return tagsDaoInstance;
	}
	
	/**
	 * Get Tags by Id
	 * @param id
	 * @return Tags
	 */
	public Optional<Tags> getTagsById(int id) {
		return tagsList.stream()
				.filter(tags -> tags.id == id)
				.findAny();
	}
	
	/**
	 * Get all available tags
	 * @return Tags
	 */
	public Iterable<Tags> getAllTags() {
		return tagsList.stream()
				.map(tags -> tags)
				.collect(Collectors.toList());
	}
	
	/**
	 * Create a new tags
	 * @param tags
	 * @return Boolean
	 */
	public Boolean createTags(Tags tags) {
		if(tags != null && tags.dealId != null) {
			tagsList.add(tags);
			return true; 
		} else {
			return false;
		}
	}
	
	/**
	 * Update an existing tags
	 * @param id
	 * @param tags
	 */
	public void updateTags(int id, Tags tags) {
		tagsList.stream()
		.filter(t -> t.id == id)
		.forEach(t -> {
			if(tags.name.length() > 0) t.name = tags.name;
			if(tags.tagUrl.length() > 0) t.tagUrl = tags.tagUrl;
			if(tags.dealId > 0) t.dealId = tags.dealId;
			if(tags.supplySources.size() > 0) t.supplySources = tags.supplySources;
		});
	}
	
	/**
	 * Associate supply Id to all tags
	 * @param supplyId
	 * @param tagsIds
	 */
	public void associateSupply(int supplyId, Set<Integer> tagsIds) {
		
		for(int tagsId: tagsIds) {
			tagsList.stream()
			.filter(tags -> tags.id == tagsId)
			.forEach(tags -> tags.supplySources.add(supplyId));
		}
	}
}
