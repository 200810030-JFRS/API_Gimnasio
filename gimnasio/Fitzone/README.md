# FitZone - API REST (SOA)

Proyecto académico sencillo para administrar inscripciones a clases grupales.

## Requisitos
- Java 21
- Conexión a Internet la primera vez que Maven descarga dependencias
- Postman

## Ejecutar
Abre la carpeta `Fitzone` en IntelliJ IDEA o VS Code y ejecuta `FitzoneApplication.java`.
También puedes usar `mvnw.cmd spring-boot:run` en Windows.
El servidor inicia en `http://localhost:8080`.

Se utiliza H2 con persistencia en la carpeta `datos` (no hace falta instalar MySQL).
No es necesario crear tablas manualmente. Java crea las tablas al iniciar.

## Pruebas rápidas en Postman

### 1. Crear socio
POST http://localhost:8080/api/socios
Body > raw > JSON:
```json
{"nombre":"Ana Lopez","correo":"ana@correo.com","membresiaHasta":"2027-12-31"}
```

### 2. Verificar membresía
GET http://localhost:8080/api/socios/1/membresia

### 3. Crear clase
POST http://localhost:8080/api/clases
Body > raw > JSON (usa una fecha FUTURA):
```json
{"nombre":"Yoga","sucursal":"Centro","fechaHora":"2027-12-01T18:00:00"}
```

### 4. Consultar cupo
GET http://localhost:8080/api/clases/1/cupo

### 5. Inscribirse
POST http://localhost:8080/api/inscripciones
Body > raw > JSON:
```json
{"socioId":1,"claseId":1}
```

### 6. Ver inscripciones
GET http://localhost:8080/api/inscripciones

### 7. Cancelar inscripción
DELETE http://localhost:8080/api/inscripciones/1

## Otros endpoints
- GET /api/socios
- GET /api/clases
- GET /api/clases/{id}/inscripciones

## Reglas
- Máximo 20 personas por clase.
- La membresía debe estar vigente.
- No se permiten inscripciones duplicadas.
- No se permite inscripción a clases pasadas.
- Se imprime una confirmación en la consola al inscribir.
- Los recordatorios se imprimen en la consola cuando faltan 2 horas o menos (revisión cada minuto). No se envían correos reales.

## Servicios candidatos (SOA)
- Socios: consultarMembresia
- Clases: consultarCupo
- Inscripciones: inscribirEnClase, cancelarInscripcion
- Notificaciones: enviarRecordatorios (asíncrono por tarea programada; simulación local)

## Nota de exposición
Es una API monolítica para demostrar separación lógica en servicios dentro de un proyecto sencillo. No son microservicios independientes.
