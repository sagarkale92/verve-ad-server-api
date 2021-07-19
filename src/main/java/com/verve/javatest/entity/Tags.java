package com.verve.javatest.entity;

import java.util.HashSet;
import java.util.Set;

public class Tags {
	public int id;
	public String name = "";
	public String tagUrl = "";
	public Integer dealId = null;
	public Set<Integer> supplySources = new HashSet<>();
}
