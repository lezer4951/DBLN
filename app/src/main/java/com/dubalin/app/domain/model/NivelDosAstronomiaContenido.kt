package com.dubalin.app.domain.model

object NivelDosAstronomiaContenido {
    val sesiones = listOf(
        s(1, "Forma y estructura de la Tierra", "Distinguir la forma real y las capas terrestres.", "La Tierra no es una esfera perfecta ni una roca uniforme.",
            "Como una mandarina ligeramente achatada y formada por capas.",
            "La Tierra es un esferoide oblato: está algo achatada en los polos. Su interior se divide en corteza, manto y núcleo; conocemos esas capas principalmente mediante ondas sísmicas.",
            "Las ondas de un terremoto cambian de velocidad y trayectoria al atravesar materiales distintos.", "La forma y el interior terrestre se determinan con mediciones, no solo con fotografías.",
            q("¿Qué forma describe mejor la Tierra?", "Esferoide oblato", "Disco plano", "Esfera perfecta", 0, "La rotación produce un ligero abultamiento ecuatorial.", "Piensa en una pelota flexible que gira y se ensancha un poco en su cintura."),
            q("¿Cómo estudiamos el interior profundo?", "Con ondas sísmicas", "Excavando hasta el núcleo", "Con horóscopos", 0, "Las ondas revelan cambios de material.", "Es parecido a usar una ecografía para estudiar algo que no podemos abrir.")),
        s(2, "Rotación y traslación", "Separar los dos movimientos principales de la Tierra.", "Explican el día y el año sin confundir sus causas.",
            "Una bailarina puede girar sobre sí misma mientras recorre el escenario.",
            "La rotación es el giro terrestre sobre su eje, de unas 24 horas. La traslación es su órbita alrededor del Sol, de aproximadamente 365.25 días.",
            "Vistos desde el polo norte, ambos movimientos principales ocurren en sentido antihorario.", "Rotación produce el ciclo diario; traslación define el año.",
            q("¿Qué movimiento dura aproximadamente 24 horas?", "Rotación", "Traslación", "Precesión anual", 0, "Es el giro sobre el eje.", "Es la vuelta de la Tierra como un trompo."),
            q("¿Qué movimiento define el año?", "Traslación", "Rotación", "Marea", 0, "Es una órbita completa alrededor del Sol.", "Es la vuelta de la bailarina por todo el escenario.")),
        s(3, "Día, noche y estaciones", "Explicar dos ciclos sin atribuir las estaciones a la distancia.", "Corrige uno de los errores astronómicos más comunes.",
            "Una lámpara ilumina media pelota; al girarla, cada zona entra y sale de la luz.",
            "La rotación causa día y noche. Las estaciones resultan de la inclinación del eje combinada con la traslación, que cambia el ángulo de iluminación y la duración del día.",
            "En enero la Tierra está cerca del perihelio y, aun así, es invierno en el hemisferio norte.", "Las estaciones dependen de la inclinación, no de estar más cerca o lejos del Sol.",
            q("¿Qué causa la noche?", "La zona gira fuera de la luz solar", "El Sol se apaga", "La Luna tapa siempre al Sol", 0, "La mitad opuesta al Sol queda en sombra.", "La lámpara sigue encendida; la pelota simplemente gira."),
            q("¿Qué causa principalmente las estaciones?", "Inclinación del eje", "Distancia al Sol", "Fases lunares", 0, "Cambia el ángulo solar y la duración del día.", "La misma linterna concentra más energía cuando ilumina de frente que en diagonal.")),
        s(4, "La inclinación terrestre", "Relacionar la inclinación de 23.5° con solsticios y equinoccios.", "Permite entender estaciones opuestas entre hemisferios.",
            "Una sombrilla inclinada recibe luz de manera desigual al rodear una lámpara.",
            "El eje terrestre mantiene una inclinación cercana a 23.5° respecto de la perpendicular orbital. En solsticios un hemisferio se inclina más hacia el Sol; en equinoccios ninguno tiene esa ventaja.",
            "Cuando es verano en el norte, es invierno en el sur.", "La inclinación distribuye de manera cambiante la energía solar durante el año.",
            q("¿Cuánto se inclina aproximadamente el eje?", "23.5°", "90°", "1°", 0, "Su oblicuidad ronda 23.5 grados.", "Es una inclinación moderada, no una Tierra acostada como Urano."),
            q("¿Qué ocurre en un equinoccio?", "Ningún hemisferio se inclina preferentemente al Sol", "La Tierra deja de girar", "La Luna desaparece", 0, "Día y noche tienen duración cercana a doce horas.", "La iluminación se reparte de forma casi equilibrada.")),
        s(5, "Origen y estructura de la Luna", "Describir el origen probable y las regiones lunares.", "La historia lunar está ligada a la historia temprana de la Tierra.",
            "Un gran choque puede lanzar fragmentos que luego vuelven a reunirse.",
            "La hipótesis más aceptada propone que un impacto gigante expulsó material que formó la Luna. Tiene corteza, manto y núcleo pequeño; sus tierras altas son antiguas y los mares son llanuras basálticas.",
            "Las muestras lunares comparten semejanzas isotópicas con rocas terrestres.", "La Luna es un mundo rocoso con una historia geológica registrada en cráteres.",
            q("¿Qué hipótesis explica mejor su origen?", "Impacto gigante", "Captura de una estrella", "Fragmento del Sol", 0, "Un gran choque temprano explica varias evidencias.", "Los restos del impacto formaron un anillo y después se agruparon."),
            q("¿Qué son los mares lunares?", "Llanuras de lava antigua", "Océanos actuales", "Nubes oscuras", 0, "Son basaltos solidificados.", "Los antiguos observadores vieron manchas y las llamaron mares, pero están secas.")),
        s(6, "Fases de la Luna", "Construir la geometría de las fases lunares.", "Evita pensar que la sombra terrestre produce cada fase.",
            "Una pelota siempre tiene media superficie iluminada; desde distintos lugares vemos porciones diferentes.",
            "La Luna refleja luz solar. Al orbitar la Tierra cambia la fracción iluminada visible: nueva, creciente, llena y menguante. El ciclo sinódico dura unos 29.5 días.",
            "En luna llena, la Tierra está aproximadamente entre el Sol y la Luna, pero normalmente no quedan perfectamente alineados.", "Las fases son perspectiva de iluminación, no eclipses cotidianos.",
            q("¿Qué produce las fases?", "La geometría Sol-Tierra-Luna", "La sombra terrestre cada semana", "Nubes lunares", 0, "Vemos distintas porciones de la mitad iluminada.", "La pelota no cambia; cambia cuánto de su lado iluminado puedes ver."),
            q("¿Cuánto dura el ciclo de fases?", "29.5 días", "24 horas", "365 días", 0, "Es el mes sinódico.", "Es un poco más largo que su vuelta respecto de las estrellas.")),
        s(7, "Eclipses solares", "Explicar tipos y condiciones de un eclipse solar.", "Permite observar alineaciones sin caer en mitos ni riesgos visuales.",
            "Una moneda cercana puede ocultar una lámpara lejana si queda justo frente a ella.",
            "Un eclipse solar ocurre en luna nueva cuando la Luna pasa entre la Tierra y el Sol cerca de un nodo orbital. Puede ser total, parcial o anular.",
            "La totalidad se observa desde una franja estrecha porque la umbra lunar es pequeña.", "Nunca se mira el Sol sin protección certificada, salvo durante la totalidad completa y breve.",
            q("¿En qué fase puede ocurrir?", "Luna nueva", "Luna llena", "Cuarto menguante", 0, "La Luna debe quedar entre Tierra y Sol.", "La moneda tiene que estar delante de la lámpara."),
            q("¿Por qué no ocurre cada mes?", "La órbita lunar está inclinada", "El Sol cambia de tamaño", "La Luna deja de orbitar", 0, "La alineación suele pasar por arriba o abajo.", "Dos pistas inclinadas solo se cruzan en puntos concretos.")),
        s(8, "Eclipses lunares", "Explicar cómo la Luna entra en la sombra terrestre.", "Diferencia este fenómeno de las fases normales.",
            "La Tierra funciona como una pantalla que bloquea la luz de la lámpara solar.",
            "Un eclipse lunar ocurre en luna llena cerca de un nodo, cuando la Luna atraviesa la penumbra o la umbra terrestre. La luz rojiza refractada por la atmósfera puede teñirla.",
            "Puede verse desde toda la mitad nocturna de la Tierra, una región mucho mayor que la franja de un eclipse solar.", "En un eclipse lunar sí interviene la sombra de la Tierra.",
            q("¿En qué fase ocurre?", "Luna llena", "Luna nueva", "Cualquier fase", 0, "La Tierra debe quedar entre Sol y Luna.", "La pantalla terrestre se coloca delante de la Luna."),
            q("¿Por qué puede verse roja?", "La atmósfera filtra y desvía luz rojiza", "La Luna produce fuego", "Marte la ilumina", 0, "La atmósfera dispersa más la luz azul.", "Es como reunir todos los amaneceres y atardeceres alrededor de la Tierra.")),
        s(9, "Las mareas", "Relacionar gravedad lunar, solar y rotación terrestre.", "Conecta la Astronomía con cambios diarios en los océanos.",
            "La gravedad estira el océano como si apretaras suavemente una pelota flexible por dos lados.",
            "La gravedad diferencial de la Luna produce dos abultamientos de marea; la rotación terrestre hace que muchas costas atraviesen ambos. El Sol refuerza o reduce el efecto según la alineación.",
            "Luna nueva y llena favorecen mareas vivas; los cuartos producen mareas muertas.", "Las mareas dependen de diferencias gravitatorias, no solo de un tirón hacia la Luna.",
            q("¿Qué cuerpo domina las mareas terrestres?", "La Luna", "Marte", "Venus", 0, "Su cercanía hace fuerte el efecto diferencial.", "La distancia importa mucho para la diferencia de tirón entre lados de la Tierra."),
            q("¿Cuándo ocurren mareas vivas?", "Luna nueva y llena", "Solo en cuarto creciente", "Cuando no hay Luna", 0, "Sol, Tierra y Luna están alineados.", "Los efectos solar y lunar se suman.")),
        s(10, "Coordenadas celestes", "Usar altitud, acimut, ascensión recta y declinación de forma básica.", "Permite indicar posiciones del cielo sin decir simplemente 'por allá'.",
            "Es una cuadrícula imaginaria sobre una esfera que rodea al observador.",
            "Altitud y acimut dependen del lugar y la hora. Ascensión recta y declinación forman una cuadrícula ligada a la esfera celeste, parecida a longitud y latitud.",
            "Una estrella conserva casi la misma declinación, aunque su altitud cambie durante la noche.", "Las coordenadas elegidas dependen de si queremos apuntar ahora o catalogar el cielo.",
            q("¿Qué coordenada mide altura sobre el horizonte?", "Altitud", "Declinación", "Longitud terrestre", 0, "Va de 0° en el horizonte a 90° en el cenit.", "Es cuánto debes levantar la mirada."),
            q("¿Qué sistema sirve para catálogos estelares?", "Ascensión recta y declinación", "Altitud y acimut fijos", "Código postal", 0, "Está ligado a la esfera celeste.", "Es la dirección astronómica equivalente a una dirección global.")),
        s(11, "Movimiento aparente del cielo", "Explicar por qué el cielo parece girar.", "Ayuda a planear observaciones y reconocer trayectorias.",
            "Desde un tren parece que el paisaje se mueve hacia atrás, aunque quien se mueve eres tú.",
            "La rotación terrestre hacia el este hace que astros parezcan salir por el este y ponerse por el oeste. Cerca de los polos celestes, las estrellas trazan círculos.",
            "Una exposición fotográfica larga muestra arcos estelares centrados aproximadamente en el polo celeste.", "El movimiento diario del cielo es principalmente reflejo de la rotación terrestre.",
            q("¿Por qué las estrellas parecen ir al oeste?", "La Tierra rota al este", "La galaxia gira cada día", "El Sol las empuja", 0, "Es movimiento aparente por nuestro giro.", "El paisaje parece retroceder cuando el tren avanza."),
            q("¿Qué estrella está cerca del polo norte celeste?", "Polaris", "Sirio", "Betelgeuse", 0, "Polaris cambia poco de posición durante la noche.", "Está cerca del eje imaginario alrededor del cual parece girar el cielo.")),
        s(12, "Constelaciones", "Distinguir patrones aparentes de agrupaciones físicas.", "Sirven para orientarse y dividir oficialmente el cielo.",
            "Desde lejos, varias farolas parecen formar una figura aunque estén a distancias diferentes.",
            "Una constelación moderna es una región oficial del cielo. Sus estrellas pueden no estar relacionadas físicamente; parecen próximas por proyección desde la Tierra.",
            "La Unión Astronómica Internacional reconoce 88 constelaciones que cubren toda la esfera celeste.", "Una constelación es un mapa visual, no necesariamente una familia de estrellas.",
            q("¿Las estrellas de una constelación están siempre juntas?", "No", "Sí", "Solo en invierno", 0, "Pueden hallarse a distancias muy diferentes.", "Las farolas alineadas desde tu ventana pueden estar en calles distintas."),
            q("¿Cuántas constelaciones oficiales hay?", "88", "12", "365", 0, "Las 88 regiones cubren todo el cielo.", "El zodiaco es solo una franja del mapa completo.")),
        s(13, "Contaminación lumínica", "Reconocer tipos, consecuencias y soluciones.", "Proteger el cielo también beneficia salud, fauna y energía.",
            "Intentar ver una vela mientras alguien apunta una linterna a tus ojos.",
            "La luz artificial mal dirigida crea resplandor celeste, deslumbramiento e intrusión lumínica. Oculta objetos débiles y altera ritmos biológicos.",
            "Luminarias apantalladas, cálidas, reguladas y usadas solo cuando se necesitan reducen el problema.", "Iluminar mejor no significa iluminar más.",
            q("¿Qué luminaria reduce el resplandor?", "Apantallada hacia abajo", "Dirigida al cielo", "Azul muy intensa", 0, "Evita enviar luz inútil hacia arriba.", "La luz debe llegar al suelo que queremos ver, no escaparse al cielo."),
            q("¿A quién afecta además de astrónomos?", "Fauna y personas", "A nadie", "Solo a satélites", 0, "Altera ciclos naturales y sueño.", "La noche es un ambiente biológico, no solo un fondo para telescopios.")),
        s(14, "Orientación con el cielo", "Usar referencias celestes sin reemplazar instrumentos de seguridad.", "Integra movimiento, coordenadas y constelaciones en una habilidad práctica.",
            "El cielo es un reloj y una brújula aproximados que cambian con lugar y estación.",
            "En el hemisferio norte, Polaris indica aproximadamente el norte y su altura se aproxima a la latitud. En el sur, la Cruz del Sur ayuda a estimar el polo celeste sur.",
            "El Sol sale aproximadamente por el este y se pone por el oeste, pero los puntos exactos cambian durante el año.", "El cielo permite orientación aproximada; para navegación crítica se usan instrumentos y mapas.",
            q("¿Qué indica Polaris aproximadamente?", "Norte", "Sur", "Este", 0, "Está cerca del polo norte celeste.", "Es como la marca casi fija en el centro del carrusel celeste."),
            q("¿El Sol sale siempre exactamente por el este?", "No", "Sí", "Solo en el polo", 0, "Ocurre aproximadamente en los equinoccios.", "El punto de salida se desplaza con las estaciones."))
    )

