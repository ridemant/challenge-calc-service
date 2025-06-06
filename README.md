# 📊 Challenge Calc Service

## 📝 Descripción

**Challenge Calc Service** es un servicio desarrollado con Spring Boot que realiza una operación básica de multiplicación aplicando un porcentaje dinámico.

Primero intenta obtener el porcentaje desde Redis (caché en memoria).
Si no está disponible, lo consulta desde un servicio externo, lo guarda en Redis por 30 minutos y luego lo aplica en el cálculo.

Además, registra cada operación en una base de datos PostgreSQL.
Incluye documentación con Swagger, pruebas unitarias y puede ejecutarse fácilmente con Docker.

---

## 🧱 Arquitectura

![Arquitectura del sistema](https://i.ibb.co/5XZM0kmb/arq1.jpg)

> **Flujo de procesamiento:**
> 1. El cliente realiza una solicitud de cálculo a `challenge-calc-service`.
> 2. El servicio intenta obtener el porcentaje desde **Redis** (caché).
> 3. Si no está disponible, consulta el **API externo** de porcentaje.
> 4. El valor obtenido se guarda en Redis (con TTL de 30 minutos).
> 5. Se realiza el cálculo: `(num1 + num2) * (1 + porcentaje/100)`.
> 6. Se guarda el historial del cálculo en **PostgreSQL**.


## ⚙️ Funcionalidades

- 📐 Cálculo de suma con aplicación de porcentaje dinámico.
- ⚡ Consulta a Redis como caché primaria.
- 🌐 Consulta al API externo solo si no hay valor en caché.
- 🧾 Registro del historial de cálculos en base de datos PostgreSQL.
- 🛑 Manejo centralizado y claro de errores personalizados.
- 🧪 Pruebas unitarias con JUnit y Mockito.
- 🧰 Documentación con Swagger.
- 🐳 Despliegue con Docker / Docker Compose.

## 🔁 Endpoints

### 📌 POST `/api/calculate`

Realiza un cálculo aplicando un porcentaje dinámico. Primero se intenta obtener el porcentaje desde Redis; si no existe, se consulta al API externo (15%) y se guarda en caché.

#### 🟢 Request
```json
{
  "num1": 150,
  "num2": 50
}
```

####  Response: 200
```json
{
  "success": true,
  "code": 200,
  "timestamp": "2025-06-04T01:46:10.5268586",
  "data": {
    "result": 230.0
  }
}
```
####  Response: 400
```json
{
  "success": false,
  "code": 400,
  "timestamp": "2025-06-04T01:53:15.0087166",
  "data": [
    {
      "field": "num1",
      "message": "El número 1 es obligatorio."
    },
    {
      "field": "num2",
      "message": "El número 2 es obligatorio."
    }
  ]
}
```
####  Response: 503
```json
{
  "success": false,
  "code": 503,
  "timestamp": "2025-06-04T01:54:06.1376708",
  "data": {
    "message": "No se pudo obtener el porcentaje ni existe valor en caché."
  }
}
```

### 📌 GET `/api/historial?page=0&size=10`

Devuelve la lista de cálculos realizados previamente, con todos los campos registrados.

####  Response: 200
```json
{
  "success": true,
  "code": 200,
  "timestamp": "2025-06-05T17:13:27.3612938",
  "data": {
    "content": [
      {
        "id": 1,
        "endpoint": "/calculate",
        "parametros": "{\"num1\":80.0,\"num2\":20.0}",
        "respuesta": "115.00",
        "esError": false,
        "fecha": "2025-06-04T16:05:49.712526"
      },
      ....
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10,
      "sort": [],
      "offset": 0,
      "paged": true,
      "unpaged": false
    },
    "last": false,
    "totalElements": 41,
    "totalPages": 5,
    "size": 10,
    "number": 0,
    "sort": [],
    "numberOfElements": 10,
    "first": true,
    "empty": false
  }
}
```

## 🧪 Pruebas unitarias

El proyecto cuenta con pruebas unitarias escritas con **JUnit 5** y **Mockito**, cubriendo el controlador y la lógica de servicio.

### 🔹 CalculationControllerTest
- Valida respuestas exitosas al calcular.
- Simula fallos del API externo y uso de caché.
- Verifica manejo de errores con código 503.

### 🔹 PercentageServiceImplTest
- Obtiene porcentaje desde el API externo.
- Retorna desde Redis si ya está cacheado.
- Cachea el valor nuevo si no estaba presente.
- Lanza excepción si no hay valor ni en API ni en Redis.

> Las pruebas se encuentran en `src/test/java/cl/tenpo/challengecalcservice/` y cubren los principales escenarios del servicio.

## 🐳 Despliegue con Docker (usando Docker Hub)

Desde una imagen publicada en Docker Hub: https://hub.docker.com/r/americoallende/challenge-calc-service


### ✅ Instrucciones

Abre una terminal y ejecutar siguientes comandos:

```bash
# 1. Descargar docker-compose.yml desde GitHub
curl -O https://raw.githubusercontent.com/ridemant/challenge-calc-service/refs/heads/master/docker/docker-compose.yml

# 2. Levantar servicios
docker compose -f docker-compose.yml up
```


## 🚀 Despliegue Local

Desde ambiente local
### ✅ Instrucciones

```bash
# 1. Clona el repositorio (rama master)
git clone -b master https://github.com/ridemant/challenge-calc-service.git
cd challenge-calc-service

# 2. Compila el proyecto con Maven
./mvnw clean package -DskipTests

# 3. Levanta los servicios
docker-compose up --build
```
Accede a la aplicación:
## 📖 Accede a la aplicación
- API: http://localhost:8080
- Acceso a Swagger: http://localhost:8080/swagger-ui.html
- Descargar Collection Postman: https://raw.githubusercontent.com/ridemant/challenge-calc-service/refs/heads/master/docs/tenpo.postman_collection.json
