package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

import java.time.DayOfWeek;

public class ServicioActualizarReserva {

    private static final String LA_RESERVA_NO_EXISTE_EN_EL_SISTEMA = "La reserva no existe en el sistema";
    private static final String LA_FECHA_DE_RESERVA_NO_ES_VALIDA = "La fecha de reserva no es valida";
    private final RepositorioReserva repositorioReserva;

    public ServicioActualizarReserva(RepositorioReserva respositorioReserva) {
        this.repositorioReserva = respositorioReserva;
    }

    public void ejecutar(Reserva reserva) {
        validarExistenciaPrevia(reserva);
        validarFechaCorrecta(reserva);
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
}
