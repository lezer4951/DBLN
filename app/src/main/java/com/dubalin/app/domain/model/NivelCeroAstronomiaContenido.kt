package com.dubalin.app.domain.model

enum class ExperienciaAstronomia { PRIMERA_VEZ, CONOCIMIENTOS_PREVIOS }

enum class FormatoAprendizaje { TEXTO, VISUAL, QUIZ, EQUILIBRADO }

data class PlanEstudioAstronomia(
    val minutosDiarios: Int,
    val sesionesEstimadas: Int,
    val diasEstimados: Int,
    val temasPorDia: Int
)

object PlanificadorAstronomia {
    private const val MINUTOS_NIVEL_CERO = 180

    fun crearPlan(minutosDiarios: Int): PlanEstudioAstronomia {
        require(minutosDiarios > 0)
        val dias = (MINUTOS_NIVEL_CERO + minutosDiarios - 1) / minutosDiarios
        return PlanEstudioAstronomia(
            minutosDiarios = minutosDiarios,
            sesionesEstimadas = dias,
            diasEstimados = dias,
            temasPorDia = ((10 + dias - 1) / dias).coerceAtLeast(1)
        )
    }
}

data class PreguntaAutoevaluacion(
    val enunciado: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int,
    val explicacion: String,
    val explicacionAlternativa: String
)

data class SesionAstronomia(
    val numero: Int,
    val titulo: String,
    val objetivo: String,
    val porQueImporta: String,
    val duracionMinutos: Int,
    val analogia: String,
    val explicacion: String,
    val ejemploVisual: String,
    val ideaClave: String,
    val autoevaluacion: List<PreguntaAutoevaluacion>
)

data class PreguntaAstronomia(
    val tema: Int,
    val enunciado: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int,
    val explicacion: String
)

