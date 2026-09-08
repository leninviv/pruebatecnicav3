# Prueba Técnica - Java / Spring Boot

## Contexto

La empresa dispone de una API REST para administrar productos e inventario.

El proyecto contiene una implementación inicial que deberá ser analizada, completada y mejorada.

El área de operaciones ha reportado que, en algunos escenarios de inventario y consulta, el comportamiento de la API no coincide con lo esperado. Identificar, reproducir y corregir esos problemas forma parte de la prueba.

Tiempo estimado de trabajo: **aproximadamente 2 horas**.
Plazo de entrega: **hasta 24 horas** desde la recepción de la prueba.

## Tecnologías

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- JUnit

## Objetivo

Completar las funcionalidades solicitadas y corregir los problemas encontrados en la aplicación.

Se evaluará especialmente:

- Calidad del código
- Comprensión de Spring Boot
- Diseño de APIs REST
- Manejo de persistencia con JPA
- SQL
- Manejo de errores
- Pruebas automatizadas
- Capacidad de análisis
- Claridad y mantenibilidad de la solución

## Funcionalidades requeridas

### 1. Gestión de productos

Implementar o completar:

- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/products`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

Un producto contiene como mínimo:

- `id`
- `code`
- `name`
- `price`
- `stock`
- `active`

### 2. Búsqueda de productos

La API debe permitir consultar productos por nombre y estado.

Ejemplos:

- `GET /api/products?name=teclado`
- `GET /api/products?active=true`

También debería ser posible combinar criterios cuando corresponda.

### 3. Movimientos de inventario

Implementar:

- `POST /api/products/{id}/stock`

El movimiento debe permitir indicar:

- `IN` = entrada de inventario
- `OUT` = salida de inventario

Ejemplo de cuerpo:

```json
{
  "type": "OUT",
  "quantity": 2
}
```

Reglas:

- `IN` debe incrementar el stock.
- `OUT` debe disminuir el stock.
- No se debe permitir que el stock quede negativo.
- Cada movimiento debe quedar registrado.

### 4. Historial de movimientos

Implementar:

- `GET /api/products/{id}/stock-movements`

Debe devolver los movimientos asociados al producto.

### 5. Manejo de errores

La API debe manejar correctamente situaciones como:

- Producto inexistente
- Datos inválidos
- Movimiento de inventario inválido
- Intento de dejar stock negativo

Las respuestas HTTP deben utilizar códigos apropiados.

### 6. Pruebas

Agregar pruebas automatizadas para las funcionalidades principales.

Como mínimo deberían contemplarse casos como:

- Crear producto
- Obtener producto
- Producto inexistente
- Entrada de inventario
- Salida de inventario
- Intento de stock negativo

Puedes decidir libremente si utilizas pruebas unitarias, de integración, una combinación de ambas o si simplemente compartes en el GIT un archivo colleccion de postman y un excel con las pruebas documentadas

### 7. Reporte de kardex

El gerente necesita un reporte de kardex para revisar el estado de los productos junto con su inventario.

Implementar:

- `GET /api/reports/kardex`

El resultado debe ser **JSON** y debe **combinar información de productos con sus movimientos de inventario**. No basta con devolver solo el catálogo de productos.

Cada ítem del reporte debería permitir entender, como mínimo:

- datos del producto (`id`, `code`, `name`, `price`, `stock`, `active`)
- movimientos asociados (`IN` / `OUT`, cantidad, fecha)
- el efecto de esos movimientos sobre el stock

Si lo consideras útil, el endpoint puede aceptar filtros (por producto, por estado u otros criterios), siempre que el reporte siga mezclando ambas fuentes de información.

## Base de datos

La aplicación utiliza MySQL.

El proyecto contiene un script SQL inicial con las tablas y datos necesarios para ejecutar la prueba:

`src/main/resources/db/init.sql`

No es necesario modificar la estructura de la base de datos salvo que consideres técnicamente necesario hacerlo.

El script carga un catálogo de productos de ejemplo (periféricos, laptops, almacenamiento, redes, etc.). Incluye productos activos y al menos un producto inactivo.

Algunos registros de referencia:

| code     | name                    | stock | active |
|----------|-------------------------|------:|--------|
| PROD-001 | Teclado Logitech K120   |    50 | true   |
| PROD-004 | Laptop Lenovo ThinkPad  |     8 | true   |
| PROD-011 | Cable HDMI 2M           |   100 | true   |
| TEC-001  | Teclado Mecanico        |    12 | true   |
| SIL-001  | Silla Gamer             |     3 | false  |

## Ejecución

### Requisitos previos

- JDK 21 o superior
- Maven 3.9+ (o el Maven Wrapper incluido: `mvnw` / `mvnw.cmd`)
- MySQL 8 accesible desde tu entorno
- Git

### Configuración de las credenciales de MySQL

Edita `src/main/resources/application.properties` y completa los valores de conexión:

```properties
spring.datasource.url=jdbc:mysql://HOST:3306/NOMBRE_BASE?useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username=USUARIO
spring.datasource.password=PASSWORD
```

Si las tablas aún no existen, ejecuta el script `src/main/resources/db/init.sql` sobre tu base MySQL. La aplicación también puede crear o actualizar el esquema al iniciar y cargar los datos de prueba.

### Cómo ejecutar el proyecto

Desde la raíz del repositorio:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

También puedes ejecutar la clase `com.example.pruebatecnica.PruebatecnicaApplication` desde tu IDE.

La API queda disponible en:

`http://localhost:8080`

