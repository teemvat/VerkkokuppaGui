package dao;

import java.util.List;

public interface IDAO {
    public void persist(Object o);
    public Object find(int id);
    public void update(Object o);
    public void delete(Object o);
    public List<Object> findAll();
}
