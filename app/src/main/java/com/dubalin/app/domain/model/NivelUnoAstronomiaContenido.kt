package com.dubalin.app.domain.model

object NivelUnoAstronomiaContenido {
    val sesiones = listOf(
        s(1, "Cómo nació el Sistema Solar", "Explicar el origen común del Sol y los planetas.", "Conecta la composición y las órbitas de todos los mundos.",
            "Imagina una masa de pizza girando: al aplanarse, casi todo queda en el mismo plano.",
            "Hace unos 4,600 millones de años una nube de gas y polvo colapsó por gravedad. El centro formó el Sol y el disco restante produjo planetesimales y planetas por acreción.",
            "En un disco giratorio, los granos chocan y se adhieren hasta formar cuerpos mayores.", "El Sistema Solar se formó de una misma nebulosa en rotación.",
            q("¿Qué proceso unió polvo en cuerpos mayores?", "Acreción", "Evaporación", "Erosión", 0, "La acreción acumula material mediante choques y gravedad.", "Como una bola de nieve que crece al rodar y recoger más nieve."),
            q("¿Por qué muchas órbitas comparten un plano?", "Se formaron en un disco", "El Sol es plano", "Las estrellas las ordenaron", 0, "El disco protoplanetario dejó esa geometría.", "Una masa que gira tiende a extenderse como un disco.")),
        s(2, "El Sol, nuestra estrella", "Reconocer la función del Sol en el sistema.", "Contiene casi toda la masa y suministra energía.",
            "El Sol es como el ancla pesada de un carrusel: su gravedad organiza el movimiento.",
            "El Sol contiene cerca del 99.8% de la masa del Sistema Solar. En su núcleo, la fusión convierte hidrógeno en helio y libera energía.",
            "La luz solar tarda unos ocho minutos en llegar a la Tierra.", "El Sol es una estrella y el centro gravitatorio dominante.",
            q("¿Qué mantiene a los planetas en órbita?", "La gravedad solar", "El viento terrestre", "La luz lunar", 0, "La gravedad solar curva su trayectoria.", "Sin el tirón hacia el centro, continuarían en línea recta."),
            q("¿De dónde viene la energía solar?", "Fusión nuclear", "Combustión", "Electricidad planetaria", 0, "La fusión une núcleos ligeros.", "No es una fogata: el núcleo transforma materia y libera energía.")),
        s(3, "Los planetas rocosos", "Comparar Mercurio, Venus, Tierra y Marte.", "Son los mundos sólidos más cercanos al Sol.",
            "Son cuatro panes horneados con la misma harina, pero con tamaños y ambientes distintos.",
            "Los planetas terrestres son pequeños, densos y compuestos principalmente por roca y metal. Tienen superficies sólidas y pocos satélites.",
            "Venus es el más caliente por su intenso efecto invernadero, aunque Mercurio está más cerca del Sol.", "Cercanía al Sol no es lo único que controla la temperatura.",
            q("¿Cuál rasgo comparten los planetas rocosos?", "Superficie sólida", "Anillos enormes", "Composición de hidrógeno", 0, "Predominan roca y metal.", "Son las 'canicas densas' del vecindario interior."),
            q("¿Por qué Venus supera a Mercurio en temperatura?", "Efecto invernadero", "Tiene dos soles", "Carece de gravedad", 0, "Su atmósfera retiene calor eficazmente.", "Como un automóvil cerrado al Sol, su atmósfera dificulta que escape el calor.")),
        s(4, "Los gigantes gaseosos", "Distinguir Júpiter y Saturno.", "Dominan por tamaño, lunas y sistemas de anillos.",
            "Imagina dos enormes remolinos sin un suelo sólido donde pararte.",
            "Júpiter y Saturno están formados sobre todo por hidrógeno y helio. Poseen núcleos internos, atmósferas profundas, muchas lunas y anillos.",
            "La Gran Mancha Roja de Júpiter es una tormenta mayor que la Tierra.", "Gigante gaseoso no significa una nube ligera: son planetas masivos.",
            q("¿Qué elementos dominan en Júpiter y Saturno?", "Hidrógeno y helio", "Hierro y granito", "Oxígeno líquido", 0, "Su composición recuerda a la del Sol.", "Son grandes depósitos de los elementos más ligeros."),
            q("¿Solo Saturno tiene anillos?", "No", "Sí", "Solo de noche", 0, "Los cuatro planetas gigantes tienen anillos.", "Saturno luce los más visibles, pero no tiene la exclusiva.")),
        s(5, "Los gigantes helados", "Diferenciar Urano y Neptuno de los gigantes gaseosos.", "Amplía la clasificación planetaria más allá de tamaño y color.",
            "Dos bebidas pueden verse parecidas, pero una contiene ingredientes en proporciones muy distintas.",
            "Urano y Neptuno contienen mayor proporción de agua, amoníaco y metano en sus interiores que Júpiter y Saturno. Por eso se clasifican como gigantes helados.",
            "El metano atmosférico absorbe luz roja y contribuye a sus tonos azulados.", "'Helado' describe composición, no una bola completamente congelada.",
            q("¿Qué distingue a los gigantes helados?", "Mayor proporción de compuestos volátiles", "Superficie rocosa visible", "Ausencia de atmósfera", 0, "Sus interiores son ricos en agua, amoníaco y metano.", "La etiqueta habla de ingredientes interiores, no solo de temperatura."),
            q("¿Qué planeta gira muy inclinado?", "Urano", "Mercurio", "Júpiter", 0, "El eje de Urano está inclinado casi de lado.", "Parece una pelota que rueda alrededor del Sol.")),
        s(6, "Planetas enanos", "Aplicar la definición de planeta enano.", "Aclara por qué Plutón cambió de categoría.",
            "En una pista, no basta correr: también hay que cumplir todas las reglas de la categoría.",
            "Un planeta enano orbita al Sol, es casi redondo y no es satélite, pero no ha limpiado gravitatoriamente su vecindad orbital.",
            "Plutón comparte su región con muchos objetos del cinturón de Kuiper.", "Planeta enano es una categoría científica, no un insulto cósmico.",
            q("¿Qué requisito no cumple Plutón como planeta?", "Limpiar su vecindad orbital", "Orbitar al Sol", "Ser casi redondo", 0, "Comparte su zona con otros objetos.", "No domina gravitatoriamente su carril orbital."),
            q("¿Cuál es un planeta enano?", "Ceres", "Europa", "Titán", 0, "Ceres orbita al Sol en el cinturón principal.", "Europa y Titán orbitan planetas; son lunas.")),
        s(7, "Satélites naturales", "Comprender la diversidad de las lunas.", "Algunas son candidatos importantes para estudiar habitabilidad.",
            "Los planetas son como ciudades y las lunas como barrios que los acompañan en su viaje.",
            "Un satélite natural orbita un planeta o cuerpo menor. Puede formarse junto a él, ser capturado o surgir de un gran impacto.",
            "Europa y Encélado probablemente albergan océanos bajo capas de hielo.", "Las lunas son mundos complejos, no simples adornos.",
            q("¿Qué define a una luna?", "Orbita otro cuerpo no estelar", "Produce luz propia", "Siempre tiene atmósfera", 0, "Es un satélite natural.", "Su característica clave es a quién orbita."),
            q("¿Qué luna tiene mares de metano?", "Titán", "Fobos", "La Luna", 0, "Titán posee lagos y mares de hidrocarburos.", "Es un ciclo parecido al del agua terrestre, pero con metano.")),
        s(8, "Asteroides", "Ubicar y describir los asteroides.", "Son restos de la formación planetaria y posibles riesgos de impacto.",
            "Son ladrillos que quedaron sin incorporarse a una casa planetaria.",
            "Los asteroides son cuerpos rocosos o metálicos. Muchos se concentran en el cinturón entre Marte y Júpiter, aunque también existen troyanos y objetos cercanos a la Tierra.",
            "El cinturón principal está muy disperso; atravesarlo no equivale a esquivar rocas continuamente.", "Los asteroides conservan material antiguo del Sistema Solar.",
            q("¿Dónde está el cinturón principal?", "Entre Marte y Júpiter", "Después de la Nube de Oort", "Entre Tierra y Luna", 0, "Ocupa la región entre ambos planetas.", "Es una zona ancha, no una pared compacta."),
            q("¿Todos los asteroides amenazan la Tierra?", "No", "Sí", "Solo los grandes orbitan", 0, "Solo ciertas órbitas se acercan a la terrestre.", "Estar en la misma ciudad no significa pasar por la misma calle.")),
        s(9, "Cometas", "Explicar su composición y actividad.", "Revelan materiales conservados de regiones frías.",
            "Un cometa es como una bola de nieve polvorienta que despierta al acercarse a una estufa.",
            "Los cometas contienen hielos, polvo y roca. Cerca del Sol, los hielos subliman y forman una coma y colas empujadas por radiación y viento solar.",
            "Las colas apuntan aproximadamente en dirección opuesta al Sol, no necesariamente detrás del movimiento.", "La cola depende del Sol, no de la dirección de viaje.",
            q("¿Qué activa a un cometa?", "Calor solar", "Sombra terrestre", "Gravedad lunar", 0, "El calentamiento sublima hielos.", "Al acercarse al horno solar, libera gas y polvo."),
            q("¿Hacia dónde apunta la cola?", "Lejos del Sol", "Siempre hacia su órbita anterior", "Hacia Júpiter", 0, "Radiación y viento solar la orientan.", "El Sol sopla el material hacia afuera.")),
        s(10, "El cinturón de Kuiper", "Describir la región transneptuniana.", "Es hogar de Plutón y muchos cuerpos helados.",
            "Es un suburbio ancho más allá de la última gran avenida planetaria, Neptuno.",
            "El cinturón de Kuiper es una región en forma de disco más allá de Neptuno, poblada por objetos helados y planetas enanos.",
            "Plutón, Haumea y Makemake pertenecen a esta población transneptuniana.", "El cinturón de Kuiper es distante, pero sigue siendo parte del sistema planetario.",
            q("¿Dónde comienza el cinturón de Kuiper?", "Más allá de Neptuno", "Dentro del Sol", "Entre Venus y Tierra", 0, "Se extiende en la región transneptuniana.", "Está después del último planeta principal."),
            q("¿Qué cuerpo pertenece a él?", "Plutón", "La Luna", "Mercurio", 0, "Plutón es un objeto del cinturón de Kuiper.", "Su vecindario explica por qué no domina su órbita.")),
        s(11, "La Nube de Oort", "Diferenciar una región inferida del cinturón de Kuiper.", "Explica el origen probable de cometas de periodo largo.",
            "Imagina una cáscara esférica lejanísima rodeando el Sistema Solar.",
            "La Nube de Oort es una reserva hipotética, aproximadamente esférica, de cuerpos helados muy distantes. Se infiere por las órbitas de cometas de periodo largo.",
            "No ha sido fotografiada como conjunto; su existencia es un modelo respaldado indirectamente.", "Una inferencia científica puede ser sólida sin una fotografía directa.",
            q("¿Qué forma se propone para la Nube de Oort?", "Aproximadamente esférica", "Un anillo junto a Saturno", "Una línea", 0, "Rodearía al sistema en muchas direcciones.", "A diferencia del disco de Kuiper, sería una envoltura."),
            q("¿Qué evidencia la sugiere?", "Órbitas de cometas largos", "Eclipses solares", "Manchas de Júpiter", 0, "Sus trayectorias parecen venir de una reserva distante.", "Reconstruimos el origen siguiendo las rutas de quienes llegan.")),
        s(12, "Órbitas y leyes de Kepler", "Interpretar las tres leyes de Kepler sin cálculo avanzado.", "Permite predecir cómo se mueven los planetas.",
            "Una patinadora se mueve más rápido cuando recoge los brazos; un planeta acelera al acercarse al Sol.",
            "Las órbitas son elipses con el Sol en un foco; el radio vector barre áreas iguales en tiempos iguales; y el periodo crece con el tamaño de la órbita según P² proporcional a a³.",
            "La Tierra viaja ligeramente más rápido en perihelio que en afelio.", "Una órbita combina caída gravitatoria y movimiento hacia adelante.",
            q("¿Qué forma tienen las órbitas planetarias?", "Elipses", "Cuadrados", "Espirales hacia el Sol", 0, "La primera ley describe elipses.", "Un círculo es un caso especial de elipse."),
            q("¿Cuándo se mueve más rápido un planeta?", "Cerca del Sol", "Lejos del Sol", "Siempre igual", 0, "La segunda ley implica mayor velocidad cerca del perihelio.", "La gravedad tira con más intensidad cuando la distancia es menor.")),
        s(13, "La escala del Sistema Solar", "Construir un mapa mental de distancias y tiempos.", "Evita la imagen falsa de planetas apretados unos junto a otros.",
            "Si el Sol fuera una pelota, Neptuno estaría a varios kilómetros en un modelo fiel.",
            "Las distancias crecen enormemente hacia el exterior. La luz tarda minutos en llegar a los planetas interiores y horas en alcanzar los confines planetarios.",
            "A 1 UA está la Tierra; Neptuno se encuentra aproximadamente a 30 UA del Sol.", "El Sistema Solar es, sobre todo, espacio vacío.",
            q("¿A cuántas UA está aproximadamente Neptuno?", "30", "3", "3000", 0, "Su distancia media ronda 30 UA.", "La Tierra marca 1; Neptuno está unas treinta veces más lejos."),
            q("¿Qué ocupa la mayor parte del Sistema Solar?", "Espacio vacío", "Planetas sólidos", "La cola de cometas", 0, "Los cuerpos son diminutos comparados con sus separaciones.", "En una maqueta fiel caminarías mucho entre objetos minúsculos."))
    )

