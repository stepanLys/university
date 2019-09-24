package entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal salary;

    private String name;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    @ManyToMany(mappedBy = "employees", cascade = CascadeType.ALL)
    private List<Department> departments = new ArrayList<>();

    @OneToOne(mappedBy = "headOfDepartment", cascade = CascadeType.ALL)
    private Department headInDepartment;
}
