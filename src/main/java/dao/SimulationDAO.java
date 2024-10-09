package dao;

import jakarta.persistence.EntityManager;
import simu.model.entity.Simulation;
import java.util.List;


public class SimulationDAO {

    public void persist(Simulation s) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    public Simulation find(int id) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        Simulation s = em.find(Simulation.class, id);
        return s;
    }

    public List<Simulation> findAll() {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        return (List<Simulation>) em.createQuery("select e from Order e").getResultList();
    }

    public void update(Simulation s) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(s);
        em.getTransaction().commit();
    }

    public void delete(Simulation s) {
        EntityManager em = datasource.MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.remove(s);
        em.getTransaction().commit();
    }
}
