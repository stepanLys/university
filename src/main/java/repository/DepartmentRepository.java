package repository;

import entity.Department;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Iterator;
import java.util.List;

public class DepartmentRepository {

    private static final String FOR_LIKE = "%";

    private final EntityManager entityManager;

    public DepartmentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Department> findAll() {
        try {
            return entityManager
                    .createQuery("select d from Department d", Department.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<List> getStatistic(String department) {
        try {
            return entityManager
                    .createQuery("select new List(e.degree, count(e.degree)) from Department d " +
                            "join d.employees e " +
                            "where d.name = :department group by e.degree", List.class)
                    .setParameter("department", department)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Double avgSalary(String department) {
        try {
            return entityManager
                    .createQuery("select avg(e.salary) from Department d " +
                            "join d.employees e " +
                            "where d.name = :department", Double.class)
                    .setParameter("department", department)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long countOfEmployee(String department) {
        try {
            return entityManager
                    .createQuery("select count (e.id) from Department d " +
                            "join d.employees e " +
                            "where d.name = :department", Long.class)
                    .setParameter("department", department)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public String headOfDepartment(String department) {
        try {
            return entityManager
                    .createQuery("select e.name from Department d " +
                            "join d.employees e " +
                            "where d.headOfDepartment = e.headInDepartment and d.name = :department", String.class)
                    .setParameter("department", department)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Department findById(Long id) {
        try {
            return entityManager
                    .createQuery("select c from Department c where c.id = :id", Department.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
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
