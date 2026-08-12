# Plan de Pruebas - Gestión de Inventarios y Rutas

## 1. Módulo de Menús e Inputs
- [ ] Verificar que el menú administrador muestre las opciones 4, 5 y 6.
- [ ] Comprobar que al solicitar ubicación de cliente se convierta a minúsculas sin error.

## 2. Módulo de Conectividad (Validación de Flujo)
- [ ] Intentar atender a un cliente con ubicación conectada a 'san jose' (Debe facturar y mostrar ruta).
- [ ] Intentar atender a un cliente con ubicación desconectada (Debe mostrar error y mantenerlo en cola).

## 3. Módulo de Edición de Grafo
- [ ] Insertar vértice nuevo y verificar su existencia en 'Mostrar grafo'.
- [ ] Crear arista para conectar a un cliente previamente desconectado y comprobar que ahora sí pueda ser atendido.
