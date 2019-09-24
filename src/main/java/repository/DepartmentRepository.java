package repository;

import entity.Department;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

public class DepartmentRepository {

    private static final String FOR_LIKE = "%";

    private final EntityManager entityManager;

    public DepartmentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Department> findAll() {
        return entityManager
                .createQuery("select c from Department c", Department.class)
                .getResultList();
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
