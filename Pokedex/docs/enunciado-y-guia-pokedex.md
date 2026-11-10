# Pokédex de Cartas — Enunciado y guía (JavaFX + Gson)

> Documento de estudio convertido a Markdown desde el texto original entregado en el aula
> (`Enunciado evaluación parcial 2` + guía del docente). Se corrigieron únicamente los
> artefactos de la extracción del PDF: palabras pegadas, tablas desarmadas y listas partidas.

---

## Parte 1 — Enunciado: Evaluación Parcial 2

**Programación Orientada a Objetos**

| Sigla | Asignatura | Experiencia de Aprendizaje |
| --- | --- | --- |
| DSY1102 | Programación Orientada a Objetos | EA2: Desarrollo de Interfaces Gráficas con Persistencia en Archivo |

| Tiempo | Modalidad de Trabajo | Indicadores de logro |
| --- | --- | --- |
| 3 bloques | Individual | IL 2.1 al IL 2.8 |

### Condiciones de la evaluación

#### Del entorno y la entrega

- La evaluación es de carácter individual. No está permitida ninguna forma de colaboración entre estudiantes.
- No se permite el acceso a internet ni el uso de teléfonos, relojes inteligentes ni ningún otro dispositivo externo.
- El programa debe compilar y ejecutarse correctamente. Una solución que no compile no permite evidenciar ningún indicador de logro.
- El proyecto debe entregarse comprimido en formato ZIP, con tu nombre completo como nombre del archivo, en la actividad habilitada en AVA antes de que finalice la sesión.
- No se aceptan entregas por ningún otro medio ni fuera del horario establecido. La ausencia de entrega en AVA genera automáticamente la nota mínima.

#### De tu solución

- El código debe organizarse en clases separadas, respetando las convenciones de nomenclatura de Java: PascalCase para nombres de clase, camelCase para métodos y atributos.
- Puedes crear métodos o clases auxiliares siempre que no contradigan los requerimientos del caso.
- No se admite el uso de herramientas de inteligencia artificial ni de recursos digitales externos.
- Cualquier indicio de colaboración no autorizada, uso de IA o plagio implica la anulación inmediata de la evaluación con nota mínima de 1.0.

#### Instrucciones de entrega

- Comprime el proyecto completo (todas las clases) en un único archivo ZIP.
- Nombra el archivo con tu nombre completo, sin espacios ni tildes (ejemplo: `Juan_Perez_Lopez.zip`).
- Sube el archivo a la actividad de entrega habilitada en AVA antes de que finalice la sesión asignada.
- No se aceptan entregas por correo electrónico, USB ni ningún otro medio.
- La ausencia de entrega en AVA genera automáticamente la nota mínima, independientemente del avance de la solución.

### 1. Contexto del caso

La tienda de coleccionismo CartaMon ya cuenta con el modelo de clases desarrollado en la evaluación anterior: el sistema distingue cartas Pokémon y cartas de Energía, calcula el valor estimado de cada carta según su tipo, y permite activar la evolución de las cartas Pokémon dentro de la colección. Sin embargo, ese sistema solo funciona por consola y la información se pierde cada vez que el programa se cierra.

La administración de la tienda ha solicitado transformarlo en una aplicación de escritorio de una sola ventana, que permita al personal de inventario registrar y eliminar cartas, y que conserve la información en archivos para que esté disponible la próxima vez que se abra la aplicación.

#### Proyecto base

Al inicio de la evaluación, el/la docente te entregará un proyecto Maven con la configuración inicial, la clase de arranque y el modelo ya implementado y probado, correspondiente al diagrama siguiente. Tu trabajo consiste en construir sobre ese modelo la interfaz gráfica y el almacenamiento en archivos. No debes reescribir ni reemplazar las clases entregadas.

El modelo entregado ya aplica las siguientes reglas:

- **Carta Pokémon:** valor base de $3.000. Si la carta es brillante (holográfica), el valor se incrementa en un 40%.
- **Carta de Energía:** valor base de $1.000. Si la cantidad de energía supera 3,0 puntos, el valor se incrementa en un 20%.
- **Datos comunes:** el nombre no puede ser nulo ni vacío, el año de edición debe estar entre 1996 y 2026 y la cantidad debe ser mayor que cero. Si un dato no cumple, el modelo lo rechaza con un mensaje que explica el motivo.

### 2. Estructura del proyecto

La solución debe separar responsabilidades según el patrón Modelo-Vista-Controlador, con una capa de acceso a datos basada en el patrón DAO/Repository. El proyecto debe contener, como mínimo:

- **Configuración del proyecto:** el proyecto base ya incluye las dependencias de JavaFX y la clase de arranque de la aplicación. Debes agregar al archivo `pom.xml` la dependencia de la librería Gson disponible en el laboratorio, necesaria para trabajar con archivos JSON.
- **Distribución de paquetes y responsabilidades** acorde al patrón de diseño.

### 3. Ventana de la aplicación

Al ejecutar la aplicación se abre una única ventana, desde la cual se realizan todas las acciones. La ventana debe contener:

- Un formulario de registro con los campos descritos más abajo y un botón **Registrar**.
- Una tabla con las cartas registradas y las columnas **Categoría**, **Nombre**, **Año**, **Cantidad** y **Valor estimado**. El valor que se muestra es el que calcula el modelo para cada carta.
- Un botón **Eliminar**, que quita del registro la carta seleccionada en la tabla. Si no hay una fila seleccionada, la aplicación informa que se debe seleccionar una carta y no realiza ningún cambio.
- Un área de mensajes dentro de la misma ventana, donde se informa el resultado de cada acción: registro exitoso, dato no válido, eliminación, falta de selección o error al leer o guardar los archivos. Cada mensaje reemplaza al anterior. Los mensajes no se muestran por consola.

#### Campos del formulario

- Un selector de tipo con las opciones **Pokémon** y **Energía**.
- Datos comunes: nombre, año de edición y cantidad.
- Datos de la carta Pokémon: tipo elemental, si es brillante (sí/no) y si tiene la evolución activa (sí/no).
- Dato de la carta de Energía: cantidad de energía (puntos).

Al cambiar el tipo en el selector, solo quedan habilitados los datos específicos del tipo elegido; los datos del otro tipo quedan deshabilitados y no se consideran al registrar.

Al presionar **Registrar**, la aplicación valida los datos según la sección 4. Si son correctos, registra la carta, la tabla la muestra de inmediato, el área de mensajes informa la confirmación (por ejemplo: "Pikachu (Pokémon) registrada correctamente.") y el formulario queda vacío para un nuevo registro.

### 4. Validaciones del formulario

Antes de registrar, el formulario debe verificar los datos ingresados según las siguientes reglas:

- **Tipo:** debe estar seleccionado.
- **Nombre:** no puede estar vacío ni contener solo espacios.
- **Año de edición:** número entero entre 1996 y 2026.
- **Cantidad:** número entero mayor que cero.
- **Tipo elemental** (solo carta Pokémon): no puede estar vacío ni contener solo espacios.
- **Cantidad de energía** (solo carta de Energía): número mayor que cero; acepta decimales.

Las reglas de nombre, año de edición y cantidad son las mismas que ya aplica el modelo entregado. Si algún dato no es válido:

- la carta no se registra;
- el área de mensajes indica qué campo es incorrecto y por qué;
- los valores ya ingresados se mantienen para que puedan corregirse;
- la aplicación no se detiene ni muestra errores por consola.

### 5. Almacenamiento de la información

La información de las cartas debe conservarse entre una ejecución y otra, en formato JSON, en la carpeta raíz del proyecto:

- `pokemons.json`: contiene las cartas Pokémon registradas.
- `energias.json`: contiene las cartas de Energía registradas.

El comportamiento esperado es el siguiente:

- Al iniciar, la aplicación carga automáticamente la información de ambos archivos y la muestra en la tabla.
- Si los archivos no existen (por ejemplo, la primera vez que se ejecuta), la aplicación inicia con la tabla vacía y sin mostrar errores.
- Cada vez que se registra o se elimina una carta, los archivos se actualizan automáticamente, sin que el usuario deba presionar un botón para guardar.
- Lo que muestra la tabla y lo guardado en los archivos debe coincidir en todo momento.

### 6. Datos de prueba y resultado esperado

Registra desde la aplicación las siguientes cartas:

