package com.dubalin.app.domain.model

object NivelOchoAstronomiaContenido {
    val sesiones = listOf(
        s(1, "Qué estudia la cosmología", "Definir el universo como sistema físico observable.", "Cambia la escala desde objetos concretos hasta la historia del cosmos.",
            "En vez de estudiar una casa, la cosmología investiga todo el vecindario, su origen y cómo cambia.",
            "La cosmología estudia el origen, composición, evolución y estructura a gran escala del universo mediante modelos físicos y observaciones astronómicas.",
            "Un modelo cosmológico debe explicar simultáneamente expansión, fondo cósmico y distribución de galaxias.", "Cosmología no es especulación libre: contrasta modelos globales con varias evidencias.",
            q("¿Qué estudia la cosmología?", "La evolución del universo", "Solo planetas", "Constelaciones culturales", 0, "Integra la historia y estructura cósmicas.", "Observa el vecindario completo y su pasado."),
            q("¿Cómo se evalúa un modelo?", "Con observaciones independientes", "Por popularidad", "Con una sola imagen", 0, "Debe predecir distintos fenómenos medibles.", "Un plano útil debe coincidir con muchas partes del vecindario.")),
        s(2, "El universo observable", "Distinguir universo total y región observable.", "Evita afirmar que vemos todo lo existente.",
            "Desde un barco solo ves hasta el horizonte, aunque el océano continúe.",
            "La luz viaja a velocidad finita y el universo tiene una edad finita. Por eso solo recibimos señales de una región causalmente conectada con nosotros.",
            "La expansión hace que el radio observable actual sea mayor que la distancia recorrida simplemente como edad por velocidad de la luz.", "Observable no significa total ni implica que ocupemos un centro especial.",
            q("¿Vemos todo el universo?", "No necesariamente", "Sí", "Solo de noche", 0, "Existe un horizonte cosmológico.", "Nuestro horizonte no marca el borde del océano."),
            q("¿Estamos en un centro especial?", "No hay evidencia de ello", "Sí", "La Tierra es el borde", 0, "Cada observador tiene su propia región observable.", "Todo barco ve un horizonte alrededor de sí.")),
        s(3, "Escala y homogeneidad", "Interpretar el principio cosmológico.", "Permite construir modelos globales manejables.",
            "Una pared se ve rugosa de cerca, pero uniforme al observarla desde lejos.",
            "A escalas enormes el universo es aproximadamente homogéneo e isótropo: tiene propiedades estadísticas similares entre lugares y direcciones, aunque localmente existan galaxias y vacíos.",
            "Mapas de galaxias muestran una red compleja que se vuelve uniforme al promediar volúmenes muy grandes.", "Uniformidad estadística a gran escala no significa ausencia de estructura local.",
            q("¿Es uniforme a toda escala?", "No", "Sí, perfectamente", "Solo en galaxias", 0, "Hay estructura local y uniformidad al promediar grandes escalas.", "La pared conserva rugosidades cercanas."),
            q("¿Qué significa isotropía?", "Propiedades similares en distintas direcciones", "Ausencia de gravedad", "Que todo está quieto", 0, "No observamos una dirección cósmica privilegiada a gran escala.", "La pared luce parecida al mirar hacia distintos lados.")),
        s(4, "Corrimiento al rojo cosmológico", "Relacionar expansión y longitud de onda.", "Es una de las medidas esenciales de cosmología observacional.",
            "Una cuadrícula elástica se estira y también alarga una onda dibujada sobre ella.",
            "Durante su viaje, la luz de galaxias lejanas se estira con la expansión del espacio. Sus líneas espectrales aparecen desplazadas hacia longitudes de onda mayores.",
            "A distancias pequeñas, el movimiento peculiar puede sumarse o restarse al patrón general.", "El corrimiento cosmológico mide expansión acumulada, no una explosión desde un punto central.",
            q("¿Qué se estira con la expansión?", "La longitud de onda", "La velocidad local de la luz", "Los átomos ligados", 0, "La escala cósmica cambia durante el viaje.", "La onda sobre la cuadrícula se alarga."),
            q("¿Toda galaxia cercana se aleja?", "No", "Sí", "Ninguna", 0, "Movimientos locales pueden dominar, como Andrómeda.", "El flujo general admite movimientos dentro del vecindario.")),
        s(5, "Ley de Hubble–Lemaître", "Interpretar la relación entre distancia y recesión.", "Cuantifica la expansión actual.",
            "Al estirar una liga marcada, puntos más separados aumentan su distancia más rápido.",
            "En el régimen cercano, la velocidad de recesión es aproximadamente proporcional a la distancia. La constante de Hubble expresa la tasa de expansión presente.",
            "No existe un centro de expansión dentro del espacio en este modelo; todas las regiones lejanas observan un patrón semejante.", "Más distancia implica, en promedio, mayor recesión por expansión del espacio.",
            q("¿Qué galaxia tiene mayor recesión media?", "La más distante", "La más cercana", "Todas igual", 0, "La relación aproximada es proporcional.", "En la liga, los puntos separados se distancian más por unidad de tiempo."),
            q("¿Dónde está el centro de la expansión?", "No está dentro del espacio observado", "En la Tierra", "En el Sol", 0, "La expansión ocurre entre regiones del espacio.", "La liga se estira en todos sus tramos.")),
        s(6, "El modelo del Big Bang", "Describir un universo temprano caliente y denso.", "Reemplaza la idea incorrecta de una explosión dentro de un vacío.",
            "Una película reproducida hacia atrás muestra la masa más junta y más caliente, no viajando hacia un punto del escenario.",
            "El Big Bang describe la evolución desde un estado temprano extremadamente caliente y denso. Es expansión del espacio en todas partes, no una explosión desde un lugar preexistente.",
            "El modelo describe con éxito épocas tempranas, pero no establece por sí solo qué ocurrió en un instante inicial absoluto.", "Big Bang es un modelo de evolución cósmica, no una bomba con centro.",
            q("¿Fue una explosión desde un punto del espacio?", "No", "Sí", "Ocurrió en la Vía Láctea", 0, "El propio espacio se expande.", "Toda la película cambia de escala."),
            q("¿Describe con certeza un instante cero?", "No", "Sí por completo", "No describe el pasado", 0, "Las teorías actuales tienen límites en condiciones extremas.", "La película observada no garantiza conocer su primer fotograma.")),
        s(7, "Nucleosíntesis primordial", "Explicar el origen temprano de núcleos ligeros.", "Aporta una prueba cuantitativa del universo caliente.",
            "Durante pocos minutos hubo una cocina cósmica con tiempo para recetas simples.",
            "Al enfriarse el universo temprano se formaron principalmente núcleos de hidrógeno y helio, con pequeñas cantidades de deuterio y litio. La expansión detuvo pronto esas reacciones.",
            "Las abundancias observadas concuerdan ampliamente con cálculos basados en física nuclear.", "El Big Bang produjo sobre todo elementos ligeros; las estrellas fabricarían muchos de los demás.",
            q("¿Qué elemento abundante se formó entonces?", "Helio", "Hierro", "Uranio", 0, "La nucleosíntesis primordial favoreció núcleos ligeros.", "La cocina breve alcanzó recetas sencillas."),
            q("¿Dónde se formó mucho hierro?", "En generaciones estelares", "En el Big Bang temprano", "En el vacío", 0, "Los elementos pesados requieren procesos posteriores.", "La primera cocina cerró antes de esas recetas complejas.")),
        s(8, "Fondo cósmico de microondas", "Interpretar la luz fósil del universo temprano.", "Es una de las evidencias más precisas del modelo cosmológico.",
            "Una fotografía infantil conserva información del universo cuando se volvió transparente.",
            "Unos 380 000 años después del Big Bang, electrones y núcleos formaron átomos y la luz pudo viajar libremente. La expansión estiró esa radiación hasta microondas.",
            "Pequeñas variaciones de temperatura registran semillas de estructuras futuras.", "El fondo cósmico es luz antigua enfriada por expansión, no radiación de estrellas cercanas.",
            q("¿Cuándo se liberó esa luz?", "Cuando el universo se volvió transparente", "Al formarse la Tierra", "Durante una supernova", 0, "La formación de átomos permitió que viajara libremente.", "Es la fotografía de una infancia cósmica."),
            q("¿Por qué hoy son microondas?", "Por la expansión", "Porque perdió velocidad", "Por la atmósfera", 0, "Sus longitudes de onda se estiraron.", "La fotografía se desplazó a una escala más larga.")),
        s(9, "Inflación cósmica", "Presentar una expansión temprana acelerada como hipótesis física.", "Explica rasgos del universo y genera predicciones comprobables.",
            "Una arruga microscópica estirada enormemente puede convertirse en una ondulación de gran escala.",
            "La inflación propone una etapa brevísima de expansión acelerada muy temprana. Ayuda a explicar uniformidad, geometría casi plana y el origen cuántico de perturbaciones iniciales.",
            "Existen muchos modelos inflacionarios y no todos sus detalles están confirmados.", "Inflación es un marco respaldado por predicciones exitosas, pero conserva preguntas abiertas.",
            q("¿Qué etapa propone?", "Expansión acelerada muy temprana", "Contracción actual", "Formación del Sol", 0, "Ocurriría antes del plasma descrito por el Big Bang caliente.", "La arruga se estira en una fracción diminuta de tiempo."),
            q("¿Están confirmados todos sus detalles?", "No", "Sí", "No produce predicciones", 0, "Diferentes mecanismos siguen bajo estudio.", "Conocemos rasgos de la expansión, no toda la maquinaria.")),
        s(10, "Formación de estructura", "Explicar cómo pequeñas fluctuaciones crecieron por gravedad.", "Conecta el fondo cósmico con galaxias y cúmulos.",
            "Pequeñas acumulaciones atraen más material y terminan formando una red de nodos y filamentos.",
            "Variaciones iniciales de densidad crecieron bajo gravedad. La materia oscura formó halos y filamentos; el gas cayó en ellos y produjo estrellas, galaxias y cúmulos.",
            "Simulaciones comparadas con mapas de galaxias prueban modelos de crecimiento.", "La red cósmica amplifica semillas tempranas mediante gravedad durante miles de millones de años.",
            q("¿Qué amplifica las fluctuaciones?", "La gravedad", "El sonido", "La luz lunar", 0, "Las regiones densas atraen más materia.", "Los pequeños montones siguen acumulando material."),
            q("¿Qué forma primero una red de soporte?", "La materia oscura", "Los planetas", "La atmósfera", 0, "Sus halos guían la caída del gas.", "Es el andamiaje invisible de la red.")),
        s(11, "Materia oscura cosmológica", "Integrar sus evidencias en varias escalas.", "Explica rotación, lentes, cúmulos y crecimiento de estructura.",
            "Un andamio invisible guía la forma de un edificio aunque no forme sus paredes luminosas.",
            "La materia oscura interactúa gravitacionalmente y representa más materia que la ordinaria. Curvas de rotación, lentes, cúmulos, fondo cósmico y estructura apoyan su presencia.",
            "Aún no se conoce con certeza qué partícula o fenómeno la compone.", "Tenemos evidencia convergente de su gravedad, pero su naturaleza microscópica sigue abierta.",
            q("¿Cómo la detectamos?", "Por efectos gravitacionales", "Por brillo directo", "Por sonido", 0, "No se observa como materia luminosa común.", "Reconocemos el andamio por la estructura que sostiene."),
            q("¿Conocemos su partícula?", "No", "Sí con certeza", "Es polvo", 0, "Su identidad sigue siendo investigación activa.", "Vemos la función del material, no sabemos aún de qué está hecho.")),
        s(12, "Energía oscura", "Relacionar supernovas lejanas y expansión acelerada.", "Describe el componente dominante del modelo actual y su gran incertidumbre.",
            "Una pelota lanzada cuesta arriba empieza a separarse cada vez más rápido: algo cambia el comportamiento esperado.",
            "Observaciones de supernovas tipo Ia y otras sondas indican que la expansión cósmica se acelera. Energía oscura es el nombre del componente o efecto responsable en el modelo estándar.",
            "La constante cosmológica es la explicación más simple, pero su naturaleza fundamental no se comprende.", "Energía oscura nombra una evidencia dinámica; no significa que sepamos qué sustancia es.",
            q("¿Qué hace la expansión actualmente?", "Se acelera", "Se detuvo", "Siempre se contrae", 0, "Varias observaciones favorecen aceleración tardía.", "Las separaciones crecen de una forma inesperadamente acelerada."),
            q("¿Se conoce su naturaleza?", "No", "Sí por completo", "Es materia negra", 0, "El nombre resume un efecto aún no explicado fundamentalmente.", "Medimos el comportamiento sin conocer toda su causa.")),
        s(13, "Edad e historia cósmica", "Ordenar las principales épocas del universo.", "Integra procesos antes estudiados por separado.",
            "Un calendario cósmico organiza una historia de 13 800 millones de años.",
            "Tras etapas muy tempranas llegaron nucleosíntesis, recombinación, edades oscuras, primeras estrellas, galaxias y expansión acelerada. La edad se obtiene ajustando observaciones a un modelo consistente.",
            "Mirar lejos es mirar atrás porque la luz tarda en llegar.", "La edad cósmica surge de varias medidas coherentes, no de un único reloj.",
            q("¿Cuál es la edad aproximada?", "13 800 millones de años", "4 600 millones", "100 mil años", 0, "Ese valor concuerda con el modelo cosmológico actual.", "El calendario cósmico es mucho más antiguo que el Sistema Solar."),
            q("¿Por qué ver lejos es ver el pasado?", "La luz tarda en viajar", "El tiempo se invierte", "Los telescopios inventan imágenes", 0, "Recibimos señales emitidas hace mucho.", "Las postales más lejanas tardan más en llegar.")),
        s(14, "Geometría, destino y preguntas abiertas", "Distinguir resultados medidos de escenarios futuros.", "Cierra el nivel con incertidumbre científica explícita.",
            "Conocer la forma de una carretera y cómo acelera ayuda a proyectar el viaje, pero el destino depende del motor.",
            "Las observaciones indican geometría espacial cercana a plana. Si la energía oscura se comporta como constante cosmológica, la expansión continuará y las galaxias no ligadas se alejarán cada vez más.",
            "La naturaleza de materia oscura, energía oscura, inflación y gravedad cuántica permanece abierta.", "El modelo actual es muy exitoso y a la vez incompleto en sus componentes fundamentales.",
            q("¿Qué geometría favorecen los datos?", "Casi plana", "Una esfera pequeña", "Ningún espacio", 0, "El fondo cósmico restringe fuertemente la curvatura.", "La carretera cósmica parece plana a gran escala."),
            q("¿Está completa la cosmología?", "No", "Sí", "No tiene evidencias", 0, "Persisten preguntas fundamentales bien definidas.", "Un mapa preciso todavía puede contener regiones sin explicar."))
    )

