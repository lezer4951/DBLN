package com.dubalin.app.domain.model

object NivelSeisAstronomiaContenido {
    val sesiones = listOf(
        s(1, "Nebulosas y nubes moleculares", "Identificar los reservorios donde nacen estrellas.", "Conecta el gas galáctico con una nueva generación estelar.",
            "Una nube molecular es como un vivero oscuro: parece quieto, pero guarda el material de muchas vidas nuevas.",
            "Las nubes moleculares son regiones frías y densas de gas y polvo. El polvo bloquea luz visible, mientras observaciones infrarrojas y de radio revelan moléculas y estrellas jóvenes.",
            "Los Pilares de la Creación son columnas de gas erosionadas por estrellas cercanas.", "Las estrellas nacen en las zonas frías y densas del medio interestelar.",
            q("¿Dónde nacen estrellas?", "En nubes moleculares", "En planetas", "En el vacío perfecto", 0, "El gas frío puede concentrarse hasta colapsar.", "El vivero necesita material antes de formar una estrella."),
            q("¿Qué atraviesa mejor el polvo?", "La radiación infrarroja", "Solo luz azul", "El sonido", 0, "Longitudes de onda largas permiten estudiar regiones ocultas.", "Una cámara distinta deja mirar dentro del vivero oscuro.")),
        s(2, "Colapso gravitacional", "Explicar cómo comienza la formación estelar.", "La gravedad transforma una nube dispersa en objetos compactos.",
            "Una pequeña depresión en una sábana atrae más canicas y se vuelve cada vez más profunda.",
            "Si una región de la nube acumula suficiente masa, la gravedad supera presión y turbulencia. El fragmento colapsa, se densifica y convierte energía gravitatoria en calor.",
            "Una onda de choque o colisión de nubes puede comprimir gas y favorecer el colapso.", "El nacimiento estelar comienza cuando la gravedad vence el soporte interno.",
            q("¿Qué fuerza impulsa el colapso?", "La gravedad", "El magnetismo terrestre", "La luz lunar", 0, "La masa atrae más material hacia el centro.", "La depresión reúne cada vez más canicas."),
            q("¿Qué ocurre al contraerse el gas?", "Se calienta", "Pierde toda energía", "Se vuelve planeta de inmediato", 0, "Parte de la energía gravitatoria se convierte en calor.", "Comprimir el material eleva su temperatura.")),
        s(3, "Protoestrellas y discos", "Describir la etapa previa a la fusión estable.", "Explica el origen común de estrella y sistema planetario.",
            "Antes de encender un motor, combustible y piezas giran y se organizan alrededor del centro.",
            "Una protoestrella es un objeto joven que aún obtiene energía principalmente de su contracción. El material con momento angular forma un disco de acreción del que pueden surgir planetas.",
            "Chorros bipolares expulsan parte del material y regulan el crecimiento.", "Una protoestrella todavía no es una estrella estable de secuencia principal.",
            q("¿Qué alimenta primero una protoestrella?", "Contracción gravitatoria", "Fisión de uranio", "Luz reflejada", 0, "La fusión estable aún no domina.", "El motor todavía se prepara y calienta."),
            q("¿Por qué aparece un disco?", "Por conservación del momento angular", "Porque no existe gravedad", "Por la atmósfera terrestre", 0, "La rotación aplana el material que cae.", "La materia giratoria se organiza alrededor del centro.")),
        s(4, "Secuencia principal", "Definir la etapa estable más larga de una estrella.", "El Sol se encuentra actualmente en esta fase.",
            "Es la vida adulta de una estrella: mantiene un trabajo estable durante la mayor parte de su existencia.",
            "Una estrella entra en secuencia principal cuando fusiona hidrógeno en su núcleo de forma sostenida. Su masa determina temperatura, luminosidad y ubicación en el diagrama H–R.",
            "El Sol lleva aproximadamente 4 600 millones de años en esta etapa.", "Secuencia principal significa equilibrio con fusión de hidrógeno central.",
            q("¿Qué fusiona el núcleo en esta etapa?", "Hidrógeno", "Hierro", "Planetas", 0, "El hidrógeno se transforma principalmente en helio.", "Es el combustible estable de la vida adulta estelar."),
            q("¿Está el Sol en secuencia principal?", "Sí", "No, es una protoestrella", "No, es una enana blanca", 0, "Su núcleo mantiene fusión estable de hidrógeno.", "Nuestra estrella atraviesa su larga etapa adulta.")),
        s(5, "Equilibrio hidrostático", "Relacionar gravedad y presión interna.", "Explica por qué una estrella estable no colapsa ni se dispersa.",
            "Dos equipos tiran de una cuerda con la misma fuerza: hay tensión, pero el centro no se desplaza.",
            "La gravedad tira hacia dentro y el gradiente de presión empuja hacia fuera. En equilibrio hidrostático ambas tendencias se compensan capa por capa.",
            "Si cambia la producción de energía, la estrella se reajusta hasta alcanzar otro equilibrio.", "Una estrella estable es un equilibrio dinámico, no una esfera inmóvil.",
            q("¿Qué tira hacia dentro?", "La gravedad", "La presión térmica", "La radiación exterior", 0, "La masa estelar atrae sus capas al centro.", "Es uno de los equipos de la cuerda."),
            q("¿Qué se opone al colapso?", "La presión interna", "El vacío", "Las fases lunares", 0, "El gas caliente y la radiación sostienen las capas.", "El otro equipo compensa la atracción.")),
        s(6, "La masa decide el destino", "Usar la masa inicial para anticipar evolución y duración.", "Es la variable principal de la biografía estelar.",
            "Un motor grande consume combustible mucho más rápido aunque tenga un tanque mayor.",
            "Las estrellas masivas tienen núcleos más calientes, fusionan con rapidez, brillan intensamente y viven menos. Las de baja masa consumen combustible lentamente y pueden durar muchísimo.",
            "Una estrella con varias masas solares vive mucho menos que el Sol.", "Más masa suele significar más brillo, mayor temperatura central y menor vida.",
            q("¿Qué estrella vive menos?", "La más masiva", "La menos masiva", "Todas igual", 0, "Su consumo de combustible crece enormemente.", "El motor potente vacía antes su tanque."),
            q("¿Qué predice mejor el destino estelar?", "La masa inicial", "El nombre", "La constelación aparente", 0, "La masa controla presión, temperatura y fusiones posibles.", "La biografía depende del tamaño del motor.")),
        s(7, "Gigantes rojas", "Explicar la expansión de estrellas de masa solar.", "Muestra cómo cambia una estrella al agotar hidrógeno central.",
            "Cuando se agota el combustible del centro, el motor cambia de configuración y la envoltura se infla.",
            "Al disminuir el hidrógeno del núcleo, este se contrae y calienta. La fusión continúa en una capa; las regiones externas se expanden y enfrían, formando una gigante roja.",
            "En esta fase puede comenzar la fusión de helio y producir carbono y oxígeno.", "Roja describe una superficie más fría; gigante, un radio mucho mayor.",
            q("¿Por qué se vuelve gigante?", "Se expanden sus capas externas", "Aumenta la gravedad terrestre", "Se acerca a nosotros", 0, "El reajuste interno infla la envoltura.", "El motor cambia y la cubierta se expande."),
            q("¿Su superficie es más caliente?", "No, es más fría", "Sí, siempre", "No tiene temperatura", 0, "Por eso su color se desplaza hacia el rojo.", "Una superficie extensa puede ser más fría aunque la estrella sea luminosa.")),
        s(8, "Nebulosas planetarias", "Describir la expulsión de capas de una estrella de baja masa.", "Conecta el final solar con el enriquecimiento interestelar.",
            "Una estrella envejecida deja escapar su envoltura y revela el núcleo caliente, como quitar capas de una lámpara.",
            "Una estrella similar al Sol expulsa sus capas externas. El núcleo caliente ioniza ese gas y crea una nebulosa planetaria; el nombre histórico no implica relación con planetas.",
            "Estas nebulosas duran poco a escala astronómica y muestran formas complejas.", "Una nebulosa planetaria es gas expulsado iluminado por un núcleo estelar caliente.",
            q("¿Proviene de un planeta?", "No", "Sí", "Solo de Júpiter", 0, "El nombre surgió por su aspecto en telescopios antiguos.", "La etiqueta histórica no describe su origen real."),
            q("¿Qué ilumina el gas expulsado?", "El núcleo caliente", "La Luna", "Un agujero negro lejano", 0, "Su radiación ioniza la envoltura.", "La lámpara central hace brillar las capas liberadas.")),
        s(9, "Enanas blancas", "Caracterizar el remanente de una estrella de masa baja o intermedia.", "Describe el probable destino final del Sol.",
            "Una brasa pequeña conserva calor después de que termina el fuego principal.",
            "Una enana blanca es un núcleo compacto sin fusión sostenida, sostenido por presión de degeneración de electrones. Tiene masa comparable al Sol en un volumen parecido al de la Tierra.",
            "Se enfría lentamente durante tiempos enormes; no es una estrella pequeña que siga quemando combustible normal.", "Una enana blanca brilla por calor residual y no por fusión central estable.",
            q("¿Qué sostiene una enana blanca?", "Presión de degeneración electrónica", "Fusión de hidrógeno estable", "Una corteza planetaria", 0, "La física cuántica impide una compresión ilimitada.", "La brasa compacta encuentra un soporte distinto al fuego."),
            q("¿Cuál es el destino probable del Sol?", "Una enana blanca", "Una supernova de colapso", "Un planeta", 0, "El Sol no posee masa suficiente para el final de una estrella masiva.", "Nuestro motor terminará como una brasa compacta.")),
        s(10, "Supergigantes y fusión avanzada", "Explicar las capas de fusión en estrellas masivas.", "Prepara el escenario para supernovas y elementos pesados.",
            "Una cebolla tiene capas distintas; una estrella masiva envejecida fusiona combustibles diferentes a distintas profundidades.",
            "Las estrellas masivas pueden fusionar helio, carbono, neón, oxígeno y silicio. Forman una estructura en capas hasta producir hierro en el núcleo.",
            "Cada etapa avanzada dura menos que la anterior porque exige condiciones extremas.", "La fusión hasta hierro libera energía; fusionar hierro ya no sostiene eficazmente la estrella.",
            q("¿Qué se acumula finalmente en el núcleo?", "Hierro", "Hidrógeno puro", "Agua", 0, "La cadena de fusiones avanzadas llega al grupo del hierro.", "La cebolla estelar termina con un centro que ya no aporta energía útil."),
            q("¿Las etapas avanzadas duran más?", "No, duran menos", "Sí, siempre", "Todas duran igual", 0, "El combustible se consume muy deprisa.", "Cada nueva fase del motor es más intensa y breve.")),
        s(11, "Supernovas de colapso", "Explicar la explosión final de una estrella masiva.", "Distribuye elementos y deja remanentes compactos.",
            "Cuando falla el soporte central de un edificio, el interior cae y el rebote sacude las capas externas.",
            "Al crecer un núcleo de hierro, ya no obtiene energía de la fusión. El núcleo colapsa, se forma un objeto compacto y una onda expulsa gran parte de la estrella como supernova.",
            "Durante días una supernova puede rivalizar con el brillo de toda su galaxia.", "La explosión sigue a un colapso central rápido, no a una combustión química.",
            q("¿Qué desencadena el colapso?", "Un núcleo de hierro sin soporte suficiente", "La rotación terrestre", "Una lluvia de meteoros", 0, "La fusión deja de sostener eficazmente el núcleo.", "La columna central pierde su capacidad de soporte."),
            q("¿Es una explosión química?", "No", "Sí", "Solo contiene oxígeno", 0, "Intervienen gravedad, física nuclear y neutrinos.", "Es mucho más profunda que quemar combustible químico.")),
        s(12, "Estrellas de neutrones y púlsares", "Describir un remanente ultracompacto.", "Lleva materia y campos magnéticos a condiciones extremas.",
            "Comprimir una masa mayor que la solar hasta el tamaño de una ciudad crea una peonza cósmica extrema.",
            "Una estrella de neutrones puede quedar tras una supernova. Su densidad es enorme; rota rápidamente y posee campos magnéticos intensos. Si sus haces cruzan la Tierra, observamos un púlsar.",
            "Los pulsos no son explosiones repetidas: son un efecto de faro por rotación.", "Un púlsar es una estrella de neutrones cuyo haz barre nuestra línea de visión.",
            q("¿Qué causa los pulsos?", "La rotación de haces", "Explosiones periódicas", "Eclipses lunares", 0, "El haz actúa como un faro.", "La lámpara gira; no se enciende desde cero en cada vuelta."),
            q("¿Qué tamaño puede tener?", "El de una ciudad", "El de una galaxia", "Menor que un átomo", 0, "Concentra masa estelar en decenas de kilómetros.", "La peonza es pequeña y extremadamente densa.")),
        s(13, "Agujeros negros estelares", "Relacionar masa del núcleo y colapso sin superficie sólida.", "Distingue estos objetos del agujero negro supermasivo galáctico.",
            "Si la pendiente gravitatoria se vuelve demasiado profunda, ni la luz puede subir más allá de cierto límite.",
            "Algunos núcleos masivos colapsan hasta formar agujeros negros. El horizonte de sucesos marca la frontera desde la que no escapan señales; no es una superficie material.",
            "En sistemas binarios pueden detectarse por el movimiento de una compañera, rayos X o ondas gravitacionales.", "Detectamos agujeros negros estelares por sus efectos, no por ver su interior.",
            q("¿Qué es el horizonte?", "Una frontera causal", "Una superficie rocosa", "Una nube brillante", 0, "Más allá no pueden regresar señales.", "Es el punto sin retorno de la pendiente."),
            q("¿Cómo se detecta uno?", "Por sus efectos gravitatorios", "Por luz de su interior", "Por sonido en el espacio", 0, "Influye en materia, estrellas y espacio-tiempo cercanos.", "Aunque el centro no se vea, su huella sí puede medirse.")),
        s(14, "Origen de los elementos", "Relacionar estrellas y explosiones con la química cósmica.", "Explica de dónde provienen muchos átomos de planetas y seres vivos.",
            "Las estrellas son cocinas cósmicas; distintas recetas y explosiones fabrican y dispersan ingredientes.",
            "La fusión estelar produce elementos hasta el hierro. Supernovas y fusiones de estrellas de neutrones crean y dispersan muchos núcleos más pesados mediante captura de neutrones.",
            "El calcio de los huesos y el hierro de la sangre proceden de generaciones estelares anteriores.", "Somos materia reciclada: la evolución estelar enriquece el medio interestelar.",
            q("¿Dónde se produce carbono?", "En estrellas", "Solo en planetas", "En el vacío sin energía", 0, "La fusión de helio puede formar carbono.", "La cocina estelar prepara ese ingrediente."),
            q("¿Qué dispersa elementos?", "Vientos y explosiones estelares", "Solo la gravedad terrestre", "Las fases lunares", 0, "Devuelven material enriquecido al espacio.", "La cocina reparte sus ingredientes a nuevas nubes."))
    )