| Categoría | Nombre | Año | Cantidad | Datos específicos | Valor estimado |
| --- | --- | --- | --- | --- | --- |
| Pokémon | Pikachu | 1998 | 3 | Tipo: Eléctrico · Brillante: sí · Evolución activa: sí | $4.200 |
| Pokémon | Charmander | 2016 | 1 | Tipo: Fuego · Brillante: no · Evolución activa: no | $3.000 |
| Energía | Agua Básica | 2020 | 5 | Energía: 4,0 pts | $1.200 |
| Energía | Planta Básica | 2021 | 2 | Energía: 2,0 pts | $1.000 |

Luego de registrarlas, la ventana debe verse de forma similar a la siguiente:

*Figura 1. Ventana con las cuatro cartas registradas. [pendiente: capturar pantalla real de la app]*

Si se presiona **Eliminar** sin seleccionar una fila, el aviso aparece en la misma ventana:

*Figura 2. Aviso por falta de selección. [pendiente: capturar pantalla real de la app]*

Si un dato del formulario no es válido, el área de mensajes indica el campo con problemas y se mantienen los valores ingresados:

*Figura 3. Error de validación en el año de edición. [pendiente: capturar pantalla real de la app]*

#### Verificación de almacenamiento

1. Cierra la aplicación y vuelve a abrirla: la tabla debe mostrar las mismas cuatro cartas.
2. Elimina la carta Charmander, cierra la aplicación y vuelve a abrirla: Charmander ya no debe aparecer.

Las imágenes son referenciales: la distribución, los textos y el estilo de las ventanas pueden variar, siempre que estén presentes todos los elementos solicitados y que los valores calculados sean correctos.

---

## Parte 2 — Guía paso a paso: Pokédex de Cartas (JavaFX + Gson)

> *Claudio Esteban Rojas Rodríguez · 15:28*

Para estudiantes de primer año.

Esta guía te lleva, paso a paso, a construir una aplicación de escritorio en JavaFX que registra y elimina cartas Pokémon de una colección, calcula su valor de mercado estimado, y guarda todo en dos archivos JSON mediante Gson.

Al final de la guía encontrarás todo el código completo, listo para copiar, compilar y — sobre todo — estudiar. No te limites a pegarlo: léelo línea por línea y relee las secciones de teoría (campos calculados, ComboBox/CheckBox, persistencia en dos archivos) tantas veces como necesites.

### 1. Qué vas a construir

Una única ventana con:

- Un formulario para registrar una carta: tipo (Pokémon o Energía), nombre, año de edición, cantidad de copias, y los campos propios de cada tipo.
- Una tabla con todas las cartas registradas, incluyendo una columna calculada: el valor estimado en pesos.
- Botones **Registrar** y **Eliminar**.
- Un área de mensajes (sin usar la consola).
- Persistencia automática en dos archivos JSON: `pokemons.json` y `energias.json`.

#### 1.1 Reglas del dominio (defínelas así, no las cambies después)

**CartaPokemon**

- Valor base: $3.000.
- Si la carta es brillante (holográfica), bono del 40% → $4.200.
- Datos propios: `tipoElemental` (Fuego, Agua, Planta, Eléctrico, etc.), `brillante`, `evolucionActiva`.
- Se puede activar su evolución en la colección con `activarEvolucion()`.

**CartaEnergia**

- Valor base: $1.000.
- Si `cantidadEnergia` supera 3.0 puntos, recargo del 20% → $1.200.
- Dato propio: `cantidadEnergia` (decimal).

**Validaciones comunes (clase `Carta`)**

- Nombre: no puede ser nulo ni vacío.
- Año de edición: entre 1996 (año del primer set Pokémon TCG) y 2026.
- Cantidad: entero mayor que cero.

Guarda estas reglas; las vas a usar también para validar el formulario y para armar la tabla de pruebas del final.

### 2. Preparar el proyecto

1. Crea un proyecto Maven nuevo en IntelliJ IDEA: `groupId = cl.duoc`, `artifactId = pokedex`.
2. Verifica que el JDK configurado sea 21 (*File > Project Structure > Project*).
3. Reemplaza el contenido de `pom.xml` por este:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cl.duoc</groupId>
    <artifactId>pokedex</artifactId>
    <version>1.0</version>
    <packaging>jar</packaging>
    <name>Pokedex</name>

    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <javafx.version>21.0.6</javafx.version>
    </properties>

    <dependencies>
        <!-- JavaFX: controles gráficos y carga de vistas FXML -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- Gson: lectura y escritura de archivos JSON -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.11.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Permite ejecutar la aplicación con: mvn javafx:run -->
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>cl.duoc.pokedex.Launcher</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

4. Recarga el proyecto Maven (ícono "Load Maven Changes").
5. Crea esta estructura de carpetas (paquetes Java + carpeta de vistas):

```text
src/main/java/cl/duoc/pokedex/
├── App.java
├── Launcher.java
├── model/
├── dao/
├── repository/
└── controller/

src/main/resources/cl/duoc/pokedex/view/
```

Esta separación en paquetes es obligatoria: `model` no sabe nada de JavaFX ni de archivos; `dao` solo sabe de Gson y archivos; `repository` solo sabe de listas en memoria y de delegar en el DAO; `controller` solo sabe de la ventana. Nadie hace el trabajo de otro.

### 3. El modelo de dominio

El modelo es el corazón de la aplicación: las reglas de negocio (costos, validaciones) viven aquí y en ninguna otra parte. El controlador nunca vuelve a calcular un valor "a mano"; siempre le pregunta al objeto.

#### 3.1 Interfaz Evolucionable

```java
package cl.duoc.pokedex.model;

/**
 * Contrato que cumplen las cartas que participan del sistema de evolución
 * dentro de la colección.
 */
public interface Evolucionable {

    /**
     * Indica si la carta tiene su evolución activa en la colección.
     *
     * @return true si la evolución está activa
     */
    boolean tieneEvolucionActiva();

    /**
     * Registra que la carta activa su evolución.
     */
    void activarEvolucion();
}
```

#### 3.2 Clase abstracta Carta

```java
package cl.duoc.pokedex.model;

/**
 * Representa cualquier carta de la colección. Define los datos comunes y
 * delega en cada tipo de carta el cálculo de su valor estimado.
 */
public abstract class Carta {

    private String nombre;
    private int anioEdicion;
    private int cantidad;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos (Gson).
     */
    protected Carta() {
    }

    public Carta(String nombre, int anioEdicion, int cantidad) {
        setNombre(nombre);
        setAnioEdicion(anioEdicion);
        setCantidad(cantidad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    public int getAnioEdicion() {
        return anioEdicion;
    }

    public void setAnioEdicion(int anioEdicion) {
        if (anioEdicion < 1996 || anioEdicion > 2026) {
            throw new IllegalArgumentException("El año de edición debe estar entre 1996 y 2026.");
        }
        this.anioEdicion = anioEdicion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        this.cantidad = cantidad;
    }

    /**
     * Calcula el valor estimado de la carta según las reglas propias de cada tipo.
     *
     * @return valor estimado en pesos
     */
    public abstract double calcularValorEstimado();

    /**
     * Retorna el nombre de la categoría de la carta (por ejemplo, "Pokémon" o "Energía").
     *
     * @return categoría de la carta
     */
    public abstract String getCategoria();

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Año: " + anioEdicion;
    }
}
```

> **¿Por qué existe el constructor vacío `protected Carta()`?**
> Gson reconstruye objetos a partir del JSON sin pasar por tu constructor con validaciones (usa reflexión). Si no existe un constructor sin parámetros, Gson no puede crear la instancia al leer el archivo. Es un "atajo" que solo usa la librería, nunca tu propio código.

#### 3.3 CartaPokemon

