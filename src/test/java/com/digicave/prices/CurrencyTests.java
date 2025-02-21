package com.digicave.prices;

import com.digicave.prices.currency.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyTests {

    @Test
    void getFormattedTest() {

        final int VALUE= 3916;
        Currency c= new Currency("€","EUR", 2);
        final String expected= "3.15 € / EUR";

        String formatted= c.getFormatted(VALUE);
        Assertions.assertEquals(expected, formatted);
    }

}
