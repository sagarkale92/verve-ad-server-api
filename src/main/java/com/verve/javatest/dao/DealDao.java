package com.verve.javatest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.verve.javatest.entity.Deal;

public class DealDao {
	private List<Deal> deals = new ArrayList<>();
	private static DealDao dealDaoInstance = null;;
	
	private DealDao() {}
	
	/**
	 * Singleton instance of a class
	 * @return
	 */
	public static DealDao instance() {
		if(dealDaoInstance == null) {
			dealDaoInstance = new DealDao();
		}
		
		return dealDaoInstance;
	}
	
	/**
	 * Get a deal by id
	 * @param id
	 * @return
	 */
	public Optional<Deal> getDealById(int id) {
		return deals.stream()
				.filter(deal -> deal.id == id)
				.findAny();
	}
	
	/**
	 * Get all available deals
	 * @return
	 */
	public Iterable<Deal> getAllDeals() {
		return deals.stream()
				.map(deal -> deal)
				.collect(Collectors.toList());
	}
	
	/**
	 * Create a new deal
	 * @param deal
	 */
	public void createDeal(Deal deal) {
		deals.add(deal);
	}
}
