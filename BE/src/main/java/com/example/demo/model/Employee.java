package com.example.demo.model;

public class Employee {
	private String Email;
	private String DOB;
	public Employee() {
		
	}
	public Employee(String email, String dOB) {
		super();
		Email = email;
		DOB = dOB;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
}
