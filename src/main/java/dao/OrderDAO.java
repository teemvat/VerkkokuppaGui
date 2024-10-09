package dao;

import simu.model.entity.Order;
import java.util.List;
import jakarta.persistence.EntityManager;


public class OrderDAO {

    public void persist(Order o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public Order find(int id) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        Order o = em.find(Order.class, id);
        return o;
    }

    public List<Order> findAll() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.createQuery("select e from Order e").getResultList();
    }

    public void update(Order o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

    public void delete(Order o) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }
}
