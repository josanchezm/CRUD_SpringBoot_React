package com.sofka.contactos.repository;

import com.sofka.contactos.domain.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para la entidad Contacto
 *
 */
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    /**
     * Busca los contactos que tengan true como estado (borrado lógico)
     *
     * @return Listado de contactos encontrados
     *
     */
    @Query(value = "SELECT cnt " +
            "FROM Contacto cnt " +
            "WHERE cnt.estado = true")
    public List<Contacto> findByEstadoTrue();

    /**
     * Actualiza el estado de un contacto basado en su identificador
     *
     * @param id Identificador del contacto
     * @param estado Nueva estado del contacto
     *
     */
    @Modifying
    @Query(value = "update Contacto cnt set cnt.estado = :estado, cnt.updatedAt = CURRENT_TIMESTAMP where cnt.id = :id")
    public void updateEstado(@Param(value = "id") Integer id, @Param(value = "estado") Boolean estado);
}