object NivelCeroAstronomiaContenido {
    val sesiones = listOf(
        sesion(
            1, "¿Qué es la Astronomía?",
            "Reconocer qué estudia la Astronomía y qué preguntas intenta responder.",
            "Te dará el mapa de todo lo que aprenderás después.",
            "Piensa en un detective que no puede visitar la escena: reúne pistas que viajaron desde muy lejos.",
            "La Astronomía es la ciencia que estudia los objetos y fenómenos del universo mediante observaciones, modelos y leyes físicas.",
            "Imagina la Tierra como una canica, el Sol como una pelota enorme y la galaxia como una ciudad llena de millones de esas pelotas.",
            "La Astronomía explica el universo usando evidencia comprobable.",
            q("¿Cuál pregunta pertenece a la Astronomía?", "Cómo nacen las estrellas", "Qué signo predice mi semana", "Qué número trae suerte", 0,
                "La formación de estrellas puede observarse y explicarse con física.", "Si una afirmación puede ponerse a prueba con observaciones del cielo, entra en el terreno científico."),
            q("¿Qué estudia la Astronomía?", "Solo los planetas", "Objetos y fenómenos del universo", "Únicamente la Tierra", 1,
                "Incluye desde polvo espacial hasta galaxias y el universo completo.", "Es como estudiar toda una ciudad: no solo sus casas, también sus calles, historia y cambios.")
        ),
        sesion(
            2, "Astronomía y astrología",
            "Distinguir una disciplina científica de una creencia sin evidencia verificable.",
            "Evitarás confundir predicciones personales con conocimiento científico.",
            "Un pronóstico meteorológico usa mediciones; una galleta de la fortuna ofrece frases que podrían servirle a cualquiera.",
            "La Astronomía contrasta hipótesis con datos y acepta correcciones. La astrología atribuye efectos personales a los astros sin evidencia reproducible.",
            "Dos personas nacen a la misma hora en ciudades distintas: la ciencia exige una predicción precisa que pueda comprobarse en ambas.",
            "Que algo hable de estrellas no lo convierte en Astronomía.",
            q("¿Qué característica distingue a la Astronomía?", "Sus resultados se pueden comprobar", "Predice personalidades", "Nunca cambia", 0,
                "La ciencia exige resultados contrastables y revisables.", "Una receta científica debe poder repetirse: otras personas tienen que obtener resultados compatibles."),
            q("¿Cuál afirmación es astrológica?", "Marte tiene dos lunas", "Tu signo determina tu carácter", "El Sol emite luz", 1,
                "Relacionar el signo con el carácter no cuenta con respaldo científico reproducible.", "Pregunta: ¿podríamos medir esa afirmación y obtener siempre el mismo resultado? Si no, no es una conclusión científica.")
        ),
        sesion(
            3, "El método científico",
            "Ordenar observación, hipótesis, predicción, prueba y conclusión.",
            "Es la herramienta que separa una buena idea de una explicación respaldada.",
            "Si una planta se marchita, primero observas, propones una causa, cambias una condición y comparas el resultado.",
            "El método científico formula explicaciones provisionales, deriva predicciones y las contrasta con datos. Si fallan, la hipótesis se corrige o descarta.",
            "Al notar que una estrella baja de brillo cada cierto tiempo, se predice cuándo volverá a hacerlo y se programa otra observación.",
            "La ciencia no busca tener razón para siempre; busca explicaciones que sobrevivan a pruebas exigentes.",
            q("¿Qué debe ocurrir después de proponer una hipótesis?", "Probar una predicción", "Declararla verdadera", "Ignorar nuevos datos", 0,
                "Una hipótesis científica debe generar predicciones comprobables.", "Una hipótesis es una sospecha del detective, no la sentencia del juez: aún necesita pruebas."),
            q("Si los datos contradicen la hipótesis, ¿qué se hace?", "Se ocultan", "Se revisa la hipótesis", "Se cambian los datos", 1,
                "Aceptar y explicar la evidencia contraria es parte esencial de la ciencia.", "El mapa debe adaptarse al territorio; nunca deformamos el territorio para salvar el mapa.")
        ),
        sesion(
            4, "La luz trae mensajes",
            "Comprender que la luz transporta información de objetos lejanos.",
            "Casi todo lo que sabemos del cosmos llegó hasta nosotros en forma de radiación.",
            "Como una carta con remitente, la luz conserva pistas sobre el lugar del que salió.",
            "Al medir brillo, color y espectro, los astrónomos infieren temperatura, composición y movimiento. La luz visible es solo una parte del espectro electromagnético.",
            "Un arcoíris separa la luz; las líneas oscuras de un espectro funcionan como códigos de barras de los elementos químicos.",
            "Observar el cielo es recibir y medir mensajes de luz.",
            q("¿Qué puede revelar el espectro de una estrella?", "Su composición", "Su signo zodiacal", "El sonido que emite en el vacío", 0,
                "Cada elemento deja un patrón característico en el espectro.", "Piensa en un código de barras: las líneas permiten identificar qué elementos produjeron o absorbieron la luz."),
            q("¿La luz visible es toda la radiación?", "Sí", "No, es solo una parte", "Solo existe de noche", 1,
                "También existen radio, infrarrojo, ultravioleta, rayos X y gamma.", "Nuestros ojos oyen una sola 'octava' de un enorme teclado de radiación.")
        ),
        sesion(
            5, "Escalas del universo",
            "Comparar tamaños y distancias sin perder la intuición.",
            "El universo es tan grande que necesitamos referencias adecuadas para entenderlo.",
            "Un mapa de barrio y un mapa del país usan escalas distintas; ninguno cabe con detalle en el otro.",
            "La escala permite representar relaciones enormes mediante proporciones. En Astronomía se usan modelos y potencias de diez para comparar desde planetas hasta galaxias.",
            "Si el Sol fuera una toronja, la Tierra sería un grano pequeño situado a unos 15 metros.",
            "Una imagen astronómica puede engañar si no conocemos su escala.",
            q("¿Por qué usamos modelos a escala?", "Para comparar magnitudes enormes", "Para agrandar realmente los planetas", "Para evitar medir", 0,
                "Los modelos conservan proporciones y vuelven manejables números gigantescos.", "Una maqueta no cambia el edificio: solo lo traduce a un tamaño que podemos abarcar."),
            q("Si el Sol se reduce, ¿qué debe pasar en un modelo correcto?", "Nada", "Distancias y planetas se reducen proporcionalmente", "La Tierra aumenta", 1,
                "Una escala coherente aplica la misma proporción a tamaños y distancias.", "Como una fotografía: al reducirla, todas sus partes se reducen juntas.")
        ),
        sesion(
            6, "UA y año luz",
            "Diferenciar dos unidades de distancia astronómica.",
            "Elegir la unidad correcta evita llenar la libreta de ceros y confundir tiempo con distancia.",
            "No medirías una carretera en milímetros; eliges kilómetros porque se ajustan al trayecto.",
            "Una unidad astronómica (UA) es la distancia media Tierra-Sol, unos 150 millones de km. Un año luz es la distancia recorrida por la luz en un año, unos 9.46 billones de km.",
            "Las UA sirven dentro de sistemas planetarios; los años luz, para distancias entre estrellas.",
            "Un año luz mide distancia, aunque su nombre contenga la palabra año.",
            q("¿Qué mide un año luz?", "Tiempo", "Distancia", "Velocidad", 1,
                "Mide la distancia que la luz recorre durante un año.", "Un 'día de camino' también puede describir qué tan lejos está un lugar; aquí usamos el viaje anual de la luz."),
            q("¿Qué unidad conviene para Tierra-Sol?", "Unidad astronómica", "Año luz", "Kilogramo", 0,
                "Esa distancia define aproximadamente 1 UA.", "La UA es la regla del vecindario solar; el año luz es la regla entre ciudades estelares.")
        ),
        sesion(
            7, "Planetas, estrellas y galaxias",
            "Ordenar las estructuras principales del cosmos.",
            "Te permitirá hablar del universo sin mezclar objetos de escalas distintas.",
            "Una casa está en una calle, la calle en una ciudad y la ciudad en un país.",
            "Un planeta orbita una estrella; un sistema planetario incluye esa estrella y sus cuerpos; enormes conjuntos de estrellas, gas y materia oscura forman galaxias.",
            "La Tierra está en el Sistema Solar, que está en la Vía Láctea, una entre muchísimas galaxias.",
            "Planeta, estrella, sistema y galaxia no son sinónimos: forman una jerarquía.",
            q("¿Cuál secuencia va de menor a mayor?", "Planeta, sistema, galaxia", "Galaxia, planeta, sistema", "Universo, estrella, planeta", 0,
                "Un planeta integra un sistema y los sistemas están dentro de galaxias.", "Recuerda: casa, barrio, ciudad. Tierra, Sistema Solar, Vía Láctea."),
            q("¿Qué es el Sol?", "Un planeta", "Una estrella", "Una galaxia", 1,
                "El Sol es la estrella alrededor de la cual orbita la Tierra.", "Las estrellas producen energía; los planetas reflejan gran parte de la luz que reciben.")
        ),
        sesion(
            8, "Telescopios",
            "Reconocer que distintos telescopios captan distintas clases de radiación.",
            "Ningún instrumento muestra por sí solo todo lo que ocurre en el universo.",
            "Un médico combina radiografía, ultrasonido y análisis: cada herramienta revela algo diferente.",
            "Los refractores usan lentes, los reflectores usan espejos y los radiotelescopios reciben ondas de radio. Otros instrumentos detectan infrarrojo, ultravioleta, rayos X o gamma.",
            "Una nube de polvo puede ocultar una estrella en luz visible y dejarla aparecer en infrarrojo.",
            "Un telescopio recolecta radiación; no acerca físicamente los astros.",
            q("¿Cuál es la función principal de un telescopio?", "Recolectar radiación", "Mover planetas", "Crear estrellas", 0,
                "Su apertura reúne señal para registrarla y analizarla.", "Es como un embudo para luz: cuanto más recoge, mejor podemos estudiar una fuente débil."),
            q("¿Por qué se observan varias longitudes de onda?", "Revelan fenómenos distintos", "Todas muestran exactamente lo mismo", "Para cambiar la órbita", 0,
                "Cada región del espectro responde a procesos y temperaturas diferentes.", "Como mirar una escena con luz normal y con cámara térmica: la misma escena ofrece información distinta.")
        ),
        sesion(
            9, "Observatorios, satélites y sondas",
            "Diferenciar plataformas de observación y exploración.",
            "Sabrás por qué algunos instrumentos se quedan en tierra y otros viajan por el espacio.",
            "Un faro observa desde un lugar fijo, un dron mira desde arriba y un explorador viaja hasta el destino.",
            "Un observatorio reúne instrumentos en Tierra o espacio; un satélite orbita un cuerpo; una sonda viaja para medir de cerca uno o varios destinos sin tripulación.",
            "Los telescopios espaciales evitan parte de la atmósfera; las sondas pueden analizar suelo, campos magnéticos o atmósferas localmente.",
            "La ubicación del instrumento depende de la pregunta científica.",
            q("¿Qué vehículo viaja para estudiar un destino de cerca?", "Sonda", "Constelación", "Galaxia", 0,
                "Las sondas llevan instrumentos hacia otros cuerpos o regiones.", "Es el explorador del equipo: no espera a que el mensaje llegue, se acerca al lugar."),
            q("¿Qué ventaja tiene un telescopio espacial?", "Evita parte de la atmósfera", "No necesita instrumentos", "Puede tocar estrellas", 0,
                "Fuera de la atmósfera capta radiación bloqueada o distorsionada desde tierra.", "Es como mirar sin una ventana empañada entre el observador y el paisaje.")
        ),
        sesion(
            10, "Una breve historia del cielo",
            "Relacionar observaciones históricas con el desarrollo de modelos científicos.",
            "La Astronomía avanzó al reemplazar autoridad por evidencia cada vez más precisa.",
            "Un mapa se corrige cuando nuevos viajeros encuentran rutas que el mapa antiguo no explicaba.",
            "Diversas culturas registraron ciclos celestes. El modelo heliocéntrico, las observaciones telescópicas, las leyes del movimiento y la física moderna transformaron nuestra explicación del cosmos.",
            "Copérnico propuso un modelo heliocéntrico; Galileo aportó observaciones telescópicas; Kepler describió órbitas; Newton explicó su dinámica gravitatoria.",
            "La historia científica es una cadena de modelos corregidos por mejores evidencias.",
            q("¿Qué impulsó los grandes cambios en Astronomía?", "Mejores evidencias y modelos", "No volver a observar", "Aceptar toda idea sin prueba", 0,
                "Las mediciones nuevas permiten comparar y corregir explicaciones.", "Cada generación afina el mapa cuando obtiene una brújula o una vista mejor."),
            q("¿Quién describió las órbitas planetarias con leyes matemáticas?", "Kepler", "Galileo", "Copérnico", 0,
                "Kepler formuló tres leyes del movimiento planetario a partir de observaciones precisas.", "Copérnico reorganizó el sistema; Galileo observó; Kepler encontró el patrón matemático de las órbitas.")
        )
    )

