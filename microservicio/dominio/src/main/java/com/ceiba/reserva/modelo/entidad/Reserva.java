package com.ceiba.reserva.modelo.entidad;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;

@Getter
@Setter
public class Reserva {

    private static final String SE_DEBE_INGRESAR_EL_TIPO_DE_USUARIO = "Se debe ingresar el tipo de usuario";
    private static final String SE_DEBE_INGRESAR_EL_NOMBRE_DE_CLIENTE = "Se debe ingresar el nombre del cliente";
    private static final String SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO = "Se debe ingresar el numero de documento";


    private Long id;
    private String nombreCliente;
    private Integer tipoUsuario;
    private Integer numeroDocumento;
    private Long costoReserva;
    private LocalDate fechaReserva;

    public Reserva(Long id, String nombreCliente, Integer tipoUsuario, Integer numeroDocumento, Long costoReserva, LocalDate fechaReserva) {
        validarObligatorio(nombreCliente, SE_DEBE_INGRESAR_EL_NOMBRE_DE_CLIENTE);
        validarObligatorio(tipoUsuario, SE_DEBE_INGRESAR_EL_TIPO_DE_USUARIO);
        validarObligatorio(numeroDocumento,SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO);

        this.id = id;
        this.nombreCliente = nombreCliente;
        this.tipoUsuario = tipoUsuario;
        this.numeroDocumento = numeroDocumento;
        this.fechaReserva = fechaReserva;
        this.costoReserva = costoReserva;
    }

}
