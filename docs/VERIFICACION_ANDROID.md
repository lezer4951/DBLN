# Verificación de estabilización

## Estado real

Se implementaron validaciones del repositorio, mezcla de opciones, recuperación de evaluaciones mediante SavedStateHandle, práctica acumulativa, práctica libre, curiosidades, Memorama y Supervivencia locales.

Las comprobaciones estáticas no sustituyen la compilación ni una prueba con Android. En el entorno de edición Gradle 8.7 no pudo descargarse (`Network is unreachable`). No se ha generado ni probado un APK con estos cambios.

## Ejecutar en Android Studio o terminal con SDK y red

```sh
./gradlew testDebugUnitTest lintDebug assembleDebug
./gradlew connectedDebugAndroidTest
```

La segunda orden requiere emulador o dispositivo. Guardar los informes de Gradle y los esquemas JSON generados por Room en `app/schemas` bajo control de versiones después de compilar. No fabricar esquemas históricos: conservar los exportados desde las versiones originales cuando estén disponibles.

## Casos que deben pasar antes de entregar

- Crear cuenta, iniciar y cerrar sesión; comprobar aislamiento entre dos usuarios.
- Abrir Home desde cada pestaña y desde una lección, quiz, apunte y juego.
- Aprobar niveles 0–10 en orden. Reprobar no avanza; volver a aprobar un nivel anterior no reduce progreso.
- El Nivel 10 desbloqueado muestra menos de 100 %. Solo la graduación final muestra 100 % y Rango 10.
- Verificar que las opciones correctas no ocupan siempre A y que el feedback sigue correspondiendo a la respuesta.
- Poner un examen a mitad, enviar la app a segundo plano y terminar el proceso desde herramientas de desarrollo sin borrar su tarea. Restaurar desde Recientes: mismo orden, selección, puntuación y feedback. No confundir esto con forzar detención o eliminar la tarea, que pueden descartar el estado guardado de Android.
- Pulsar repetidamente el botón de guardar: un solo guardado mientras está en curso. Ante fallo, reintentar sin sumar de nuevo los aciertos.
- Comprobar que un examen fallido lleva a los temas correctos del mismo nivel.
- Verificar que la práctica identifica preguntas de niveles anteriores y que su finalización habilita el examen.
- Práctica libre: 22 preguntas, resultado, reinicio y vuelta. No altera el progreso académico.
- Curiosidades: anterior/siguiente, extremos deshabilitados, rotación y texto grande.
- Memorama: estado vacío con menos de cuatro flashcards, ocho cartas con cuatro o más; parejas correctas, fallos, intentos, continuación manual y restauración del tablero.
- Supervivencia: primer error termina el reto después del feedback. Completar todas las preguntas también termina; reiniciar crea otra mezcla.
- Probar rotación, tamaño de letra grande, TalkBack, tema oscuro y animaciones del sistema desactivadas.
- Ejecutar pruebas de migración e integración; verificar conservación del usuario y progresión contra Room real.

## Límites pendientes

PvP, retos remotos entre amigos y ranking compartido siguen explícitamente pendientes. Requieren elegir y configurar backend, autenticación remota, autorización por usuario, sincronización y reglas contra manipulación. Los juegos locales no se presentan como partidas multijugador ni publican puntuaciones.

SavedStateHandle recupera intentos cuando Android restaura la tarea; no es almacenamiento durable para intentos tras borrar la tarea o forzar detención. El avance académico completado permanece en Room.

La prueba de migración completa reconstruye un esquema inicial desde las tablas base actuales y valida la cadena con Room. Complementa, pero no reemplaza, una prueba contra copias reales de bases de datos antiguas y sus esquemas históricos.
