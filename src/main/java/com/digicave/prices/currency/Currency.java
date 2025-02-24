package com.digicave.prices.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Currency {
    private String symbol;
    private String code;
    private int decimals;

    public String getFormatted(Integer value)
    {
        String _decimal= "%." + decimals +"f" + " " + symbol;
        double quotient= Math.pow(10,decimals);

        return String.format(_decimal, (double)value/quotient);
    }
}
