package com.svalero.games;

import com.svalero.games.domain.Game;
import com.svalero.games.dto.GameOutDto;
import com.svalero.games.repository.GameRepository;
import com.svalero.games.service.GameService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Equals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //extensión que sirve para mockear
public class GameServiceTests {

    @InjectMocks
    private GameService gameService; //como no hay datos de verdad, le decimos que los simule con el InjectMocks

    @Mock
    private GameRepository gameRepository;  //como la repository no la hemos programado nosotros le ponemos el mock

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {
        List<Game> mockGameList = List.of(
                new Game(1,"7 days to die","juego divertido", "shooter",LocalDate.now(), 100, "survival", null, null),
                new Game(2,"Fifa 25","football game", "sports",LocalDate.now(), 60, "sports", null, null)
        );

        List<GameOutDto> modelMapperOut = List.of(
                new GameOutDto(1,"7 days to die","juego divertido", "shooter", "survival"),
                new GameOutDto(2,"Fifa 25","football game", "sports", "sports")
        );

        when(gameRepository.findAll()).thenReturn(mockGameList);
        when(modelMapper.map(mockGameList, new TypeToken<List<GameOutDto>>(){}.getType())).thenReturn(modelMapperOut);
        //cuando llames a mockGameList devuelveme modelMapperOut

        List<GameOutDto> actualGameList = gameService.findAll(""); //filtramos así sin categoría y testeamos
        assertEquals(2,actualGameList.size());
        assertEquals("7 days to die", actualGameList.get(0).getName());  //comprobamos que el 1º juego de la lista tenga este nombre

        verify(gameRepository, times(1)).findAll(); //verificamos que hemos llamado una vez al método findAll
        verify(gameRepository, times(0)).findByCategory(""); //comprobamos que no se haya llamado al método category
    }

    @Test
    public void findAllByCategory() {
        List<Game> mockGameList = List.of(
                new Game(1,"7 days to die","juego divertido", "shooter",LocalDate.now(), 100, "survival", null, null),
                new Game(2,"Fifa 25","football game", "sports",LocalDate.now(), 60, "sports", null, null),
                new Game(3,"Fifa 26","football game", "sports",LocalDate.now(), 60, "sports", null, null)
        );

        List<GameOutDto> mockModelMapperOut = List.of(
                new GameOutDto(1,"7 days to die","juego divertido", "shooter", "survival"),
                new GameOutDto(2,"Fifa 25","football game", "sports", "sports"),
                new GameOutDto(3,"Fifa 26","football game", "sports", "sports")
        );

        when(gameRepository.findByCategory("sports")).thenReturn(mockGameList);
        when(modelMapper.map(mockGameList, new TypeToken<List<GameOutDto>>(){}.getType())).thenReturn(mockModelMapperOut);
        //cuando llames a mockGameList devuelveme mockModelMapperOut

        List<GameOutDto> actualGameList = gameService.findAll("sports"); //filtramos así sin categoría y testeamos
        assertEquals(3,actualGameList.size());
        assertEquals("Fifa 26", actualGameList.get(2).getName());  //comprobamos que el 1º juego de la lista tenga este nombre

        verify(gameRepository, times(0)).findAll(); //verificamos que hemos llamado una vez al método findAll
        verify(gameRepository, times(1)).findByCategory("sports"); //comprobamos que no se haya llamado al método category

    }
}
