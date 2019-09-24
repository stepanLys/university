package repository;

import entity.Employee;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

public class EmployeeRepository  {

    private static final String FOR_LIKE = "%";

    private final EntityManager entityManager;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Employee> findAll() {
        return entityManager
                .createQuery("select c from Employee c", Employee.class)
                .getResultList();
    }

    public Employee findById(Long id) {
        return entityManager
                .createQuery("select c from Employee c where c.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public void save(Employee employee) {
        entityManager.getTransaction().begin();
        entityManager.merge(employee);
        entityManager.getTransaction().commit();
    }

    public void saveAll(List<Employee> employees) {
        entityManager.getTransaction().begin();

        Iterator<Employee> iterator = employees.iterator();
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
