import entity.Degree;
import entity.Department;
import entity.Employee;
import repository.DepartmentRepository;
import repository.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static boolean status = true;

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("university");
        EntityManager manager = factory.createEntityManager();

        EmployeeRepository employeeRepository = new EmployeeRepository(manager);
        DepartmentRepository departmentRepository = new DepartmentRepository(manager);

//        addEmployees(employeeRepository);
//        addDepartments(employeeRepository, departmentRepository);


        while (status) {
            System.out.println("\n\n1 - show head of department\n" +
                    "2 - department statistic\n" +
                    "3 - show the average salary for department\n" +
                    "4 - show count of employee for department\n" +
                    "5 - global search by template\n" +
                    "q - quit\n\n");
            String menuPicker = sc.next();
            switch (menuPicker) {
                case "1": {
                    String department = askDepartmentName(departmentRepository);

                    String header = departmentRepository.headOfDepartment(department);

                    System.out.println("Head of " + department + " department is " + header);
                    break;
                }
                case "2": {
                    String department = askDepartmentName(departmentRepository);

                    List statistic = departmentRepository.getStatistic(department);

                    statistic.forEach(System.out::println);

                    break;
                }
                case "3": {
                    String department = askDepartmentName(departmentRepository);

                    Double avgSalary = departmentRepository.avgSalary(department);

                    System.out.println("The average salary of " + department + " is " + avgSalary);

                    break;
                }
                case "4": {
                    String department = askDepartmentName(departmentRepository);

                    Long countOfEmployees = departmentRepository.countOfEmployee(department);

                    System.out.println("Count of employees for " + department + " is " + countOfEmployees);
                    break;
                }
                case "5": {
                    System.out.print("Global search by: ");
                    String template = sc.next();

                    List<String> byTemplateName = employeeRepository.findByTemplateName(template);

                    if (byTemplateName.isEmpty()) {
                        System.out.println("Employees with the same name doesn't exist");
                    } else {
                        byTemplateName.forEach(e -> System.out.print(e + ", "));
                    }

                    break;
                }
                case "q": {
                    status = false;
                    break;
                }
            }
        }


        manager.close();
        factory.close();
    }

    private static String askDepartmentName(DepartmentRepository departmentRepository) {
        System.out.print("Enter department name: ");
        String department = sc.next();
        while (departmentRepository.findByDepartmentName(department) == null) {
            System.out.print("Wrong! Please, enter correct department name: ");
            department = sc.next();
        }
        return department;
    }

    private static void addDepartments(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        Department department1 = new Department();
        department1.setName("ISM");
        department1.setEmployees(new ArrayList<>() {{
            add(employeeRepository.findById(1L));
            add(employeeRepository.findById(2L));
            add(employeeRepository.findById(4L));
            add(employeeRepository.findById(5L));
            add(employeeRepository.findById(8L));
        }});
        department1.setHeadOfDepartment(employeeRepository.findById(1L));

        Department department2 = new Department();
        department2.setName("ASU");
        department2.setEmployees(new ArrayList<>() {{
            add(employeeRepository.findById(2L));
            add(employeeRepository.findById(4L));
            add(employeeRepository.findById(7L));
            add(employeeRepository.findById(12L));
        }});
        department2.setHeadOfDepartment(employeeRepository.findById(4L));

        Department department3 = new Department();
        department3.setName("SAPR");
        department3.setEmployees(new ArrayList<>() {{
            add(employeeRepository.findById(3L));
            add(employeeRepository.findById(6L));
            add(employeeRepository.findById(7L));
            add(employeeRepository.findById(10L));
            add(employeeRepository.findById(11L));
        }});
        department3.setHeadOfDepartment(employeeRepository.findById(7L));

        departmentRepository.saveAll(List.of(department1, department2, department3));
    }

    private static void addEmployees(EmployeeRepository employeeRepository) {
        Employee employee1 = new Employee();
        employee1.setName("Igor Bobalo");
        employee1.setDegree(Degree.PROFESSOR);
        employee1.setSalary(BigDecimal.valueOf(1500.00));

        Employee employee2 = new Employee();
        employee2.setName("Vasyl Vasyliv");
        employee2.setDegree(Degree.ASSISTANT);
        employee2.setSalary(BigDecimal.valueOf(500.20));

        Employee employee3 = new Employee();
        employee3.setName("Ivan Ivaniv");
        employee3.setDegree(Degree.ASSOCIATE_PROFESSOR);
        employee3.setSalary(BigDecimal.valueOf(900.00));

        Employee employee4 = new Employee();
        employee4.setName("Bohdan Vovk");
        employee4.setDegree(Degree.PROFESSOR);
        employee4.setSalary(BigDecimal.valueOf(1457.25));

        Employee employee5 = new Employee();
        employee5.setName("Volodymyr Volod");
        employee5.setDegree(Degree.ASSISTANT);
        employee5.setSalary(BigDecimal.valueOf(666.6));

        Employee employee6 = new Employee();
        employee6.setName("Ivan Vas");
        employee6.setDegree(Degree.ASSISTANT);
        employee6.setSalary(BigDecimal.valueOf(703.20));

        Employee employee7 = new Employee();
        employee7.setName("Volodymyr Melnyk");
        employee7.setDegree(Degree.PROFESSOR);
        employee7.setSalary(BigDecimal.valueOf(1450.80));

        Employee employee8 = new Employee();
        employee8.setName("Petro Yuriev");
        employee8.setDegree(Degree.ASSOCIATE_PROFESSOR);
        employee8.setSalary(BigDecimal.valueOf(700));

        Employee employee9 = new Employee();
        employee9.setName("Oleg Petrov");
        employee9.setDegree(Degree.ASSOCIATE_PROFESSOR);
        employee9.setSalary(BigDecimal.valueOf(833.1));

        Employee employee10 = new Employee();
        employee10.setName("Olga Bodnar");
        employee10.setDegree(Degree.ASSISTANT);
        employee10.setSalary(BigDecimal.valueOf(450.20));

        Employee employee11 = new Employee();
        employee11.setName("Sophia Borys");
        employee11.setDegree(Degree.ASSISTANT);
        employee11.setSalary(BigDecimal.valueOf(550.50));

        Employee employee12 = new Employee();
        employee12.setName("Borys Melnyk");
        employee12.setDegree(Degree.ASSISTANT);
        employee12.setSalary(BigDecimal.valueOf(500));

        employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4, employee5,
                employee6, employee7, employee8, employee9, employee10, employee11, employee12));
    }
}
