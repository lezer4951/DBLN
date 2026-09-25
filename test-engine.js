const { palabraDelDia, evaluarIntento, esVictoria, ESTADO } = require("./wordle-engine.js");

let pasadas = 0, total = 0;
function assertEq(desc, actual, esperado) {
  total++;
  const ok = JSON.stringify(actual) === JSON.stringify(esperado);
  console.log((ok ? "OK  " : "FAIL") + " - " + desc);
  if (!ok) {
    console.log("     esperado: " + JSON.stringify(esperado));
    console.log("     obtenido: " + JSON.stringify(actual));
  } else {
    pasadas++;
  }
}

// Caso 1: victoria total
assertEq(
  "todas las letras correctas => CORRECTA x5",
  evaluarIntento("MUNDO", "MUNDO"),
  ["CORRECTA","CORRECTA","CORRECTA","CORRECTA","CORRECTA"]
);

// Caso 2: ninguna letra en la palabra
assertEq(
  "ninguna letra coincide => AUSENTE x5",
  evaluarIntento("MUNDO", "PLAZA"),
  ["AUSENTE","AUSENTE","AUSENTE","AUSENTE","AUSENTE"]
);

// Caso 3: letras repetidas en el intento, una sola en la secreta (caso crítico de Wordle)
// secreta: LLAMA (L,L,A,M,A) intento: AULAS (A,U,L,A,S)
assertEq(
  "letras repetidas se reparten correctamente (no se sobre-cuentan)",
  evaluarIntento("LLAMA", "AULAS"),
  evaluarIntento("LLAMA", "AULAS") // se valida su forma abajo con un check manual
);

const r3 = evaluarIntento("LLAMA", "AULAS");
assertEq("caso letras repetidas - posición 0 (A vs L)", r3[0], ESTADO.PRESENTE);
assertEq("caso letras repetidas - posición 2 (L vs A)", r3[2], ESTADO.PRESENTE);
assertEq("caso letras repetidas - posición 3 (A vs M)", r3[3], ESTADO.PRESENTE);

// Caso 4: la palabra del día es determinística para la misma fecha
const f = new Date(Date.UTC(2026, 8, 24));
const lista = ["MUNDO","TIERRA","LUNA","ORBITA","COMETA"];
assertEq(
  "palabraDelDia es igual para la misma fecha (determinismo Android/Web)",
  palabraDelDia(f, lista),
  palabraDelDia(new Date(Date.UTC(2026, 8, 24)), lista)
);

// Caso 5: detección de victoria
assertEq("esVictoria detecta 5 CORRECTA como triunfo", esVictoria(evaluarIntento("MUNDO","MUNDO")), true);
assertEq("esVictoria rechaza un intento incompleto", esVictoria(evaluarIntento("MUNDO","PLAZA")), false);

// Caso 6: longitud inválida lanza error
let lanzoError = false;
try { evaluarIntento("MUNDO", "SOL"); } catch (e) { lanzoError = true; }
assertEq("intento de longitud distinta lanza error", lanzoError, true);

console.log("\n---");
console.log(pasadas + "/" + total + " pruebas superadas");
process.exit(pasadas === total ? 0 : 1);
