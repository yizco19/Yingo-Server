@echo off
rem Iniciar Redis
start "Redis Server" ".\Redis-x64-3.2.100\redis-server.exe" ".\Redis-x64-3.2.100\redis.windows.conf"

rem Esperar unos segundos para que Redis se inicie completamente
timeout /t 5 /nobreak >nul

rem Iniciar el backend (reemplaza "nombre_del_archivo.jar" con el nombre real de tu archivo JAR)
java -jar servidor_shop.jar
