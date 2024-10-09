package dao;

import jakarta.persistence.EntityManager;
import simu.model.entity.Simulation;
import java.util.List;

/**
 * This class is a Data Access Object class for Simulation. It is used to persist, update, delete, find and find all Simulation objects in the database.
 *
 * @author Mira Peryshko
 * @version 1.0
 */
public class SimulationDAO implements IDAO {

    /**
     * Persists an object to the database.
     * 1. Get the entity manager from the datasource class MariaDbJpaConnection
     * 2. Begin a transaction
     * 3. Persist the object to the database
     * 4. Commit the transaction to save the changes to the database
     *
     * @param o Object to persist
     */
    @Override
    public void persist(Object o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    /**
     * Updates an object in the database.
     * 1. Get the entity manager from the datasource class MariaDbJpaConnection
     * 2. Begin a transaction
     * 3. Merge the object to the database
     * 4. Commit the transaction to save the changes to the database
     *
     * @param o Object to update
     */
    @Override
    public void update(Object o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

    /**
     * Deletes an object from the database.
     * 1. Get the entity manager from the datasource class MariaDbJpaConnection
     * 2. Begin a transaction
     * 3. Remove the object from the database
     * 4. Commit the transaction to save the changes to the database
     *
     * @param o Object to delete
     */
    @Override
    public void delete(Object o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }

    /**
     * Finds an object by id in the database.
     * 1. Get the entity manager from the datasource class MariaDbJpaConnection
     * 2. Find the object by id
     *
     * @param id Id of the object
     * @return Object found
     */
    public Simulation find(int id) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        Simulation s = em.find(Simulation.class, id);
        return s;
    }

    /**
     * Finds all objects in the database.
     * 1. Get the entity manager from the datasource class MariaDbJpaConnection
     * 2. Create a query to select all objects
     *
     * @return List of objects
     */
    public List<Object> findAll() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.createQuery("select e from Order e").getResultList();
    }
}