    val practica = listOf(
        e(4, "Las líneas de una galaxia lejana aparecen estiradas hacia el rojo. ¿Qué revela a escala cósmica?", "Expansión durante el viaje de la luz", "Que la luz se detuvo", "Una superficie sólida", "Ausencia de gravedad", 0, "La longitud de onda crece con la escala del universo."),
        e(6, "¿Cuál representación del Big Bang es correcta?", "El espacio se expandió en todas partes", "Materia explotó desde la Tierra", "Una estrella creó todas las galaxias", "Ocurrió dentro de un vacío central", 0, "No existe un centro espacial de la expansión."),
        e(8, "Se miden pequeñas variaciones en microondas por todo el cielo. ¿Qué observamos?", "Semillas del fondo cósmico", "Luz de planetas", "Radiación de Hawking", "Una aurora", 0, "Son huellas del universo cuando se volvió transparente."),
        e(10, "¿Cómo crecieron galaxias desde fluctuaciones pequeñas?", "Por gravedad sobre materia oscura y gas", "Sin evolución", "Por explosiones químicas", "Solo por agujeros negros", 0, "Las sobredensidades atrajeron progresivamente más materia."),
        e(11, "Rotación, lentes y cúmulos requieren masa adicional. ¿Qué componente se infiere?", "Materia oscura", "Energía estelar", "Polvo visible", "Radiación solar", 0, "Múltiples pruebas revelan gravedad no explicada por materia luminosa."),
        e(12, "Supernovas tipo Ia lejanas resultan más tenues de lo previsto. ¿Qué apoyan?", "Expansión acelerada", "Contracción actual", "Un universo inmóvil", "Ausencia de distancia", 0, "Su relación distancia-corrimiento reveló aceleración tardía."),
        e(13, "Una galaxia se observa como era hace miles de millones de años. ¿Por qué?", "Su luz tardó en llegar", "Retrocedió en el tiempo", "El telescopio la modificó", "Está dentro del Sistema Solar", 0, "La observación astronómica siempre mira al pasado.")
    )

