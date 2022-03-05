package com.ceiba.reserva.entidad;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservaTest {

    @Test
    @DisplayName("Deberia crear correctamente la reserva")
    void deberiaCrearCorrectamenteLaReserva() {
        // arrange
        LocalDate fechaReserva = LocalDate.now();
        //act
        Reserva reserva = new ReservaTestDataBuilder().conFechaReserva(fechaReserva).conId(1L).build();
        //assert
        assertEquals(1, reserva.getId());
        assertEquals("Antonio", reserva.getNombreCliente());
        assertEquals(1, reserva.getTipoUsuario());
        assertEquals(220000,reserva.getCostoReserva());
        assertEquals(fechaReserva, reserva.getFechaReserva());
    }

    @Test
    void deberiaFallarSinNombreDeCliente() {

        //Arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().conNombreCliente(null).conId(1L);
        //act-assert
        BasePrueba.assertThrows(() -> {
                    reservaTestDataBuilder.build();
                },
                ExcepcionValorObligatorio.class, "Se debe ingresar el nombre del cliente");
    }

    @Test
    void deberiaFallarSinTipoUsuario() {

        //Arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().conTipoUsuario(null).conId(1L);
        //act-assert
        BasePrueba.assertThrows(() -> {
                    reservaTestDataBuilder.build();
                },
                ExcepcionValorObligatorio.class, "Se debe ingresar el tipo de usuario");
    }

    @Test
    void deberiaFallarSinNumeroDocumento() {

        //Arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().conNumeroDocumento(null).conId(1L);
        //act-assert
        BasePrueba.assertThrows(() -> {
                    reservaTestDataBuilder.build();
                },
                ExcepcionValorObligatorio.class, "Se debe ingresar el numero de documento");
    }

    @Test
    @DisplayName("Deberia calcular el costo de la creacion de la reserva para tipo de usuario nativo")
    void deberiaCalcularCostoNativo() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(1).build();
        // act
        Long costoReserva = reserva.getCostoReserva();
        //- assert
        assertEquals(160000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la creacion de la reserva para tipo de usuario turista")
    void deberiaCalcularCostoTurista() {
        // arrange
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(2).build();
        // act
        Long costoReserva = reserva.getCostoReserva();
        //- assert
        assertEquals(200000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la actualizacion de la reserva para tipo de usuario nativo")
    void deberiaCalcularCostoActualizacionNativo() {
        // arrange
        LocalDate fechaReserva = LocalDate.now();
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(1).conFechaReserva(fechaReserva).build();
        // act
        Long costoReserva = reserva.getCostoReserva();
        //- assert
        assertEquals(220000L,costoReserva);
    }
    @Test
    @DisplayName("Deberia calcular el costo de la actualizacion de la reserva para tipo de usuario nativo un sabado")
    void deberiaCalcularCostoActualizacionNativoSabado() {
        // arrange
        LocalDate fechaReserva = LocalDate.of(2022,02,19);
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(1).conFechaReserva(fechaReserva).build();
        // act
        Long costoReserva = reserva.getCostoReserva();
        //- assert
        assertEquals(220000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la actualizacion de la reserva para tipo de usuario turista")
    void deberiaCalcularCostoActualizacionTurista() {
        // arrange
        LocalDate fechaReserva = LocalDate.now();
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(2).conFechaReserva(fechaReserva).build();
        // act
        Long costoReserva = reserva.getCostoReserva();
        //- assert
        assertEquals(260000L,costoReserva);
    }

    @Test
    @DisplayName("Deberia calcular el costo de la actualizacion de la reserva para tipo de usuario turista un día sabado")
    void deberiaCalcularCostoActualizacionTuristaSabado() {
        // arrange
        LocalDate fechaReserva = LocalDate.of(2022,02,19);
        Reserva reserva = new ReservaTestDataBuilder().conTipoUsuario(2).conFechaReserva(fechaReserva).build();
        // act
        Long costoReserva = reserva.getCostoReserva();
        //- assert
        assertEquals(260000L,costoReserva);
    }
}