```java
package cl.duoc.pokedex.model;

/**
 * Carta de un Pokémon individual. Participa en el sistema de evolución.
 * Valor estimado: base $3.000; si es brillante (holográfica), bono del 40%.
 */
public class CartaPokemon extends Carta implements Evolucionable {

    private static final double VALOR_BASE = 3000;
    private static final double BONO_BRILLANTE = 0.40;

    private String tipoElemental;
    private boolean brillante;
    private boolean evolucionActiva;

    public CartaPokemon() {
    }

    public CartaPokemon(String nombre, int anioEdicion, int cantidad,
                         String tipoElemental, boolean brillante) {
        super(nombre, anioEdicion, cantidad);
        setTipoElemental(tipoElemental);
        setBrillante(brillante);
        this.evolucionActiva = false;
    }

    public String getTipoElemental() {
        return tipoElemental;
    }

    public void setTipoElemental(String tipoElemental) {
        if (tipoElemental == null || tipoElemental.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo elemental no puede ser nulo ni vacío.");
        }
        this.tipoElemental = tipoElemental.trim();
    }

    public boolean isBrillante() {
        return brillante;
    }

    public void setBrillante(boolean brillante) {
        this.brillante = brillante;
    }

    @Override
    public boolean tieneEvolucionActiva() {
        return evolucionActiva;
    }

    @Override
    public void activarEvolucion() {
        this.evolucionActiva = true;
    }

    @Override
    public double calcularValorEstimado() {
        if (brillante) {
            return VALOR_BASE + VALOR_BASE * BONO_BRILLANTE;
        }
        return VALOR_BASE;
    }

    @Override
    public String getCategoria() {
        return "Pokémon";
    }
}
```

#### 3.4 CartaEnergia

```java
package cl.duoc.pokedex.model;

/**
 * Carta de energía que alimenta los ataques.
 * Valor estimado: base $1.000; si aporta más de 3.0 puntos de energía, recargo del 20%.
 */
public class CartaEnergia extends Carta {

    private static final double VALOR_BASE = 1000;
    private static final double RECARGO_ENERGIA = 0.20;
    private static final double LIMITE_ENERGIA = 3.0;

    private double cantidadEnergia;

    public CartaEnergia() {
    }

    public CartaEnergia(String nombre, int anioEdicion, int cantidad, double cantidadEnergia) {
        super(nombre, anioEdicion, cantidad);
        setCantidadEnergia(cantidadEnergia);
    }

    public double getCantidadEnergia() {
        return cantidadEnergia;
    }

    public void setCantidadEnergia(double cantidadEnergia) {
        if (cantidadEnergia <= 0) {
            throw new IllegalArgumentException("La cantidad de energía debe ser mayor que cero.");
        }
        this.cantidadEnergia = cantidadEnergia;
    }

    @Override
    public double calcularValorEstimado() {
        if (cantidadEnergia > LIMITE_ENERGIA) {
            return VALOR_BASE + VALOR_BASE * RECARGO_ENERGIA;
        }
        return VALOR_BASE;
    }

    @Override
    public String getCategoria() {
        return "Energía";
    }
}
```

Compila el proyecto (`mvn compile`) antes de seguir. Si el modelo no compila, nada de lo que sigue va a funcionar.

### 4. Concepto nuevo: campos calculados en una TableView

Hasta ahora, en otros ejercicios, probablemente llenaste una tabla mostrando datos que ya existían en el objeto: el nombre, el año. Bastaba con un getter. Pero el valor estimado no está guardado en ningún atributo: se calcula llamando a `calcularValorEstimado()`. Esto se llama **campo calculado** y es un concepto nuevo que necesitas entender bien.

#### 4.1 La idea clave

Una `TableView` no muestra objetos completos: muestra un valor por columna. Cada columna le pregunta a cada fila:

> "Para esta carta, ¿qué valor debo mostrar?"

Esa pregunta se responde con una función llamada `cellValueFactory`. Como es una función, puede hacer cualquier cálculo, no solo devolver un atributo.

```text
TableView<Carta>
 └── TableColumn<Carta, String> valorColumn
      └── cellValueFactory: (fila) -> valor de la celda
```

| Columna | Qué devuelve `cellValueFactory` |
| --- | --- |
| Normal (getter) | `carta.getNombre()` |
| Calculada | `carta.calcularValorEstimado()` |
| Calculada con formato | `formatearMoneda(carta.calcularValorEstimado())` |
| Calculada condicional | `carta instanceof CartaPokemon ? ... : ...` |

#### 4.2 Anatomía de una TableColumn

Una columna se declara con dos tipos genéricos:

```java
TableColumn<Carta, String> nombreColumn;
//           ↑        ↑
//     tipo de fila   tipo del valor de la celda
```

- **Primer tipo (`Carta`):** qué objeto hay en cada fila de la tabla.
- **Segundo tipo (`String`):** qué valor entrega esa columna.

El `cellValueFactory` debe devolver un `ObservableValue` de ese segundo tipo:

```java
nombreColumn.setCellValueFactory(dato ->
        new SimpleStringProperty(dato.getValue().getNombre()));
```

| Parte | Significado |
| --- | --- |
| `dato` | Un `CellDataFeatures<Carta, String>`: información de la fila que se está dibujando |
| `dato.getValue()` | La `Carta` de esa fila |
| `.getNombre()` | El valor que queremos mostrar |
| `new SimpleStringProperty(...)` | Envuelve el valor para que JavaFX pueda observarlo |

**Regla de oro:** el tipo del `SimpleXxxProperty` debe coincidir con el segundo genérico de la columna.

Una columna de "solo lectura" también puede escribirse con `PropertyValueFactory`, que busca automáticamente un getter:

```java
nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
// Busca el método getNombre() en Carta
```

Esto solo funciona para getters. En cuanto el valor es calculado, `PropertyValueFactory` ya no sirve: buscaría un método `getCalcularValorEstimado()`, que no existe, y la columna quedaría vacía.

#### 4.3 El campo calculado del proyecto: el valor estimado

```java
TableColumn<Carta, String> valorColumn;

valorColumn.setCellValueFactory(dato ->
        new SimpleStringProperty(
                formatearMoneda(dato.getValue().calcularValorEstimado())
        ));
private String formatearMoneda(double valor) {
    NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.of("es", "CL"));
    return formato.format(valor);
}
```

Resultado en pantalla: `$3.000`, `$4.200`, `$1.000`, `$1.200`.

**¿Por qué `String` y no `Double`?** Porque queremos mostrar el valor ya formateado como moneda. Si la columna fuera `TableColumn<Carta, Double>`, JavaFX mostraría `4200.0` en vez de `$4.200`.

#### 4.4 Tipos de columna y cómo envolver cada valor

| Quieres mostrar | Tipo de columna | Cómo se envuelve |
| --- | --- | --- |
| Texto | `String` | `new SimpleStringProperty(texto)` |
| Entero | `Integer` | `new SimpleIntegerProperty(numero).asObject()` |
| Decimal | `Double` | `new SimpleDoubleProperty(numero).asObject()` |
| Booleano | `Boolean` | `new SimpleBooleanProperty(valor).asObject()` |

```java
// Año (int → Integer)
anioColumn.setCellValueFactory(dato ->
        new SimpleIntegerProperty(dato.getValue().getAnioEdicion()).asObject());

// Cantidad (int → Integer)
cantidadColumn.setCellValueFactory(dato ->
        new SimpleIntegerProperty(dato.getValue().getCantidad()).asObject());
```

> **Ojo:** `new SimpleIntegerProperty(5)` no se puede asignar directamente a `TableColumn<Carta, Integer>`. Hay que llamar a `.asObject()` para convertir el `IntegerProperty` en un `ObservableValue<Integer>`.

#### 4.5 Un valor distinto según el tipo de carta

`CartaPokemon` tiene `tipoElemental`; `CartaEnergia` tiene `cantidadEnergia`. Puedes unificarlos en una sola columna "Detalle" sin modificar el modelo:

```java
TableColumn<Carta, String> detalleColumn;

detalleColumn.setCellValueFactory(dato -> {
    Carta carta = dato.getValue();

    if (carta instanceof CartaPokemon pokemon) {
        return new SimpleStringProperty(pokemon.getTipoElemental());
    }
    if (carta instanceof CartaEnergia energia) {
        return new SimpleStringProperty(energia.getCantidadEnergia() + " pts");
    }
    return new SimpleStringProperty("-");
});
```

`instanceof CartaPokemon pokemon` es *pattern matching* (Java 16+): si la condición se cumple, `pokemon` ya queda declarado y convertido, sin necesidad de un cast manual.

#### 4.6 ¿Cuándo se actualiza un campo calculado?

| Situación | ¿Se actualiza solo? |
| --- | --- |
| Agregas o quitas un objeto de la `ObservableList` | Sí |
| Modificas un atributo del objeto y la columna lo lee en vivo | No |
| Modificas datos y quieres recalcular la columna | Llama a `cartasTable.refresh()` |

