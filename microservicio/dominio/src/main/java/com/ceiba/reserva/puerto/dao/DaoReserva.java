package com.ceiba.reserva.puerto.dao;

import com.ceiba.reserva.modelo.dto.DtoReserva;


public interface DaoReserva {

    /**
     * Permite listar una reserva
     * @param id
     * @return reserva
     */
    DtoReserva listar(Long id);
}
