package com.dubalin.app.domain.model

object NivelCincoAstronomiaContenido {
    val sesiones = listOf(
        s(1, "Qué es una galaxia", "Definir una galaxia y distinguir sus componentes.", "Sitúa estrellas, gas y materia oscura en una estructura común.",
            "Una galaxia es como una ciudad enorme: tiene habitantes visibles, calles de gas y una infraestructura invisible.",
            "Una galaxia es un sistema gravitacional de estrellas, gas, polvo, remanentes y materia oscura. Puede contener desde millones hasta billones de estrellas.",
            "Las estrellas que vemos son solo una parte de la masa galáctica.", "Una galaxia permanece unida por gravedad y no es solo un montón de estrellas.",
            q("¿Qué mantiene unida una galaxia?", "La gravedad", "El sonido", "El viento solar", 0, "La gravedad organiza toda su materia.", "La ciudad cósmica necesita una fuerza que mantenga juntas sus partes."),
            q("¿Una galaxia contiene solo estrellas?", "No", "Sí", "Solo las espirales", 0, "También contiene gas, polvo y materia oscura.", "Los puntos luminosos no muestran toda la estructura.")),
        s(2, "La Vía Láctea", "Describir nuestra galaxia como una espiral barrada.", "Es el hogar galáctico del Sistema Solar.",
            "Desde dentro de un bosque cuesta ver su forma completa; debemos combinar muchas observaciones.",
            "La Vía Láctea es una galaxia espiral barrada de aproximadamente cien mil años luz de diámetro. La franja lechosa del cielo es su disco visto desde dentro.",
            "Mapas de estrellas, gas y radio permiten reconstruir una galaxia que no podemos fotografiar desde fuera.", "Vivimos dentro del disco de una galaxia espiral barrada.",
            q("¿Qué tipo de galaxia es la Vía Láctea?", "Espiral barrada", "Elíptica gigante", "Irregular", 0, "Posee disco, brazos y una barra central.", "La barra cruza el centro del remolino estelar."),
            q("¿Por qué aparece como una franja?", "Vemos su disco desde dentro", "Es una nube cercana", "La rodea un anillo sólido", 0, "Miramos a través de muchas estrellas del plano galáctico.", "Desde dentro del bosque, los árboles se acumulan en ciertas direcciones.")),
        s(3, "Núcleo, disco, brazos y halo", "Identificar las regiones principales de una galaxia espiral.", "Cada región conserva poblaciones y movimientos distintos.",
            "Como una ciudad con centro, barrios, avenidas curvas y periferia extensa.",
            "El núcleo y el bulbo concentran estrellas; el disco contiene gas, polvo y brazos; el halo rodea el sistema con cúmulos globulares y materia oscura.",
            "Los brazos no son filas fijas de estrellas: son patrones donde se comprime gas y se favorece formación estelar.", "Las regiones galácticas se diferencian por forma, contenido y dinámica.",
            q("¿Dónde abundan gas y estrellas jóvenes?", "En el disco y los brazos", "Solo en el halo", "Fuera de la galaxia", 0, "Allí se concentra material para formar estrellas.", "Los viveros estelares siguen las avenidas del disco."),
            q("¿Qué rodea ampliamente el disco?", "El halo", "La fotosfera", "Una superficie sólida", 0, "El halo incluye cúmulos y materia oscura.", "Es la periferia extensa de la ciudad galáctica.")),
        s(4, "Ubicación del Sistema Solar", "Localizar el Sol sin colocarlo en el centro galáctico.", "Conecta nuestra escala planetaria con la galáctica.",
            "Nuestra casa está en un barrio periférico, no en la plaza central.",
            "El Sistema Solar está en el espolón de Orión, entre los brazos de Sagitario y Perseo, a unos 26 000 años luz del centro. Orbita la galaxia en cientos de millones de años.",
            "Un año galáctico del Sol dura aproximadamente 230 millones de años.", "El Sol es una estrella del disco, lejos del centro de la Vía Láctea.",
            q("¿Está el Sol en el centro galáctico?", "No", "Sí", "Solo en invierno", 0, "Se encuentra en el disco, a gran distancia del núcleo.", "Vivimos en un barrio, no en la plaza central."),
            q("¿Qué es un año galáctico?", "Una órbita del Sol alrededor de la galaxia", "Una órbita lunar", "La edad del universo", 0, "Describe el largo recorrido solar por la Vía Láctea.", "Es el año medido por la vuelta de todo nuestro vecindario.")),
        s(5, "Galaxias espirales", "Relacionar disco, brazos y formación estelar.", "Son laboratorios de estructura y evolución galáctica.",
            "Un remolino con carriles de tráfico: el patrón gira, pero los autos entran y salen de cada carril.",
            "Las espirales tienen disco rotante, bulbo central y brazos ricos en gas. Sus brazos resaltan por estrellas jóvenes y regiones de formación estelar.",
            "M31, Andrómeda, es una gran espiral del Grupo Local.", "Los brazos espirales son patrones dinámicos, no estructuras rígidas.",
            q("¿Qué hace visibles los brazos?", "Estrellas jóvenes y gas", "Una pared sólida", "Ausencia de gravedad", 0, "La formación estelar ilumina el patrón.", "Los carriles resaltan cuando concentran tráfico luminoso."),
            q("¿Qué galaxia cercana es espiral?", "Andrómeda", "La Gran Nube de Magallanes", "Ninguna", 0, "Andrómeda es la gran espiral vecina.", "Es otro gran remolino del Grupo Local.")),
        s(6, "Galaxias elípticas", "Comparar galaxias elípticas con espirales.", "Muestran sistemas dominados por estrellas viejas y poco gas frío.",
            "Una nube redondeada de luciérnagas con pocos materiales para fabricar nuevas lámparas.",
            "Las elípticas van de casi esféricas a alargadas. Suelen tener poco gas frío, escasa formación estelar y movimientos estelares en muchas direcciones.",
            "Algunas elípticas gigantes ocupan centros de cúmulos galácticos.", "La forma suave no implica simplicidad: refleja otra historia dinámica.",
            q("¿Qué suele escasear en una elíptica?", "Gas frío", "Estrellas", "Gravedad", 0, "Sin mucho gas frío nacen pocas estrellas nuevas.", "La fábrica tiene muchas luces antiguas, pero poco material nuevo."),
            q("¿Cómo se mueven sus estrellas?", "En muchas direcciones", "En un único brazo", "Todas inmóviles", 0, "No domina un disco delgado y ordenado.", "La nube tiene trayectorias variadas.")),
        s(7, "Galaxias irregulares", "Reconocer sistemas sin forma espiral o elíptica clara.", "Las interacciones y el gas pueden producir morfologías complejas.",
            "Una ciudad remodelada por fuerzas externas pierde su plano simétrico.",
            "Las galaxias irregulares carecen de estructura global regular. Muchas son pequeñas, ricas en gas y activas formando estrellas; otras fueron deformadas por encuentros.",
            "Las Nubes de Magallanes son vecinas irregulares de la Vía Láctea.", "Irregular describe la forma observada, no ausencia de organización física.",
            q("¿Qué define a una galaxia irregular?", "No tiene forma regular clara", "No tiene estrellas", "No posee gravedad", 0, "No encaja bien como espiral o elíptica.", "Su plano urbano no sigue una simetría sencilla."),
            q("¿Qué vecinas son irregulares?", "Las Nubes de Magallanes", "Andrómeda y M87", "El Sol y la Luna", 0, "Son galaxias satélite cercanas.", "Acompañan a nuestra galaxia en el entorno local.")),
        s(8, "Galaxias activas", "Explicar el brillo de un núcleo galáctico activo.", "Revela acreción extrema alrededor de agujeros negros supermasivos.",
            "Un faro diminuto puede superar el brillo de toda la ciudad si convierte combustible con enorme eficiencia.",
            "Un núcleo galáctico activo se alimenta cuando materia cae hacia un agujero negro supermasivo. El disco caliente y, a veces, chorros relativistas emiten gran energía.",
            "Un cuásar es un núcleo activo tan luminoso que puede verse a enormes distancias.", "El brillo proviene de materia acelerada y calentada, no del interior del agujero negro.",
            q("¿Qué alimenta un núcleo activo?", "Materia en acreción", "Luz atrapada que escapa", "Planetas en combustión", 0, "El gas se calienta antes de cruzar el horizonte.", "El faro brilla por el combustible que cae a su alrededor."),
            q("¿Qué es un cuásar?", "Un núcleo galáctico muy luminoso", "Una luna", "Un tipo de cometa", 0, "Es una manifestación extrema de actividad nuclear.", "Es un faro galáctico visible desde muy lejos.")),
        s(9, "Grupos y cúmulos de galaxias", "Distinguir agrupaciones galácticas ligadas por gravedad.", "Las galaxias evolucionan dentro de ambientes mayores.",
            "Ciudades forman regiones metropolitanas pequeñas y, a otra escala, redes urbanas inmensas.",
            "Los grupos contienen decenas de galaxias; los cúmulos pueden reunir cientos o miles, gas muy caliente y abundante materia oscura.",
            "El gas intracúmulo emite rayos X por sus enormes temperaturas.", "Las galaxias no suelen estar aisladas: pertenecen a estructuras gravitacionales.",
            q("¿Qué suele ser mayor?", "Un cúmulo", "Un grupo", "Un sistema planetario", 0, "Los cúmulos reúnen muchas más galaxias.", "La red urbana supera a un vecindario."),
            q("¿Qué llena el espacio de un cúmulo?", "Gas muy caliente", "Aire respirable", "Agua líquida", 0, "El gas intracúmulo alcanza temperaturas de rayos X.", "Entre las ciudades hay un medio caliente, no vacío absoluto.")),
        s(10, "El Grupo Local", "Ubicar la Vía Láctea entre sus vecinas gravitacionales.", "Define nuestro entorno extragaláctico inmediato.",
            "Nuestro barrio incluye dos casas grandes y muchas viviendas pequeñas.",
            "El Grupo Local contiene la Vía Láctea, Andrómeda, Triángulo y numerosas galaxias enanas. La Vía Láctea y Andrómeda dominan gran parte de su masa visible.",
            "Andrómeda se aproxima y ambas galaxias probablemente se fusionarán dentro de miles de millones de años.", "El Grupo Local es una comunidad ligada, no una colección casual en el cielo.",
            q("¿Qué dos galaxias dominan el Grupo Local?", "Vía Láctea y Andrómeda", "Sol y Sirio", "Marte y Venus", 0, "Son sus grandes espirales principales.", "Son las dos casas mayores del barrio."),
            q("¿Andrómeda se acerca o se aleja?", "Se acerca", "Se aleja para siempre", "Está inmóvil", 0, "Su movimiento neto conduce a una futura interacción.", "Las dos grandes casas del barrio se aproximan.")),
        s(11, "Colisiones galácticas", "Explicar qué ocurre cuando interactúan galaxias.", "Las fusiones transforman formas, gas y formación estelar.",
            "Dos enjambres pueden atravesarse casi sin choques individuales, pero sus campos colectivos reorganizan todo.",
            "En una colisión galáctica las estrellas rara vez chocan por las enormes separaciones. La gravedad deforma discos, crea colas y comprime gas, mientras los sistemas pueden fusionarse.",
            "Las Galaxias Antena muestran colas de marea y brotes de formación estelar.", "Colisión galáctica significa interacción gravitatoria intensa, no choques frecuentes entre estrellas.",
            q("¿Chocan normalmente las estrellas entre sí?", "Casi nunca", "Siempre", "Solo las del halo", 0, "Las distancias entre estrellas son enormes.", "Los miembros de los enjambres tienen mucho espacio entre ellos."),
            q("¿Qué puede comprimir una interacción?", "El gas", "El tiempo", "La velocidad de la luz", 0, "El gas comprimido puede formar nuevas estrellas.", "La sacudida colectiva concentra material de construcción.")),
        s(12, "Formación y evolución galáctica", "Describir crecimiento por gas, estrellas y fusiones.", "Las galaxias cambian durante toda la historia cósmica.",
            "Una ciudad crece desde barrios pequeños, recibe materiales y se une con otras.",
            "Las primeras fluctuaciones de materia atrajeron gas y formaron estrellas. Acreción, retroalimentación estelar, actividad nuclear y fusiones regulan el crecimiento galáctico.",
            "Observar galaxias lejanas permite ver etapas tempranas porque su luz tardó miles de millones de años en llegar.", "La clasificación actual de una galaxia es una etapa de una historia evolutiva.",
            q("¿Cómo crecen las galaxias?", "Por acreción y fusiones", "Sin cambiar", "Solo creando planetas", 0, "Incorporan gas y sistemas menores.", "La ciudad recibe materiales y se integra con vecinas."),
            q("¿Por qué galaxias lejanas muestran el pasado?", "La luz tarda en llegar", "Viajan hacia atrás", "Los telescopios detienen el tiempo", 0, "Las vemos como eran cuando emitieron esa luz.", "La postal cósmica tarda muchísimo en entregarse.")),
        s(13, "Materia oscura en galaxias", "Relacionar curvas de rotación y masa invisible.", "Explica por qué la gravedad observada supera la materia luminosa.",
            "Si los corredores exteriores mantienen gran velocidad, quizá existe una pista invisible que aporta gravedad.",
            "Las estrellas y el gas lejos del centro orbitan más rápido de lo esperado por la materia visible. Un halo extenso de materia oscura explica gran parte de esa gravedad adicional.",
            "La lente gravitacional ofrece otra manera de mapear masa aunque no emita luz.", "Inferimos materia oscura por sus efectos gravitacionales, no porque la veamos brillar.",
            q("¿Qué evidencia aparece en discos galácticos?", "Curvas de rotación planas", "Ausencia de órbitas", "Brillo oscuro directo", 0, "Las regiones externas giran demasiado rápido para la masa visible.", "Los corredores lejanos no frenan como predecía la pista visible."),
            q("¿Cómo detectamos materia oscura?", "Por efectos gravitacionales", "Por su color negro", "Por sonido", 0, "Afecta movimientos y desvía luz.", "Reconocemos la infraestructura por lo que hace.")),
        s(14, "El agujero negro central", "Describir la evidencia de Sagitario A*.", "Conecta órbitas estelares con un objeto compacto supermasivo.",
            "Vemos hojas girar velozmente alrededor de un desagüe oculto y deducimos qué hay en el centro.",
            "Sagitario A* es el agujero negro supermasivo central de la Vía Láctea. Órbitas rápidas de estrellas cercanas revelan millones de masas solares concentradas en una región diminuta.",
            "Imágenes de radio muestran la sombra asociada al entorno inmediato de Sagitario A*.", "La mejor evidencia combina dinámica estelar y observación del plasma cercano.",
            q("¿Qué demuestra la masa central compacta?", "Órbitas de estrellas cercanas", "Las fases lunares", "Los brazos visibles", 0, "Sus velocidades requieren una enorme masa en poco espacio.", "Las hojas rápidas revelan la fuerza del desagüe oculto."),
            q("¿Cómo se llama el objeto central?", "Sagitario A*", "Andrómeda", "Próxima Centauri", 0, "Es la fuente compacta del centro galáctico.", "Es el nombre del corazón gravitatorio de nuestra galaxia."))
    )

    val practica = listOf(
        e(4, "El Sol completa una vuelta alrededor del centro galáctico. ¿Qué transcurrió?", "Un año galáctico", "Un mes lunar", "Un tránsito", "Una paralaje", 0, "Es una órbita del Sistema Solar por la Vía Láctea."),
        e(3, "Un espectro presenta líneas desplazadas al rojo. ¿Qué indica directamente?", "Alejamiento relativo", "Una galaxia sin estrellas", "Materia oscura visible", "Ausencia de movimiento", 0, "El efecto Doppler conecta desplazamiento espectral y movimiento."),
        e(8, "Un punto central supera el brillo de su galaxia y emite chorros. ¿Cuál es la explicación?", "Acreción en un núcleo activo", "Un planeta gigante", "Una sola estrella normal", "Reflejo lunar", 0, "La materia caliente alrededor de un agujero negro alimenta el núcleo."),
        e(10, "¿Qué galaxias grandes dominan nuestro grupo?", "Vía Láctea y Andrómeda", "Vía Láctea y Marte", "Andrómeda y el Sol", "Triángulo y la Luna", 0, "Son las dos grandes espirales del Grupo Local."),
        e(11, "Dos galaxias se atraviesan. ¿Qué es lo menos probable?", "Choques directos entre estrellas", "Colas de marea", "Compresión de gas", "Cambio de forma", 0, "Las estrellas están demasiado separadas para chocar con frecuencia."),
        e(13, "Las regiones externas giran más rápido de lo previsto. ¿Qué inferimos?", "Masa no visible adicional", "Ausencia de gravedad", "Que el disco es sólido", "Que toda la masa está en estrellas", 0, "Las curvas de rotación apoyan un halo de materia oscura."),
        e(14, "¿Qué observación pesa el objeto central de la Vía Láctea?", "Órbitas estelares cercanas", "Tránsitos de exoplanetas", "Cráteres lunares", "Auroras", 0, "La dinámica de estrellas cercanas mide la masa compacta.")
    )