### Cómo ejecutar las pruebas

```bash
./mvnw test
```

En Windows:

```bash
mvnw.cmd test
```

### Cómo verificar que la aplicación funciona

Con la aplicación en ejecución, consulta el listado de productos:

```bash
curl http://localhost:8080/api/products
```

Deberías obtener los productos de prueba. A partir de ahí puedes ejercitar el resto de endpoints con tu cliente HTTP preferido (cURL, Postman, IntelliJ HTTP Client, etc.).

## Entrega

Trabaja sobre la rama que se te asignó.

Realiza commits durante el desarrollo y haz **push** de todos tus cambios a esa rama antes de finalizar la prueba.

No es necesario utilizar una cantidad específica de commits. Se recomienda utilizar mensajes de commit descriptivos.

Al finalizar, agrega al README las siguientes secciones.

---

## Decisiones técnicas

### Principales cambios realizados

**Bugs corregidos:**

1. **Comparación de Strings con `==` (StockService)** — La condición `type == "OUT"` siempre evaluaba como `false` en Java porque compara referencias de objetos, no contenido. El stock nunca se reducía con movimientos OUT. Corregido a `"OUT".equals(type)`.

2. **Movimientos de stock no se persistían (StockService)** — El método `register()` modificaba el stock del producto pero nunca guardaba el `StockMovement` en la base de datos. El historial de movimientos siempre aparecía vacío. Se agregó el `stockMovementRepository.save(movement)`.

3. **`Optional.get()` sin validación (ProductService, StockService)** — En múltiples lugares se usaba `findById(id).get()` sin verificar si el Optional estaba presente, causando `NoSuchElementException` (HTTP 500) cuando el producto no existía. Reemplazado por `.orElseThrow(() -> new ProductNotFoundException(id))` para retornar HTTP 404.

4. **Filtro `active` ignorado en `findAll()`** — El parámetro `active` era declarado en el método pero nunca utilizado. Se implementó el filtrado completo con soporte para combinaciones de filtros (`name` + `active`, solo `name`, solo `active`).

5. **Búsqueda por nombre exacto en lugar de parcial** — `findByName()` generaba `WHERE name = ?`. Se reemplazó por `findByNameContainingIgnoreCase()` para búsquedas tipo LIKE `%nombre%` insensibles a mayúsculas/minúsculas.

6. **`update()` solo actualizaba `name` y `price`** — Los campos `stock` y `active` eran ignorados en las actualizaciones. Se completó para actualizar todos los campos.

7. **`create()` ignoraba el campo `active` del request** — Siempre fijaba `active = true` independientemente del valor enviado. Corregido para respetar el valor del request, con `true` como default si no se proporciona.

8. **Kardex devolvía solo productos sin movimientos** — `KardexController` retornaba `List<Product>`, incumpliendo el requerimiento de combinar productos con su historial de movimientos.

**Mejoras implementadas:**

