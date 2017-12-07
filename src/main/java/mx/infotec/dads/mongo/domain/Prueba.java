package mx.infotec.dads.mongo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Prueba.
 */

@Document(collection = "pruebas")
public class Prueba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 50)
    @Pattern(regexp = "hola")
    @Field("nombre")
    private String nombre;

    @NotNull
    @Min(value = 1)
    @Max(value = 23)
    @Field("edad")
    private Integer edad;

    @NotNull
    @Min(value = 1)
    @Max(value = 23)
    @Field("numero_credencial")
    private Long numeroCredencial;

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "23")
    @Field("sueldo")
    private BigDecimal sueldo;

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "23")
    @Field("impuesto")
    private Float impuesto;

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "23")
    @Field("impuesto_detalle")
    private Double impuestoDetalle;

    @NotNull
    @Field("activo")
    private Boolean activo;

    @NotNull
    @Field("fecha_local_date")
    private LocalDate fechaLocalDate;

    @NotNull
    @Field("fecha_zone_date_time")
    private ZonedDateTime fechaZoneDateTime;

    @NotNull
    @Size(min = 12, max = 56)
    @Field("imagen")
    private byte[] imagen;

    @Field("imagen_content_type")
    private String imagenContentType;

    @NotNull
    @Size(min = 78, max = 90)
    @Field("imagen_any_blob")
    private byte[] imagenAnyBlob;

    @Field("imagen_any_blob_content_type")
    private String imagenAnyBlobContentType;

    @NotNull
    @Size(min = 21, max = 23)
    @Field("imagen_blob")
    private byte[] imagenBlob;

    @Field("imagen_blob_content_type")
    private String imagenBlobContentType;

    @NotNull
    @Size(min = 3, max = 45)
    @Field("desc")
    private String desc;

    @NotNull
    @Field("instante")
    private Instant instante;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Prueba nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public Prueba edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Long getNumeroCredencial() {
        return numeroCredencial;
    }

    public Prueba numeroCredencial(Long numeroCredencial) {
        this.numeroCredencial = numeroCredencial;
        return this;
    }

    public void setNumeroCredencial(Long numeroCredencial) {
        this.numeroCredencial = numeroCredencial;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public Prueba sueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
        return this;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public Float getImpuesto() {
        return impuesto;
    }

    public Prueba impuesto(Float impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Float impuesto) {
        this.impuesto = impuesto;
    }

    public Double getImpuestoDetalle() {
        return impuestoDetalle;
    }

    public Prueba impuestoDetalle(Double impuestoDetalle) {
        this.impuestoDetalle = impuestoDetalle;
        return this;
    }

    public void setImpuestoDetalle(Double impuestoDetalle) {
        this.impuestoDetalle = impuestoDetalle;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Prueba activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaLocalDate() {
        return fechaLocalDate;
    }

    public Prueba fechaLocalDate(LocalDate fechaLocalDate) {
        this.fechaLocalDate = fechaLocalDate;
        return this;
    }

    public void setFechaLocalDate(LocalDate fechaLocalDate) {
        this.fechaLocalDate = fechaLocalDate;
    }

    public ZonedDateTime getFechaZoneDateTime() {
        return fechaZoneDateTime;
    }

    public Prueba fechaZoneDateTime(ZonedDateTime fechaZoneDateTime) {
        this.fechaZoneDateTime = fechaZoneDateTime;
        return this;
    }

    public void setFechaZoneDateTime(ZonedDateTime fechaZoneDateTime) {
        this.fechaZoneDateTime = fechaZoneDateTime;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Prueba imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Prueba imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public byte[] getImagenAnyBlob() {
        return imagenAnyBlob;
    }

    public Prueba imagenAnyBlob(byte[] imagenAnyBlob) {
        this.imagenAnyBlob = imagenAnyBlob;
        return this;
    }

    public void setImagenAnyBlob(byte[] imagenAnyBlob) {
        this.imagenAnyBlob = imagenAnyBlob;
    }

    public String getImagenAnyBlobContentType() {
        return imagenAnyBlobContentType;
    }

    public Prueba imagenAnyBlobContentType(String imagenAnyBlobContentType) {
        this.imagenAnyBlobContentType = imagenAnyBlobContentType;
        return this;
    }

    public void setImagenAnyBlobContentType(String imagenAnyBlobContentType) {
        this.imagenAnyBlobContentType = imagenAnyBlobContentType;
    }

    public byte[] getImagenBlob() {
        return imagenBlob;
    }

    public Prueba imagenBlob(byte[] imagenBlob) {
        this.imagenBlob = imagenBlob;
        return this;
    }

    public void setImagenBlob(byte[] imagenBlob) {
        this.imagenBlob = imagenBlob;
    }

    public String getImagenBlobContentType() {
        return imagenBlobContentType;
    }

    public Prueba imagenBlobContentType(String imagenBlobContentType) {
        this.imagenBlobContentType = imagenBlobContentType;
        return this;
    }

    public void setImagenBlobContentType(String imagenBlobContentType) {
        this.imagenBlobContentType = imagenBlobContentType;
    }

    public String getDesc() {
        return desc;
    }

    public Prueba desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Instant getInstante() {
        return instante;
    }

    public Prueba instante(Instant instante) {
        this.instante = instante;
        return this;
    }

    public void setInstante(Instant instante) {
        this.instante = instante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prueba prueba = (Prueba) o;
        if (prueba.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prueba.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prueba{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", edad='" + getEdad() + "'" +
            ", numeroCredencial='" + getNumeroCredencial() + "'" +
            ", sueldo='" + getSueldo() + "'" +
            ", impuesto='" + getImpuesto() + "'" +
            ", impuestoDetalle='" + getImpuestoDetalle() + "'" +
            ", activo='" + isActivo() + "'" +
            ", fechaLocalDate='" + getFechaLocalDate() + "'" +
            ", fechaZoneDateTime='" + getFechaZoneDateTime() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            ", imagenAnyBlob='" + getImagenAnyBlob() + "'" +
            ", imagenAnyBlobContentType='" + imagenAnyBlobContentType + "'" +
            ", imagenBlob='" + getImagenBlob() + "'" +
            ", imagenBlobContentType='" + imagenBlobContentType + "'" +
            ", desc='" + getDesc() + "'" +
            ", instante='" + getInstante() + "'" +
            "}";
    }
}
