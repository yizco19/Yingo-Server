# Yingo Server

Este repositorio contiene el código fuente del servidor de YingoShop, una aplicación de comercio electrónico desarrollada con Spring Boot.

## Descripción

El servidor de YingoShop proporciona una API RESTful para gestionar productos, usuarios, pedidos y otras funcionalidades relacionadas con la tienda en línea. Está diseñado para ser escalable, seguro y eficiente, permitiendo una fácil integración con diferentes clientes, como aplicaciones web y móviles.``

## Tecnologías Utilizadas

- **Spring Boot**: Framework de desarrollo de aplicaciones Java para la creación de servicios RESTful.
- **MyBatis**: Biblioteca de mapeo objeto-relacional (ORM) para facilitar la interacción con la base de datos.
- **Redis**: Almacén de datos en memoria para el almacenamiento eficiente de tokens de autenticación de usuarios.
- **JWT**: Token de autenticación para la autenticación de usuarios.
## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes principales:

- **cm.zy.controller**: Contiene los controladores REST que manejan las solicitudes HTTP entrantes.
- **cm.zy.mapper**: Define las interfaces de mapeo MyBatis para acceder a la base de datos.
- **cm.zy.pojo**: Contiene las clases POJO que representan las tablas de la base de datos.
- **cm.zy.exception**: Contiene las clases de excepción personalizadas.
- **cm.zy.service.impl**: Contiene las implementaciones de los métodos de servicio.
- **cm.zy.service**: Contiene la lógica de negocio de la aplicación.
- **cm.zy.interceptors**: Contiene los interceptores de la aplicación.
- **cm.zy.constant**: Contiene las constantes utilizadas en la aplicación.
- **cm.zy.utils**: Contiene clases de utilidad, como el manejo de tokens JWT.

## Instalación y Uso

1. Clona este repositorio en tu máquina local.
2. Configura la base de datos MySQL y Redis según sea necesario.
3. Ejecuta la aplicación utilizando tu IDE favorito o mediante la línea de comandos con Maven:
