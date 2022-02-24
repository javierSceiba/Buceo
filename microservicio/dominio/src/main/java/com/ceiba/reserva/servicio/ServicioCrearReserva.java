package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class ServicioCrearReserva {

    private static final String EL_CLIENTE_TIENE_UNA_RESERVA_ACTIVA = "El cliente tiene una reserva activa";
    private static final int UNO = 1;
    private static final int DIEZ = 10;
    private static final int VEINTE = 20;
    private static final int CIEN = 100;
    private static final Long TARIFA_FIJA = Long.valueOf(200000);
    private final RepositorioReserva repositorioReserva;

    public ServicioCrearReserva(RepositorioReserva repositorioReserva) {
        this.repositorioReserva = repositorioReserva;
    }

    public Long ejecutar(Reserva reserva) {
        LocalDate fechaHoy = LocalDate.now();
        validarReservaActiva(reserva);
        reserva.setFechaReserva(calcularFechaReserva(fechaHoy));
        reserva.setCostoReserva(calcularCostoReserva(reserva));
        return this.repositorioReserva.crear(reserva);
    }

    private void validarReservaActiva(Reserva reserva) {
            LocalDate fechaHoy = LocalDate.now();
                if(this.repositorioReserva.existe(reserva.getNumeroDocumento(),fechaHoy)){
                    throw new ExcepcionDuplicidad(EL_CLIENTE_TIENE_UNA_RESERVA_ACTIVA);
                }
    }

    protected LocalDate calcularFechaReserva(LocalDate fechaHoy){
        fechaHoy = fechaHoy.plusDays(UNO);
        if(fechaHoy.getDayOfWeek() == DayOfWeek.SUNDAY){
            fechaHoy = fechaHoy.plusDays(UNO);
        }
        return fechaHoy;
    }

    protected Long calcularCostoReserva(Reserva reserva){
        Long costoReserva = TARIFA_FIJA;
        if(reserva.getFechaReserva().getDayOfWeek() == DayOfWeek.SATURDAY){
            costoReserva =  costoReserva + calcularPorcentaje(DIEZ);
        }
        if(reserva.getTipoUsuario() == UNO){
            costoReserva =  costoReserva - calcularPorcentaje(VEINTE);
        }
        return costoReserva;
    }

    private Long calcularPorcentaje(Integer porcentaje){
        return ((TARIFA_FIJA * porcentaje) / CIEN);
    }
}