Ejemplo: si activas la evolución de una carta ya cargada, la tabla no se entera por sí sola de ningún cambio visual relacionado hasta que la refresques:

```java
pokemon.activarEvolucion();
cartasTable.refresh();
```

#### 4.7 Errores comunes con campos calculados

| Síntoma | Causa probable | Solución |
| --- | --- | --- |
| La columna aparece vacía | Usaste `PropertyValueFactory` con un método calculado | Usa una lambda con `setCellValueFactory` |
| `NullPointerException` al iniciar | El `fx:id` del FXML no coincide con el campo `@FXML` | Revisa que los nombres sean idénticos |
| "Incompatible types" al compilar | El tipo de la columna no coincide con la propiedad | Usa `String`/`Double`/`Integer` y `.asObject()` cuando corresponda |
| Los números salen como `3000.0` | La columna es `Double` y no aplicaste formato | Formatea a `String` |
| La tabla se congela | Lees archivos dentro de `cellValueFactory` | Calcula en memoria; nunca hagas E/S dentro de una celda |

### 5. Concepto nuevo: ComboBox y CheckBox

Ahora el otro lado del formulario: los controles con los que el usuario elige opciones.

| Control | Sirve para | En este proyecto |
| --- | --- | --- |
| `ComboBox` | Elegir una opción de una lista | Categoría: Pokémon / Energía |
| `CheckBox` | Activar o desactivar una condición independiente | Brillante: sí/no · Evolución activa: sí/no |

#### 5.1 ComboBox: qué es

Es una lista desplegable: el usuario hace clic y elige una opción.

```text
┌──────────────────────────┐
│ Pokémon                 ▼ │
├──────────────────────────┤
│ Pokémon                   │
│ Energía                   │
└──────────────────────────┘
```

En el FXML:

```xml
<ComboBox fx:id="categoriaComboBox" maxWidth="Infinity"/>
```

`fx:id` conecta el control con el controlador. Si no coincide, el campo queda en `null` y la aplicación falla al iniciar.

Las opciones no se declaran en el FXML: se cargan desde el controlador (es la forma más flexible).

En el controlador:

```java
@FXML
private ComboBox<String> categoriaComboBox;
```

El genérico `<String>` indica que cada opción es un texto. Si el genérico no coincide con lo que se carga, el código no compila.

Cargar las opciones (en `initialize()`):

```java
categoriaComboBox.setItems(FXCollections.observableArrayList(CATEGORIA_POKEMON, CATEGORIA_ENERGIA));
```

Seleccionar una opción por defecto:

```java
categoriaComboBox.getSelectionModel().selectFirst();
```

Sin esta línea el `ComboBox` aparece vacío y `getValue()` devuelve `null` hasta que el usuario elige algo.

Leer el valor elegido:

```java
String categoria = categoriaComboBox.getValue(); // null si no hay selección
```

Por eso siempre se valida:

```java
if (categoria == null) {
    throw new IllegalArgumentException("Debe seleccionar una categoría de carta.");
}
```

Reaccionar a un cambio de opción. Hay dos mecanismos y no son iguales:

```java
// A) Solo cuando el usuario elige en la lista
categoriaComboBox.setOnAction(evento -> actualizarCamposPorCategoria());

// B) Cada vez que cambia el valor, venga del usuario o del código (la que usa este proyecto)
categoriaComboBox.valueProperty().addListener(
        (observable, anterior, nuevo) -> actualizarCamposPorCategoria());
```

| Situación | `setOnAction` | listener de `valueProperty()` |
| --- | --- | --- |
| El usuario elige en la lista | ✅ | ✅ |
| El código hace `setValue(...)` o `selectFirst()` | ❌ | ✅ |

**Regla práctica:** si la reacción debe ocurrir siempre que el valor cambie (incluido el arranque), usa el listener. Si solo debe ocurrir ante una acción manual del usuario, usa `setOnAction`.

El listener debe conectarse **antes** de llamar a `selectFirst()`, para que el primer valor también dispare la actualización de los campos.

#### 5.2 CheckBox: qué es

Es una casilla que puede estar marcada o desmarcada: representa un valor booleano, independiente de otras casillas.

```text
☑ Brillante
☐ Evolución activa
```

En el FXML:

```xml
<CheckBox fx:id="brillanteCheckBox" text="Sí"/>
```

En el controlador:

```java
@FXML
private CheckBox brillanteCheckBox;
```

Leer su estado:

```java
boolean brillante = brillanteCheckBox.isSelected();
```

`isSelected()` devuelve `true`/`false`. No se usa `getText()`: el texto es la etiqueta ("Sí"), no el estado.

Cambiar su estado desde el código (por ejemplo, al limpiar el formulario):

```java
brillanteCheckBox.setSelected(false);
```

Si no se resetea, el siguiente registro "heredaría" el estado del anterior.

#### 5.3 El corazón del formulario: habilitar campos según la categoría

Si se elige Pokémon, se habilitan tipo elemental, brillante y evolución activa. Si se elige Energía, se habilita la cantidad de energía.

```java
@FXML
public void initialize() {
    configurarTabla();

    categoriaComboBox.setItems(FXCollections.observableArrayList(CATEGORIA_POKEMON, CATEGORIA_ENERGIA));
    categoriaComboBox.valueProperty().addListener(
            (observable, anterior, nuevo) -> actualizarCamposPorCategoria());
    categoriaComboBox.getSelectionModel().selectFirst();

    cargarDatos();
}

private void actualizarCamposPorCategoria() {
    boolean esPokemon = CATEGORIA_POKEMON.equals(categoriaComboBox.getValue());

    tipoElementalField.setDisable(!esPokemon);
    brillanteCheckBox.setDisable(!esPokemon);
    evolucionActivaCheckBox.setDisable(!esPokemon);
    cantidadEnergiaField.setDisable(esPokemon);

    if (esPokemon) {
        cantidadEnergiaField.clear();
    } else {
        tipoElementalField.clear();
        brillanteCheckBox.setSelected(false);
        evolucionActivaCheckBox.setSelected(false);
    }
}
```

Puntos clave:

- `setDisable(true)` deshabilita el control: se ve gris y el usuario no puede escribir ni marcar.
- Deshabilitar **no borra** el valor: un `TextField` deshabilitado conserva su texto y un `CheckBox` deshabilitado conserva su estado. Por eso el método limpia los campos del otro tipo.
- `CATEGORIA_POKEMON.equals(categoriaComboBox.getValue())` es seguro aunque `getValue()` sea `null` (devuelve `false`). Al revés (`categoriaComboBox.getValue().equals(...)`) lanzaría `NullPointerException`.

#### 5.4 Errores comunes con ComboBox / CheckBox

| Síntoma | Causa | Solución |
| --- | --- | --- |
| El desplegable está vacío | No se llamó a `setItems(...)` | Carga las opciones en `initialize()` |
| `getValue()` devuelve `null` | No hay selección por defecto | `selectFirst()` o `setValue(...)` |
| `NullPointerException` al comparar | Se usó `getValue().equals(...)` | Usa `CONSTANTE.equals(getValue())` |
| El listener no se ejecuta | Se conectó después de `selectFirst()` | Conecta el listener antes de seleccionar |
| Cambiar la categoría no limpia los campos | Deshabilitaste, pero no borraste | Limpia los datos del tipo que ya no aplica |
| Siempre se registra `false` | Se leyó `getText()` en vez de `isSelected()` | Usa `isSelected()` |
| El estado "se hereda" entre registros | No se reseteó en `limpiarFormulario()` | `setSelected(false)` |

### 6. Concepto nuevo: persistir dos archivos con el mismo DAO + Gson

Ya sabes guardar y leer un archivo JSON con Gson. Aquí el desafío es distinto: la colección completa vive como una sola lista en memoria (`List<Carta>`), pero se guarda repartida en dos archivos, uno por cada tipo concreto: `pokemons.json` y `energias.json`.

#### 6.1 Por qué no se puede guardar todo junto

`Carta` es una clase abstracta: nunca existe un objeto que sea "solo una Carta", siempre es una `CartaPokemon` o una `CartaEnergia`. Gson necesita saber el tipo concreto para poder reconstruir los objetos al leer el archivo:

