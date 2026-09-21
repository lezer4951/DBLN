package com.dubalin.app.domain.model

object NivelSieteAstronomiaContenido {
    val sesiones = listOf(
        s(1, "Gravedad extrema", "Explicar un agujero negro sin imaginarlo como un aspirador.", "Establece la base física para todo el nivel.",
            "Una pendiente puede hacerse tan profunda que ninguna ruta disponible permita volver a subir.",
            "Un agujero negro es una región donde la curvatura del espacio-tiempo impide que señales salgan desde el interior del horizonte. Lejos de él, su gravedad actúa como la de cualquier objeto con la misma masa.",
            "Si el Sol fuera reemplazado por un agujero negro de igual masa, la Tierra conservaría aproximadamente su órbita, aunque perdería luz y calor.", "Un agujero negro no succiona todo: su efecto depende de masa y distancia.",
            q("¿Atrae más que una estrella de igual masa a igual distancia?", "No", "Sí, infinitamente", "Solo de día", 0, "La gravedad exterior depende de la masa y la distancia.", "La pendiente lejana sería equivalente."),
            q("¿Qué vuelve extrema la región?", "La curvatura del espacio-tiempo", "Una atmósfera densa", "El sonido", 0, "La masa compacta deforma intensamente el espacio-tiempo.", "La pendiente se hace extraordinariamente profunda.")),
        s(2, "Velocidad de escape", "Relacionar compactación con imposibilidad de escapar.", "Ofrece una intuición inicial sobre el horizonte.",
            "Cuanto más profundo es un pozo, más rápido debes subir para salir.",
            "La velocidad de escape aumenta con la masa y disminuye con el radio. Si una masa queda dentro de un radio crítico, ni una trayectoria luminosa dirigida hacia fuera puede alcanzar el exterior.",
            "No significa que la luz se vuelva lenta localmente; significa que todas las rutas futuras apuntan hacia dentro.", "El horizonte surge por geometría causal, no porque la luz pierda su rapidez.",
            q("¿Qué aumenta la velocidad de escape?", "Más masa en menor radio", "Menos gravedad", "Mayor distancia", 0, "Compactar masa profundiza el pozo gravitatorio.", "Un pozo más profundo exige más velocidad."),
            q("¿La luz se frena dentro del vacío?", "No", "Sí", "Solo la luz roja", 0, "Localmente siempre viaja a la velocidad de la luz.", "El problema es la dirección de las rutas, no la potencia del viajero.")),
        s(3, "Formación estelar", "Explicar cómo nace un agujero negro de masa estelar.", "Conecta este nivel con la evolución de estrellas masivas.",
            "Cuando falla el último soporte de una estructura muy pesada, el centro puede seguir cayendo sin formar una superficie estable.",
            "Tras agotar su combustible, el núcleo de una estrella masiva puede colapsar. Si el remanente supera el soporte de una estrella de neutrones, puede formar un agujero negro.",
            "Algunos se forman con supernova visible; otros podrían surgir mediante colapso casi directo.", "No toda estrella produce un agujero negro: la masa y la evolución del núcleo importan.",
            q("¿Qué progenitor puede formarlo?", "Una estrella masiva", "Cualquier planeta", "Una enana roja joven", 0, "Hace falta un núcleo final suficientemente masivo.", "Solo una estructura muy pesada puede superar todos sus soportes."),
            q("¿Todos nacen con una supernova brillante?", "No", "Sí", "Solo los supermasivos", 0, "Algunos colapsos pueden ser débiles o casi directos.", "La caída final no siempre produce el mismo espectáculo.")),
        s(4, "Horizonte de sucesos", "Definir la frontera de no retorno.", "Evita confundir el horizonte con una superficie material.",
            "Es como una cascada: pasada cierta línea, incluso el nadador más rápido es arrastrado río abajo.",
            "El horizonte de sucesos es una frontera causal. Una vez cruzada, ninguna señal puede volver al exterior. Para un agujero negro grande, una persona en caída libre no encontraría una pared en esa frontera.",
            "Un observador lejano recibe señales cada vez más enrojecidas y espaciadas de un objeto que se aproxima al horizonte.", "El horizonte es una propiedad del espacio-tiempo, no una cáscara sólida.",
            q("¿Qué hay físicamente en el horizonte?", "Una frontera causal", "Una pared", "Una superficie de fuego obligatoria", 0, "No es una cubierta material.", "La línea de la cascada marca un cambio de destino, no una barrera."),
            q("¿Puede volver una señal tras cruzarlo?", "No", "Sí, si es luz", "Sí, por radio", 0, "Todas las trayectorias futuras conducen hacia el interior.", "Después de la línea, toda corriente va río abajo.")),
        s(5, "Radio de Schwarzschild", "Relacionar masa con tamaño del horizonte no rotante.", "Permite comparar agujeros negros de distintas masas.",
            "Más masa necesita un círculo de no retorno proporcionalmente mayor.",
            "Para un agujero negro sin rotación ni carga, el radio de Schwarzschild es proporcional a la masa: aproximadamente tres kilómetros por masa solar.",
            "Uno de diez masas solares tendría un radio del orden de treinta kilómetros.", "El horizonte crece linealmente con la masa en el caso ideal no rotante.",
            q("¿Qué pasa al duplicar la masa?", "Se duplica el radio", "El radio desaparece", "Se reduce a la mitad", 0, "La relación ideal es lineal.", "Dos veces la masa corresponde a dos veces el círculo crítico."),
            q("¿Es el radio una superficie sólida?", "No", "Sí", "Solo si rota", 0, "Describe la ubicación del horizonte.", "La medida señala una frontera geométrica.")),
        s(6, "Rotación y ergosfera", "Reconocer cómo cambia un agujero negro rotante.", "Los objetos astrofísicos reales suelen poseer giro.",
            "Un remolino arrastra el agua cercana; un objeto rotante arrastra el propio espacio-tiempo.",
            "Un agujero negro de Kerr rota y produce arrastre de referencia. Fuera del horizonte aparece la ergosfera, donde nada puede permanecer estático respecto a observadores lejanos.",
            "En principio, ciertos procesos pueden extraer parte de la energía de rotación.", "Rotación modifica la geometría y añade una ergosfera fuera del horizonte.",
            q("¿Qué produce la rotación?", "Arrastre del espacio-tiempo", "Ausencia de gravedad", "Una superficie rocosa", 0, "La geometría gira con el objeto compacto.", "El remolino arrastra su entorno."),
            q("¿Dónde está la ergosfera?", "Fuera del horizonte", "Dentro de un planeta", "En toda galaxia por igual", 0, "Es una región externa característica de la rotación.", "Rodea la frontera principal del remolino.")),
        s(7, "Discos de acreción", "Explicar el brillo alrededor de un objeto oscuro.", "Gran parte de lo que detectamos procede de materia exterior.",
            "El agua de un desagüe gira, choca y se calienta antes de caer.",
            "Gas con momento angular forma un disco. Fricción y turbulencia transportan momento angular, calientan la materia y pueden generar rayos X antes de que cruce el horizonte.",
            "El disco puede superar en brillo a sistemas estelares completos; el agujero negro en sí no emite esa luz.", "La acreción, no el interior del agujero negro, produce la radiación observable.",
            q("¿Qué emite los rayos X?", "El gas caliente del disco", "El interior del horizonte", "El vacío", 0, "La materia exterior se calienta intensamente.", "Brilla el agua agitada antes del desagüe."),
            q("¿Por qué forma un disco?", "Por momento angular", "Porque desaparece la gravedad", "Por sonido", 0, "La materia no cae radialmente de inmediato.", "El flujo gira antes de acercarse al centro.")),
        s(8, "Chorros relativistas", "Distinguir chorros de materia y agujero negro.", "Explica emisiones que atraviesan escalas galácticas.",
            "Un sistema de campos actúa como una boquilla que canaliza energía en dos direcciones.",
            "Campos magnéticos en el disco y cerca de un agujero negro rotante pueden lanzar chorros de plasma a velocidades cercanas a la de la luz. Los chorros nacen fuera del horizonte.",
            "En galaxias activas pueden extenderse miles de años luz.", "Nada sale del interior: los chorros se originan en el sistema de acreción externo.",
            q("¿Salen los chorros de dentro del horizonte?", "No", "Sí", "Solo los de radio", 0, "Se forman en plasma y campos exteriores.", "La boquilla está antes del punto sin retorno."),
            q("¿Qué contienen?", "Plasma", "Planetas sólidos", "Materia sin energía", 0, "Son flujos de partículas cargadas.", "La boquilla canaliza materia ionizada.")),
        s(9, "Tipos y escalas", "Clasificar agujeros negros por masa.", "Distingue orígenes y ambientes observacionales.",
            "Una misma familia puede tener miembros del tamaño de una casa y otros del tamaño de una ciudad.",
            "Hay agujeros negros de masa estelar, candidatos de masa intermedia y supermasivos de millones o miles de millones de masas solares. Los primordiales siguen siendo hipotéticos.",
            "Los supermasivos ocupan los centros de muchas galaxias, pero no se forman simplemente de una sola estrella.", "La masa define categorías; no todos los agujeros negros comparten el mismo origen.",
            q("¿Dónde suelen estar los supermasivos?", "En centros galácticos", "Dentro de planetas", "En cada estrella", 0, "Muchas galaxias albergan uno en su núcleo.", "Los gigantes de la familia ocupan centros urbanos galácticos."),
            q("¿Están confirmados los primordiales?", "No", "Sí, todos", "Son estrellas", 0, "Siguen siendo candidatos hipotéticos.", "Esa rama de la familia todavía no tiene identificación segura.")),
        s(10, "Cómo los detectamos", "Inferir objetos oscuros por movimiento y radiación cercana.", "Muestra cómo la ciencia mide lo que no emite luz propia.",
            "Aunque no veas al compañero de baile, el movimiento de la otra persona revela su presencia.",
            "En sistemas binarios medimos la órbita de una estrella visible y la radiación del gas caliente. Una masa invisible, compacta y demasiado grande para ser estrella de neutrones es candidata a agujero negro.",
            "La evidencia mejora al combinar masa orbital, espectro y variabilidad.", "No basta una mancha oscura: se necesitan efectos cuantificables y alternativas descartadas.",
            q("¿Qué revela una compañera visible?", "La masa del objeto invisible", "Su color interior", "Una superficie", 0, "Su órbita responde a la gravedad conjunta.", "El baile permite pesar a la pareja oculta."),
            q("¿Una imagen negra basta?", "No", "Sí", "Solo de noche", 0, "Hace falta evidencia dinámica o radiativa.", "La ausencia de luz puede tener muchas causas.")),
        s(11, "Sagitario A* y M87*", "Comparar dos agujeros negros supermasivos observados.", "Conecta órbitas estelares e imágenes del horizonte.",
            "Podemos estudiar una ciudad siguiendo vehículos cerca del centro o fotografiando la silueta de su glorieta central.",
            "Sagitario A* se pesa mediante órbitas estelares en la Vía Láctea. M87* es mucho más masivo y alimenta un gran chorro. El Event Horizon Telescope obtuvo imágenes de anillos alrededor de ambos.",
            "Las imágenes muestran emisión del plasma curvada por gravedad y una región central oscura, no una fotografía del interior.", "Métodos independientes prueban la presencia de objetos supermasivos compactos.",
            q("¿Cómo se pesa Sagitario A*?", "Con órbitas estelares", "Con fases lunares", "Con tránsitos planetarios", 0, "Estrellas cercanas responden a su masa.", "Los vehículos trazan la fuerza del centro oculto."),
            q("¿Qué muestra el anillo del EHT?", "Plasma curvado por gravedad", "El interior del agujero negro", "Una superficie sólida", 0, "La emisión procede del entorno inmediato.", "Vemos la glorieta iluminada alrededor de la zona oscura.")),
        s(12, "Fuerzas de marea", "Explicar la espaguetificación sin exageraciones universales.", "Aclara que el efecto depende de masa y distancia.",
            "Si la gravedad tira mucho más de tus pies que de tu cabeza, te estira en una dirección y comprime en otra.",
            "Las fuerzas de marea son diferencias de gravedad entre puntos cercanos. Cerca de agujeros negros estelares pueden ser extremas antes del horizonte; en supermasivos, pueden ser moderadas al cruzarlo.",
            "Una estrella demasiado cercana puede ser desgarrada y producir un evento de disrupción de marea.", "La espaguetificación depende del gradiente gravitatorio, no solo de la gravedad total.",
            q("¿Qué causa el estiramiento?", "Diferencias de gravedad", "Ausencia de masa", "Luz visible", 0, "Distintas partes sienten aceleraciones diferentes.", "Pies y cabeza reciben tirones desiguales."),
            q("¿Siempre ocurre antes del horizonte?", "No", "Sí", "Nunca ocurre", 0, "En uno supermasivo el gradiente del horizonte puede ser menor.", "El tamaño del objeto central cambia dónde se vuelve extrema la diferencia.")),
        s(13, "Ondas gravitacionales", "Relacionar fusiones con ondulaciones del espacio-tiempo.", "Abre una forma de astronomía que no depende de luz.",
            "Dos pesas que giran aceleradamente agitan la tela sobre la que se mueven.",
            "Dos agujeros negros en espiral pierden energía mediante ondas gravitacionales, se fusionan y producen un remanente que vibra antes de estabilizarse.",
            "La forma de la señal permite estimar masas, giros y distancia del sistema.", "Las fusiones se escuchan gravitacionalmente mediante deformaciones diminutas del espacio-tiempo.",
            q("¿Qué transportan las ondas?", "Energía y información del sistema", "Aire", "Luz obligatoriamente", 0, "Son perturbaciones propagadas del espacio-tiempo.", "La tela lleva la huella del baile de las masas."),
            q("¿Qué deja una fusión?", "Un agujero negro mayor", "Un planeta", "Nada de masa", 0, "Parte de la masa-energía se irradia y queda un remanente.", "Las dos pesas forman una sola que se estabiliza.")),
        s(14, "Hawking, información y límites", "Distinguir resultados establecidos de preguntas abiertas.", "Introduce física de frontera sin convertir hipótesis en certezas.",
            "Un objeto aparentemente negro puede tener una fuga cuántica extremadamente lenta.",
            "La teoría cuántica en espacio-tiempo curvo predice radiación de Hawking y evaporación. Para agujeros negros astrofísicos la temperatura es minúscula. Cómo se conserva la información sigue siendo un problema activo.",
            "No hemos detectado directamente radiación de Hawking de un agujero negro astrofísico.", "La predicción teórica es sólida, pero la observación directa y el problema de información siguen abiertos.",
            q("¿Se ha detectado directamente radiación de Hawking?", "No", "Sí, del Sol", "Sí, en cada cuásar", 0, "La señal astrofísica sería extremadamente débil.", "La fuga prevista aún no ha sido medida en esos objetos."),
            q("¿Está resuelto el problema de información?", "No", "Sí por completo", "No existe", 0, "Continúa siendo una cuestión de gravedad cuántica.", "La frontera entre teorías aún guarda preguntas."))
    )

