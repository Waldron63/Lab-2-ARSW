### Escuela Colombiana de Ingeniería
### Arquitecturas de Software - ARSW 2025-2
### Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

## Integrantes:
### Juan David Martínez Mendez
### Santiago Gualdrón Rincón

## Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

En la primera imagen se muestra como el nucleoal parecer que se utiliza con mayor frecuencia es el sexto (6) y el menos utilizado es el primero (1)
<img width="1451" height="889" alt="Captura de pantalla 2025-08-28 174557" src="https://github.com/user-attachments/assets/d1e1c9a6-a6b1-4db0-b670-28b4905f83f5" />

Una vez finalizada la tarea, todos los procesadores se estabilizan, mostrando como la tarea fue finalizada y el hilo termino.
<img width="1455" height="886" alt="image" src="https://github.com/user-attachments/assets/ce62ea63-45c7-4595-abb2-15307476b97b" />

2. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

Ahora, en comparación al anterior, se muestra como todos los procesadores tienen picos mayores al anterior punto, teniendo un mayor uso el tercero (3), y el menor uso el primero (1)
<img width="1582" height="876" alt="image" src="https://github.com/user-attachments/assets/e8d6a071-84b5-455b-90a5-2a921d5744bf" />

Nuevamente, se muestra como los procesadores bajan los picos en el momento en que se finalizan los 3 hilos.
<img width="1584" height="874" alt="image" src="https://github.com/user-attachments/assets/c16a48e1-2a92-4331-9fcf-fb0d2de83bd9" />

3. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo.

Se muestra como, todos los hilos paran a los 5 segundos, los picos de los procesadores vuelven a bajar y el resultado que se ha calculado hasta el momento de la sumatoria de los primos alcanzados; esperando a que se reanude con un ENTER.
<img width="1709" height="797" alt="image" src="https://github.com/user-attachments/assets/ac4b0cae-9224-44e9-8e50-dcb0b76102d0" />

Prueba 2:
<img width="1740" height="877" alt="image" src="https://github.com/user-attachments/assets/ac49355e-db26-4136-97b0-b76dcd38caaf" />


Prueba 3:
<img width="1733" height="874" alt="image" src="https://github.com/user-attachments/assets/0c7a196c-d39e-4833-99fa-b453f36bdcb0" />


## Parte II 

1. Al ejecutar el programa tenemos que el ganador se muestra sin siquiera haber terminado la carrera, utilizando join aplicado a cada uno de los hilos "galgos", podemos lograr que la respuesta se de apenas todos terminen.

Antes:

<img width="770" height="566" alt="image" src="https://github.com/user-attachments/assets/b7a68ac1-99e8-4bd4-963b-48d1d936c0e4" />

Después:

<img width="781" height="731" alt="image" src="https://github.com/user-attachments/assets/d02a2905-4c9c-4ca6-838c-2a9eff943a45" />



2.  Al ejecutar el programa, podemos tener resultados como:

* Varios galgos con puestos repetidos
* La consola muestra el ranking de manera desorganizada

Esto lo podemos evidenciar en la siguiente imágen

<img width="688" height="313" alt="image" src="https://github.com/user-attachments/assets/35b68af0-e1b1-44ad-a70c-f7583ced9625" />

La región crítica del código es la forma en como los Galgos registran su llegada en el objeto "RegistroLlegada", como cambian la última posición alcanzada y definen el ganador, por lo que es una zona importante de sincronizar para evitar los errores mencionados.

3.  Para solucionar los problemas anteriormente mencionados, definimos la variable "ultimaPosicionAlcanzada" como estática y sincronizamos los métodos "setGanador", y el get y set de la última Posición Alcanzada, también sincronizamos la forma en como se registran los galgos al iniciar el método "corra", al realizar esto, evidenciamos el siguiente resultado:

<img width="531" height="327" alt="image" src="https://github.com/user-attachments/assets/d9645cd1-da89-4000-b428-0a4b41d8e621" />

4.  Para implementar esta funcionalidad, debemos tener en cuenta los oyentes de los botones y donde vamos a implementar el wait y notifyall:
Para esto implementamos una variable estática y volátil llamada "pause" dentro de la clase "Galgo" para realizar la verificación que nos permitira detener/reanudar los hilos, para cambiar el estado de "pause" definimos los métodos "stopGalgos" y "playGalgos".

Para implementar el método "notifyall", todos los hilos deben tener un objeto en común para que la sincronización aplique a todos, para eso creamos un objeto llamado "monitorPausa", el cual será pasado al constructor de los Galgos y así realizar la sincronización correctamente, por lo que con aplicar los métodos stop y play Galgos a un único objeto, aplicará para todos los 17 Galgos.

El "notifyall" esta contenido dentro de playGalgos mientras que el "wait" esta contenido dentro del método "corra", toda la sincronización de esta solucion gira entorno a este objeto "monitorPausa" para lograr eficientemente la implementación de estas dos funcionalidades, he aquí una prueba:

Cuando pausamos la carrera:

<img width="1174" height="768" alt="image" src="https://github.com/user-attachments/assets/7ac53d5a-b6c1-491b-9722-6bacdc521230" />

Cuando reanudamos la carrera:

<img width="1175" height="769" alt="image" src="https://github.com/user-attachments/assets/854bc83e-eda7-44a5-8a4e-e4a444d59563" />

Resultado de la carrera:

<img width="958" height="418" alt="image" src="https://github.com/user-attachments/assets/69d93fad-ede8-4b3d-8847-f1df1e693f2e" />



