package controller;

import java.util.List;

/**
 * This interface is used to provide methods for DAO.
 */
public interface IControllerForDao {

    /**
     * This method is used to save an entity.
     *
     * @param entity the entity to be saved
     * @return the saved entity
     */
    public <T> T save(T entity);

    /**
     * This method is used to update a Simulation and Order entities.
     *
     * @param id1 the id of the Simulation to be updated.
     * @param id2 the id of the Order to be updated.
     * @param time completion time of the Order.
     */
    public void update(int id1, int id2, double time);

    /**
     * This method is used to find an entity by its id.
     *
     * @param type the type of the entity to be found
     * @param id the id of the entity to be found
     * @return the found entity
     */
    public <T> T find(Class<T> type, int id);

    /**
     * This method is used to find all entities of a certain type.
     *
     * @param type the type of the entities to be found
     * @return a list of all entities of the given type
     */
    public <T> List<T> findAll(Class<T> type);
}
