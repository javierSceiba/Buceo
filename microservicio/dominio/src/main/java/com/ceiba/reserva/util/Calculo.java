package com.ceiba.reserva.util;

import com.ceiba.reserva.modelo.entidad.Reserva;

import java.time.DayOfWeek;

public class Calculo {
    private static final int UNO = 1;
    private static final int DIEZ = 10;
    private static final int VEINTE = 20;
    private static final int CIEN = 100;
    private static final Long TARIFA_FIJA = Long.valueOf(200000);

    public Long calcularCostoReserva(Reserva reserva, Boolean crear){
        Long costoReserva = (crear == true) ? TARIFA_FIJA : (TARIFA_FIJA +  calcularPorcentaje(VEINTE));
        if(reserva.getFechaReserva().getDayOfWeek() == DayOfWeek.SATURDAY){
            costoReserva =  costoReserva + calcularPorcentaje(DIEZ);
        }
        if(reserva.getTipoUsuario() == UNO){
            costoReserva =  costoReserva - calcularPorcentaje(VEINTE);
        }
        return costoReserva;
    }

    public Long calcularPorcentaje(Integer porcentaje){
        return ((TARIFA_FIJA * porcentaje) / CIEN);
    }
}
