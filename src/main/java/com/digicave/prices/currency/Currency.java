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
        return String.format(_decimal, value/10*decimals);
    }
}
