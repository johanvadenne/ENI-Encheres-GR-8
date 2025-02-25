package fr.campus.eni.encheres.bll;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {
    void add(T entity);

    public List<T> getAll();

    Optional<T> getById(int id);

    void update(T entity);
    
    void save(T entity);

    void delete(int id);

}