    val practica = listOf(
        e(3, "Es enero y la Tierra está cerca del perihelio. ¿Por qué es invierno en el hemisferio norte?", "Está inclinado alejándose del Sol", "La Tierra está demasiado lejos", "El Sol produce menos energía", "La Luna bloquea calor", 0, "La inclinación reduce el ángulo solar y acorta los días."),
        e(6, "Ves media cara lunar iluminada y aumentando cada noche. ¿Qué fase es?", "Cuarto creciente", "Luna nueva", "Luna llena", "Eclipse", 0, "La fracción visible aumenta durante la fase creciente."),
        e(7, "Hay luna nueva, pero no eclipse. ¿Qué explicación es correcta?", "La Luna pasó fuera de un nodo", "El Sol se apagó", "La Tierra no tiene sombra", "La Luna dejó de orbitar", 0, "La inclinación orbital evita la alineación exacta la mayoría de los meses."),
        e(9, "Sol, Tierra y Luna están alineados. ¿Qué mareas se esperan?", "Vivas", "Muertas", "Ninguna", "Solo atmosféricas", 0, "Los efectos gravitatorios se refuerzan."),
        e(11, "Una estrella parece desplazarse al oeste durante la noche. La causa principal es…", "Rotación terrestre hacia el este", "Expansión del universo", "Órbita lunar", "Viento solar", 0, "Nuestro giro genera el movimiento diario aparente."),
        e(1, "Repaso Nivel 0: ¿qué hace científica una explicación?", "Puede contrastarse con evidencia", "Usa palabras complejas", "Nunca cambia", "Es popular", 0, "La comprobación con evidencia sigue siendo el criterio central."),
        e(12, "Repaso Nivel 1: un planeta se acerca al Sol en su órbita. Según Kepler, su velocidad…", "Aumenta", "Disminuye", "Se anula", "No cambia jamás", 0, "La segunda ley implica mayor rapidez cerca del Sol.")
    )

