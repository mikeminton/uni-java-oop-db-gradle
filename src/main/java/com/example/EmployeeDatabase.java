package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeeDatabase {
    private static final String URL  = "jdbc:mysql://localhost:3306/work";
    private static final String USER = "root";
    private static final String PASS = "";

      // Method to fetch a single employee by ID
    public static Employee getEmployeeById(String employeeId) {
        Employee employee = null;
        String   query    = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employeeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String id         = rs.getString("id");
                    String name       = rs.getString("name");
                    String department = rs.getString("department");
                    String position   = rs.getString("position");
                    double salary     = rs.getDouble("salary");

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

      // Method to add a new employee
    public static boolean addEmployee(Employee employee) {
        String query = "INSERT INTO employees (id, name, department, position, salary) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setString(4, employee.getPosition());
            pstmt.setDouble(5, employee.getSalary());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }

      // Method to view all employees
    public static void viewAllEmployees() {
        String query = "SELECT * FROM employees";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(query)) {
            System.out.println("\n ");
            System.out.printf("%-10s %-20s %-20s %-20s %-10s%n", "ID", "Name", "Department", "Position", "Salary");
            System.out.println("------------------------------------------------------------------------------------");

            while (rs.next()) {
                String id         = rs.getString("id");
                String name       = rs.getString("name");
                String department = rs.getString("department");
                String position   = rs.getString("position");
                double salary     = rs.getDouble("salary");

                System.out.printf("%-10s %-20s %-20s %-20s %-10.2f%n", id, name, department, position, salary);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching employees: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        boolean running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Search and update employee");
                System.out.println("2. Add new employee");
                System.out.println("3. View all employees");
                System.out.println("4. Quit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter employee ID to search: ");
                        String employeeId = scanner.nextLine();

                        Employee employee = getEmployeeById(employeeId);
                        if (employee != null) {
                            employee.displayInfo();

                              // Prompt the user to update the employee details
                            System.out.println("Enter new details for the employee (leave blank to keep current value):");
                            System.out.print("Name (" + employee.getName() + "): ");
                            String newName = scanner.nextLine();
                            System.out.print("Department (" + employee.getDepartment() + "): ");
                            String newDepartment = scanner.nextLine();
                            System.out.print("Position (" + employee.getPosition() + "): ");
                            String newPosition = scanner.nextLine();
                            System.out.print("Salary (" + employee.getSalary() + "): ");
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
                    case 2 -> {
                          // Add new employee
                        System.out.print("Enter new employee ID: ");
                        String newId = scanner.nextLine();
                        System.out.print("Enter name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter department: ");
                        String newDepartment = scanner.nextLine();
                        System.out.print("Enter position: ");
                        String newPosition = scanner.nextLine();
                        System.out.print("Enter salary: ");
                        double newSalary = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline

                        Employee newEmployee = new Employee(newId, newName, newDepartment, newPosition, newSalary);

                        if (addEmployee(newEmployee)) {
                            System.out.println("New employee added successfully.");
                            newEmployee.displayInfo();
                        } else {
                            System.out.println("Failed to add new employee.");
                        }
                    }
                    case 3 -> {
                        // View all employees
                        viewAllEmployees();
                    }
                    case 4 -> {
                        // Quit the program
                        running = false;
                        System.out.println("Exiting program.");
                    }
                    default -> System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
                }
            }
        }
    }
}
