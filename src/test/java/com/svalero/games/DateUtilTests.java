package com.svalero.games;

import com.svalero.games.util.DateUtil;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilTests {

    @Test
    public void testGetDaysBetweenDates() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 1);

        long days = DateUtil.getDaysBetweenDates(startDate,endDate);
        assertEquals(31,days); //aqui lo que pasamos es la comprobación.
        //desde el 1 de enero hasta el 1 de febrero tendrían que habe pasado 31 dias que si no diera fallo
        //sería igual a "days"

        startDate = LocalDate.of(2025,01,14);
        endDate = LocalDate.of(2025,01,25);
        days = DateUtil.getDaysBetweenDates(startDate,endDate);
        assertEquals(11,days);
    }
}
