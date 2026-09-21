package com.dubalin.app.domain.model

object NivelNueveAstronomiaContenido {
    val sesiones = listOf(
        s(1, "Por qué exploramos", "Distinguir objetivos científicos, tecnológicos y humanos.", "Permite evaluar una misión por sus preguntas y resultados.",
            "Una expedición necesita una pregunta clara antes de elegir vehículo y equipaje.",
            "La exploración espacial combina ciencia básica, observación de la Tierra, desarrollo tecnológico, cooperación y preparación para destinos futuros. Cada misión traduce objetivos en mediciones concretas.",
            "Una sonda puede buscar composición, cartografiar terreno o probar una técnica de navegación.", "Una misión exitosa comienza con objetivos medibles, no solo con llegar lejos.",
            q("¿Qué define primero una misión?", "Sus objetivos", "Su color", "El nombre del cohete", 0, "Los objetivos determinan instrumentos y trayectoria.", "La pregunta decide el equipaje de la expedición."),
            q("¿Explorar sirve solo para viajar?", "No", "Sí", "Solo si hay astronautas", 0, "También produce ciencia y tecnología.", "Una expedición puede aprender sin llevar personas.")),
        s(2, "Cohetes y propulsión", "Aplicar conservación del momento al movimiento espacial.", "Explica cómo despega y maniobra una nave.",
            "Al lanzar masa hacia atrás, un patinador se mueve hacia delante.",
            "Un cohete acelera expulsando propelente a gran velocidad. No necesita empujar aire: funciona en el vacío mediante conservación del momento.",
            "El empuje debe superar peso y pérdidas durante el lanzamiento, pero en órbita bastan maniobras calculadas.", "El cohete lleva su propelente y funciona por acción y reacción, incluso sin atmósfera.",
            q("¿Necesita aire un cohete?", "No", "Sí", "Solo en la Luna", 0, "Expulsa su propia masa de reacción.", "El patinador no necesita empujar una pared."),
            q("¿Qué produce el avance?", "Expulsar propelente", "Ausencia de gravedad", "Luz reflejada siempre", 0, "El momento total se conserva.", "La masa lanzada atrás impulsa el vehículo adelante.")),
        s(3, "Etapas y ecuación del cohete", "Explicar por qué se desecha masa durante el ascenso.", "Relaciona combustible, velocidad y diseño.",
            "Un excursionista avanza mejor si deja atrás recipientes vacíos que ya no necesita.",
            "La ecuación del cohete relaciona cambio de velocidad, velocidad de escape del propelente y proporción de masas. Las etapas eliminan tanques y motores vacíos para no seguir acelerándolos.",
            "Añadir combustible exige también acelerar ese combustible, por lo que los beneficios no crecen linealmente.", "Reducir masa inerte es tan importante como añadir propelente.",
            q("¿Por qué se separan etapas?", "Para desechar masa inútil", "Para perder velocidad", "Porque no hay gravedad", 0, "Los tanques vacíos ya no aportan empuje.", "El excursionista deja recipientes usados."),
            q("¿Duplicar combustible duplica siempre la velocidad?", "No", "Sí", "Solo en órbita", 0, "También hay que acelerar el combustible añadido.", "Más equipaje puede dificultar cargar el propio equipaje.")),
        s(4, "Órbita: caer alrededor", "Interpretar una órbita como caída libre continua.", "Evita pensar que en órbita desaparece la gravedad.",
            "Una pelota lanzada suficientemente rápido cae mientras la superficie se curva debajo de ella.",
            "Una nave orbital está en caída libre bajo gravedad, con velocidad lateral suficiente para no alcanzar la superficie. Los astronautas flotan porque nave y tripulación caen juntas.",
            "En la Estación Espacial la gravedad sigue siendo gran parte de la terrestre.", "Microgravedad significa caída libre compartida, no ausencia de gravedad.",
            q("¿Por qué flotan astronautas?", "Porque caen junto con la estación", "Porque no existe gravedad", "Porque no tienen masa", 0, "Todo el sistema acelera casi igual.", "La pelota y su cabina caen alrededor de la Tierra."),
            q("¿Hay gravedad en órbita baja?", "Sí", "No", "Solo de noche", 0, "La gravedad mantiene la órbita.", "Sin pendiente gravitatoria no habría caída orbital.")),
        s(5, "Transferencias y asistencia gravitatoria", "Describir trayectorias eficientes entre órbitas.", "Muestra por qué las misiones no vuelan en línea recta.",
            "Cambiar de carril en una pista móvil exige acelerar en momentos precisos; un encuentro puede intercambiar energía.",
            "Las maniobras orbitales cambian energía y forma de la órbita. Una asistencia gravitatoria intercambia momento con un planeta para modificar velocidad y dirección de una sonda.",
            "Voyager utilizó alineaciones planetarias para visitar varios mundos con menos propelente.", "La trayectoria eficiente aprovecha tiempo, geometría y gravedad.",
            q("¿Una sonda viaja normalmente en línea recta?", "No", "Sí", "Solo a Marte", 0, "Sigue órbitas alrededor del Sol y realiza transferencias.", "Cambia de carril en una pista curva."),
            q("¿Qué aporta una asistencia?", "Cambio de velocidad y dirección", "Combustible creado", "Ausencia de tiempo", 0, "Intercambia una cantidad diminuta de momento con el planeta.", "El encuentro ayuda a redirigir la expedición.")),
        s(6, "Sondas, orbitadores y rovers", "Elegir una arquitectura según la pregunta científica.", "Cada plataforma ofrece cobertura, detalle y riesgo diferentes.",
            "Un mapa aéreo, una visita rápida y un laboratorio móvil responden preguntas distintas.",
            "Un sobrevuelo estudia un mundo durante un encuentro; un orbitador lo observa repetidamente; un módulo de descenso analiza una zona y un rover explora varios sitios.",
            "Combinar orbitador y vehículo de superficie mejora mapas, comunicaciones y contexto.", "No hay una plataforma universal: la ciencia determina la arquitectura.",
            q("¿Qué ofrece observación repetida global?", "Un orbitador", "Un sobrevuelo único", "Un traje espacial", 0, "Permanece ligado al destino.", "Es el mapa aéreo que vuelve una y otra vez."),
            q("¿Qué explora varios sitios cercanos?", "Un rover", "Una antena terrestre", "Un telescopio fijo", 0, "Puede desplazarse sobre la superficie.", "Es el laboratorio móvil de la expedición.")),
        s(7, "Entrada, descenso y aterrizaje", "Explicar cómo una nave reduce su energía al llegar.", "Es una de las fases de mayor riesgo.",
            "Frenar un vehículo rápido requiere combinar fricción, paracaídas y motores según el terreno.",
            "Una atmósfera permite aerofrenado y paracaídas, pero genera calor. Retrocohetes, escudos térmicos, bolsas de aire o grúas celestes completan el descenso según destino y masa.",
            "Marte tiene atmósfera demasiado tenue para frenar cargas pesadas solo con paracaídas.", "Aterrizar es gestionar energía con las herramientas disponibles en cada mundo.",
            q("¿Para qué sirve un escudo térmico?", "Para manejar el calentamiento", "Para crear gravedad", "Para producir oxígeno", 0, "La compresión y fricción atmosférica elevan temperatura.", "Protege al vehículo durante el frenado rápido."),
            q("¿Basta un paracaídas grande en Marte?", "No siempre", "Sí siempre", "Marte no tiene atmósfera", 0, "Su aire tenue limita el frenado.", "La herramienta depende del medio del destino.")),
        s(8, "Exploración de la Luna", "Relacionar ciencia lunar y preparación tecnológica.", "La Luna conserva historia temprana y está relativamente cerca.",
            "Un archivo antiguo cercano permite estudiar páginas borradas en la Tierra y ensayar herramientas nuevas.",
            "Muestras lunares datan impactos y volcanismo; orbitadores cartografían composición y hielo polar. Las misiones humanas permiten trabajo flexible, pero requieren más masa y seguridad.",
            "Regiones polares permanentemente sombreadas pueden preservar agua helada.", "La Luna es laboratorio científico y banco de pruebas, no solo una escala hacia otro destino.",
            q("¿Qué preservan sus cráteres?", "Historia de impactos", "Clima terrestre actual", "Luz propia", 0, "La superficie se renueva poco.", "El archivo conserva páginas muy antiguas."),
            q("¿Dónde puede haber hielo?", "En sombras polares", "En cualquier zona soleada", "Solo en lava", 0, "Las temperaturas permanentemente bajas preservan volátiles.", "Los rincones del archivo permanecen como congeladores.")),
        s(9, "Exploración de Marte", "Evaluar objetivos, evidencias y limitaciones marcianas.", "Marte permite estudiar habitabilidad pasada y evolución planetaria.",
            "Un antiguo cauce seco no prueba que aún fluya agua, pero conserva pistas del ambiente pasado.",
            "Orbitadores, módulos y rovers hallaron minerales y formas del terreno producidos por agua antigua. Las misiones buscan habitabilidad y biofirmas; no han confirmado vida marciana.",
            "Traer muestras permitiría analizarlas con laboratorios terrestres mucho más diversos.", "Evidencia de agua pasada no equivale a evidencia confirmada de vida.",
            q("¿Hay vida marciana confirmada?", "No", "Sí", "Los rovers son vida", 0, "La búsqueda continúa sin detección confirmada.", "El cauce habla de ambiente, no demuestra habitantes."),
            q("¿Qué indican ciertos minerales?", "Agua antigua", "Civilizaciones", "Ausencia de atmósfera pasada", 0, "Algunos se forman en presencia de agua.", "Son rastros químicos del antiguo paisaje.")),
        s(10, "Sistema Solar exterior", "Comparar misiones a gigantes, lunas y cuerpos helados.", "Estos destinos prueban formación planetaria y posibles océanos.",
            "Visitar islas muy lejanas exige viajes largos, energía duradera y comunicaciones pacientes.",
            "Misiones como Galileo, Cassini, Juno, New Horizons y Voyager revelaron atmósferas, anillos, magnetosferas y mundos oceánicos. Lejos del Sol suele usarse energía nuclear.",
            "Europa y Encélado son objetivos de astrobiología por sus océanos internos.", "La distancia cambia energía, comunicación y duración, pero también abre ambientes inesperados.",
            q("¿Por qué usar energía nuclear lejos?", "Hay menos luz solar", "No existe gravedad", "Para crear planetas", 0, "Los paneles reciben menos energía.", "La isla lejana dispone de poca luz para abastecerse."),
            q("¿Qué lunas pueden tener océanos?", "Europa y Encélado", "Fobos y Deimos necesariamente", "La Luna y Mercurio", 0, "Evidencias apoyan agua bajo el hielo.", "Son islas con mares ocultos bajo la superficie.")),
        s(11, "Telescopios espaciales", "Explicar las ventajas y límites de observar fuera de la atmósfera.", "Permiten acceder a longitudes de onda bloqueadas y lograr estabilidad.",
            "Subir por encima de una ventana borrosa permite ver con claridad y colores antes bloqueados.",
            "La atmósfera absorbe gran parte del ultravioleta, rayos X e infrarrojo y distorsiona imágenes. Observatorios espaciales evitan esos efectos, aunque son costosos y difíciles de reparar.",
            "Hubble, Chandra y Webb observan bandas distintas y responden preguntas complementarias.", "El espacio amplía el espectro observable; no vuelve innecesarios los telescopios terrestres.",
            q("¿Qué ventaja ofrece el espacio?", "Evita absorción atmosférica", "Elimina toda luz", "Acerca físicamente galaxias", 0, "Permite observar bandas bloqueadas desde el suelo.", "Está encima de la ventana borrosa."),
            q("¿Un telescopio sirve para todas las bandas?", "No", "Sí", "Solo si es grande", 0, "Detectores y óptica se especializan.", "Cada instrumento ve colores diferentes del universo.")),
        s(12, "Comunicaciones y navegación", "Describir cómo se controla una nave distante.", "Sincroniza decisiones humanas con señales que tardan.",
            "Dirigir un explorador con mensajes que tardan horas exige autonomía y planes anticipados.",
            "Redes de antenas miden tiempo de viaje y efecto Doppler para determinar posición y velocidad. La latencia crece con distancia, así que las sondas ejecutan secuencias y responden a fallos localmente.",
            "Una orden a Marte tarda minutos en llegar; a Voyager, muchas horas.", "Las comunicaciones no son instantáneas y la autonomía es parte esencial del diseño.",
            q("¿Por qué no se conduce un rover en tiempo real?", "Por la demora de la luz", "Porque no tiene cámaras", "Porque no hay gravedad", 0, "La señal tarda varios minutos entre planetas.", "El mensaje llega demasiado tarde para reaccionar al instante."),
            q("¿Qué mide velocidad radial?", "El efecto Doppler", "El color del suelo", "Las fases lunares", 0, "El cambio de frecuencia revela movimiento relativo.", "La antena escucha cómo cambia el tono de la señal.")),
        s(13, "Vida y trabajo en el espacio", "Reconocer riesgos y sistemas de soporte vital.", "La exploración humana exige proteger un organismo terrestre.",
            "Una nave tripulada es una pequeña biosfera que debe reciclar recursos y proteger a su comunidad.",
            "Microgravedad afecta huesos y músculos; radiación, aislamiento y distancia añaden riesgos. Soporte vital controla aire, agua, temperatura y residuos, con redundancia y mantenimiento.",
            "Ejercicio diario reduce parte de la pérdida ósea y muscular en órbita.", "Llevar personas multiplica capacidades y también requisitos de seguridad y recursos.",
            q("¿Qué afecta la microgravedad?", "Huesos y músculos", "Solo el cabello", "La velocidad de la luz", 0, "El cuerpo se adapta a menor carga mecánica.", "La biosfera también debe mantener sano a su equipo."),
            q("¿Qué controla soporte vital?", "Aire y agua", "Órbitas planetarias", "Materia oscura", 0, "Mantiene condiciones habitables.", "Es la infraestructura básica de la pequeña biosfera.")),
        s(14, "Exploración responsable", "Integrar protección planetaria, basura espacial y decisiones éticas.", "Explorar exige preservar ciencia, seguridad y ambientes.",
            "Un buen visitante no contamina el lugar ni deja herramientas peligrosas en el camino.",
            "La protección planetaria reduce contaminación biológica entre mundos. La mitigación de desechos limita colisiones orbitales. Decisiones sobre recursos y presencia humana requieren cooperación y reglas verificables.",
            "Esterilizar componentes puede proteger experimentos que buscan vida de microbios terrestres.", "El éxito no es solo llegar: incluye no comprometer futuras investigaciones ni el entorno orbital.",
            q("¿Qué evita protección planetaria?", "Contaminación biológica", "Toda exploración", "La gravedad", 0, "Protege otros mundos y las muestras terrestres.", "El visitante limpia sus herramientas antes de entrar."),
            q("¿Por qué importa la basura orbital?", "Puede causar colisiones", "Produce gravedad", "Crea atmósferas", 0, "Fragmentos rápidos amenazan naves y satélites.", "Las herramientas abandonadas pueden bloquear el camino futuro."))
    )