    val practica = listOf(
        e(3, "Venus está más cerca del Sol que la Tierra y posee una atmósfera densa de CO₂. ¿Qué explica mejor su calor extremo?", "Efecto invernadero intenso", "Dos soles", "Falta de gravedad", "Su tamaño", 0, "La atmósfera retiene eficientemente radiación infrarroja."),
        e(6, "Un cuerpo orbita al Sol, es redondo y comparte su zona con muchos objetos. ¿Cómo se clasifica?", "Planeta enano", "Luna", "Estrella", "Galaxia", 0, "No ha limpiado su vecindad orbital."),
        e(9, "Un cometa viaja hacia el Sol. ¿Hacia dónde apunta su cola?", "Lejos del Sol", "Siempre detrás de su movimiento", "Hacia la Tierra", "Hacia Júpiter", 0, "La radiación y el viento solar dominan la orientación."),
        e(12, "Un planeta se acerca al perihelio. ¿Qué ocurre con su velocidad?", "Aumenta", "Disminuye", "Se vuelve cero", "No puede saberse", 0, "La segunda ley de Kepler exige que barra áreas iguales."),
        e(13, "Una señal tarda más de cuatro horas en ir y volver a una nave. ¿Qué conclusión es razonable?", "Está en el Sistema Solar exterior", "Está en la Luna", "Está dentro del Sol", "Viaja más rápido que la luz", 0, "Las comunicaciones con regiones exteriores tienen retrasos de horas.")
    )