    val examen = listOf(
        e(1, "¿Qué debe hacer un modelo cosmológico?", "Explicar varias observaciones independientes", "Basarse en una imagen", "Ignorar la física", "Describir solo planetas", 0, "La convergencia de pruebas restringe los modelos."),
        e(2, "¿Universo observable significa universo total?", "No", "Sí", "Solo si es plano", "Solo desde la Tierra", 0, "Nuestro horizonte limita las señales recibidas."),
        e(3, "¿Qué significa homogeneidad a gran escala?", "Propiedades estadísticas semejantes al promediar", "Ausencia de galaxias", "Todo es idéntico localmente", "Nada se mueve", 0, "La estructura local se suaviza en escalas enormes."),
        e(5, "¿Qué relaciona la ley de Hubble–Lemaître?", "Distancia y recesión", "Masa y temperatura estelar", "Radio y masa planetaria", "Edad y color lunar", 0, "La recesión media aumenta con distancia."),
        e(6, "¿Qué describe el Big Bang?", "Un universo temprano caliente y denso en expansión", "Una explosión desde un centro", "El nacimiento del Sol", "Una supernova", 0, "Es evolución del espacio y su contenido."),
        e(7, "¿Qué produjo la nucleosíntesis primordial?", "Principalmente núcleos ligeros", "Todos los elementos pesados", "Galaxias completas", "Agujeros negros", 0, "Se formaron sobre todo hidrógeno y helio."),
        e(8, "¿Qué es el fondo cósmico?", "Luz fósil del universo temprano", "Radiación del Sol", "Ondas de una fusión reciente", "Luz de un planeta", 0, "Fue liberado cuando el universo se volvió transparente."),
        e(10, "¿Qué papel cumple la materia oscura?", "Forma andamiaje gravitacional", "Produce toda la luz", "Detiene la gravedad", "Crea átomos por sí sola", 0, "Sus halos ayudan al crecimiento de estructura."),
        e(12, "¿Qué indica la evidencia sobre la expansión actual?", "Se acelera", "Está detenida", "Se contrae rápidamente", "No puede medirse", 0, "Supernovas y otras sondas apoyan aceleración."),
        e(14, "¿Cuál es el estado del modelo cosmológico?", "Exitoso pero con componentes fundamentales abiertos", "Completo sin preguntas", "Sin apoyo observacional", "Solo filosófico", 0, "Explica muchos datos aunque materia y energía oscuras sigan sin identificar.")
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
