package com.ceiba.reserva.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.reserva.comando.ComandoReserva;
import com.ceiba.reserva.testdatabuilder.ComandoReservaTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComandoControladorReserva.class)
@ContextConfiguration(classes= ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ComandoControladorReservaTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mocMvc;

    @Test
    @DisplayName("Deberia crear un usuario")
    void deberiaCrearUnUsuario() throws Exception{
        // arrange
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().build();
        // act - assert
        mocMvc.perform(post("/buceo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'valor': 2}"));
    }

    @Test
    @DisplayName("Deberia actualizar un usuario")
    void deberiaActualizarUnUsuario() throws Exception{
        // arrange
        Long id = 1L;
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().build();
        // act - assert
        mocMvc.perform(put("/buceo/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deberia no actualizar un usuario")
    void deberiaNoActualizarUnUsuario() throws Exception{
        // arrange
        Long id = 100L;
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().build();
        // act - assert
        mocMvc.perform(put("/buceo/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia no crear un usuario")
    void deberiaNoCrearUnUsuario() throws Exception{
        // arrange
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().conTipoUsuario(null).build();
        // act - assert
        mocMvc.perform(post("/buceo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isBadRequest());
    }

}
