package dao;

import simu.model.entity.Order;
import java.util.List;
import jakarta.persistence.EntityManager;

/**
 * DAO class for Order
 */
public class OrderDAO implements IDAO {

    /**
     * Persists an object
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
     * Updates an object
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
     * Deletes an object
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
     * Finds an object by id
     * @param id Id of the object
     * @return Object found
     */
    public Order find(int id) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.find(Order.class, id);
    }

    /**
     * Finds all objects
     * @return List of objects
     */
    public List<Object> findAll() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.createQuery("select e from Order e").getResultList();
    }
 }
