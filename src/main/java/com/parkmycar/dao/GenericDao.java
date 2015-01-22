package com.parkmycar.dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDao <T, PK extends Serializable>
{
    public List<T> getAll();

    public T get(PK id);

    public boolean exists(PK id);

    public T save(T object);

    public T update(T object);
    
    public void remove(PK id);
    
    public void flush();
    
}
