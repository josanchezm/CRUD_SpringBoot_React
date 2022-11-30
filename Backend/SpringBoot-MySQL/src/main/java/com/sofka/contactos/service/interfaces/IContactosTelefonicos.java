package com.sofka.contactos.service.interfaces;

import com.sofka.contactos.domain.Contacto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interfaz para el servicio de Libreta
 *
 */
public interface IContactosTelefonicos {

    /**
     * Devuelve una lista de Contactos con todos contactos del sistema
     *
     * @return Lista de contactos
     *
     */
    public List<Contacto> getList();

    /**
     * Devuelve una lista de con los Contactos que tengan como estado true (borrado lógico)
     *
     * @return Lista de contactos con estado true
     *
     */
    @Transactional(readOnly = true)
    List<Contacto> getListByEstado();

    /**
     * Crea un contacto en el sistema
     *
     * @param contacto Objeto del contacto a crear
     * @return Objeto del contacto creado
     *
     */
    public Contacto createContacto(Contacto contacto);

    /**
     * Actualiza una tupla completa de un contacto
     *
     * @param id Identificador del contacto a actualizar
     * @param contacto Objeto del contacto a actualizar
     * @return Objeto del contacto actualizado
     *
     */
    Contacto updateContacto(Integer id, Contacto contacto);

    /**
     * Actualiza el estado de un contacto basado en su identificador
     *
     * @param id Identificador del contacto a actualizar
     * @param contacto Objeto del contacto a actualizar
     * @return Objeto del contacto actualizado
     *
     */
    Contacto updateEstado(Integer id, Contacto contacto);

    /**
     * Borra un contacto del sistema basado en su identificador
     *
     * @param id Identificación del contacto a borrar
     * @return Objeto del contacto borrado
     *
     */
    Contacto deleteContacto(Integer id);
}
