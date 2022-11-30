package com.sofka.contactos.controller;

import com.sofka.contactos.domain.Contacto;
import com.sofka.contactos.service.ContactosTelefonicosService;
import com.sofka.contactos.utility.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controlador para la libreta
 */
@Slf4j
@RestController
public class ContactosTelefonicosController {

    /**
     * Servicio para el manejo de la libreta
     */
    @Autowired
    private ContactosTelefonicosService contactosTelefonicosService;

    /**
     * Variable para el manejo de las respuestas de las API
     */
    private Response response = new Response();

    /**
     * Manejo del código HTTP que se responde en las API
     */
    private HttpStatus httpStatus = HttpStatus.OK;

    /**
     * Atención a la dirección raíz del sistema, este redirige a /api/v1/index
     *
     * @param httpResponse Objeto HttpServletResponse usado para redireccionar el controlador
     * @return Objeto Response en formato JSON
     *
     */
    @GetMapping(path = "/")
    public ResponseEntity<Response> homeIndex1(HttpServletResponse httpResponse) {
        return getResponseHome(httpResponse);
    }

    /**
     * Atención a la dirección raíz, API del sistema, este redirige a /api/v1/index
     *
     * @param httpResponse Objeto HttpServletResponse usado para redireccionar el controlador
     * @return Objeto Response en formato JSON
     */
    @GetMapping(path = "/api/")
    public ResponseEntity<Response> homeIndex2(HttpServletResponse httpResponse) {
        return getResponseHome(httpResponse);
    }

    /**
     * Atención a la dirección raíz, API del sistema y versión, este redirige a /api/v1/index
     *
     * @param httpResponse Objeto HttpServletResponse usado para redireccionar el controlador
     * @return Objeto Response en formato JSON
     */
    @GetMapping(path = "/api/v1/")
    public ResponseEntity<Response> homeIndex3(HttpServletResponse httpResponse) {
        return getResponseHome(httpResponse);
    }

    /**
     * Index del sistema, responde con el listado de contactos, sus teléfonos y emails
     *
     * @return Objeto Response en formato JSON
     *
     */
    @GetMapping(path = "/api/v1/index")
    public ResponseEntity<Response> index() {
        response.restart();
        try {
            response.data = contactosTelefonicosService.getList();
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Responde con la lista de contactos que tengan como estado true (borrado lógico)
     *
     * @return Objeto Response en formato JSON
     *
     */
    @GetMapping(path = "/api/v1/index/true")
    public ResponseEntity<Response> softDelete() {
        response.restart();
        try {
            response.data = contactosTelefonicosService.getListByEstado();
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Crea un nuevo contacto en el sistema
     *
     * @param contacto Objeto Contacto a crear
     * @return Objeto Response en formato JSON
     *
     */
    @PostMapping(path = "/api/v1/contact")
    public ResponseEntity<Response> createContacto(@RequestBody Contacto contacto) {
        response.restart();
        try {
            log.info("Contacto a crear: {}", contacto);
            response.data = contactosTelefonicosService.createContacto(contacto);
            httpStatus = HttpStatus.CREATED;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Actualiza todos los campos de un contacto
     *
     * @param contacto Objeto contacto a actualizar
     * @param id Identificador del contacto a actualizar
     * @return Objeto Response en formato JSON
     *
     */
    @PutMapping(path = "/api/v1/contact/{id}")
    public ResponseEntity<Response> updateContacto(
            @RequestBody Contacto contacto,
            @PathVariable(value="id") Integer id
    ) {
        response.restart();
        try {
            response.data = contactosTelefonicosService.updateContacto(id, contacto);
            httpStatus = HttpStatus.OK;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Actualiza el estado de un contacto basado en su identificador (para borrado lógico)
     *
     * @param contacto Objeto Contacto
     * @param id Identificador del contacto a actualizar
     * @return Objeto Response en formato JSON
     *
     */
    @PatchMapping(path = "/api/v1/contact/{id}/estado")
    public ResponseEntity<Response> updateEstadoFromContacto(
            @RequestBody Contacto contacto,
            @PathVariable(value="id") Integer id
    ) {
        response.restart();
        try {
            response.data = contactosTelefonicosService.updateEstado(id, contacto);
            httpStatus = HttpStatus.OK;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Borra un contacto del sistema
     *
     * @param id Identificador del contacto a borrar
     * @return Objeto Response en formato JSON
     *
     */
    @DeleteMapping(path = "/api/v1/contact/{id}")
    public ResponseEntity<Response> deleteContacto(@PathVariable(value="id") Integer id) {
        response.restart();
        try {
            response.data = contactosTelefonicosService.deleteContacto(id);
            if (response.data == null) {
                response.message = "El contacto no existe";
                httpStatus = HttpStatus.NOT_FOUND;
            } else {
                response.message = "El contacto fue removido exitosamente";
                httpStatus = HttpStatus.OK;
            }
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Administrador para la redirección al controllador /api/v1/index
     *
     * @param httpResponse Objeto HttpServletResponse para el manejo de la redirección
     * @return Objeto Response en formato JSON
     *
     */
    private ResponseEntity<Response> getResponseHome(HttpServletResponse httpResponse) {
        response.restart();
        try {
            httpResponse.sendRedirect("/api/v1/index");
        } catch (IOException exception) {
            response.error = true;
            response.data = exception.getCause();
            response.message = exception.getMessage();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Administrador para las excepciones del sistema
     *
     * @param exception Objeto Exception
     *
     */
    private void getErrorMessageInternal(Exception exception) {
        response.error = true;
        response.message = exception.getMessage();
        response.data = exception.getCause();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Administrador para las excepciones a nivel de SQL con respecto al manejo del acceso a los datos
     *
     * @param exception Objeto DataAccessException
     *
     */
    private void getErrorMessageForResponse(DataAccessException exception) {
        response.error = true;
        if(exception.getRootCause() instanceof SQLException) {
            SQLException sqlEx = (SQLException) exception.getRootCause();
            var sqlErrorCode = sqlEx.getErrorCode();
            switch (sqlErrorCode) {
                case 1062:
                    response.message = "El dato ya está registrado";
                    break;
                case 1452:
                    response.message = "El usuario indicado no existe";
                    break;
                default:
                    response.message = exception.getMessage();
                    response.data = exception.getCause();
            }
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            response.message = exception.getMessage();
            response.data = exception.getCause();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