```java
new TypeToken<List<Carta>>() {}.getType();       // ❌ Gson no sabe qué subclase crear
new TypeToken<List<CartaPokemon>>() {}.getType(); // ✅ Gson sabe que debe crear CartaPokemon
```

Si intentaras leer una lista de `Carta` directamente, Gson no tendría cómo decidir si cada elemento del JSON es un `CartaPokemon` o un `CartaEnergia` (el JSON no incluye el nombre de la clase Java, solo sus atributos). Por eso cada tipo concreto necesita su propio archivo y su propio `TypeToken`.

#### 6.2 La solución: un DAO con cuatro operaciones

```text
CartaDao
 ├── leerCartasPokemon()       : List<CartaPokemon>   ← desde pokemons.json
 ├── leerCartasEnergia()       : List<CartaEnergia>   ← desde energias.json
 ├── guardarCartasPokemon(...) : escribe pokemons.json
 └── guardarCartasEnergia(...) : escribe energias.json
```

No hay un método genérico "guardar cualquier carta": hay un par de métodos (leer/guardar) por cada tipo concreto, uno para `pokemons.json` y otro para `energias.json`.

#### 6.3 Evitar duplicar el código de lectura/escritura

Aunque hay cuatro métodos públicos, la lógica de leer y escribir un archivo es idéntica para ambos tipos; solo cambian la ruta del archivo y el `Type` del `TypeToken`. Por eso, en la implementación, se escribe una sola vez esa lógica en dos métodos privados genéricos (`<T>`), y los cuatro métodos públicos simplemente los invocan con sus propios parámetros:

```java
private <T> List<T> leer(Path archivo, Type tipoLista) { ... }     // sirve para cualquier tipo T
private void escribir(Path archivo, Object datos, Type tipoLista) { ... }
```

Esto evita copiar y pegar el mismo bloque `try/catch` cuatro veces.

#### 6.4 Cómo el Repository combina los dos archivos en una sola colección

El controlador no sabe (ni le importa) que existen dos archivos: para él, todo es una lista de `Carta`. Quien hace el "pegamento" entre la lista única y los dos archivos es el `CartaRepository`:

```text
cargar():
  dao.leerCartasPokemon() ──┐
  dao.leerCartasEnergia() ──┴──► lista única en memoria: List<Carta>

registrar(carta) / eliminar(carta):
  lista en memoria (List<Carta>)
        │
        │  separar con instanceof
        ▼
  List<CartaPokemon>        List<CartaEnergia>
        │                         │
  dao.guardarCartasPokemon()  dao.guardarCartasEnergia()
        │                         │
   pokemons.json             energias.json
```

Fíjate en un detalle importante: cada vez que registras o eliminas UNA carta, se vuelven a escribir los DOS archivos completos, no solo el que cambió. El repository siempre parte de la lista completa en memoria, la separa por tipo, y le pide al DAO que guarde ambas listas. Es intencional: así el repository nunca tiene que decidir "¿cuál archivo toca esta vez?" — simplemente garantiza que ambos archivos reflejen siempre el estado completo de la colección.

```java
private void guardar(List<Carta> lista) {
    List<CartaPokemon> pokemones = new ArrayList<>();
    List<CartaEnergia> energias = new ArrayList<>();

    for (Carta carta : lista) {
        if (carta instanceof CartaPokemon pokemon) {
            pokemones.add(pokemon);
        } else if (carta instanceof CartaEnergia energia) {
            energias.add(energia);
        }
    }

    dao.guardarCartasPokemon(pokemones);
    dao.guardarCartasEnergia(energias);
}
```

Esto responde la pregunta que te vas a hacer tarde o temprano: "¿por qué tengo que recorrer toda la lista otra vez si solo agregué una carta?" Porque el archivo JSON no se "actualiza" como una base de datos: Gson lo reescribe completo cada vez. La única forma de que el archivo quede igual a la lista en memoria es volver a escribirlo entero.

#### 6.5 Errores comunes con dos archivos

| Síntoma | Causa probable | Solución |
| --- | --- | --- |
| Una carta registrada desaparece al recargar | Se guardó en el archivo equivocado o no se llamó a `guardar()` para ambos tipos | Revisa que `guardar()` siempre separe y guarde las dos listas |
| `ClassCastException` al leer | Se mezcló `CartaPokemon` y `CartaEnergia` en el mismo `TypeToken` | Cada tipo concreto tiene su propio `TypeToken` y su propio archivo |
| El archivo queda vacío sin motivo | Se guardó antes de cargar (`cargar()` no se llamó al iniciar) | Siempre cargar primero en `initialize()` |
| Al eliminar, el otro archivo también se reescribe aunque no cambió nada en él | Comportamiento esperado | No es un error: Gson siempre reescribe el archivo completo |

### 7. Capa dao: código completo

#### 7.1 CartaDao (interfaz)

```java
package cl.duoc.pokedex.dao;

import cl.duoc.pokedex.model.CartaEnergia;
import cl.duoc.pokedex.model.CartaPokemon;

import java.util.List;

/**
 * Contrato de acceso a datos para las cartas de la colección.
 * Define las operaciones de lectura y escritura sobre los archivos JSON.
 */
public interface CartaDao {

    /**
     * Lee todas las cartas Pokémon almacenadas.
     *
     * @return lista de cartas Pokémon; vacía si el archivo no existe
     */
    List<CartaPokemon> leerCartasPokemon();

    /**
     * Lee todas las cartas de energía almacenadas.
     *
     * @return lista de cartas de energía; vacía si el archivo no existe
     */
    List<CartaEnergia> leerCartasEnergia();

    /**
     * Guarda la lista de cartas Pokémon en su archivo JSON.
     *
     * @param cartas cartas Pokémon a persistir
     */
    void guardarCartasPokemon(List<CartaPokemon> cartas);

    /**
     * Guarda la lista de cartas de energía en su archivo JSON.
     *
     * @param cartas cartas de energía a persistir
     */
    void guardarCartasEnergia(List<CartaEnergia> cartas);
}
```

#### 7.2 CartaDaoJson (implementación con Gson)

```java
package cl.duoc.pokedex.dao;

import cl.duoc.pokedex.model.CartaEnergia;
import cl.duoc.pokedex.model.CartaPokemon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO basada en Gson. Gestiona los archivos
 * {@code pokemons.json} y {@code energias.json} ubicados en la raíz del proyecto.
 */
public class CartaDaoJson implements CartaDao {

    private static final Path ARCHIVO_POKEMON = Paths.get("pokemons.json");
    private static final Path ARCHIVO_ENERGIA = Paths.get("energias.json");

    private static final Type LISTA_POKEMON = new TypeToken<List<CartaPokemon>>() {
    }.getType();
    private static final Type LISTA_ENERGIA = new TypeToken<List<CartaEnergia>>() {
    }.getType();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<CartaPokemon> leerCartasPokemon() {
        return leer(ARCHIVO_POKEMON, LISTA_POKEMON);
    }

    @Override
    public List<CartaEnergia> leerCartasEnergia() {
        return leer(ARCHIVO_ENERGIA, LISTA_ENERGIA);
    }

    @Override
    public void guardarCartasPokemon(List<CartaPokemon> cartas) {
        escribir(ARCHIVO_POKEMON, cartas, LISTA_POKEMON);
    }

    @Override
    public void guardarCartasEnergia(List<CartaEnergia> cartas) {
        escribir(ARCHIVO_ENERGIA, cartas, LISTA_ENERGIA);
    }

    private <T> List<T> leer(Path archivo, Type tipoLista) {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(archivo)) {
            List<T> datos = gson.fromJson(reader, tipoLista);
            return datos != null ? datos : new ArrayList<>();
        } catch (IOException | JsonParseException e) {
            throw new RuntimeException("No se pudo leer el archivo " + archivo.toAbsolutePath(), e);
        }
    }

    private void escribir(Path archivo, Object datos, Type tipoLista) {
        try (Writer writer = Files.newBufferedWriter(archivo)) {
            gson.toJson(datos, tipoLista, writer);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el archivo " + archivo.toAbsolutePath(), e);
        }
    }
}
```

Observa que `leer` y `escribir` son privados y genéricos (`<T>`): es el mismo código para ambos tipos de carta, parametrizado por la ruta del archivo y el `Type`. Los cuatro métodos públicos de la interfaz solo deciden qué archivo y qué `TypeToken` usar.

