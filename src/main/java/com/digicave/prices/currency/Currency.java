package com.digicave.prices.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Currency {
    private String symbol;
    private String code;
    private int decimals;

    public String getFormatted(Double value)
    {
        String _decimal= "%." + decimals +"f" + " " + symbol + " / " + code;
        return String.format(_decimal, value);
    }
}
