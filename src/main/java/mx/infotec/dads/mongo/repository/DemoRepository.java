package mx.infotec.dads.mongo.repository;

import mx.infotec.dads.mongo.domain.Demo;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Demo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemoRepository extends MongoRepository<Demo,String> {

}