### 8. Capa repository: código completo

```java
package cl.duoc.pokedex.repository;

import cl.duoc.pokedex.dao.CartaDao;
import cl.duoc.pokedex.dao.CartaDaoJson;
import cl.duoc.pokedex.model.Carta;
import cl.duoc.pokedex.model.CartaEnergia;
import cl.duoc.pokedex.model.CartaPokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository de cartas. Mantiene la colección de dominio en memoria y
 * delega en el DAO la lectura y escritura de los archivos JSON.
 */
public class CartaRepository {

    private final CartaDao dao;
    private final List<Carta> cartas = new ArrayList<>();

    public CartaRepository() {
        this(new CartaDaoJson());
    }

    public CartaRepository(CartaDao dao) {
        this.dao = dao;
    }

    /**
     * Carga desde los archivos todas las cartas y reemplaza la colección en memoria.
     */
    public void cargar() {
        List<Carta> cargadas = new ArrayList<>();
        cargadas.addAll(dao.leerCartasPokemon());
        cargadas.addAll(dao.leerCartasEnergia());

        cartas.clear();
        cartas.addAll(cargadas);
    }

    /**
     * Retorna una copia de la colección de cartas.
     */
    public List<Carta> listar() {
        return new ArrayList<>(cartas);
    }

    /**
     * Agrega una carta y persiste la colección completa. Si el guardado falla,
     * la colección en memoria se mantiene sin cambios.
     */
    public void registrar(Carta carta) {
        List<Carta> actualizadas = new ArrayList<>(cartas);
        actualizadas.add(carta);
        guardar(actualizadas);

        cartas.clear();
        cartas.addAll(actualizadas);
    }

    /**
     * Elimina una carta y persiste la colección completa. Si el guardado falla,
     * la colección en memoria se mantiene sin cambios.
     */
    public void eliminar(Carta carta) {
        List<Carta> actualizadas = new ArrayList<>(cartas);
        if (!actualizadas.remove(carta)) {
            return;
        }
        guardar(actualizadas);

        cartas.clear();
        cartas.addAll(actualizadas);
    }

    private void guardar(List<Carta> lista) {
        List<CartaPokemon> pokemones = new ArrayList<>();
        List<CartaEnergia> energias = new ArrayList<>();

        for (Carta carta : lista) {
            if (carta instanceof CartaPokemon pokemon) {
                pokemones.add(pokemon);
            } else if (carta instanceof CartaEnergia energia) {
                energias.add(energia);
            }
        }

        dao.guardarCartasPokemon(pokemones);
        dao.guardarCartasEnergia(energias);
    }
}
```

### 9. La vista: pokedex-view.fxml

Crea el archivo en `src/main/resources/cl/duoc/pokedex/view/pokedex-view.fxml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.CheckBox?>
<?import javafx.scene.control.ComboBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx"
      xmlns:fx="http://javafx.com/fxml"
      fx:controller="cl.duoc.pokedex.controller.PokedexController"
      spacing="12.0"
      prefHeight="680.0"
      prefWidth="900.0">

    <padding>
        <Insets top="18.0" right="18.0" bottom="18.0" left="18.0"/>
    </padding>

    <Label text="Pokédex de Cartas"
           style="-fx-font-size: 22px; -fx-font-weight: bold;"/>

    <GridPane hgap="10.0" vgap="9.0">
        <columnConstraints>
            <ColumnConstraints minWidth="150.0" prefWidth="150.0"/>
            <ColumnConstraints minWidth="200.0" prefWidth="240.0"/>
            <ColumnConstraints minWidth="150.0" prefWidth="150.0"/>
            <ColumnConstraints minWidth="200.0" prefWidth="240.0"/>
        </columnConstraints>

        <Label text="Categoría:"
               GridPane.rowIndex="0" GridPane.columnIndex="0"/>
        <ComboBox fx:id="categoriaComboBox" maxWidth="Infinity"
                  GridPane.rowIndex="0" GridPane.columnIndex="1"/>

        <Label text="Nombre:"
               GridPane.rowIndex="0" GridPane.columnIndex="2"/>
        <TextField fx:id="nombreField" promptText="Ej: Pikachu"
                   GridPane.rowIndex="0" GridPane.columnIndex="3"/>

        <Label text="Año de edición:"
               GridPane.rowIndex="1" GridPane.columnIndex="0"/>
        <TextField fx:id="anioField" promptText="Entre 1996 y 2026"
                   GridPane.rowIndex="1" GridPane.columnIndex="1"/>

        <Label text="Cantidad:"
               GridPane.rowIndex="1" GridPane.columnIndex="2"/>
        <TextField fx:id="cantidadField" promptText="Mayor que cero"
                   GridPane.rowIndex="1" GridPane.columnIndex="3"/>

        <Label text="Tipo elemental:"
               GridPane.rowIndex="2" GridPane.columnIndex="0"/>
        <TextField fx:id="tipoElementalField" promptText="Solo para Pokémon"
                   GridPane.rowIndex="2" GridPane.columnIndex="1"/>

        <Label text="Brillante:"
               GridPane.rowIndex="2" GridPane.columnIndex="2"/>
        <CheckBox fx:id="brillanteCheckBox" text="Sí"
                  GridPane.rowIndex="2" GridPane.columnIndex="3"/>

        <Label text="Evolución activa:"
               GridPane.rowIndex="3" GridPane.columnIndex="0"/>
        <CheckBox fx:id="evolucionActivaCheckBox" text="Sí"
                  GridPane.rowIndex="3" GridPane.columnIndex="1"/>

        <Label text="Energía (pts):"
               GridPane.rowIndex="3" GridPane.columnIndex="2"/>
        <TextField fx:id="cantidadEnergiaField" promptText="Solo para Energía"
                   GridPane.rowIndex="3" GridPane.columnIndex="3"/>
    </GridPane>

    <HBox spacing="10.0">
        <Button fx:id="registrarButton" text="Registrar" onAction="#registrarCarta"/>
        <Button fx:id="eliminarButton" text="Eliminar" onAction="#eliminarCarta"/>
    </HBox>

    <Label fx:id="mensajeLabel" text="" wrapText="true"/>

    <TableView fx:id="cartasTable" VBox.vgrow="ALWAYS">
        <columns>
            <TableColumn fx:id="categoriaColumn" text="Categoría" prefWidth="100.0"/>
            <TableColumn fx:id="nombreColumn" text="Nombre" prefWidth="160.0"/>
            <TableColumn fx:id="anioColumn" text="Año" prefWidth="90.0"/>
            <TableColumn fx:id="cantidadColumn" text="Cantidad" prefWidth="100.0"/>
            <TableColumn fx:id="valorColumn" text="Valor estimado" prefWidth="160.0"/>
        </columns>
    </TableView>

</VBox>
```

Cada `fx:id` de este archivo debe coincidir exactamente con un campo `@FXML` del controlador (siguiente paso). Si escribes `nombreField` en el FXML y `nombreTextField` en Java, la aplicación compila pero falla al abrir la ventana con `NullPointerException`.

### 10. El controlador: código completo

