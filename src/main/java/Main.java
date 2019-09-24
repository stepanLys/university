import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("university");
        EntityManager manager = factory.createEntityManager();


        manager.close();
        factory.close();
    }
}
