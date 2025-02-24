package com.digicave.prices;

import com.digicave.prices.currency.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyTests {

    @Test
    void getFormattedTest() {

        final int VALUE= 3916;
        Currency c= new Currency("€","EUR", 2);
        final String expected= "39.16 €";

        String formatted= c.getFormatted(VALUE);
        assertEquals(expected, formatted);
    }

}
