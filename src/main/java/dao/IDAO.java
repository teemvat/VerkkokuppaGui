package dao;

import java.util.List;

/**
 * Interface for DAO classes
 */
public interface IDAO {

    /**
     * Persists an object
     * @param o Object to persist
     */
    public void persist(Object o);

    /**
     * Finds an object by id
     * @param id Id of the object
     * @return Object found
     */
    public Object find(int id);

    /**
     * Updates an object
     * @param o Object to update
     */
    public void update(Object o);

    /**
     * Deletes an object
     * @param o Object to delete
     */
    public void delete(Object o);

    /**
     * Finds all objects
     * @return List of objects
     */
    public List<Object> findAll();
}
