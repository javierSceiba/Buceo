package com.ceiba.reserva.puerto.repositorio;

import com.ceiba.reserva.modelo.entidad.Reserva;

import java.time.LocalDate;

public interface RepositorioReserva {
    /**
     * Permite crear una reserva
     * @param reserva
     * @return el id generado
     */
    Long crear(Reserva reserva);

    /**
     * Permite actualizar una reserva
     * @param reserva
     */
    void actualizar(Reserva reserva);

    /**
     * Permite consultar si existe una reserva activa con un número de documento y fecha
     * @param numero
     * @param fecha
     * @return
     */
    boolean existe(Integer numero, LocalDate fecha);

    /**
     * Permite validar si existe una reserva en el sistema por id
     * @param id
     * @return si existe o no
     */
    boolean existePorId(Long id);
}
