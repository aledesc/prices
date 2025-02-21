package com.digicave.prices.tariffs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TariffDTO {
    private Integer id;
    private Integer brandId;
    private Integer productId;
    private String startDate;
    private String endDate;
    private String price;
}
