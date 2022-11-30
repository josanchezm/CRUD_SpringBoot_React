package com.sofka.contactos.domain;


import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * Entidad del Contacto
 *
 */
@Data
@Entity
@Table(name = "contacto")
public class Contacto implements Serializable {
    /**
     * Variable usada para manejar el tema del identificador de la tupla (consecutivo)
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la tupla
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnt_id", nullable = false)
    private Integer id;

    /**
     * Nombre del contacto
     */
    @Column(name = "cnt_nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Apellido del contacto
     */
    @Column(name = "cnt_apellido", nullable = false, length = 100)
    private String apellido;

    /**
     * Teléfono del contacto
     */
    @Column(name = "cnt_telefono", nullable = false, length = 30)
    private String telefono;

    /**
     * Email del contacto
     */
    @Column(name = "cnt_email", nullable = false, length = 100)
    private String email;

    /**
     * Fecha de nacimiento del contacto
     */
    @Column(name = "cnt_fecha_nac", nullable = false)
    private Date fecha_nac;

    /**
     * Estado del contacto (para implementar borrado lógico)
     */
    @Column(name = "cnt_estado", nullable = false)
    private Boolean estado = true;

    /**
     * Fecha y hora en que la tupla ha sido creada
     */
    @Column(name = "cnt_created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Fecha y hora en que la tupla ha sido actualizada por última vez
     */
    @Column(name = "cnt_updated_at")
    private Instant updatedAt;
}