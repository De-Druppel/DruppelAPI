package com.druppel.api.service;


import com.druppel.api.model.Measurement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/*public interface IMeasurementDao<T>{

    <T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}*/
@Repository
public interface IMeasurementDao extends CrudRepository<Measurement, Integer> {
}