    val practica = listOf(
        e(3, "Una estrella masiva pierde soporte nuclear y su núcleo sigue colapsando. ¿Qué puede formar?", "Un agujero negro estelar", "Una nube molecular intacta", "Un planeta", "Una galaxia", 0, "Un remanente suficientemente masivo puede superar el soporte neutrónico."),
        e(7, "Se detectan rayos X variables junto a una masa invisible. ¿Qué emite esa radiación?", "El gas caliente en acreción", "El interior del horizonte", "Materia oscura", "El vacío", 0, "La materia exterior se calienta antes de cruzar el horizonte."),
        e(9, "Un objeto compacto de millones de masas solares está en un centro galáctico. ¿Qué categoría corresponde?", "Supermasivo", "Planetario", "Enana blanca", "Protoestelar", 0, "Los agujeros negros supermasivos habitan núcleos galácticos."),
        e(10, "Una estrella orbita rápidamente algo oscuro. ¿Qué dato permite inferir?", "La masa invisible", "El color del horizonte", "La composición interior", "Su sonido", 0, "La órbita mide el efecto gravitatorio del compañero."),
        e(11, "El EHT observa un anillo brillante con centro oscuro. ¿Qué brilla?", "Plasma alrededor del horizonte", "El interior", "Una superficie sólida", "Luz desde la singularidad", 0, "La gravedad curva la emisión del entorno inmediato."),
        e(12, "Una estrella pasa demasiado cerca y se desgarra. ¿Qué ocurrió?", "Disrupción de marea", "Tránsito planetario", "Fusión de hidrógeno", "Paralaje", 0, "El gradiente gravitatorio supera la cohesión de la estrella."),
        e(13, "Una señal muestra espiral, fusión y relajación sin luz. ¿Qué se detectó?", "Ondas gravitacionales", "Un espectro químico", "Una aurora", "Un eclipse", 0, "La forma de onda registra la unión de objetos compactos.")
    )