    val examen = listOf(
        e(1, "¿Cómo sabemos que la Tierra tiene capas internas?", "Por ondas sísmicas", "Por observación directa del núcleo", "Por fases lunares", "Por constelaciones", 0, "Las ondas cambian al cruzar materiales distintos."),
        e(2, "¿Qué movimiento terrestre produce el día y la noche?", "Rotación", "Traslación", "Precesión", "Marea", 0, "El giro sobre el eje alterna iluminación y sombra."),
        e(3, "¿Cuál es la causa principal de las estaciones?", "Inclinación del eje", "Distancia al Sol", "Fases lunares", "Manchas solares", 0, "Cambia el ángulo de incidencia y la duración del día."),
        e(6, "¿Qué causa las fases lunares?", "La geometría de iluminación", "La sombra terrestre cada mes", "Nubes lunares", "Cambios de tamaño", 0, "Vemos distintas fracciones del hemisferio iluminado."),
        e(7, "¿Cuándo puede ocurrir un eclipse solar?", "Luna nueva cerca de un nodo", "Luna llena lejos de un nodo", "Cualquier noche", "Cuarto creciente", 0, "La Luna debe alinearse entre Tierra y Sol."),
        e(8, "¿Por qué la Luna puede verse roja durante un eclipse?", "La atmósfera terrestre filtra la luz", "Produce luz roja", "Marte la ilumina", "Pierde su superficie", 0, "La luz rojiza atraviesa y se refracta en la atmósfera."),
        e(9, "¿Cuándo son más intensas las mareas vivas?", "Luna nueva y llena", "Cuartos", "Solo en verano", "Sin Sol", 0, "Las fuerzas solar y lunar se alinean."),
        e(10, "¿Qué coordenada mide altura sobre el horizonte?", "Altitud", "Ascensión recta", "Longitud", "Declinación", 0, "La altitud va del horizonte al cenit."),
        e(12, "¿Qué es una constelación moderna?", "Una región oficial del cielo", "Un grupo siempre unido físicamente", "Un solo sistema solar", "Una galaxia", 0, "Las 88 constelaciones dividen toda la esfera celeste."),
        e(14, "¿Qué referencia señala aproximadamente el norte celeste?", "Polaris", "Sirio", "Venus", "La Luna llena", 0, "Polaris está cerca del polo norte celeste.")
    )

    const val mascaraCompleta = (1 shl 14) - 1
    fun completado(mascara: Int, indice: Int) = mascara and (1 shl indice) != 0

    private fun s(numero: Int, titulo: String, objetivo: String, importa: String, analogia: String,
        explicacion: String, ejemplo: String, clave: String, vararg preguntas: PreguntaAutoevaluacion
    ) = SesionAstronomia(numero, titulo, objetivo, importa, 28, analogia, explicacion, ejemplo, clave, preguntas.toList())
    private fun q(enunciado: String, a: String, b: String, c: String, correcta: Int, explicacion: String, alternativa: String) =
        PreguntaAutoevaluacion(enunciado, listOf(a, b, c), correcta, explicacion, alternativa)
    private fun e(tema: Int, enunciado: String, a: String, b: String, c: String, d: String, correcta: Int, explicacion: String) =
        PreguntaAstronomia(tema, enunciado, listOf(a, b, c, d), correcta, explicacion)
}
