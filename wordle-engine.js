// Motor de "Palabra del Día" — lógica pura, sin dependencias de plataforma.
// Mismo algoritmo que se implementa en Kotlin para Android (ver WordleEngine.kt).

const ESTADO = { CORRECTA: "CORRECTA", PRESENTE: "PRESENTE", AUSENTE: "AUSENTE" };

/**
 * Selecciona la palabra del día de forma determinística a partir de una fecha,
 * para que Android y Web muestren SIEMPRE la misma palabra el mismo día.
 * @param {Date} fecha
 * @param {string[]} listaPalabras
 */
function palabraDelDia(fecha, listaPalabras) {
  const epoch = Date.UTC(2026, 0, 1); // ancla fija: 1 ene 2026 UTC
  const dias = Math.floor((Date.UTC(fecha.getUTCFullYear(), fecha.getUTCMonth(), fecha.getUTCDate()) - epoch) / 86400000);
  const indice = ((dias % listaPalabras.length) + listaPalabras.length) % listaPalabras.length;
  return listaPalabras[indice];
}

/**
 * Evalúa un intento contra la palabra secreta, letra por letra,
 * manejando correctamente letras repetidas (algoritmo de dos pasadas).
 * @param {string} secreta
 * @param {string} intento
 * @returns {string[]} arreglo de estados por posición
 */
function evaluarIntento(secreta, intento) {
  const n = secreta.length;
  if (intento.length !== n) {
    throw new Error("El intento debe tener " + n + " letras");
  }
  const s = secreta.toUpperCase().split("");
  const t = intento.toUpperCase().split("");
  const resultado = new Array(n).fill(ESTADO.AUSENTE);
  const disponibles = {};

  // Pasada 1: marcar correctas exactas
  for (let i = 0; i < n; i++) {
    if (t[i] === s[i]) {
      resultado[i] = ESTADO.CORRECTA;
    } else {
      disponibles[s[i]] = (disponibles[s[i]] || 0) + 1;
    }
  }
  // Pasada 2: marcar presentes usando el remanente de letras no consumidas
  for (let i = 0; i < n; i++) {
    if (resultado[i] === ESTADO.CORRECTA) continue;
    const letra = t[i];
    if (disponibles[letra] > 0) {
      resultado[i] = ESTADO.PRESENTE;
      disponibles[letra]--;
    }
  }
  return resultado;
}

function esVictoria(resultado) {
  return resultado.every((e) => e === ESTADO.CORRECTA);
}

if (typeof module !== "undefined") {
  module.exports = { palabraDelDia, evaluarIntento, esVictoria, ESTADO };
}