    val practica = listOf(
        e(1, "Una región es invisible en luz visible pero brillante en infrarrojo. ¿Qué podría ocultar?", "Formación estelar entre polvo", "Un vacío perfecto", "Solo planetas fríos", "Ausencia de materia", 0, "El infrarrojo atraviesa mejor el polvo de las nubes."),
        e(3, "Gas cae hacia un objeto joven con disco y chorros, sin fusión estable. ¿Qué es?", "Una protoestrella", "Una enana blanca", "Una galaxia elíptica", "Un púlsar", 0, "La contracción y acreción dominan antes de la secuencia principal."),
        e(6, "Dos estrellas nacen juntas; una es mucho más masiva. ¿Cuál abandona antes la secuencia principal?", "La más masiva", "La menos masiva", "Ambas a la vez", "Ninguna", 0, "La estrella masiva consume combustible mucho más rápido."),
        e(8, "Se observa gas ionizado alrededor de un núcleo caliente de baja masa. ¿Qué es?", "Una nebulosa planetaria", "Un planeta gaseoso", "Un brazo galáctico", "Materia oscura", 0, "Son capas expulsadas por una estrella evolucionada."),
        e(11, "Una estrella masiva desarrolla un núcleo de hierro. ¿Qué puede seguir?", "Colapso y supernova", "Regreso a protoestrella", "Formación de continentes", "Una órbita galáctica", 0, "El hierro ya no sostiene el núcleo mediante fusión útil."),
        e(12, "Pulsos de radio llegan con regularidad extrema. ¿Cuál es una explicación?", "Un haz de una estrella de neutrones rotante", "Explosiones químicas", "Fases de Venus", "Una nube inmóvil", 0, "Un púlsar funciona como un faro cósmico."),
        e(14, "¿Qué conecta evolución estelar con planetas rocosos?", "La producción y dispersión de elementos", "La ausencia de fusión", "El sonido de supernovas", "La materia sin gravedad", 0, "Generaciones estelares enriquecen las nubes que forman planetas.")
    )

