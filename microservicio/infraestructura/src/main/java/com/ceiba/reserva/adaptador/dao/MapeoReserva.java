package com.ceiba.reserva.adaptador.dao;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.reserva.modelo.dto.DtoReserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MapeoReserva implements RowMapper<DtoReserva>, MapperResult {

    @Override
    public DtoReserva mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Long id = resultSet.getLong("id");
        String nombreCliente = resultSet.getString("nombre_cliente");
        Integer tipoUsuario = resultSet.getInt("tipo_usuario");
        Integer numeroDocumento = resultSet.getInt("numero_documento");
        Long costoReserva = resultSet.getLong("costo_reserva");
        LocalDate fechaReserva = extraerLocalDate(resultSet, "fecha_reserva");

        return new DtoReserva(id,nombreCliente,tipoUsuario,numeroDocumento,costoReserva,fechaReserva);
    }

}
