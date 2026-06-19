# Operaciones Transportista

Sistema de gestión de operaciones logísticas para empresas de transporte de carga. Permite administrar la flota de camiones, el personal de camioneros y el ciclo completo de los viajes, garantizando que un camión o camionero no pueda quedar asignado a más de un viaje activo a la vez.

## ¿Qué hace el sistema?

- **Gestión de camiones**: alta, edición, baja y control de estado (`Disponible`, `En Viaje`, `En Taller`).
- **Gestión de camioneros**: alta, edición, baja y habilitación/inhabilitación según corresponda (por ejemplo, vencimiento de licencia).
- **Gestión de viajes**: creación, edición y seguimiento de estado (`Pendiente`, `En Curso`, `Finalizado`, `Cancelado`).
- **Sincronización automática**: cuando un viaje pasa a "En Curso", el camión asignado se bloquea automáticamente (`En Viaje`); cuando el viaje finaliza o se cancela, el camión vuelve a estar disponible. Esta lógica vive en el backend, no depende de que el frontend la respete.
- **Panel principal (Dashboard)**: vista general con el total de viajes, el estado de la flota (cuántos camiones disponibles, en viaje y en taller) y las empresas activas.
- **Historial por camionero**: consulta de los viajes asociados a un camionero específico.

## Stack Tecnológico

### Backend
- **Java** con **Spring Boot 3.x**
- **Spring Data JPA** / **Hibernate** para persistencia
- **MySQL** como motor de base de datos
- **MapStruct** para el mapeo entre entidades y DTOs
- **Lombok** para reducir código repetitivo (getters/setters, constructores)
- **Jakarta Validation** para validar los datos de entrada
- **Spring Security** con autenticación basada en **JWT**

### Frontend
- **React** (con **Vite** como herramienta de build)
- **React-Bootstrap** para los componentes de interfaz
- **React Hook Form** para el manejo de formularios y validaciones
- **Axios** para el consumo de la API REST, con interceptores para autenticación y manejo centralizado de errores
- **React Toastify** para notificaciones al usuario

## Arquitectura

El backend sigue una arquitectura en capas:

```
Controller → Service → Repository → Entity
```

- **Controller**: expone los endpoints REST.
- **Service**: contiene las reglas de negocio y las validaciones (por ejemplo, impedir editar un viaje ya finalizado, o sincronizar el estado del camión cuando cambia el estado del viaje).
- **Repository**: acceso a datos mediante Spring Data JPA.
- **DTOs** (Request/Response): desacoplan la API de las entidades de base de datos.

El frontend consume la API mediante servicios dedicados (uno por entidad: camiones, camioneros, viajes, empresas), y la lógica de negocio crítica nunca se asume desde el frontend — siempre se valida y resuelve en el backend.

## Seguridad

El acceso a la API está protegido con **Spring Security** y autenticación mediante **JWT (JSON Web Token)**:

- Al iniciar sesión, el backend genera un token que el frontend almacena y envía en cada request mediante el header `Authorization: Bearer <token>`.
- Un interceptor de Axios en el frontend agrega este header automáticamente a cada petición.
- Los endpoints de la API requieren un token válido para ser consumidos, evitando el acceso no autenticado a los datos del sistema.

## Instrucciones básicas de uso

### Backend
1. Configurar la conexión a MySQL en `application.properties` (URL, usuario y contraseña de la base de datos).
2. Ejecutar la aplicación Spring Boot (por ejemplo, desde el IDE o con `./mvnw spring-boot:run`).
3. El backend queda disponible por defecto en `http://localhost:8081`.

### Frontend
1. Instalar las dependencias con `npm install`.
2. Ejecutar el proyecto en modo desarrollo con `npm run dev`.
3. Acceder desde el navegador a la URL que indique Vite (por defecto `http://localhost:5173`).

> Nota: el frontend espera que el backend esté corriendo y accesible en `http://localhost:8081/api` (configurado en `services/api.js`).

## Estado del proyecto

MVP funcional. El backend cuenta con las validaciones de negocio centrales implementadas (bloqueo de recursos en viaje, prohibición de editar viajes finalizados, cálculo automático de disponibilidad de camiones). El frontend cubre la gestión completa de camiones, camioneros y viajes, además de un panel principal con estadísticas generales.
El archivo sql es para insertar en base de datos y hacer pruebas directamente
