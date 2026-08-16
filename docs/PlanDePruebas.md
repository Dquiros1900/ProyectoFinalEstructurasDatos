# Plan de Pruebas - Sistema de Inventario y Rutas

Este documento detalla los casos de prueba para validar la experiencia de usuario, las entradas del menú y la correcta integración del grafo con las entregas de la tienda.

## 1. Validaciones del Menú Principal
| ID | Descripción | Acción / Entrada | Resultado Esperado | Estado |
|---|---|---|---|---|
| M-01 | Ingreso de tipo de dato incorrecto | Escribir letras cuando el menú pide un número de opción | El sistema atrapa la excepción, muestra un mensaje de error amigable y vuelve a mostrar el menú sin cerrarse. | Comprobado |
| M-02 | Formato de las ubicaciones | Ingresar ubicaciones con mayúsculas y tildes (ej. "San José") | El menú convierte la entrada a minúsculas y sin acentos automáticamente ("san jose") antes de mandarlo a la lógica. | Comprobado |
| M-03 | Validar campos vacíos | Dejar el nombre del producto o la ubicación en blanco al registrar | Muestra una advertencia de campo obligatorio y pide el dato de nuevo. | Comprobado |

## 2. Pruebas del Grafo y Mapa Inicial
| ID | Descripción | Acción / Entrada | Resultado Esperado | Estado |
|---|---|---|---|---|
| G-01 | Carga de datos base | Iniciar el programa y mostrar el grafo | Se deben ver cargados los vértices iniciales: san jose, heredia, cartago, alajuela. | Comprobado |
| G-02 | Registro de nueva ubicación | Agregar un cliente a la cola con una ubicación nueva (ej. "escazu") | La ubicación se agrega automáticamente como un vértice nuevo en el grafo. | Comprobado |
| G-03 | Conexión de nueva ruta | Insertar una arista desde el menú entre "san jose" y "escazu" | El grafo se actualiza y muestra la nueva conexión. | Comprobado  |

## 3. Pruebas de Despacho y Algoritmo de Dijkstra
| ID | Descripción | Acción / Entrada | Resultado Esperado | Estado |
|---|---|---|---|--|
| D-01 | Cliente desconectado del mapa | Intentar atender al siguiente cliente cuya ubicación no tiene aristas conectadas | El sistema muestra un error indicando que no hay conexión, NO atiende al cliente y lo deja esperando en la cola. | Comprobado |
| D-02 | Cliente con ruta válida | Atender cliente ubicado en "heredia" | Junto con la factura, se imprime el camino más corto calculado con Dijkstra desde la tienda y la distancia total del viaje. | Comprobado |
| D-03 | Cola de clientes vacía | Seleccionar atender cliente cuando no hay nadie | El menú avisa que no hay pedidos pendientes en este momento. | Comprobado |
