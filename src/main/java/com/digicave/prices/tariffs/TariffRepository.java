package com.digicave.prices.tariffs;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface TariffRepository extends ReactiveCrudRepository<Tariff, Integer> {

//    Permitir a partir de una fecha, el identificador del producto y el identificador de la marca, recuperar la
//    tarifa a aplicar con los precios correctamente formateados con los decimales proporcionados por el servicio
//    de monedas
    @Query("select * from t_rates where brand_id=:brandId and product_id=:productId and :date between start_date and end_date")
    Mono<Tariff> findByDateBrandProduct(Integer brandId, Integer productId, LocalDate date);
}
