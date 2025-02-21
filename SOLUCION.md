# spring-boot-exercice-1

**Resource Owner password credentials grant flow**
Se habilita una autenticación simple, tipo _Resource Owner password credentials grant flow_, por lo que para correr las peticiones hay que proporcionar las credenciales del 
Resource OWner, que es el servicio, al iniciar el boot - **./gradlew bootRun**, en los logs iniciales que se muestran 
aparece la password generada, tal como se ve en el fragmento siguiente:

    2025-02-21T10:45:05.922+01:00  INFO 47195 --- [price] [  restartedMain] ctiveUserDetailsServiceAutoConfiguration :
    
    Using generated security password: 9c3d76a1-7629-46cb-a908-dfa7a6ba7882
    
    2025-02-21T10:45:05.942+01:00 DEBUG 47195 --- [price] [  restartedMain] o.s.w.r.r.m.a.ControllerMethodResolver   : ControllerAdvice beans: none

El nombre de usuario es 'user'.

# ¿Qué se pide?
* Permitir crear una tarifa nueva
  - POST http://localhost:8080/v1/tariff
  - curl --header "Content-Type: application/json" --request POST --data '{"brandId":3,"productId":"111","startDate":"2025-11-01","endDate":"2025-11-30","price":53,"currency":"EUR"}' http://localhost:8080/v1/tariff 

* Permitir recuperar una tarifa por id, con los precios debidamente formateados y mostrando el código y símbolo de la moneda.
  - GET http://localhost:8080/v1/tariff/{tarifa}
  - curl -i -u user:9c3d76a1-7629-46cb-a908-dfa7a6ba7882 -X GET http://localhost:8080/v1/tariff/5

* Permitir modificar el precio de una tarifa
  - PATCH http://localhost:8080/v1/tariff/{tarifa}/{precio}
  - curl -i -u user:9c3d76a1-7629-46cb-a908-dfa7a6ba7882 -X PATCH http://localhost:8080/v1/tariff/5/3700

* Permitir borrar una tarifa por id
  - DELETE http://localhost:8080/v1/tariff/{tarifa}
  - curl -i -u user:9c3d76a1-7629-46cb-a908-dfa7a6ba7882 -X DELETE http://localhost:8080/v1/tariff/5
  
* Permitir a partir de una fecha, el identificador del producto y el identificador de la marca, recuperar la tarifa a aplicar con los precios correctamente formateados con los decimales proporcionados por el servicio de monedas
  - GET http://localhost:8080/v1/tariff/{marca}/{producto}/{fecha}
  - curl -i -u user:9c3d76a1-7629-46cb-a908-dfa7a6ba7882 -X GET http://localhost:8080/v1/tariff/2/1/2022-09-01

# ¿Qué se valorará?

* Especificación en OpenAPI de la API del micro de tarifas del ejercicio
* Estructura del proyecto y codificación clara y comprensible
* Mínimo un test representativo de los que incluirías
* Optimización de accesos a base de datos y llamadas al servicio de currencies
* Se valorará el uso de DDD y arquitectura hexagonal
* Una resolución reactiva se valorará positivamente
