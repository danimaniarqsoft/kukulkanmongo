package mx.infotec.dads.mongo.web.rest;

import mx.infotec.dads.mongo.KukulkanmongoApp;

import mx.infotec.dads.mongo.domain.Prueba;
import mx.infotec.dads.mongo.repository.PruebaRepository;
import mx.infotec.dads.mongo.service.PruebaService;
import mx.infotec.dads.mongo.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static mx.infotec.dads.mongo.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PruebaResource REST controller.
 *
 * @see PruebaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KukulkanmongoApp.class)
public class PruebaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "hola";
    private static final String UPDATED_NOMBRE = "holaB";

    private static final Integer DEFAULT_EDAD = 1;
    private static final Integer UPDATED_EDAD = 2;

    private static final Long DEFAULT_NUMERO_CREDENCIAL = 1L;
    private static final Long UPDATED_NUMERO_CREDENCIAL = 2L;

    private static final BigDecimal DEFAULT_SUELDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUELDO = new BigDecimal(2);

    private static final Float DEFAULT_IMPUESTO = 1F;
    private static final Float UPDATED_IMPUESTO = 2F;

    private static final Double DEFAULT_IMPUESTO_DETALLE = 1D;
    private static final Double UPDATED_IMPUESTO_DETALLE = 2D;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final LocalDate DEFAULT_FECHA_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FECHA_ZONE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ZONE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(12, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(56, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_ANY_BLOB = TestUtil.createByteArray(78, "0");
    private static final byte[] UPDATED_IMAGEN_ANY_BLOB = TestUtil.createByteArray(90, "1");
    private static final String DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_ANY_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_BLOB = TestUtil.createByteArray(21, "0");
    private static final byte[] UPDATED_IMAGEN_BLOB = TestUtil.createByteArray(23, "1");
    private static final String DEFAULT_IMAGEN_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSTANTE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTANTE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PruebaRepository pruebaRepository;

    @Autowired
    private PruebaService pruebaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPruebaMockMvc;

    private Prueba prueba;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PruebaResource pruebaResource = new PruebaResource(pruebaService);
        this.restPruebaMockMvc = MockMvcBuilders.standaloneSetup(pruebaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prueba createEntity() {
        Prueba prueba = new Prueba()
            .nombre(DEFAULT_NOMBRE)
            .edad(DEFAULT_EDAD)
            .numeroCredencial(DEFAULT_NUMERO_CREDENCIAL)
            .sueldo(DEFAULT_SUELDO)
            .impuesto(DEFAULT_IMPUESTO)
            .impuestoDetalle(DEFAULT_IMPUESTO_DETALLE)
            .activo(DEFAULT_ACTIVO)
            .fechaLocalDate(DEFAULT_FECHA_LOCAL_DATE)
            .fechaZoneDateTime(DEFAULT_FECHA_ZONE_DATE_TIME)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .imagenAnyBlob(DEFAULT_IMAGEN_ANY_BLOB)
            .imagenAnyBlobContentType(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE)
            .imagenBlob(DEFAULT_IMAGEN_BLOB)
            .imagenBlobContentType(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE)
            .desc(DEFAULT_DESC)
            .instante(DEFAULT_INSTANTE);
        return prueba;
    }

    @Before
    public void initTest() {
        pruebaRepository.deleteAll();
        prueba = createEntity();
    }

    @Test
    public void createPrueba() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();

        // Create the Prueba
        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isCreated());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate + 1);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPrueba.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testPrueba.getNumeroCredencial()).isEqualTo(DEFAULT_NUMERO_CREDENCIAL);
        assertThat(testPrueba.getSueldo()).isEqualTo(DEFAULT_SUELDO);
        assertThat(testPrueba.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testPrueba.getImpuestoDetalle()).isEqualTo(DEFAULT_IMPUESTO_DETALLE);
        assertThat(testPrueba.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testPrueba.getFechaLocalDate()).isEqualTo(DEFAULT_FECHA_LOCAL_DATE);
        assertThat(testPrueba.getFechaZoneDateTime()).isEqualTo(DEFAULT_FECHA_ZONE_DATE_TIME);
        assertThat(testPrueba.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testPrueba.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testPrueba.getImagenAnyBlob()).isEqualTo(DEFAULT_IMAGEN_ANY_BLOB);
        assertThat(testPrueba.getImagenAnyBlobContentType()).isEqualTo(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE);
        assertThat(testPrueba.getImagenBlob()).isEqualTo(DEFAULT_IMAGEN_BLOB);
        assertThat(testPrueba.getImagenBlobContentType()).isEqualTo(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE);
        assertThat(testPrueba.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPrueba.getInstante()).isEqualTo(DEFAULT_INSTANTE);
    }

    @Test
    public void createPruebaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pruebaRepository.findAll().size();

        // Create the Prueba with an existing ID
        prueba.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setNombre(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEdadIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setEdad(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNumeroCredencialIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setNumeroCredencial(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSueldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setSueldo(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkImpuestoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setImpuesto(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkImpuestoDetalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setImpuestoDetalle(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setActivo(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFechaLocalDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setFechaLocalDate(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFechaZoneDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setFechaZoneDateTime(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkImagenIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setImagen(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkImagenAnyBlobIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setImagenAnyBlob(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkImagenBlobIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setImagenBlob(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setDesc(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkInstanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pruebaRepository.findAll().size();
        // set the field null
        prueba.setInstante(null);

        // Create the Prueba, which fails.

        restPruebaMockMvc.perform(post("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isBadRequest());

        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPruebas() throws Exception {
        // Initialize the database
        pruebaRepository.save(prueba);

        // Get all the pruebaList
        restPruebaMockMvc.perform(get("/api/pruebas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prueba.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].numeroCredencial").value(hasItem(DEFAULT_NUMERO_CREDENCIAL.intValue())))
            .andExpect(jsonPath("$.[*].sueldo").value(hasItem(DEFAULT_SUELDO.intValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.doubleValue())))
            .andExpect(jsonPath("$.[*].impuestoDetalle").value(hasItem(DEFAULT_IMPUESTO_DETALLE.doubleValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaLocalDate").value(hasItem(DEFAULT_FECHA_LOCAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].fechaZoneDateTime").value(hasItem(sameInstant(DEFAULT_FECHA_ZONE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].imagenAnyBlobContentType").value(hasItem(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenAnyBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_ANY_BLOB))))
            .andExpect(jsonPath("$.[*].imagenBlobContentType").value(hasItem(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_BLOB))))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].instante").value(hasItem(DEFAULT_INSTANTE.toString())));
    }

    @Test
    public void getPrueba() throws Exception {
        // Initialize the database
        pruebaRepository.save(prueba);

        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", prueba.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prueba.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.numeroCredencial").value(DEFAULT_NUMERO_CREDENCIAL.intValue()))
            .andExpect(jsonPath("$.sueldo").value(DEFAULT_SUELDO.intValue()))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.doubleValue()))
            .andExpect(jsonPath("$.impuestoDetalle").value(DEFAULT_IMPUESTO_DETALLE.doubleValue()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.fechaLocalDate").value(DEFAULT_FECHA_LOCAL_DATE.toString()))
            .andExpect(jsonPath("$.fechaZoneDateTime").value(sameInstant(DEFAULT_FECHA_ZONE_DATE_TIME)))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.imagenAnyBlobContentType").value(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenAnyBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_ANY_BLOB)))
            .andExpect(jsonPath("$.imagenBlobContentType").value(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_BLOB)))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.instante").value(DEFAULT_INSTANTE.toString()));
    }

    @Test
    public void getNonExistingPrueba() throws Exception {
        // Get the prueba
        restPruebaMockMvc.perform(get("/api/pruebas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePrueba() throws Exception {
        // Initialize the database
        pruebaService.save(prueba);

        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // Update the prueba
        Prueba updatedPrueba = pruebaRepository.findOne(prueba.getId());
        updatedPrueba
            .nombre(UPDATED_NOMBRE)
            .edad(UPDATED_EDAD)
            .numeroCredencial(UPDATED_NUMERO_CREDENCIAL)
            .sueldo(UPDATED_SUELDO)
            .impuesto(UPDATED_IMPUESTO)
            .impuestoDetalle(UPDATED_IMPUESTO_DETALLE)
            .activo(UPDATED_ACTIVO)
            .fechaLocalDate(UPDATED_FECHA_LOCAL_DATE)
            .fechaZoneDateTime(UPDATED_FECHA_ZONE_DATE_TIME)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .imagenAnyBlob(UPDATED_IMAGEN_ANY_BLOB)
            .imagenAnyBlobContentType(UPDATED_IMAGEN_ANY_BLOB_CONTENT_TYPE)
            .imagenBlob(UPDATED_IMAGEN_BLOB)
            .imagenBlobContentType(UPDATED_IMAGEN_BLOB_CONTENT_TYPE)
            .desc(UPDATED_DESC)
            .instante(UPDATED_INSTANTE);

        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrueba)))
            .andExpect(status().isOk());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate);
        Prueba testPrueba = pruebaList.get(pruebaList.size() - 1);
        assertThat(testPrueba.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPrueba.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testPrueba.getNumeroCredencial()).isEqualTo(UPDATED_NUMERO_CREDENCIAL);
        assertThat(testPrueba.getSueldo()).isEqualTo(UPDATED_SUELDO);
        assertThat(testPrueba.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testPrueba.getImpuestoDetalle()).isEqualTo(UPDATED_IMPUESTO_DETALLE);
        assertThat(testPrueba.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testPrueba.getFechaLocalDate()).isEqualTo(UPDATED_FECHA_LOCAL_DATE);
        assertThat(testPrueba.getFechaZoneDateTime()).isEqualTo(UPDATED_FECHA_ZONE_DATE_TIME);
        assertThat(testPrueba.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testPrueba.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testPrueba.getImagenAnyBlob()).isEqualTo(UPDATED_IMAGEN_ANY_BLOB);
        assertThat(testPrueba.getImagenAnyBlobContentType()).isEqualTo(UPDATED_IMAGEN_ANY_BLOB_CONTENT_TYPE);
        assertThat(testPrueba.getImagenBlob()).isEqualTo(UPDATED_IMAGEN_BLOB);
        assertThat(testPrueba.getImagenBlobContentType()).isEqualTo(UPDATED_IMAGEN_BLOB_CONTENT_TYPE);
        assertThat(testPrueba.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPrueba.getInstante()).isEqualTo(UPDATED_INSTANTE);
    }

    @Test
    public void updateNonExistingPrueba() throws Exception {
        int databaseSizeBeforeUpdate = pruebaRepository.findAll().size();

        // Create the Prueba

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPruebaMockMvc.perform(put("/api/pruebas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prueba)))
            .andExpect(status().isCreated());

        // Validate the Prueba in the database
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePrueba() throws Exception {
        // Initialize the database
        pruebaService.save(prueba);

        int databaseSizeBeforeDelete = pruebaRepository.findAll().size();

        // Get the prueba
        restPruebaMockMvc.perform(delete("/api/pruebas/{id}", prueba.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prueba> pruebaList = pruebaRepository.findAll();
        assertThat(pruebaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prueba.class);
        Prueba prueba1 = new Prueba();
        prueba1.setId("id1");
        Prueba prueba2 = new Prueba();
        prueba2.setId(prueba1.getId());
        assertThat(prueba1).isEqualTo(prueba2);
        prueba2.setId("id2");
        assertThat(prueba1).isNotEqualTo(prueba2);
        prueba1.setId(null);
        assertThat(prueba1).isNotEqualTo(prueba2);
    }
}
