package com.example;

import java.sql.*;
import java.util.Scanner;
import com.example.Employee;


public class EmployeeDatabase {
    private static final String URL  = "jdbc:mysql://localhost:3306/work";
    private static final String USER = "root";
    private static final String PASS = "";

    // Method to fetch a single employee by ID
    public static Employee getEmployeeById(String employeeId) {
        Employee employee = null;
        String query = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employeeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String department = rs.getString("department");
                    String position = rs.getString("position");
                    double salary = rs.getDouble("salary");

                    employee = new Employee(id, name, department, position, salary);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employee: " + e.getMessage());
        }
        return employee;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter employee ID to search: ");
        String employeeId = scanner.nextLine();

        Employee employee = getEmployeeById(employeeId);
        if (employee != null) {
            employee.displayInfo();
        } else {
            System.out.println("No employee found with ID: " + employeeId);
        }
    }
}