    val examen = listOf(
        e(1, "A gran distancia, ¿cómo atrae un agujero negro frente a una estrella de igual masa?", "De forma equivalente", "Infinitamente más", "No atrae", "Solo atrae luz", 0, "La gravedad exterior depende de masa y distancia."),
        e(2, "¿Por qué la luz no sale tras el horizonte?", "Todas las rutas futuras conducen hacia dentro", "La luz se detiene", "Pierde color", "Choca con una pared", 0, "Es una propiedad causal del espacio-tiempo."),
        e(4, "¿Qué es el horizonte de sucesos?", "Una frontera de no retorno", "Una superficie material", "Un disco brillante", "El centro galáctico", 0, "No es una cáscara física."),
        e(5, "¿Cómo cambia el radio de Schwarzschild al duplicar la masa?", "Se duplica", "Se reduce", "No cambia", "Desaparece", 0, "En el caso ideal es proporcional a la masa."),
        e(7, "¿Qué origina principalmente la luz de un sistema con agujero negro?", "Materia del disco de acreción", "El interior del horizonte", "La singularidad visible", "El vacío", 0, "El gas exterior se calienta y radia."),
        e(8, "¿De dónde nacen los chorros?", "Del sistema exterior de acreción y campos", "De dentro del horizonte", "De planetas", "De la materia oscura", 0, "Nada sale del interior del horizonte."),
        e(9, "¿Qué tipo ocupa el centro de muchas galaxias?", "Supermasivo", "Solo estelar", "Enana blanca", "Protoestrella", 0, "Puede alcanzar millones o miles de millones de masas solares."),
        e(11, "¿Qué evidencia pesa Sagitario A*?", "Órbitas de estrellas cercanas", "Cráteres", "Fases lunares", "Viento solar", 0, "Su movimiento exige una masa enorme y compacta."),
        e(13, "¿Qué producen dos agujeros negros al fusionarse?", "Ondas gravitacionales", "Sonido en aire", "Una nebulosa planetaria", "Un tránsito", 0, "La aceleración de masas perturba el espacio-tiempo."),
        e(14, "¿Cuál afirmación es rigurosa sobre radiación de Hawking?", "Está predicha pero no detectada directamente en agujeros negros astrofísicos", "Ya se observa en cada cuásar", "Es luz del disco", "Prueba que el horizonte es sólido", 0, "La temperatura esperada es extremadamente pequeña.")
    )

    const val mascaraCompleta = (1 shl 14) - 1
    fun completado(mascara: Int, indice: Int) = mascara and (1 shl indice) != 0
    private fun s(numero: Int, titulo: String, objetivo: String, importa: String, analogia: String,
        explicacion: String, ejemplo: String, clave: String, vararg preguntas: PreguntaAutoevaluacion
    ) = SesionAstronomia(numero, titulo, objetivo, importa, 30, analogia, explicacion, ejemplo, clave, preguntas.toList())
    private fun q(enunciado: String, a: String, b: String, c: String, correcta: Int, explicacion: String, alternativa: String) =
        PreguntaAutoevaluacion(enunciado, listOf(a, b, c), correcta, explicacion, alternativa)
    private fun e(tema: Int, enunciado: String, a: String, b: String, c: String, d: String, correcta: Int, explicacion: String) =
        PreguntaAstronomia(tema, enunciado, listOf(a, b, c, d), correcta, explicacion)
}
