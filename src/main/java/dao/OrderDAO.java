package dao;

import simu.model.entity.Order;
import java.util.List;
import jakarta.persistence.EntityManager;


public class OrderDAO implements IDAO {
    @Override
    public void persist(Object o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    @Override
    public void update(Object o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Object o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }

    public Order find(int id) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.find(Order.class, id);
    }

    public List<Object> findAll() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.createQuery("select e from Order e").getResultList();
    }
 }
