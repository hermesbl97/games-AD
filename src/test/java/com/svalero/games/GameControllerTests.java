package com.svalero.games;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svalero.games.controller.GameController;
import com.svalero.games.dto.GameOutDto;
import com.svalero.games.service.GameService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class) //queremos testear la clase Controller
public class GameControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAll() throws Exception {
        List<GameOutDto> gamesOutDto = List.of(
                new GameOutDto(1,"7 days to die","juego divertido", "shooter", "survival"),
                new GameOutDto(2,"Fifa 25","football game", "sports", "sports")
        );

        when(gameService.findAll("")).thenReturn(gamesOutDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/games")  //respuesta de la llamada
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<GameOutDto> gamesListResponse = objectMapper.readValue(jsonResponse, new TypeReference<>() {}); //va a convertir el String de json a java

        assertEquals(2, gamesListResponse.size()); //comprobamos que hay dos elementos
        assertEquals("Fifa 25", gamesListResponse.get(1).getName());
        assertNotNull(gamesListResponse); //se supone que la respuesta de los juegos no debería ser nula
    }

    @Test
    public void testGetAllByCategory() throws Exception {
        List<GameOutDto> gamesOutDto = List.of(
                new GameOutDto(1,"7 days to die","juego divertido", "shooter", "survival"),
                new GameOutDto(2,"Fifa 25","football game", "sports", "sports"),
                new GameOutDto(3,"Fifa 26","football game", "sports", "sports")
        );

        when(gameService.findAll("sports")).thenReturn(gamesOutDto); //simulamos que hacemos la llamada de filtrado por categoria

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/games")  //respuesta de la llamada
                        .queryParam("category", "sports") //le estamos pasando un filtrado de busqueda por categoria y la palabra es sports
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<GameOutDto> gamesListResponse = objectMapper.readValue(jsonResponse, new TypeReference<>() {}); //va a convertir el String de json a java

        assertEquals(3, gamesListResponse.size()); //comprobamos que hay dos elementos
        assertEquals("Fifa 25", gamesListResponse.get(1).getName());
        assertNotNull(gamesListResponse); //se supone que la respuesta de los juegos no debería ser nula


    }
}
