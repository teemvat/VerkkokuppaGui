package dao;

import java.util.List;

/**
 * Represents a Data Access Object interface for CRUD operations on entities in the database.
 *
 * @author Mira Peryshko
 * @version 1.0
 */
public interface IDAO {

    /**
     * Persists an object to the database.
     *
     * @param o Object to persist
     */
    public void persist(Object o);

    /**
     * Finds an object by id in the database.
     *
     * @param id Id of the object
     * @return Object found
     */
    public Object find(int id);

    /**
     * Updates an object in the database.
     *
     * @param o Object to update
     */
    public void update(Object o);

    /**
     * Deletes an object from the database.
     *
     * @param o Object to delete
     */
    public void delete(Object o);

    /**
     * Finds all objects in the database.
     *
     * @return List of objects
     */
    public List<Object> findAll();
}
