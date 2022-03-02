package com.ceiba.reserva.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class ServicioCrearReservaTest {

    @Test
    @DisplayName("Deberia lanzar una exepcion cuando se valide la existencia del Usuario")
    void deberiaLanzarUnaExepcionCuandoSeValideLaExistenciaDelUsuario() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existe(Mockito.anyInt(),any(LocalDate.class))).thenReturn(true);
        ServicioCrearReserva servicioCrearUsuario = new ServicioCrearReserva(repositorioReserva);
        // act - assert
        BasePrueba.assertThrows(() -> servicioCrearUsuario.ejecutar(reserva), ExcepcionDuplicidad.class,"El cliente tiene una reserva activa");
    }

    @Test
    @DisplayName("Deberia Crear el usuario de manera correcta")
    void deberiaCrearElUsuarioDeManeraCorrecta() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existe(Mockito.anyInt(),any(LocalDate.class))).thenReturn(false);
        Mockito.when(repositorioReserva.crear(reserva)).thenReturn(10L);
        ServicioCrearReserva servicioCrearReserva = new ServicioCrearReserva(repositorioReserva);
        // act
        Long idReserva = servicioCrearReserva.ejecutar(reserva);
        //- assert
        assertEquals(10L,idReserva);
        Mockito.verify(repositorioReserva, Mockito.times(1)).crear(reserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario nativo")
    void deberiaCalcularCostoNativo() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(1).build();
        // act
        Long costoReserva = reserva.calcularCostoReserva(true);
        //- assert
        assertEquals(160000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario nativo un sabado")
    void deberiaCalcularCostoNativoSabado() {
        // arrange
        LocalDate fechaReserva = LocalDate.of(2022,02,19);
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(1).conFechaReserva(fechaReserva).build();
        // act
        Long costoReserva = reserva.calcularCostoReserva(true);
        //- assert
        assertEquals(180000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario turista")
    void deberiaCalcularCostoTurista() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(2).build();
        // act
        Long costoReserva = reserva.calcularCostoReserva(true);
        //- assert
        assertEquals(200000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la reserva para tipo de usuario turista un día sabado")
    void deberiaCalcularCostoTuristaSabado() {
        // arrange
        LocalDate fechaReserva = LocalDate.of(2022,02,19);
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(2).conFechaReserva(fechaReserva).build();
        // act
        Long costoReserva = reserva.calcularCostoReserva(true);
        //- assert
        assertEquals(220000L,costoReserva);
    }


    @Test
    @DisplayName("Deberia calcular la fecha si el día siguiente es domingo")
    void deberiaCalcularFechaReservaDomingo() {
        // arrange
        LocalDate fecha = LocalDate.of(2022,02,19);
        Reserva reserva = new ReservaTestDataBuilder().build();
        // act
        LocalDate fechaReserva = reserva.calcularFechaReserva(fecha);
        //- assert
        assertEquals(LocalDate.of(2022,02,21),fechaReserva);
    }
}
