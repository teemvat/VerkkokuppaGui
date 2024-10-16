package datasource;

import jakarta.persistence.*;


/**
 * This class is used to create a connection to the MariaDB database using JPA.
 * It is a singleton class.
 */
public class MariaDbJpaConnection {

    /**
     * The EntityManagerFactory object is used to create an EntityManager object.
     */
    private static EntityManagerFactory emf = null;

    /**
     * The EntityManager object is used to perform operations on the database.
     */
    private static EntityManager em = null;


    /**
     * This method is used to create an EntityManager object:
     * 1. If the EntityManager object is not created, then it creates an EntityManager object.
     * 2. If the EntityManagerFactory object is not created, then it creates an EntityManagerFactory object.
     * 3. It creates an EntityManager object using the EntityManagerFactory object.
     *
     * @return EntityManager object
     */
    public static EntityManager getInstance() {
        if (em==null) {
            if (emf==null) {
                emf = Persistence.createEntityManagerFactory("SimulatorMariaDbUnit");
            }
            em = emf.createEntityManager();
        }
        return em;
    }
}
