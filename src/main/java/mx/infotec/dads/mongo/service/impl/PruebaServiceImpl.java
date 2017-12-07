package mx.infotec.dads.mongo.service.impl;

import mx.infotec.dads.mongo.service.PruebaService;
import mx.infotec.dads.mongo.domain.Prueba;
import mx.infotec.dads.mongo.repository.PruebaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Prueba.
 */
@Service
public class PruebaServiceImpl implements PruebaService{

    private final Logger log = LoggerFactory.getLogger(PruebaServiceImpl.class);

    private final PruebaRepository pruebaRepository;

    public PruebaServiceImpl(PruebaRepository pruebaRepository) {
        this.pruebaRepository = pruebaRepository;
    }

    /**
     * Save a prueba.
     *
     * @param prueba the entity to save
     * @return the persisted entity
     */
    @Override
    public Prueba save(Prueba prueba) {
        log.debug("Request to save Prueba : {}", prueba);
        return pruebaRepository.save(prueba);
    }

    /**
     *  Get all the pruebas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Prueba> findAll(Pageable pageable) {
        log.debug("Request to get all Pruebas");
        return pruebaRepository.findAll(pageable);
    }

    /**
     *  Get one prueba by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Prueba findOne(String id) {
        log.debug("Request to get Prueba : {}", id);
        return pruebaRepository.findOne(id);
    }

    /**
     *  Delete the  prueba by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Prueba : {}", id);
        pruebaRepository.delete(id);
    }
}
