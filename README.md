# API_Gimnasio
SOA.--API_Gimnasio

API Gimnasio de la asignatura de Arquitectura Orientada a Objetos.

API-Gimnasio (FitZone) — Proyecto SOA

1. Descripción del proyecto

API Gimnasio (FitZone) es un proyecto desarrollado para la asignatura de Arquitectura Orientada a Objetos (SOA). Su objetivo es exponer servicios mediante una API REST para gestionar las clases grupales (yoga, spinning, entrenamiento funcional) de tres sucursales, controlar el límite de 20 participantes por clase, verificar la vigencia de membresías y coordinar inscripciones y avisos.

El proyecto utiliza Java y Spring Boot para implementar la lógica del servidor, Spring Data JPA para el acceso a datos y H2 como sistema de gestión de bases de datos.

Estado del proyecto: Funcional / Finalizado según las pruebas realizadas.

2. Funcionalidades

Consulta y gestión de socios y estado de su membresía.

Consulta y catálogo de clases, sucursales y capacidad de participantes.

Verificación dinámica de cupo disponible por clase (máximo 20 participantes).

Registro transaccional de inscripciones con validaciones de negocio (evita duplicados y membresías vencidas).

Cancelación de inscripciones con liberación automática de cupos.

Simulación de eventos de confirmación y tarea programada (scheduler) para recordatorios de clase.

Persistencia de información en base de datos H2.

Exposición de endpoints HTTP/REST para la comunicación con el cliente.

Pruebas funcionales realizadas mediante Postman.

3. Tecnologías utilizadas

Java: 21.

Spring Boot: 4.1.2.

Maven: administración de dependencias y ejecución.

H2 Database: almacenamiento de datos local y persistente.

Spring Data JPA / Hibernate: persistencia de información.

Postman: pruebas de los endpoints REST.

Git y GitHub: control de versiones y publicación del código.

4. Requisitos previos

Java JDK 21 compatible con la versión de Spring Boot del proyecto.

Git instalado si se desea clonar el repositorio.

Postman instalado para realizar las pruebas de los endpoints.

Maven instalado o uso de Maven Wrapper (mvnw) incluido en el proyecto.

5. Instalación y ejecución

5.1 Clonar el repositorio

git clone "https://github.com/200810030-JFRS/API Gimnasio.git"
cd "API Gimnasio/Fitzone"


5.2 Configurar la base de datos

El proyecto utiliza H2 Database con persistencia en archivo local. La base de datos se crea automáticamente en la carpeta ./datos/fitzone al iniciar la aplicación por primera vez, por lo que no requiere instalación ni configuración adicional previa.

5.3 Configurar las variables de entorno

La configuración por defecto se encuentra preestablecida en src/main/resources/application.properties:

server.port: Puerto del servidor (por defecto 8080).

spring.datasource.url: URL JDBC de la base de datos H2 (jdbc:h2:file:./datos/fitzone).

spring.datasource.username: Usuario de la base de datos (sa).

spring.datasource.password: Contraseña de la base de datos (vacía por defecto).

Ejemplo de configuración en application.properties:

server.port=8080
spring.datasource.url=jdbc:h2:file:./datos/fitzone;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true


5.4 Ejecutar el proyecto

En Windows PowerShell / CMD, desde la carpeta raíz del proyecto (Fitzone):

.\mvnw.cmd spring-boot:run


En Linux / macOS:

./mvnw spring-boot:run


6. Endpoints de la API

6.1 Módulo de Socios

GET /api/socios: Obtener la lista de socios registrados.

POST /api/socios: Registrar un nuevo socio.

GET /api/socios/{id}/membresia: Consultar la vigencia de la membresía de un socio.

6.2 Módulo de Clases

GET /api/clases: Consultar el catálogo de clases activas.

POST /api/clases: Registrar una nueva clase.

GET /api/clases/{id}/cupo: Consultar la capacidad, inscritos actuales y lugares disponibles de una clase.

GET /api/clases/{id}/inscripciones: Consultar la lista de socios inscritos a una clase.

6.3 Módulo de Inscripciones

GET /api/inscripciones: Consultar el historial de inscripciones.

POST /api/inscripciones: Inscribir a un socio en una clase.

DELETE /api/inscripciones/{id}: Cancelar la inscripción de un socio a una clase y restaurar el cupo.

7. Ejemplos de uso (Peticiones y Respuestas)

7.1 Alta de Socio

Solicitud: POST /api/socios

{
  "nombre": "Ana López",
  "correo": "ana@correo.com",
  "membresiaHasta": "2027-12-31"
}


Respuesta (200 OK / 201 Created):

{
  "id": 1,
  "nombre": "Ana López",
  "correo": "ana@correo.com",
  "membresiaHasta": "2027-12-31",
  "membresiaVigente": true
}


7.2 Inscribir Socio en una Clase

Solicitud: POST /api/inscripciones

{
  "socioId": 1,
  "claseId": 1
}


Respuesta (201 Created):

{
  "id": 1,
  "fechaInscripcion": "2026-10-23T10:00:00",
  "clase": {
    "id": 1,
    "nombreClase": "Spinning Morning",
    "instructor": "Juan",
    "sucursal": "Sucursal Centro"
  },
  "socio": {
    "id": 1,
    "nombre": "Ana López",
    "correo": "ana@correo.com"
  }
}


7.3 Consultar Cupo de Clase

Solicitud: GET /api/clases/1/cupo

Respuesta (200 OK):

{
  "claseId": 1,
  "capacidad": 20,
  "inscritos": 1,
  "disponibles": 19
}
