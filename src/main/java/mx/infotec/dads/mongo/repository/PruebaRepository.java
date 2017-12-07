package mx.infotec.dads.mongo.repository;

import mx.infotec.dads.mongo.domain.Prueba;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Prueba entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PruebaRepository extends MongoRepository<Prueba,String> {

}
