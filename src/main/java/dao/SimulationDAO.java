package dao;

import jakarta.persistence.EntityManager;
import simu.model.entity.Simulation;
import java.util.List;

/**
 * DAO class for Simulation
 */
public class SimulationDAO implements IDAO {

    /**
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
     *
     * @return List of objects
     */
    public List<Object> findAll() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.createQuery("select e from Order e").getResultList();
    }
}
