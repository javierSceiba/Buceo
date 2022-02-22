package com.ceiba.reserva.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicioActualizarReservaTest {

    @Test
    @DisplayName("Deberia validar la existencia previa de la reserva")
    void deberiaValidarLaExistenciaPreviaDelUsuario() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conId(1L).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existePorId(Mockito.anyLong())).thenReturn(false);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act - assert
        BasePrueba.assertThrows(() -> servicioActualizarReserva.ejecutar(reserva), ExcepcionDuplicidad.class,"La reserva no existe en el sistema");
    }
    @Test
    @DisplayName("Deberia validar que la fecha de reserva no sea un Domingo")
    void deberiaValidarLaFechaDeReserva() {
        LocalDate fechaReserva = LocalDate.of(2022,02,20);
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conId(1L).conFechaReserva(fechaReserva).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existePorId(Mockito.anyLong())).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act - assert
        BasePrueba.assertThrows(() -> servicioActualizarReserva.ejecutar(reserva), ExcepcionValorInvalido.class,"La fecha de reserva no es valida");
    }
    @Test
    @DisplayName("Deberia actualizar correctamente en el repositorio")
    void deberiaActualizarCorrectamenteEnElRepositorio() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conId(1L).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existePorId(Mockito.anyLong())).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act
        servicioActualizarReserva.ejecutar(reserva);
        //assert
        Mockito.verify(repositorioReserva,Mockito.times(1)).actualizar(reserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario nativo")
    void deberiaCalcularCostoNativo() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(1).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act
        Long costoReserva = servicioActualizarReserva.calcularCostoReserva(reserva);
        //- assert
        assertEquals(200000L,costoReserva);
    }
    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario nativo un sabado")
    void deberiaCalcularCostoNativoSabado() {
        // arrange
        LocalDate fechaReserva = LocalDate.of(2022,02,19);
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(1).conFechaReserva(fechaReserva).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act
        Long costoReserva = servicioActualizarReserva.calcularCostoReserva(reserva);
        //- assert
        assertEquals(220000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario turista")
    void deberiaCalcularCostoTurista() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(2).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act
        Long costoReserva = servicioActualizarReserva.calcularCostoReserva(reserva);
        //- assert
        assertEquals(240000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario turista un día sabado")
    void deberiaCalcularCostoTuristaSabado() {
        // arrange
        LocalDate fechaReserva = LocalDate.of(2022,02,19);
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(2).conFechaReserva(fechaReserva).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act
        Long costoReserva = servicioActualizarReserva.calcularCostoReserva(reserva);
        //- assert
        assertEquals(260000L,costoReserva);
    }
}