    val preguntas = listOf(
        examen(1, "¿Qué hace científica a una explicación astronómica?", "Que pueda contrastarse con evidencia", "Que sea antigua", "Que parezca interesante", "Que use palabras difíciles", 0, "Las explicaciones científicas deben producir predicciones contrastables."),
        examen(2, "¿Cuál afirmación pertenece a la astrología?", "Júpiter tiene una gran tormenta", "Tu signo determina tus decisiones", "La Tierra orbita al Sol", "La luz tiene un espectro", 1, "La atribución de personalidad a los signos no tiene evidencia reproducible."),
        examen(3, "Una predicción falla repetidamente. ¿Qué corresponde hacer?", "Revisar la hipótesis", "Borrar los datos", "Declararla cierta", "Evitar nuevas pruebas", 0, "El método científico corrige las hipótesis que no concuerdan con los datos."),
        examen(4, "¿Cómo conocemos la composición de una estrella lejana?", "Analizando su espectro", "Escuchando su sonido", "Visitándola", "Midiendo las mareas", 0, "Las líneas espectrales identifican elementos químicos."),
        examen(5, "En un modelo a escala correcto…", "Solo cambia el objeto mayor", "Todo mantiene la misma proporción", "Las distancias no cambian", "Los planetas crecen", 1, "Una escala aplica una proporción coherente a todo el modelo."),
        examen(6, "Próxima Centauri está a 4.24 años luz. Ese dato expresa…", "Edad", "Distancia", "Brillo", "Masa", 1, "El año luz es una unidad de distancia."),
        examen(7, "¿Dónde se encuentra el Sistema Solar?", "En la Vía Láctea", "Fuera del universo", "Dentro del Sol", "En otra dimensión", 0, "El Sistema Solar ocupa una región de la Vía Láctea."),
        examen(8, "¿Por qué combinar radio e infrarrojo con luz visible?", "Cada banda revela procesos distintos", "Para acercar los objetos", "Porque no existe luz visible", "Para cambiar su temperatura", 0, "Las longitudes de onda aportan información complementaria."),
        examen(9, "Para medir directamente el suelo de Marte conviene usar…", "Una sonda o rover", "Una carta estelar", "Solo un telescopio terrestre", "Un horóscopo", 0, "Una misión robótica puede analizar el entorno localmente."),
        examen(10, "¿Qué idea resume mejor la historia de la Astronomía?", "Los modelos mejoran con nueva evidencia", "El primer modelo fue definitivo", "La tecnología no influyó", "Observar dejó de ser necesario", 0, "La ciencia progresa corrigiendo modelos con datos más precisos.")
    )

    const val mascaraTodosLosTemas = (1 shl 10) - 1

    fun temaCompletado(mascara: Int, indice: Int): Boolean = mascara and (1 shl indice) != 0
    fun completarTema(mascara: Int, indice: Int): Int = mascara or (1 shl indice)
    fun todosLosTemasCompletados(mascara: Int): Boolean = mascara and mascaraTodosLosTemas == mascaraTodosLosTemas

    private fun sesion(numero: Int, titulo: String, objetivo: String, importa: String, analogia: String,
        explicacion: String, ejemplo: String, clave: String, vararg preguntas: PreguntaAutoevaluacion
    ) = SesionAstronomia(numero, titulo, objetivo, importa, 18, analogia, explicacion, ejemplo, clave, preguntas.toList())

    private fun q(enunciado: String, a: String, b: String, c: String, correcta: Int, explicacion: String, alternativa: String) =
        PreguntaAutoevaluacion(enunciado, listOf(a, b, c), correcta, explicacion, alternativa)

    private fun examen(tema: Int, enunciado: String, a: String, b: String, c: String, d: String, correcta: Int, explicacion: String) =
        PreguntaAstronomia(tema, enunciado, listOf(a, b, c, d), correcta, explicacion)
}
