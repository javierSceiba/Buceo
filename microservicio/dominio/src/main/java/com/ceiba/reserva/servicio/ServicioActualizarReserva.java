package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.util.Calculo;

import java.time.DayOfWeek;

public class ServicioActualizarReserva {

    private static final String LA_RESERVA_NO_EXISTE_EN_EL_SISTEMA = "La reserva no existe en el sistema";
    private static final String LA_FECHA_DE_RESERVA_NO_ES_VALIDA = "La fecha de reserva no es valida";
    private static final int UNO = 1;
    private static final int DIEZ = 10;
    private static final int VEINTE = 20;
    private static final int CIEN = 100;
    private static final Long TARIFA_FIJA = Long.valueOf(200000);
    private final RepositorioReserva repositorioReserva;
    private final Calculo calculo;

    public ServicioActualizarReserva(RepositorioReserva respositorioReserva) {
        this.repositorioReserva = respositorioReserva;
    }

    public void ejecutar(Reserva reserva) {
        validarExistenciaPrevia(reserva);
        validarFechaCorrecta(reserva);
        reserva.setCostoReserva(calcularCostoReserva(reserva));
        this.repositorioReserva.actualizar(reserva);
    }

    private void validarExistenciaPrevia(Reserva reserva) {
        boolean existe = this.repositorioReserva.existePorId(reserva.getId());
        if(!existe) {
            throw new ExcepcionDuplicidad(LA_RESERVA_NO_EXISTE_EN_EL_SISTEMA);
        }
    }

    private void validarFechaCorrecta(Reserva reserva){
        if(reserva.getFechaReserva().getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new ExcepcionValorInvalido(LA_FECHA_DE_RESERVA_NO_ES_VALIDA);
        }
    }

    public Long calcularCostoReserva(Reserva reserva){
        Long costoReserva = TARIFA_FIJA +  calcularPorcentaje(VEINTE);
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
