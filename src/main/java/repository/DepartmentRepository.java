package repository;

import entity.Department;
import entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class DepartmentRepository {

    private static final String FOR_LIKE = "%";

    private final EntityManager entityManager;

    public DepartmentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Department> findAll() {

        return entityManager
                .createQuery("select d from Department d", Department.class)
                .getResultList();
    }

    public String getStatistic(String department) {

        List statistic = entityManager
                .createQuery("select new List(e.degree, count(e.degree)) from Department d " +
                        "join d.employees e " +
                        "where d.name = :department group by e.degree", List.class)
                .setParameter("department", department)
                .getResultList();

        return statistic.toString();
    }

    public Double avgSalary(String department) {
        return entityManager
                .createQuery("select avg(e.salary) from Department d " +
                        "join d.employees e " +
                        "where d.name = :department", Double.class)
                .setParameter("department", department)
                .getSingleResult();
    }

    public Long countOfEmployee(String department) {
        return entityManager
                .createQuery("select count (e.id) from Department d " +
                        "join d.employees e " +
                        "where d.name = :department", Long.class)
                .setParameter("department", department)
                .getSingleResult();
    }

    public String headOfDepartment(String department) {
        return entityManager
                .createQuery("select e.name from Department d " +
                        "join d.employees e " +
                        "where d.name = :department and d.headOfDepartment = e.headInDepartment", String.class)
                .setParameter("department", department)
                .getSingleResult();
    }

    public Department findById(Long id) {
        return entityManager
                .createQuery("select c from Department c where c.id = :id", Department.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public void save(Department department) {
        entityManager.getTransaction().begin();
        entityManager.merge(department);
        entityManager.getTransaction().commit();
    }

    public void saveAll(List<Department> employees) {
        entityManager.getTransaction().begin();

        Iterator<Department> iterator = employees.iterator();
        while (iterator.hasNext()) {
            entityManager.merge(iterator.next());
        }

        entityManager.getTransaction().commit();
    }

    public void delete(Long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(findById(id));
        entityManager.getTransaction().commit();
    }
}