    val practica = listOf(
        e(2, "Un motor expulsa gas en el vacío. ¿Puede acelerar?", "Sí, por conservación del momento", "No, necesita aire", "Solo cerca de la Tierra", "Solo si cae", 0, "El propelente aporta la masa de reacción."),
        e(4, "Astronautas y estación caen juntos alrededor de la Tierra. ¿Qué experimentan?", "Microgravedad", "Ausencia total de gravedad", "Gravedad infinita", "Una superficie", 0, "La flotación surge de la caída libre compartida."),
        e(5, "Una sonda usa Júpiter para cambiar dirección y rapidez. ¿Qué maniobra es?", "Asistencia gravitatoria", "Aterrizaje", "Paralaje", "Tránsito", 0, "Intercambia momento con el planeta durante el encuentro."),
        e(7, "Una cápsula entra a gran velocidad en una atmósfera. ¿Qué componente es esencial?", "Escudo térmico", "Telescopio", "Panel decorativo", "Imán terrestre", 0, "Debe soportar el calentamiento de entrada."),
        e(9, "Un rover encuentra minerales formados en agua. ¿Qué conclusión es rigurosa?", "Hubo condiciones acuosas pasadas", "Existió vida confirmada", "Hay océanos actuales", "Marte fue la Tierra", 0, "La evidencia ambiental no confirma organismos."),
        e(11, "Se quiere observar rayos X cósmicos. ¿Dónde conviene el telescopio?", "Fuera de la atmósfera", "Bajo el océano para captar luz", "En una cueva", "Sobre un rover marciano obligatoriamente", 0, "La atmósfera bloquea gran parte de los rayos X."),
        e(12, "Una orden tarda horas en alcanzar una sonda. ¿Qué capacidad necesita?", "Autonomía", "Conducción instantánea", "Ausencia de software", "Más gravedad", 0, "Debe ejecutar planes y resolver fallos sin respuesta inmediata.")
    )

