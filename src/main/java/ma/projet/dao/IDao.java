package ma.projet.dao;

import java.util.List;

public interface IDao<T> {
    T create(T o);
    T update(T o);
    boolean delete(Long id);
    T findById(Long id);
    List<T> findAll();
}
