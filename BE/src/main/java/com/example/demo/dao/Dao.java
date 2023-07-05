package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

//import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.BookOrder;
import com.example.demo.model.Employee;
import com.example.demo.model.MonthRevenue;


public class Dao {
//	private String jdbcURL="jdbc:sqlserver://26.90.39.231;databaseName=QuanLyBanSach;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
	private String jdbcURL="jdbc:sqlserver://DESKTOP-6IF31R0\\CSDLPTN1;databaseName=NgheAn_Dai;encrypt=true;trustServerCertificate=true";

	private String jdbcUsername="sa";
	private String jdbcPass="123";

	
	private static final String selectAllOrder=" SELECT o.Id_order, o.Order_code, o.Order_date, o.Total_price, o.status, o.note, c.Name AS customer_name, b.Address AS branch_address, e.Name AS employee_name FROM tblOrder o INNER JOIN Customer c ON o.Id_customer = c.Id_customer INNER JOIN Branch b ON o.Id_branch = b.Id_branch INNER JOIN Employee e ON o.Id_employee = e.Id_employee;";
	private static final String selectTotalByYear="  SELECT MONTH(Order_date) AS Month,COALESCE(SUM(Total_price), 0) AS Total FROM dbo.tblOrder WHERE YEAR(Order_date) = ? GROUP BY MONTH(Order_date)";
	private static final String selectEmployeeByEmail="SELECT * FROM dbo.Employee WHERE Email = ? AND Position = 'manage';";
	private static final String deleteOrderTransaction="BEGIN TRANSACTION BEGIN TRY DELETE FROM dbo.Use_voucher WHERE Id_order = ? DELETE FROM dbo.Order_book WHERE Id_order = ? DELETE FROM dbo.tblOrder WHERE Id_order = ? COMMIT TRANSACTION END TRY BEGIN CATCH ROLLBACK TRANSACTION END CATCH";
	
	public Dao(){
	}
	
	protected Connection getConnection() {
		Connection connection = null;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPass);
				System.out.print("Okie!!!!!!");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return connection;
	}
	//view all employee
	public List<BookOrder> selectAllOrder(){
		List<BookOrder> BookOrders = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(selectAllOrder);)
		{
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int Id_order = rs.getInt("Id_order");
				String Order_code = rs.getString("Order_code");
				String Order_date = rs.getString("Order_date");
				int Total_price = rs.getInt("Total_price");
				String status = rs.getString("status");
				String note = rs.getString("note");
				String customer = rs.getString("customer_name");
				String branch = rs.getString("branch_address");
				String employee = rs.getString("employee_name");
				BookOrders.add(new BookOrder(Id_order,Order_code,Order_date,Total_price,status,note,customer,branch,employee));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
	         
		}
		return BookOrders;
	}
	//view order
	public List<MonthRevenue> selectTotalByYear(@PathVariable String year){
		List<MonthRevenue> monthRevenue = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(selectTotalByYear);)
		{
			ps.setString(1, year);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int month = rs.getInt("Month");
				int revenue = rs.getInt("Total");		
				monthRevenue.add(new MonthRevenue(month,revenue));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
	         
		}
		return monthRevenue;
		
	}
	public Employee selectAccount(@PathVariable String email){
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(selectEmployeeByEmail);)
		{
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			Employee employee= new Employee();
			while(rs.next()) {
				 employee.setEmail(rs.getString("Email"));
				 employee.setDOB(rs.getString("DOB"));
			
			}
			return employee;
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		return null;
		
		
	}
	
	public boolean deleteOrder(@PathVariable String id){
		Connection connection=null;
		try {
			connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(deleteOrderTransaction);		

			ps.setInt(1, Integer.valueOf(id));
			ps.setInt(2, Integer.valueOf(id));
			ps.setInt(3, Integer.valueOf(id));
			ps.executeQuery();
			return true;
		} catch (Exception e) {
			if (connection != null) {
		        try {
		            connection.rollback();
		            return false;
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		    e.printStackTrace();
			return false;
		}
		
	}
//	//view employee
//	public Employee selectEmployeeById(@PathVariable String id){
//		try (Connection connection = getConnection();
//				PreparedStatement ps = connection.prepareStatement(selectEmployeeById);)
//		{
//			ps.setInt(1, Integer.valueOf(id));
//			ResultSet rs = ps.executeQuery();
//			Employee employee= new Employee();
//			while(rs.next()) {
//				 employee.setId(rs.getInt("id"));
//				 employee.setName(rs.getString("name"));
//				 employee.setDob(rs.getString("dob"));
//				 employee.setDepartment(rs.getString("department"));
//				 employee.setHired(rs.getInt("hired") != 0 ? true : false);
//				return employee;
//			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//		
//		
//	}
////	//them employee
////	public boolean insertEmployee(Employee employee,String id) {
////		try (Connection connection = getConnection();
////			PreparedStatement ps = connection.prepareStatement("INSERT INTO employee (id,name, dob, department, hired) VALUES (?,?, ?, ?, ?)");)
////		{	
////			ps.setInt(1, employee.getId());
////			ps.setString(2, employee.getName());
////			ps.setString(3, employee.getDob());
////			ps.setString(4, employee.getDepartment());
////			ps.setInt(5, employee.isHired() ? 1 : 0);
////
////			int rowsInserted = ps.executeUpdate();
////			return (rowsInserted > 0);
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
////		return false;
////	}
////	//update employee
////	public boolean updateEmployee(Employee employee) {
////	    try (Connection connection = getConnection();
////	         PreparedStatement ps = connection.prepareStatement("UPDATE employee SET name=?, dob=?, department=?, hired=? WHERE id=?");)
////	    {
////	        ps.setString(1, employee.getName());
////	        ps.setString(2, employee.getDob());
////	        ps.setString(3, employee.getDepartment());
////	        ps.setInt(4, employee.isHired() ? 1 : 0);
////	        ps.setInt(5, employee.getId());
////
////	        int rowsUpdated = ps.executeUpdate();
////	        return (rowsUpdated > 0);
////	    } catch (Exception e) {
////	        // TODO: handle exception
////	    }
////	    return false;
////	}
	
}


