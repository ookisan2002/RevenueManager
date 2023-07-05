package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.Dao;
import com.example.demo.model.BookOrder;
import com.example.demo.model.Employee;
import com.example.demo.model.MonthRevenue;

@CrossOrigin
@RestController
public class RevenueController {
	private Dao dao = new Dao();
	@GetMapping("/orders")
	public List<BookOrder> getOrder(){
		List<BookOrder> BookOrders = dao.selectAllOrder();
		return  BookOrders;
		
	}
	@GetMapping("/orders/{year}")
	public List<MonthRevenue> getTotalByYear(Model model, @PathVariable String year){
		model.addAttribute("year", year);
		List<MonthRevenue> monthRevenue = dao.selectTotalByYear(year);
		return  monthRevenue;
		
	}
	@GetMapping("/account/{email}")
	public Employee getEmployee(Model model, @PathVariable String email) {
		model.addAttribute("email", email);
		Employee acc = dao.selectAccount(email);
		return acc;
	}
	@DeleteMapping("/delete/{id}")
	public boolean delete(Model model, @PathVariable String id) {
		model.addAttribute("id", id);
		return dao.deleteOrder(id);
	}
//	@GetMapping("/employees")
//	public List<Employee> getEmployee(){
//		List<Employee> employee = dao.selectAllEmployee();
//		return  employee;
//		
//	}
//	@GetMapping("/employee/{id}")
//	public Employee getEmployee(Model model, @PathVariable String id) {
//		model.addAttribute("id", id);
//		Employee employee = dao.selectEmployeeById(id);
//		return employee;
//	}
//	@PostMapping("/add/{id}")
//	public Employee addEmployee( Employee employee,@PathVariable String id) {
//		try {
//			boolean success = dao.insertEmployee(employee,id);
//			if (success) {
//				return employee;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//	}
//
//	@PutMapping("/update/{id}")
//	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable int id) {
//	    try {
//	        employee.setId(id);
//	        boolean success = dao.updateEmployee(employee);
//	        if (success) {
//	            return employee;
//	        }
//	    } catch (Exception e) {
//	        // TODO: handle exception
//	    }
//	    return null;
//	}

	
}
