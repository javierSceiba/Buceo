package com.ceiba.reserva.modelo.entidad;


import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;

@Getter
public class Reserva {

    private static final String SE_DEBE_INGRESAR_EL_TIPO_DE_USUARIO = "Se debe ingresar el tipo de usuario";
    private static final String SE_DEBE_INGRESAR_EL_NOMBRE_DE_CLIENTE = "Se debe ingresar el nombre del cliente";
    private static final String SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO = "Se debe ingresar el numero de documento";
    private static final int NATIVO = 1;
    private static final int UN_DIA = 1;
    private static final int PORCENTAJE_POR_DIA_SABADO = 10;
    private static final int PORCENTAJE_POR_SER_NATIVO = 20;
    private static final int PORCENTAJE_POR_ACTUALIZAR_RESERVA = 20;
    private static final int CIEN = 100;
    private static final Long TARIFA_FIJA = Long.valueOf(200000);

    private Long id;
    private String nombreCliente;
    private Integer tipoUsuario;
    private Integer numeroDocumento;
    private Long costoReserva;
    private LocalDate fechaReserva;

    public Reserva(Long id, String nombreCliente, Integer tipoUsuario, Integer numeroDocumento, LocalDate fechaReserva) {
        validarObligatorio(nombreCliente, SE_DEBE_INGRESAR_EL_NOMBRE_DE_CLIENTE);
        validarObligatorio(tipoUsuario, SE_DEBE_INGRESAR_EL_TIPO_DE_USUARIO);
        validarObligatorio(numeroDocumento,SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO);

        this.id = id;
        this.nombreCliente = nombreCliente;
        this.tipoUsuario = tipoUsuario;
        this.numeroDocumento = numeroDocumento;
        this.fechaReserva = (fechaReserva == null) ? calcularFechaReserva(LocalDate.now()) : fechaReserva;
        this.costoReserva = (fechaReserva == null) ? calcularCostoReserva(true) : calcularCostoReserva(false) ;
    }

    private LocalDate calcularFechaReserva(LocalDate fechaHoy){
        fechaHoy = fechaHoy.plusDays(UN_DIA);
        if(fechaHoy.getDayOfWeek() == DayOfWeek.SUNDAY){
            fechaHoy = fechaHoy.plusDays(UN_DIA);
        }
        return fechaHoy;
    }

    private Long calcularCostoReserva(Boolean crear){
        Long costo = Boolean.TRUE.equals(crear) ? TARIFA_FIJA : (TARIFA_FIJA +  calcularPorcentaje(PORCENTAJE_POR_ACTUALIZAR_RESERVA));
        if(this.fechaReserva.getDayOfWeek() == DayOfWeek.SATURDAY){
            costo =  costo + calcularPorcentaje(PORCENTAJE_POR_DIA_SABADO);
        }
        if(this.tipoUsuario == NATIVO){
            costo =  costo - calcularPorcentaje(PORCENTAJE_POR_SER_NATIVO);
        }
        return costo;
    }

    private Long calcularPorcentaje(Integer porcentaje){
        return ((TARIFA_FIJA * porcentaje) / CIEN);
    }
}
