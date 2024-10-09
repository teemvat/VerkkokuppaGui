package controller;

import java.util.List;

public interface IControllerForDao {

    // Rajapinta, joka tarjotaan DAO:lle:

    public <T> T save(T entity);
    public void update(int id1, int id2, double time);
    public <T> T find(Class<T> type, int id);
    public <T> List<T> findAll(Class<T> type);
}
