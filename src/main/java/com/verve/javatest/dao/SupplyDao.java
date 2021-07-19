package com.verve.javatest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.verve.javatest.entity.Supply;

public class SupplyDao {
	private List<Supply> supplies = new ArrayList<>();
	private static SupplyDao supplyDao = null;
	
	private SupplyDao() {
		
	}
	
	/**
	 * Singleton instance of a class
	 * @return
	 */
	public static SupplyDao instance() {
		if(supplyDao == null) {
			supplyDao = new SupplyDao();
		}
		
		return supplyDao;
	}
	
	/**
	 * Get supply by id
	 * @param id
	 * @return
	 */
	public Optional<Supply> getSupplyById(int id) {
		return supplies.stream()
				.filter(supply -> supply.id == id)
				.findAny();
	}
	
	/**
	 * Get all available supplies
	 * @return
	 */
	public Iterable<Supply> getAllSupplies() {
		return supplies.stream()
				.map(supply -> supply)
				.collect(Collectors.toList());
	}
	
	/**
	 * Create a new supply
	 * @param supply
	 */
	public void createSupply(Supply supply) {
		supplies.add(supply);
	}
	
	/**
	 * Update an existing supply
	 * @param id
	 * @param supply
	 */
	public void updateSupply(int id, Supply supply) {
		supplies.stream()
		.filter(s -> s.id == id)
		.forEach(s -> {
			if(supply.name.length() > 0) s.name = supply.name;
			if(supply.tags.size() > 0) s. tags = supply.tags;
		});
	}
	
	/**
	 * Associate tagsId to supplies
	 * @param tagsId
	 * @param supplySources
	 */
	public void associateTags(int tagsId, Set<Integer> supplySources) {
		
		for(int supplySource: supplySources) {
			supplies.stream()
			.filter(s -> s.id == supplySource)
			.forEach(s -> s.tags.add(supplySource));
		}
	}
}
