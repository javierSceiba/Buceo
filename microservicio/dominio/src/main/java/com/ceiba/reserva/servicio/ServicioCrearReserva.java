package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

import java.time.LocalDate;


public class ServicioCrearReserva {

    private static final String EL_CLIENTE_TIENE_UNA_RESERVA_ACTIVA = "El cliente tiene una reserva activa";
    private final RepositorioReserva repositorioReserva;

    public ServicioCrearReserva(RepositorioReserva repositorioReserva) {
        this.repositorioReserva = repositorioReserva;
    }

    public Long ejecutar(Reserva reserva) {
        validarReservaActiva(reserva);
        return this.repositorioReserva.crear(reserva);
    }

    private void validarReservaActiva(Reserva reserva) {
            LocalDate fechaHoy = LocalDate.now();
                if(this.repositorioReserva.existe(reserva.getNumeroDocumento(),fechaHoy)){
                    throw new ExcepcionDuplicidad(EL_CLIENTE_TIENE_UNA_RESERVA_ACTIVA);
                }
    }
}