    val examen = listOf(
        e(1, "¿Qué región es un vivero estelar?", "Una nube molecular", "Una enana blanca", "Un planeta", "Un horizonte", 0, "El gas frío y denso proporciona material para formar estrellas."),
        e(2, "¿Qué inicia la formación de una estrella?", "El colapso gravitacional", "Una reacción química", "La luz lunar", "Una órbita planetaria", 0, "La gravedad vence el soporte de una región de la nube."),
        e(3, "¿Qué caracteriza a una protoestrella?", "Aún domina la contracción", "Fusiona hierro establemente", "No tiene gravedad", "Es un remanente muerto", 0, "Todavía no alcanza la secuencia principal estable."),
        e(4, "¿Qué fusiona una estrella de secuencia principal?", "Hidrógeno en el núcleo", "Hierro en la superficie", "Planetas", "Materia oscura", 0, "La fusión central de hidrógeno define esa etapa."),
        e(5, "¿Qué equilibra la gravedad en una estrella estable?", "La presión interna", "El sonido", "La Luna", "El vacío", 0, "El gradiente de presión sostiene las capas."),
        e(6, "¿Qué estrella suele vivir menos?", "La más masiva", "La menos masiva", "La más cercana", "Todas igual", 0, "Las masivas consumen combustible con enorme rapidez."),
        e(9, "¿Qué remanente dejará probablemente el Sol?", "Una enana blanca", "Una estrella de neutrones", "Un cuásar", "Una galaxia", 0, "No tiene masa para una supernova de colapso."),
        e(11, "¿Qué ocurre cuando colapsa el núcleo de hierro de una estrella masiva?", "Puede producir una supernova", "Nace un planeta", "La estrella vuelve a ser nube fría", "Se detiene la gravedad", 0, "El colapso impulsa la explosión y forma un remanente compacto."),
        e(12, "¿Qué es un púlsar?", "Una estrella de neutrones con un haz observable", "Una estrella recién nacida", "Un planeta pulsante", "Una nebulosa oscura", 0, "Su rotación barre el haz por nuestra línea de visión."),
        e(14, "¿De dónde procede gran parte del hierro de nuestro entorno?", "De generaciones estelares anteriores", "De la atmósfera terrestre", "De la luz solar directa", "Del vacío", 0, "La nucleosíntesis y las explosiones enriquecieron el material del Sistema Solar.")
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