- **Manejo global de errores** (`GlobalExceptionHandler`): centraliza el manejo de excepciones con respuestas HTTP consistentes. Elimina la necesidad de try/catch en controladores.
- **Excepciones personalizadas**: `ProductNotFoundException` (→ 404) e `InsufficientStockException` (→ 400) con mensajes descriptivos.
- **Validaciones con Bean Validation** (`@NotBlank`, `@NotNull`, `@Min`, `@Pattern`): en `ProductRequest` y `StockMovementRequest`, con `@Valid` en los controllers.
- **Códigos HTTP correctos**: `POST /api/products` retorna 201 Created, `DELETE` retorna 204 No Content.
- **`@Transactional` en `StockService.register()`**: garantiza que la actualización del stock y la persistencia del movimiento sean atómicas.
- **Reporte Kardex completo**: nuevo `KardexService` + `KardexItem` DTO que combina datos del producto con su historial de movimientos y muestra el efecto neto de cada movimiento (`stockEffect`).
- **Pruebas unitarias con Mockito**: `ProductServiceTest` y `StockServiceTest` cubren todos los casos principales (CRUD, filtros, errores, movimientos IN/OUT, stock negativo).

### Problemas encontrados

- El bug de `type == "OUT"` es especialmente engañoso porque el código compila y arranca sin errores; el comportamiento incorrecto solo se manifiesta en tiempo de ejecución.
- La ausencia de un `GlobalExceptionHandler` hacía que cualquier error devolviera stacktraces crudos al cliente, tanto en producción como en desarrollo.
- El `KardexController` original tenía una dependencia circular implícita con `ProductService` para una funcionalidad que claramente pertenece a su propio servicio.

### Decisiones técnicas importantes

- **Arquitectura por feature packages**: se mantiene la estructura existente (`product`, `inventory`, `report`, `exception`) en lugar de una arquitectura por capas (controller/service/repository) para mantener la cohesión por dominio.
- **DTO KardexItem separado del entity Product**: evita exponer la relación JPA bidireccional directamente en la respuesta HTTP y permite controlar exactamente qué campos se incluyen en el reporte.
- **Pruebas unitarias sobre de integración**: dado el contexto de la prueba técnica con BD en la nube y tiempo limitado, se priorizaron pruebas unitarias con Mockito que son rápidas, no requieren BD y verifican la lógica de negocio de forma aislada.

### Qué mejoraría con más tiempo

- **Pruebas de integración**: agregar tests de integración con `@SpringBootTest` usando H2 en memoria para verificar el comportamiento end-to-end de los endpoints.
- **Paginación**: los endpoints `GET /api/products` y `GET /api/reports/kardex` podrían devolver demasiados registros en producción. Implementaría paginación con `Pageable`.
- **Auditoría en Product**: agregar campos `createdAt` / `updatedAt` en la entidad `Product` con `@CreationTimestamp` / `@UpdateTimestamp`.
- **Soft delete**: en lugar de eliminar físicamente los productos, marcarlos como `active = false` para preservar el historial de movimientos referenciado.
- **Caché**: para el endpoint del kardex (lectura intensiva), agregaría caché con `@Cacheable` + Spring Cache para mejorar el rendimiento en escenarios de alta transaccionalidad.
- **Documentación con OpenAPI/Swagger**: agregar `springdoc-openapi-ui` para generar documentación interactiva de la API.
- **Separar `@Valid` de ProductRequest en update vs create**: el `PUT` no debería requerir `code` (no debería modificarse), por lo que idealmente habría un `ProductUpdateRequest` separado del `ProductCreateRequest`.

---

## Tiempo empleado

Aproximadamente **2 horas** de trabajo efectivo:
- 20 min: análisis del código existente, identificación de bugs y lectura de requerimientos
- 60 min: implementación de correcciones, mejoras y nuevas funcionalidades
- 40 min: escritura de pruebas unitarias y documentación

## Restricciones

- No existe una única implementación correcta.
- Puedes modificar clases, métodos, consultas, estructura interna o agregar componentes cuando lo consideres necesario.
- No se evaluará únicamente si la aplicación funciona, sino también la calidad de la solución.
- No es necesario implementar funcionalidades que no hayan sido solicitadas.
