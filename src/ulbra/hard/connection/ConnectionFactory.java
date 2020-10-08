
package ulbra.hard.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author @andre_ments
 */
public class ConnectionFactory {

    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JavaJPAPU");

    public EntityManager getConnection() {
        return emf.createEntityManager();
    }
}
