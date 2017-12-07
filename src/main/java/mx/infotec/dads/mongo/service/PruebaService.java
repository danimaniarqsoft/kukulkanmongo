package mx.infotec.dads.mongo.service;

import mx.infotec.dads.mongo.domain.Prueba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Prueba.
 */
public interface PruebaService {

    /**
     * Save a prueba.
     *
     * @param prueba the entity to save
     * @return the persisted entity
     */
    Prueba save(Prueba prueba);

    /**
     *  Get all the pruebas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Prueba> findAll(Pageable pageable);

    /**
     *  Get the "id" prueba.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Prueba findOne(String id);

    /**
     *  Delete the "id" prueba.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