    val examen = listOf(
        e(1, "¿Qué debe establecer primero una misión?", "Objetivos medibles", "Un nombre", "Una bandera", "El color de la nave", 0, "La ciencia guía instrumentos y trayectoria."),
        e(2, "¿Por qué funciona un cohete en el vacío?", "Conserva momento al expulsar masa", "Empuja aire", "El vacío lo atrae", "Pierde masa sin reacción", 0, "No necesita un medio externo."),
        e(3, "¿Qué ventaja ofrecen las etapas?", "Desechan masa inerte", "Eliminan gravedad", "Detienen el cohete", "Crean combustible", 0, "Evitan acelerar tanques vacíos."),
        e(4, "¿Qué es una órbita?", "Caída libre continua", "Ausencia de gravedad", "Flotación sin movimiento", "Una línea recta inmóvil", 0, "La velocidad lateral evita alcanzar la superficie."),
        e(5, "¿Para qué sirve una asistencia gravitatoria?", "Cambiar velocidad y dirección", "Crear materia", "Eliminar distancia", "Aterrizar", 0, "Aprovecha un encuentro planetario."),
        e(6, "¿Qué plataforma observa repetidamente un mundo entero?", "Un orbitador", "Un rover", "Un traje", "Un paracaídas", 0, "Permanece alrededor del destino."),
        e(7, "¿Cuál es el reto principal al aterrizar?", "Disipar energía de forma controlada", "Crear gravedad", "Aumentar velocidad", "Evitar toda comunicación", 0, "La nave debe frenar sin dañarse."),
        e(9, "¿Se ha confirmado vida en Marte?", "No", "Sí", "Solo vida inteligente", "Los minerales son organismos", 0, "Las misiones han hallado habitabilidad pasada, no vida confirmada."),
        e(12, "¿Por qué una sonda distante necesita autonomía?", "Las señales tardan", "No existen antenas", "La luz no viaja", "No tiene instrumentos", 0, "La latencia impide control instantáneo."),
        e(14, "¿Qué incluye exploración responsable?", "Protección planetaria y mitigación de desechos", "Abandonar equipos", "Contaminar muestras", "Ignorar colisiones", 0, "Debe proteger ambientes, ciencia y operaciones futuras.")
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
