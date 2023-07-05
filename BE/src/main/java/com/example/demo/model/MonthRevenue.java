package com.example.demo.model;

public class MonthRevenue {
	private int month;
	private int revenue;
	public MonthRevenue() {
		
	}
	public MonthRevenue(int month, int revenue) {

		this.month = month;
		this.revenue = revenue;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	
}
