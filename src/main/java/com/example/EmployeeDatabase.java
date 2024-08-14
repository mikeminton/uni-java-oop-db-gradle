package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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

    // Method to update employee details
    public static boolean updateEmployee(Employee employee) {
        String query = "UPDATE employees SET name = ?, department = ?, position = ?, salary = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getDepartment());
            pstmt.setString(3, employee.getPosition());
            pstmt.setDouble(4, employee.getSalary());
            pstmt.setString(5, employee.getId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee ID to search: ");
        String employeeId = scanner.nextLine();

        Employee employee = getEmployeeById(employeeId);
        if (employee != null) {
            employee.displayInfo();

            // Prompt the user to update the employee details
            System.out.println("Enter new details for the employee (leave blank to keep current value):");
            System.out.println("Name (" + employee.getName() + "): ");
            String newName = scanner.nextLine();
            System.out.println("Department (" + employee.getDepartment() + "): ");
            String newDepartment = scanner.nextLine();
            System.out.println("Position (" + employee.getPosition() + "): ");
            String newPosition = scanner.nextLine();
            System.out.println("Salary (" + employee.getSalary() + "): ");
            String salaryInput = scanner.nextLine();

              // Update the employee object with new details if not empty
            if (!newName.isEmpty()) employee.setName(newName);
            if (!newDepartment.isEmpty()) employee.setDepartment(newDepartment);
            if (!newPosition.isEmpty()) employee.setPosition(newPosition);
            if (!salaryInput.isEmpty()) employee.setSalary(Double.parseDouble(salaryInput));

            // Update the employee details in the database
            if (updateEmployee(employee)) {
                System.out.println("Employee details updated successfully.");
                employee.displayInfo();
            } else {
                System.out.println("Failed to update employee details.");
            }
        } else {
            System.out.println("No employee found with ID: " + employeeId);
        }
    }
}
