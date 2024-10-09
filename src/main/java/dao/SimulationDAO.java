package dao;

import jakarta.persistence.EntityManager;
import simu.model.entity.Simulation;
import java.util.List;


public class SimulationDAO implements IDAO {

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

    public Simulation find(int id) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        Simulation s = em.find(Simulation.class, id);
        return s;
    }

    public List<Object> findAll() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return em.createQuery("select e from Order e").getResultList();
    }
}