    val examen = listOf(
        e(1, "¿Qué mantiene unida una galaxia?", "Gravedad", "Presión atmosférica", "Sonido", "Magnetismo terrestre", 0, "La gravedad liga estrellas, gas y materia oscura."),
        e(2, "¿Qué tipo de galaxia es la Vía Láctea?", "Espiral barrada", "Elíptica", "Irregular sin disco", "Cúmulo globular", 0, "Posee barra central y brazos en un disco."),
        e(3, "¿Dónde se concentra gran parte del gas y polvo?", "En el disco", "Solo en el halo", "Fuera del Grupo Local", "En una superficie sólida", 0, "El disco alberga brazos y regiones de formación estelar."),
        e(4, "¿Dónde está el Sistema Solar?", "En el disco, lejos del centro", "En el núcleo", "Fuera de la Vía Láctea", "En Andrómeda", 0, "Está en el espolón de Orión."),
        e(6, "¿Qué caracteriza a muchas galaxias elípticas?", "Poco gas frío", "Brazos azules definidos", "Solo una estrella", "Ausencia de masa", 0, "Suelen formar pocas estrellas nuevas."),
        e(8, "¿De dónde proviene la energía visible de un núcleo activo?", "De materia caliente en acreción", "Del interior visible del agujero negro", "De planetas", "De la Luna", 0, "El disco de acreción libera enorme energía."),
        e(10, "¿A qué estructura pertenece la Vía Láctea?", "Al Grupo Local", "Al Sistema Solar", "A un solo cúmulo estelar", "A la atmósfera", 0, "Comparte un grupo gravitacional con Andrómeda y otras galaxias."),
        e(11, "¿Qué puede desencadenar una colisión galáctica?", "Formación estelar por gas comprimido", "Choques de todas las estrellas", "Pérdida de gravedad", "Detención del tiempo", 0, "Las interacciones comprimen gas y reorganizan las galaxias."),
        e(13, "¿Qué apoyan las curvas de rotación galáctica?", "Un halo de materia oscura", "Un disco sólido", "La ausencia de masa", "Que no existen órbitas", 0, "La velocidad exterior requiere gravedad adicional."),
        e(14, "¿Qué objeto ocupa el centro de la Vía Láctea?", "Sagitario A*", "Andrómeda", "El Sol", "Júpiter", 0, "Sagitario A* es su agujero negro supermasivo central.")
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
