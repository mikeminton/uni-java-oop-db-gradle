package com.example;

public class Employee {
      // Attributes of the employee
    private String id;
    private String name;
    private String department;
    private String position;
    private double salary;

    // Constructor to initialize the Employee object
    public Employee(String id, String name, String department, String position, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    // Getter and setter methods for each attribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Method to display information about the employee
    public void displayInfo() {
        System.out.println("Employee ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Position: " + position);
        System.out.println("Salary: $" + salary);
    }
}
