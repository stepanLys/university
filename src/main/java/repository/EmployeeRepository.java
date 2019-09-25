package repository;

import entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class EmployeeRepository {

    private static final String FOR_LIKE = "%";

    private final EntityManager entityManager;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Employee> findAll() {
        return entityManager
                .createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }

    public Employee findById(Long id) {
        return entityManager
                .createQuery("select e from Employee e where e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List findByDepartment(String department) {
        return entityManager
                .createQuery("select new List(e.id, e.name, e.salary, e.degree) " +
                        "from Employee e join e.departments d where d.name = :department", List.class)
                .setParameter("department", department)
                .getResultList();
    }

    public List<String> findByTemplateName(String template) {
        try {
            return entityManager
                    .createQuery("select e.name from Employee e where e.name like :template", String.class)
                    .setParameter("template", FOR_LIKE + template + FOR_LIKE)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Employee employee) {
        entityManager.getTransaction().begin();
        entityManager.merge(employee);
        entityManager.getTransaction().commit();
    }

    public void saveAll(List<Employee> employees) {
        entityManager.getTransaction().begin();

        for (Employee employee : employees) {
            entityManager.merge(employee);
        }

        entityManager.getTransaction().commit();
    }

    public void delete(Long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(findById(id));
        entityManager.getTransaction().commit();
    }
}