    val examen = listOf(
        e(1, "¿Qué modelo explica el origen común del Sistema Solar?", "Nebulosa en rotación", "Choque de galaxias reciente", "Explosión terrestre", "Captura de todos los planetas", 0, "El modelo nebular explica el disco y la acreción."),
        e(2, "¿Dónde se produce la energía del Sol?", "En el núcleo por fusión", "En la superficie por fuego", "En los planetas", "En sus manchas", 0, "La fusión de hidrógeno ocurre en el núcleo."),
        e(3, "¿Cuál es un planeta rocoso?", "Marte", "Saturno", "Neptuno", "Júpiter", 0, "Marte pertenece al grupo terrestre."),
        e(5, "¿Cuál es un gigante helado?", "Urano", "Venus", "Saturno", "Mercurio", 0, "Urano y Neptuno son gigantes helados."),
        e(6, "¿Por qué Plutón es planeta enano?", "No domina su vecindad orbital", "No orbita al Sol", "No es redondo", "Es una luna", 0, "Comparte su región con otros objetos."),
        e(8, "¿Dónde está el cinturón principal de asteroides?", "Entre Marte y Júpiter", "Más allá de Oort", "Entre Sol y Mercurio", "Alrededor de la Tierra", 0, "Ocupa la región entre Marte y Júpiter."),
        e(10, "¿Qué región contiene a Plutón?", "Cinturón de Kuiper", "Nube de Venus", "Cinturón principal", "Corona solar", 0, "Plutón es transneptuniano."),
        e(11, "¿Qué objetos sugieren la Nube de Oort?", "Cometas de periodo largo", "Lunas galileanas", "Meteoritos marcianos", "Anillos de Saturno", 0, "Sus órbitas apuntan a una reserva distante."),
        e(12, "Según Kepler, un planeta cerca del Sol se mueve…", "Más rápido", "Más lento", "En línea recta", "Sin gravedad", 0, "Barre áreas iguales en tiempos iguales."),
        e(13, "¿Qué significa que Neptuno esté a unas 30 UA?", "Está unas 30 veces más lejos del Sol que la Tierra", "Tiene 30 lunas", "Tarda 30 días en orbitar", "Mide 30 Tierras", 0, "La UA usa la distancia Tierra-Sol como referencia.")
    )

    const val mascaraCompleta = (1 shl 13) - 1
    fun completado(mascara: Int, indice: Int) = mascara and (1 shl indice) != 0

    private fun s(numero: Int, titulo: String, objetivo: String, importa: String, analogia: String,
        explicacion: String, ejemplo: String, clave: String, vararg preguntas: PreguntaAutoevaluacion
    ) = SesionAstronomia(numero, titulo, objetivo, importa, 28, analogia, explicacion, ejemplo, clave, preguntas.toList())

    private fun q(enunciado: String, a: String, b: String, c: String, correcta: Int, explicacion: String, alternativa: String) =
        PreguntaAutoevaluacion(enunciado, listOf(a, b, c), correcta, explicacion, alternativa)

    private fun e(tema: Int, enunciado: String, a: String, b: String, c: String, d: String, correcta: Int, explicacion: String) =
        PreguntaAstronomia(tema, enunciado, listOf(a, b, c, d), correcta, explicacion)
}
