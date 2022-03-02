package com.ceiba.reserva.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.reserva.comando.ComandoReserva;
import com.ceiba.reserva.comando.manejador.ManejadorActualizarReserva;
import com.ceiba.reserva.comando.manejador.ManejadorCrearReserva;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buceo")
@Api(tags = { "Controlador comando usuario"})
@CrossOrigin(origins = "http://localhost:4200")
public class ComandoControladorReserva {

    private final ManejadorCrearReserva manejadorCrearReserva;
	private final ManejadorActualizarReserva manejadorActualizarReserva;

    @Autowired
    public ComandoControladorReserva(ManejadorCrearReserva manejadorCrearReserva,
									 ManejadorActualizarReserva manejadorActualizarReserva) {
        this.manejadorCrearReserva = manejadorCrearReserva;
		this.manejadorActualizarReserva = manejadorActualizarReserva;
    }

    @PostMapping
    @ApiOperation("Crear reserva")
    public ComandoRespuesta<Long> crear(@RequestBody ComandoReserva comandoReserva) {
        return manejadorCrearReserva.ejecutar(comandoReserva);
    }

	@PutMapping(value="/{id}")
	@ApiOperation("Actualizar reserva")
	public void actualizar(@RequestBody ComandoReserva comandoReserva,@PathVariable Long id) {
		comandoReserva.setId(id);
		manejadorActualizarReserva.ejecutar(comandoReserva);
	}
}
