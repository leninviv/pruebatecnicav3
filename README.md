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

Puedes decidir libremente si utilizas pruebas unitarias, de integración o una combinación de ambas.

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

### Decisiones técnicas

Indicar brevemente:

- Principales cambios realizados.
- Problemas encontrados.
- Decisiones técnicas importantes.
- Qué mejoraría si tuviera más tiempo.

### Tiempo empleado

Indicar aproximadamente cuánto tiempo tomó completar la prueba.

## Restricciones

- No existe una única implementación correcta.
- Puedes modificar clases, métodos, consultas, estructura interna o agregar componentes cuando lo consideres necesario.
- No se evaluará únicamente si la aplicación funciona, sino también la calidad de la solución.
- No es necesario implementar funcionalidades que no hayan sido solicitadas.
