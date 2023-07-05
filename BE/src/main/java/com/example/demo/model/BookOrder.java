package com.example.demo.model;

public class BookOrder {
	private int Id_order;
	private String Order_code;
	private String Order_date;
	private int Total_price;
	private String status;
	private String note;
	private String Id_customer;
	private String Id_branch;
	private String Id_employee;
	
	public BookOrder() {
		
	}
	
	public BookOrder(int id_order, String order_code, String order_date, int total_price, String status, String note,
			String id_customer, String id_branch, String id_employee) {
		super();
		this.Id_order = id_order;
		this.Order_code = order_code;
		this.Order_date = order_date;
		this.Total_price = total_price;
		this.status = status;
		this.note = note;
		this.Id_customer = id_customer;
		this.Id_branch = id_branch;
		this.Id_employee = id_employee;
	}

	public int getId_order() {
		return Id_order;
	}

	public void setId_order(int id_order) {
		Id_order = id_order;
	}

	public String getOrder_code() {
		return Order_code;
	}

	public void setOrder_code(String order_code) {
		Order_code = order_code;
	}

	public String getOrder_date() {
		return Order_date;
	}

	public void setOrder_date(String order_date) {
		Order_date = order_date;
	}

	public int getTotal_price() {
		return Total_price;
	}

	public void setTotal_price(int total_price) {
		Total_price = total_price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getId_customer() {
		return Id_customer;
	}

	public void setId_customer(String id_customer) {
		Id_customer = id_customer;
	}

	public String getId_branch() {
		return Id_branch;
	}

	public void setId_branch(String id_branch) {
		Id_branch = id_branch;
	}

	public String getId_employee() {
		return Id_employee;
	}

	public void setId_employee(String id_employee) {
		Id_employee = id_employee;
	}
	
	
	
}
