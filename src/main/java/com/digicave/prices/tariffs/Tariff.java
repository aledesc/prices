package com.digicave.prices.tariffs;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_rates")
public class Tariff {

    @Id
    private Integer id;

    @Column("BRAND_ID")
    private Integer brandId;

    @Column("PRODUCT_ID")
    private Integer productId;

    @Column("START_DATE")
    private LocalDate startDate;

    @Column("END_DATE")
    private LocalDate endDate;

    private Integer price;

    @Column("CURRENCY_CODE")
    private String currency;
}