```java
package cl.duoc.pokedex.controller;

import cl.duoc.pokedex.model.Carta;
import cl.duoc.pokedex.model.CartaEnergia;
import cl.duoc.pokedex.model.CartaPokemon;
import cl.duoc.pokedex.repository.CartaRepository;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Controlador de la vista principal. Coordina el formulario, la tabla y la
 * capa de persistencia, delegando en el modelo los cálculos y reglas propias.
 */
public class PokedexController {

    private static final String CATEGORIA_POKEMON = "Pokémon";
    private static final String CATEGORIA_ENERGIA = "Energía";

    @FXML
    private ComboBox<String> categoriaComboBox;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField anioField;
    @FXML
    private TextField cantidadField;
    @FXML
    private TextField tipoElementalField;
    @FXML
    private CheckBox brillanteCheckBox;
    @FXML
    private CheckBox evolucionActivaCheckBox;
    @FXML
    private TextField cantidadEnergiaField;

    @FXML
    private Button registrarButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Label mensajeLabel;

    @FXML
    private TableView<Carta> cartasTable;
    @FXML
    private TableColumn<Carta, String> categoriaColumn;
    @FXML
    private TableColumn<Carta, String> nombreColumn;
    @FXML
    private TableColumn<Carta, Integer> anioColumn;
    @FXML
    private TableColumn<Carta, Integer> cantidadColumn;
    @FXML
    private TableColumn<Carta, String> valorColumn;

    private final ObservableList<Carta> cartas = FXCollections.observableArrayList();
    private final CartaRepository repository = new CartaRepository();

    /**
     * Se ejecuta automáticamente al cargar la vista.
     */
    @FXML
    public void initialize() {
        configurarTabla();

        categoriaComboBox.setItems(FXCollections.observableArrayList(CATEGORIA_POKEMON, CATEGORIA_ENERGIA));
        categoriaComboBox.valueProperty().addListener((observable, anterior, nuevo) -> actualizarCamposPorCategoria());
        categoriaComboBox.getSelectionModel().selectFirst();

        cargarDatos();
    }

    private void configurarTabla() {
        categoriaColumn.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getCategoria()));
        nombreColumn.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getNombre()));
        anioColumn.setCellValueFactory(dato ->
                new SimpleIntegerProperty(dato.getValue().getAnioEdicion()).asObject());
        cantidadColumn.setCellValueFactory(dato ->
                new SimpleIntegerProperty(dato.getValue().getCantidad()).asObject());
        valorColumn.setCellValueFactory(dato ->
                new SimpleStringProperty(formatearMoneda(dato.getValue().calcularValorEstimado())));

        cartasTable.setItems(cartas);
    }

    /**
     * Habilita únicamente los campos específicos de la categoría seleccionada.
     */
    private void actualizarCamposPorCategoria() {
        boolean esPokemon = CATEGORIA_POKEMON.equals(categoriaComboBox.getValue());

        tipoElementalField.setDisable(!esPokemon);
        brillanteCheckBox.setDisable(!esPokemon);
        evolucionActivaCheckBox.setDisable(!esPokemon);
        cantidadEnergiaField.setDisable(esPokemon);

        if (esPokemon) {
            cantidadEnergiaField.clear();
        } else {
            tipoElementalField.clear();
            brillanteCheckBox.setSelected(false);
            evolucionActivaCheckBox.setSelected(false);
        }
    }

    private void cargarDatos() {
        try {
            repository.cargar();
            cartas.setAll(repository.listar());

            if (cartas.isEmpty()) {
                mostrarMensaje("No hay datos guardados. Registre una carta para comenzar.", false);
            } else {
                mostrarMensaje("Datos cargados correctamente.", false);
            }
        } catch (RuntimeException e) {
            mostrarMensaje("Error al cargar los datos: " + e.getMessage(), true);
        }
    }

    @FXML
    private void registrarCarta() {
        try {
            String categoria = categoriaComboBox.getValue();
            if (categoria == null) {
                throw new IllegalArgumentException("Debe seleccionar una categoría de carta.");
            }

            String nombre = nombreField.getText();
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío ni contener solo espacios.");
            }

            int anio = leerEntero(anioField.getText(), "año de edición");
            if (anio < 1996 || anio > 2026) {
                throw new IllegalArgumentException("El año de edición debe estar entre 1996 y 2026.");
            }

            int cantidad = leerEntero(cantidadField.getText(), "cantidad");
            if (cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
            }

            Carta carta;
            if (CATEGORIA_POKEMON.equals(categoria)) {
                String tipoElemental = tipoElementalField.getText();
                if (tipoElemental == null || tipoElemental.trim().isEmpty()) {
                    throw new IllegalArgumentException(
                            "El tipo elemental no puede estar vacío ni contener solo espacios.");
                }

                CartaPokemon pokemon = new CartaPokemon(nombre, anio, cantidad, tipoElemental.trim(),
                        brillanteCheckBox.isSelected());
                if (evolucionActivaCheckBox.isSelected()) {
                    pokemon.activarEvolucion();
                }
                carta = pokemon;
            } else {
                double cantidadEnergia = leerDecimal(cantidadEnergiaField.getText());
                if (cantidadEnergia <= 0) {
                    throw new IllegalArgumentException("La cantidad de energía debe ser mayor que cero.");
                }
                carta = new CartaEnergia(nombre, anio, cantidad, cantidadEnergia);
            }

            repository.registrar(carta);
            cartas.add(carta);

            limpiarFormulario();
            mostrarMensaje(nombre + " (" + carta.getCategoria() + ") registrada correctamente.", false);
        } catch (RuntimeException e) {
            mostrarMensaje("Error: " + e.getMessage(), true);
        }
    }

    @FXML
    private void eliminarCarta() {
        Carta seleccionada = cartasTable.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Debe seleccionar una carta de la tabla para eliminarla.", true);
            return;
        }

        try {
            repository.eliminar(seleccionada);
            cartas.remove(seleccionada);
            mostrarMensaje(seleccionada.getNombre() + " (" + seleccionada.getCategoria()
                    + ") eliminada correctamente.", false);
        } catch (RuntimeException e) {
            mostrarMensaje("Error al eliminar: " + e.getMessage(), true);
        }
    }

    private void limpiarFormulario() {
        nombreField.clear();
        anioField.clear();
        cantidadField.clear();
        tipoElementalField.clear();
        cantidadEnergiaField.clear();
        brillanteCheckBox.setSelected(false);
        evolucionActivaCheckBox.setSelected(false);
        nombreField.requestFocus();
    }

    private int leerEntero(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser un número entero.");
        }
    }

    private double leerDecimal(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("La cantidad de energía es obligatoria para una carta de Energía.");
        }
        try {
            return Double.parseDouble(texto.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad de energía debe ser un número válido.");
        }
    }

    private String formatearMoneda(double valor) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.of("es", "CL"));
        return formato.format(valor);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError
                ? "-fx-text-fill: #b00020; -fx-font-weight: bold;"
                : "-fx-text-fill: #1b5e20; -fx-font-weight: bold;");
    }
}
```

### 11. App y Launcher

#### 11.1 App.java

```java
package cl.duoc.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Aplicación JavaFX. Carga la vista principal y la muestra en la ventana.
 */
public class App extends Application {

    /** Ubicación de la vista principal dentro de src/main/resources. */
    private static final String VISTA_PRINCIPAL = "/cl/duoc/pokedex/view/pokedex-view.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        URL vista = App.class.getResource(VISTA_PRINCIPAL);
        if (vista == null) {
            throw new IllegalStateException("No se encontró la vista principal: " + VISTA_PRINCIPAL);
        }
        Parent root = FXMLLoader.load(vista);
        stage.setTitle("Pokédex de Cartas");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
```

#### 11.2 Launcher.java

```java
package cl.duoc.pokedex;

import javafx.application.Application;

/**
 * Punto de entrada del programa. Ejecuta esta clase para iniciar la aplicación.
 */
public class Launcher {

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
```

### 12. Ejecutar y probar

Desde la raíz del proyecto:

```bash
mvn javafx:run
```

o ejecuta `cl.duoc.pokedex.Launcher` desde IntelliJ IDEA.

#### 12.1 Datos de prueba

| Categoría | Nombre | Año | Cantidad | Datos específicos | Valor esperado |
| --- | --- | --- | --- | --- | --- |
| Pokémon | Pikachu | 1998 | 3 | Tipo: Eléctrico · Brillante: sí · Evolución activa: sí | $4.200 |
| Pokémon | Charmander | 2016 | 1 | Tipo: Fuego · Brillante: no · Evolución activa: no | $3.000 |
| Energía | Agua Básica | 2020 | 5 | Energía: 4,0 pts | $1.200 |
| Energía | Planta Básica | 2021 | 2 | Energía: 2,0 pts | $1.000 |

#### 12.2 Checklist de pruebas finales

1. Registra las cuatro cartas y verifica los valores de la tabla.
2. Presiona **Eliminar** sin seleccionar una fila: debe aparecer el aviso y no cambiar nada.
3. Ingresa un dato inválido (por ejemplo, año 1980): debe aparecer el error y conservarse los valores escritos.
4. Cierra y reabre la aplicación: deben aparecer las cuatro cartas.
5. Elimina la carta Charmander, cierra y reabre: Charmander ya no debe aparecer, pero las otras tres sí.
6. Revisa en la raíz del proyecto que existan `pokemons.json` (2 elementos) y `energias.json` (2 elementos) y que coincidan con lo mostrado en la tabla.

#### 12.3 Checklist de cumplimiento

