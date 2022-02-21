package com.ceiba.reserva.testdatabuilder;

import com.ceiba.reserva.comando.ComandoReserva;

import java.time.LocalDate;
import java.util.UUID;

public class ComandoReservaTestDataBuilder {

    private Long id;
    private String nombreCliente;
    private Integer tipoUsuario;
    private Integer numeroDocumento;
    private Long costoReserva;
    private LocalDate fechaReserva;

    public ComandoReservaTestDataBuilder() {
        nombreCliente = UUID.randomUUID().toString();
        tipoUsuario = 1;
        numeroDocumento = 144;
        costoReserva = Long.valueOf(200000);
        fechaReserva = LocalDate.now();
    }

    public ComandoReservaTestDataBuilder conNombre(String nombre) {
        this.nombreCliente = nombre;
        return this;
    }

    public ComandoReservaTestDataBuilder conTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        return this;
    }

    public ComandoReservaTestDataBuilder conNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public ComandoReserva build() {
        return new ComandoReserva(id,nombreCliente, tipoUsuario,numeroDocumento, costoReserva, fechaReserva);
    }
}
