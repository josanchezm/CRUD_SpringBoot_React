package com.sofka.contactos.service;

import com.sofka.contactos.domain.Contacto;
import com.sofka.contactos.repository.ContactoRepository;
import com.sofka.contactos.service.interfaces.IContactosTelefonicos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Clase tipo Servicio para el manejo de los contactos telefonicos
 *
 */
@Service
public class ContactosTelefonicosService implements IContactosTelefonicos {

    /**
     * Repositorio de Contacto
     */
    @Autowired
    private ContactoRepository contactoRepository;

    /**
     * Devuelve una lista de Contactos con todos contactos del sistema
     *
     * @return Lista de contactos
     *
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contacto> getList() {
        return contactoRepository.findAll();
    }

    /**
     * Devuelve una lista con los contactos que tengan como estado true (borrado logico)
     *
     * @return Lista de contactos con estado true
     *
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contacto> getListByEstado() {
        return contactoRepository.findByEstadoTrue();
    }

    /**
     * Crea un contacto en el sistema
     *
     * @param contacto Objeto del contacto a crear
     * @return Objeto del contacto creado
     *
     */
    @Override
    @Transactional
    public Contacto createContacto(Contacto contacto) {
        contacto.setCreatedAt(Instant.now());
        return contactoRepository.save(contacto);
    }

    /**
     * Actualiza una tupla completa de un contacto
     *
     * @param id Identificador del contacto a actualizar
     * @param contacto Objeto del contacto a actualizar
     * @return Objeto del contacto actualizado
     *
     */

    @Override
    @Transactional
    public Contacto updateContacto(Integer id, Contacto contacto) {
        contacto.setId(id);
        contacto.setUpdatedAt(Instant.now());
        return contactoRepository.save(contacto);
    }

    /**
     * Actualiza el estado de un contacto
     *
     * @param id Identificador del contacto a actualizar
     * @param contacto Objeto del contacto a actualizar
     * @return Objeto del contacto actualizado
     *
     */
    @Override
    @Transactional
    public Contacto updateEstado(Integer id, Contacto contacto) {
        contacto.setId(id);
        contacto.setUpdatedAt(Instant.now());
        contactoRepository.updateEstado(id, contacto.getEstado());
        return contacto;
    }

    /**
     * Borra un contacto del sistema
     *
     * @param id Identificación del contacto a borrar
     * @return Objeto del contacto borrado
     *
     */
    @Override
    @Transactional
    public Contacto deleteContacto(Integer id) {
        var contacto = contactoRepository.findById(id);
        if (contacto.isPresent()) {
            contactoRepository.delete(contacto.get());
            return contacto.get();
        } else {
            return null;
        }
    }
}