- [ ] Dependencia de Gson agregada en `pom.xml`.
- [ ] Vista declarada en FXML con formulario, tabla, botones y mensajes.
- [ ] Controlador vinculado a la vista mediante `fx:controller` y `@FXML`.
- [ ] `ComboBox` con opciones Pokémon y Energía, con selección por defecto.
- [ ] Campos comunes y campos específicos de cada categoría.
- [ ] Habilitación/deshabilitación dinámica de campos según la categoría, con limpieza del tipo no usado.
- [ ] Tabla con colección observable y columna de valor calculada (no almacenada).
- [ ] Validaciones de nombre, año, cantidad, tipo elemental y cantidad de energía.
- [ ] Mensajes de éxito, error, eliminación y falta de selección, todos en la interfaz (nunca por consola).
- [ ] Interfaz `CartaDao` con cuatro operaciones (leer/guardar × Pokémon/Energía).
- [ ] Implementación del DAO con Gson para `pokemons.json` y `energias.json`.
- [ ] `CartaRepository` con colección en memoria y delegación al DAO, separando por tipo antes de guardar.
- [ ] Carga automática al iniciar; archivos inexistentes se tratan como listas vacías, sin errores.
- [ ] Guardado automático después de registrar y eliminar.
- [ ] Modelo (`Carta`, `Evolucionable`, `CartaPokemon`, `CartaEnergia`) sin dependencias de JavaFX ni de archivos.
- [ ] Proyecto compila y ejecuta correctamente.

### 13. Errores comunes (resumen general)

| Síntoma | Causa probable | Solución |
| --- | --- | --- |
| `NullPointerException` al abrir la ventana | Un `fx:id` del FXML no tiene su par `@FXML` exacto en el controlador | Revisa que los nombres sean idénticos en ambos archivos |
| La columna de valor sale vacía | Se usó `PropertyValueFactory` para un método calculado | Usa una lambda con `setCellValueFactory` |
| El `ComboBox` aparece vacío | No se llamó a `setItems(...)` en `initialize()` | Carga las opciones antes de usarlas |
| Cambiar la categoría no limpia los campos del otro tipo | Solo se deshabilitó, no se limpió | Agrega `.clear()` / `setSelected(false)` |
| Una carta desaparece al reabrir la aplicación | No se guardó en el archivo correspondiente, o no se llamó a `cargar()` al iniciar | Revisa `CartaRepository.guardar()` y `initialize()` |
| `RuntimeException` al leer un archivo JSON | El archivo fue editado a mano y quedó mal formado | Bórralo (vuelve a quedar vacío) y vuelve a registrar los datos |
| Error de consola al registrar | Se dejó algún `System.out.println` o excepción no capturada | Todas las validaciones deben mostrarse en `mensajeLabel`, nunca por consola |

### 14. Ejercicios propuestos

Aquí tienes desafíos para profundizar sin modificar las clases del modelo (`Carta`, `Evolucionable`, `CartaPokemon`, `CartaEnergia`).

#### 14.1 Nuevas columnas calculadas

Agrega a la tabla, usando lo aprendido en la sección 4:

| Columna | Tipo | Cálculo |
| --- | --- | --- |
| Antigüedad | `Integer` | `2026 - anioEdicion` |
| Detalle | `String` | Tipo elemental si es Pokémon; `cantidadEnergia + " pts"` si es Energía |
| Bono/Recargo | `String` | "40%" si el Pokémon es brillante; "20%" si la Energía supera 3.0 pts; "Sin bono/recargo" en otro caso |

Solución sugerida (para estudiar después de intentarlo tú mismo):

```java
private static final int ANIO_ACTUAL = 2026;

antiguedadColumn.setCellValueFactory(dato ->
        new SimpleIntegerProperty(ANIO_ACTUAL - dato.getValue().getAnioEdicion()).asObject());

detalleColumn.setCellValueFactory(dato -> {
    Carta carta = dato.getValue();
    if (carta instanceof CartaPokemon pokemon) {
        return new SimpleStringProperty(pokemon.getTipoElemental());
    }
    if (carta instanceof CartaEnergia energia) {
        return new SimpleStringProperty(energia.getCantidadEnergia() + " pts");
    }
    return new SimpleStringProperty("-");
});

bonoColumn.setCellValueFactory(dato -> {
    Carta carta = dato.getValue();
    if (carta instanceof CartaPokemon pokemon) {
        return new SimpleStringProperty(pokemon.isBrillante() ? "40%" : "Sin bono/recargo");
    }
    if (carta instanceof CartaEnergia energia) {
        return new SimpleStringProperty(energia.getCantidadEnergia() > 3.0 ? "20%" : "Sin bono/recargo");
    }
    return new SimpleStringProperty("-");
});
```

Desafío extra: usa un `cellFactory` (ver sección 4) para pintar en rojo la columna "Bono/Recargo" cuando no diga "Sin bono/recargo".

#### 14.2 Un nuevo ComboBox y un nuevo CheckBox

- Agrega un `ComboBox<String>` llamado `edicionComboBox` con las opciones Base, Jungla, Fósil, Escarlata-Púrpura, seleccionando Base por defecto.
- Agrega un `CheckBox` llamado `favoritaCheckBox` con el texto "Marcar como favorita ⭐".
- Al registrar, muestra en el área de mensajes algo como:

  > Pikachu (Pokémon) registrada correctamente. Edición: Base. Favorita: sí.

- Desafío: agrega una columna "Favorita" a la tabla que muestre "Sí" o "No".
- Pista: ni la edición ni "favorita" son atributos de `Carta`, así que sus valores debes guardarlos en una estructura paralela (por ejemplo, `Map<Carta, Boolean>` para favoritas) para que la columna pueda consultarlos. Reflexiona: ¿por qué no puedes obtenerlo directamente del modelo ni del JSON sin modificarlos?

#### 14.3 Desafío de persistencia

Agrega una tercera categoría de carta, `CartaEntrenador` (por ejemplo, con un atributo `efecto` de tipo `String`), que se guarde en un tercer archivo `entrenadores.json`. Tendrás que:

1. Agregar dos métodos nuevos a `CartaDao` (`leerCartasEntrenador`, `guardarCartasEntrenador`).
2. Agregar su `Path` y su `TypeToken` en `CartaDaoJson`.
3. Ajustar `CartaRepository.guardar()` para separar también este tercer tipo.
4. Agregar su opción al `ComboBox` de categorías y sus campos propios al formulario.

Si entendiste la sección 6, este ejercicio es solo "repetir el patrón una vez más" — y esa es exactamente la idea: comprobar que el diseño escala a un tercer tipo sin romper nada de lo que ya funcionaba.

### 15. Resumen final

- El modelo concentra las reglas de negocio (costos/valores, validaciones); nunca depende de JavaFX ni de archivos.
- Un campo calculado en una tabla se define con una lambda en `setCellValueFactory`, no con `PropertyValueFactory`; el tipo de la columna debe coincidir con lo que envuelves (`SimpleStringProperty`, `SimpleIntegerProperty.asObject()`, etc.).
- El `ComboBox` ofrece una opción de una lista (`getValue()`); el `CheckBox` representa una condición booleana independiente (`isSelected()`). Ambos se declaran con `fx:id` en el FXML y `@FXML` en el controlador, y se inicializan en `initialize()`.
- Deshabilitar un control (`setDisable(true)`) no borra su valor: si un campo deja de aplicar al cambiar de categoría, límpialo explícitamente.
- Cuando el modelo tiene varias subclases concretas, el DAO necesita un `TypeToken` y un archivo por subclase concreta (Gson no puede reconstruir una clase abstracta). El Repository es quien oculta ese detalle: combina los archivos al leer y los separa con `instanceof` al escribir, de modo que el controlador siempre trabaja con una única lista polimórfica.
- Cada operación de registrar o eliminar reescribe ambos archivos completos, porque Gson no "actualiza" un JSON: lo vuelve a escribir entero a partir de la lista en memoria.

Con esto tienes un proyecto completo que aplica los conceptos fundamentales de la arquitectura MVC + DAO/Repository: campos calculados, ComboBox/CheckBox y persistencia dual con Gson. La prueba de que entendiste el patrón, y no solo copiaste código, es resolver el ejercicio 14.3 (tercer tipo de carta) sin volver a mirar las secciones 6 a 10.
