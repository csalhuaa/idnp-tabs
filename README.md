# Explicación del Comportamiento de los Tabs en Jetpack Compose

### Consulta
> Si tengo 2 tabs, el primero sin componentes mayores, pero en el segundo tengo una lista de frutas (por ejemplo), ¿entonces mientras está visible el primer tab, ya se carga la lista de frutas o solo se cargan al pasar al segundo tab?

---

### Respuesta y Fundamentación

En Jetpack Compose, las pantallas o componentes no se “dibujan” de inmediato como en los enfoques tradicionales, sino que funcionan bajo un **modelo declarativo y perezoso (lazy)**.  
Esto significa que **Compose solo ejecuta y compone los elementos que realmente son necesarios para mostrarse en pantalla** según el estado actual de la interfaz.

En el caso del ejemplo con dos tabs dentro de un `HorizontalPager`:

- Cuando el usuario se encuentra en el **primer tab ("Inicio")**, Compose **solo compone** el contenido de esa pestaña (por ejemplo, un texto o un mensaje de bienvenida).  
- El **segundo tab ("Lista de Frutas")** todavía **no se compone completamente**, ya que no es visible. Compose puede crear de forma interna la estructura base (por ejemplo, reservar espacio o preparar el estado), pero **no ejecuta la `LazyColumn` ni genera cada uno de los ítems**.

Solo cuando el usuario **cambia al segundo tab** (ya sea pulsando o deslizando), Compose:
1. Ejecuta la función composable correspondiente a esa pantalla (`PantallaListaDeFrutas()`).
2. Crea la `LazyColumn`.
3. Renderiza únicamente los elementos visibles de la lista (los demás se irán componiendo conforme se hace scroll).

Esto se confirma fácilmente observando los logs: no aparecen registros de `FrutaItem` hasta que el tab “Lista de Frutas” está en pantalla.

---

### En resumen

- **Mientras estás en el tab 1**, la lista del tab 2 **no se carga ni se compone aún**.  
- **Compose actúa bajo demanda**, generando la interfaz solo cuando es necesaria.  
- Esto evita trabajo innecesario, mejora el rendimiento y reduce el consumo de memoria.  
- Al cambiar de tab, Compose **compone por primera vez** la lista y sus elementos visibles.

---

### Idea central
> Jetpack Compose no “pre-renderiza” todas las pantallas, sino que **compone el contenido justo a tiempo**, en función del estado visible de la interfaz.  
> Por eso, el segundo tab se carga solo cuando el usuario lo visualiza por primera vez, no antes.
