package mx.infotec.dads.mongo.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.infotec.dads.mongo.domain.Prueba;
import mx.infotec.dads.mongo.service.PruebaService;
import mx.infotec.dads.mongo.web.rest.util.HeaderUtil;
import mx.infotec.dads.mongo.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Prueba.
 */
@RestController
@RequestMapping("/api")
public class PruebaResource {

    private final Logger log = LoggerFactory.getLogger(PruebaResource.class);

    private static final String ENTITY_NAME = "prueba";

    private final PruebaService pruebaService;

    public PruebaResource(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    /**
     * POST  /pruebas : Create a new prueba.
     *
     * @param prueba the prueba to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prueba, or with status 400 (Bad Request) if the prueba has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pruebas")
    @Timed
    public ResponseEntity<Prueba> createPrueba(@Valid @RequestBody Prueba prueba) throws URISyntaxException {
        log.debug("REST request to save Prueba : {}", prueba);
        if (prueba.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new prueba cannot already have an ID")).body(null);
        }
        Prueba result = pruebaService.save(prueba);
        return ResponseEntity.created(new URI("/api/pruebas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pruebas : Updates an existing prueba.
     *
     * @param prueba the prueba to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prueba,
     * or with status 400 (Bad Request) if the prueba is not valid,
     * or with status 500 (Internal Server Error) if the prueba couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pruebas")
    @Timed
    public ResponseEntity<Prueba> updatePrueba(@Valid @RequestBody Prueba prueba) throws URISyntaxException {
        log.debug("REST request to update Prueba : {}", prueba);
        if (prueba.getId() == null) {
            return createPrueba(prueba);
        }
        Prueba result = pruebaService.save(prueba);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prueba.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pruebas : get all the pruebas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pruebas in body
     */
    @GetMapping("/pruebas")
    @Timed
    public ResponseEntity<List<Prueba>> getAllPruebas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pruebas");
        Page<Prueba> page = pruebaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pruebas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pruebas/:id : get the "id" prueba.
     *
     * @param id the id of the prueba to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prueba, or with status 404 (Not Found)
     */
    @GetMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<Prueba> getPrueba(@PathVariable String id) {
        log.debug("REST request to get Prueba : {}", id);
        Prueba prueba = pruebaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prueba));
    }

    /**
     * DELETE  /pruebas/:id : delete the "id" prueba.
     *
     * @param id the id of the prueba to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pruebas/{id}")
    @Timed
    public ResponseEntity<Void> deletePrueba(@PathVariable String id) {
        log.debug("REST request to delete Prueba : {}", id);
        pruebaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
