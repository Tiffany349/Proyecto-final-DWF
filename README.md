# ✈️ Infiniti Sky - Sistema de Gestión de Boletos Aéreos

## 📋 Descripción
Sistema web para la gestión de boletos aéreos desarrollado con Java Spring Boot (backend) y HTML/CSS/JS (frontend). Nos permite gestionar aerolíneas, vuelos, pasajeros, reservaciones, pagos y reclamos.

## 👥 Equipo de Desarrollo
| Nombre | Carnet | Rol |
|--------|--------|-----|
| Javier Alexander Ramos Garcia | RG251044 | Líder / Frontend |
| Daniela Guadalupe Hernandez Mejia | HM250077 | Backend / Base de datos |
| Tiffany Nohemi Benitez Reyes | BR250073 | Backend |
| Diego Alejandro Cruz Campos | CC251293 | Frontend |
| Nancy Yamileth Flores Recinos | FR230188 | Documentación y pruebas |

## 🛠️ Tecnologías
- **Backend:** Java 17, Spring Boot 3.2, Spring Security, JWT
- **Frontend:** HTML5, CSS3, JavaScript
- **Base de datos:** MySQL
- **Documentación:** Swagger / OpenAPI
- **Control de versiones:** GitHub

## ⚙️ Requisitos previos
- Java 17+
- Maven
- MySQL 8+
- Navegador web moderno

## 🚀 Instalación y configuración

### Base de datos
```sql
CREATE DATABASE airline_system;
```
Luego ejecutar el archivo `airline_system.sql`

### Backend
```bash
# Clonar el repositorio
git clone https://github.com/Danys22/TU-REPO.git

# Entrar a la carpeta del backend
cd airline-system

# Configurar credenciales en src/main/resources/application.properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD

# Ejecutar
mvn spring-boot:run
```
