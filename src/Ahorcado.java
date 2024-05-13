import java.util.Scanner;
import java.util.regex.Pattern;

public class Ahorcado {
    static final String VERDE = "\u001B[32m";
    static final String VERDE_NEGRITA = "\u001B[1;32m";
    static final String AMARILLO = "\u001B[33m";
    static final String AZUL = "\u001B[34m";
    static final String MORADO_NEGRITA = "\u001B[1;35m";
    static final String ROJO_NEGRITA = "\033[1;31m";
    static final String COLOR_DEFAULT = "\u001B[0m";
    static int puntaje = 0;
    static Scanner scan = new Scanner(System.in);
    static char letraIngreso;
    static char input;
    static int accionMenu;
    static String nombreUsuario;
    static boolean finDelJuego = true;
    static String palabraUsada;
    static char[] palabraComoGuiones;
    static char[] conjuntoLetras = {};
    static int tamanoIngresos = 0;
    static char[] cadenaLetras;
    static int intentosLetra = 7;
    static int oportunidadesPalabra = 3;
    static boolean palabraAdivinada = false;

    public static void menuInicial() {
        System.out.println("|-------------------------------------------------------------|");
        System.out.println("|                                                             |");
        System.out.println("|         " + MORADO_NEGRITA + "           ¡¡¡JUEGA CON NOSOTROS!!!       " + COLOR_DEFAULT
                + "          |");
        System.out.println("|                                                             |");
        System.out.println("| " + AZUL + " Presiona un valor numerico diferente a 0 si quieres jugar "
                + COLOR_DEFAULT + " |");
        System.out.println("|                                                             |");
        System.out.println(
                "|      " + VERDE + "           Presiona 0 si no quieres jugar      " + COLOR_DEFAULT + "        |");
        System.out.println("|                                                             |");
        System.out.println(
                "|                  " + ROJO_NEGRITA + "  ♡" + COLOR_DEFAULT
                        + "" + AMARILLO + " Digita tu respuesta " + ROJO_NEGRITA + "♡          " + COLOR_DEFAULT
                        + "        |");
        System.out.println("|                                                             |");
        System.out.println("|-------------------------------------------------------------|");
        manejoError();
        if (accionMenu != 0) {
            iteraciones();
        } else {
            System.out.println(ROJO_NEGRITA + "Decidiste no jugar, esperamos vuelvas pronto");
        }
    }

    public static void manejoError() {
        try {
            accionMenu();
        } catch (Exception e) {
            manejoError();
        }
    }

    public static void accionMenu() {
        try {
            accionMenu = Integer.parseInt(scan.nextLine());
        } catch (Exception e) {
            System.out.println(VERDE + "Tienes que ingresar un numero, vuelve a intentarlo" + COLOR_DEFAULT);
            manejoError();
        }
    }

    public static void saludoInicial() {
        System.out.println(MORADO_NEGRITA + "¡¡¡Esperamos que logres ganar este juego de Ahorcado!!!" + COLOR_DEFAULT);
        String regex = "[a-zA-Z]+";
        do {
            System.out.print(AZUL + "Por favor, ingresa tu nombre: " + COLOR_DEFAULT);
            nombreUsuario = scan.nextLine();
            if (!Pattern.matches(regex, nombreUsuario)) {
                System.out.println(AMARILLO + "Entrada inválida. Los nombres solo incluyen letras." + COLOR_DEFAULT);
            }
        } while (!Pattern.matches(regex, nombreUsuario));
        System.out.print(VERDE + "El nombre ingresado es: " + COLOR_DEFAULT + nombreUsuario);
        System.out.println("\n");
    }

    public static boolean evaluarNumero(char letraIngresada) {
        char[] numeros = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == letraIngresada) {
                return true;
            }
        }
        return false;
    }

    public static boolean repetido(char letraIngresada) {
        for (int i = 0; i < conjuntoLetras.length; i++) {
            if (conjuntoLetras[i] == letraIngresada) {
                return true;
            }
        }
        return false;
    }

    public static char[] cadenaLetras() {
        boolean esRepetido = repetido(letraIngreso);
        if (!esRepetido) {
            char[] otraCadena = new char[conjuntoLetras.length + 1];
            for (int i = 0; i < conjuntoLetras.length; i++) {
                otraCadena[i] = conjuntoLetras[i];
            }
            try {
                for (int i = 0; i < otraCadena.length; i++) {
                    if (otraCadena[i] != (letraIngreso)) {
                        otraCadena[conjuntoLetras.length] = letraIngreso;
                    }
                    conjuntoLetras = otraCadena;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("");
            }
        }
        return conjuntoLetras;
    }

    public static void iteraciones() {
        if (oportunidadesPalabra == 3) {
            saludoInicial();
        }
        if (oportunidadesPalabra > 0) {
            juegoAhorcado();
        } else {
            System.err.println("\n");

            System.out.println(COLOR_DEFAULT + MORADO_NEGRITA + "Tu puntaje final es: " + COLOR_DEFAULT + ROJO_NEGRITA
                    + puntaje + COLOR_DEFAULT);
        }
    }

    public static void juegoAhorcado() {
        finDelJuego = true;
        intentosLetra = 7;
        palabraUsada = palabraSecreta();
        palabraComoGuiones = guionesComoPalabra(palabraUsada);
        for (int i = 0; i < conjuntoLetras.length; i++) {
            conjuntoLetras[i] = '\u0000';
        }
        while (finDelJuego) {
            ingresoInput();
            comprobacionLetra();
            casoExitoso();
        }
        oportunidadesPalabra--;
        System.out.println(
                COLOR_DEFAULT + AMARILLO + "Tienes " + COLOR_DEFAULT + MORADO_NEGRITA + oportunidadesPalabra
                        + COLOR_DEFAULT + AMARILLO + " oportunidades u oportunidad para adivinar una palabra"
                        + COLOR_DEFAULT);
        iteraciones();
    }

    public static void ingresoInput() {
        System.out.println(
                COLOR_DEFAULT + VERDE + "Para jugar tienes " + COLOR_DEFAULT + ROJO_NEGRITA + intentosLetra
                        + COLOR_DEFAULT + VERDE + " intentos por palabra" + COLOR_DEFAULT);
        do {
            System.out.print(AZUL + "Ingresa una letra: " + COLOR_DEFAULT);
            try {
                letraIngreso = scan.nextLine().toLowerCase().charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Ingrese un valor correcto.");
            }
            if (!Character.isLetter(letraIngreso)) {
                System.out.println(AMARILLO + "Entrada inválida. Ingrese solo letras." + COLOR_DEFAULT);
            }
        } while (!Character.isLetter(letraIngreso));
        System.out.print(VERDE + "Letra ingresada es: " + COLOR_DEFAULT + letraIngreso);
        System.out.println("\n");
    }

    public static void comprobacionLetra() {
        boolean letraEnPalabra = false;
        for (int i = 0; i < palabraUsada.length(); i++) {
            if (palabraUsada.charAt(i) == letraIngreso) {
                palabraComoGuiones[i] = letraIngreso;
                letraEnPalabra = true;
            }
        }
        System.out.println(palabraComoGuiones);
        if (!letraEnPalabra) {
            intentosLetra--;
            System.out.println("\n");
            System.out.println(AZUL + "La palabra no contiene la letra: " + COLOR_DEFAULT + letraIngreso);
            System.out.println("\n");
            System.out.print(VERDE + "Letras fallidas son: " + COLOR_DEFAULT);
            cadenaLetras = cadenaLetras();
            for (int i = 0; i < cadenaLetras.length; i++) {
                System.out.print(cadenaLetras[i]);
            }
            System.out.print("\n");
            dibujar(intentosLetra);
        }
    }

    public static void casoExitoso() {
        palabraAdivinada = hayGuiones(palabraComoGuiones);
        if (!palabraAdivinada) {
            System.out.println("\n");
            System.out.println(VERDE_NEGRITA + "¡¡¡FELICIDADES, ADIVINASTE LA PALABRA!!!" + COLOR_DEFAULT);
            puntaje += 3;
            System.out.println("\n");
            System.out.println(COLOR_DEFAULT + AMARILLO + "Tu puntaje actual es: " + COLOR_DEFAULT + MORADO_NEGRITA
                    + puntaje + COLOR_DEFAULT);
            finDelJuego = false;
            System.out.println("\n");
        }
    }

    public static char[] guionesComoPalabra(String palabraUsada) {
        int tamanoPalabra = palabraUsada.length();
        char[] guiones = palabraUsada.toCharArray();
        for (int i = 0; i < tamanoPalabra; i++) {
            guiones[i] = '_';
        }
        return guiones;
    }

    public static String palabraSecreta() {
        String[] palabras = { "carro", "manzana", "canela", "fundamentos", "desarrollo", "software", "inteligencia",
                "destreza", "concordancia", "proyecto", "residencial", "depurador" };
        int index = (int) Math.floor(Math.random() * palabras.length);
        return palabras[index];
    }

    public static boolean hayGuiones(char[] guiones) {
        for (char c : guiones) {
            if (c == '_') {
                return true;
            }
        }
        return false;
    }

    private static void dibujar(int intentosLetra) {
        if (intentosLetra == 6) {
            System.out.println(AMARILLO + " |---------------------|");
            for (int i = 0; i <= 15; i++) {
                System.out.println(" |");
            }
            System.out.println(" |__________");
            System.err.println("\n");
        } else if (intentosLetra == 5) {
            String[] errorArray5 = { AMARILLO + " |---------------------|", " |                     |",
                    " |                     |", " |                  -------", " |                 |  - -  |",
                    " |                 |   _   |", " |                  -------" };
            for (int i = 0; i < errorArray5.length; i++) {
                System.out.println(errorArray5[i]);
            }
            for (int i = 0; i <= 10; i++) {
                System.out.println(" |");
            }
            System.out.println(" |__________");
            System.err.println("\n");
        } else if (intentosLetra == 4) {
            String[] errorArray4 = { AMARILLO + " |---------------------|", " |                     |",
                    " |                     |", " |                  -------", " |                 |  - -  |",
                    " |                 |   _   |", " |                  -------", " |                     |   ",
                    " |                     |   ", " |                     |   ", " |                     |   ",
                    " |                     |   " };
            for (int i = 0; i < errorArray4.length; i++) {
                System.out.println(errorArray4[i]);
            }
            for (int i = 0; i <= 5; i++) {
                System.out.println(" |");
            }
            System.out.println(" |__________");
            System.err.println("\n");
        } else if (intentosLetra == 3) {
            String[] errorArray3 = { AMARILLO + " |---------------------|", " |                     |",
                    " |                     |", " |                  -------",
                    " |                 |  - -  |",
                    " |                 |   _   |", " |                  -------", " |                     |   ",
                    " |                   / |   ", " |                  /  |   ", " |                 /   |   ",
                    " |                     |   " };
            for (int i = 0; i < errorArray3.length; i++) {
                System.out.println(errorArray3[i]);
            }
            for (int i = 0; i <= 5; i++) {
                System.out.println(" |");
            }
            System.out.println(" |__________");
            System.err.println("\n");
        } else if (intentosLetra == 2) {
            String[] errorArray2 = { AMARILLO + " |---------------------|", " |                     |",
                    " |                     |", " |                  -------", " |                 |  - -  |",
                    " |                 |   _   |", " |                  -------", " |                     |   ",
                    " |                   / | \\ ",
                    " |                  /  |  \\ ",
                    " |                 /   |   \\ ",
                    " |                     |   " };
            for (int i = 0; i < errorArray2.length; i++) {
                System.out.println(errorArray2[i]);
            }
            for (int i = 0; i <= 5; i++) {
                System.out.println(" |");
            }
            System.out.println(" |__________");
            System.err.println("\n");
        } else if (intentosLetra == 1) {
            String[] errorArray1 = { AMARILLO + " |---------------------|", " |                     |",
                    " |                     |", " |                  -------", " |                 |  - -  |",
                    " |                 |   _   |", " |                  -------", " |                     |   ",
                    " |                   / | \\ ",
                    " |                  /  |  \\ ",
                    " |                 /   |   \\ ",
                    " |                     |   ", " |                    /  ", " |                   /      ",
                    " |                 _/       " };
            for (int i = 0; i < errorArray1.length; i++) {
                System.out.println(errorArray1[i]);
            }
            for (int i = 0; i <= 2; i++) {
                System.out.println(" |");
            }
            System.out.println(" |__________");
            System.err.println("\n");
        } else if (intentosLetra == 0) {
            String[] errorArray0 = { ROJO_NEGRITA + " |---------------------|", " |                     |",
                    " |                     |", " |                  -------", " |                 |  X X  |",
                    " |                 |   o   |", " |                  -------", " |                     |   ",
                    " |                   / | \\ ",
                    " |                  /  |  \\ ",
                    " |                 /   |   \\ ",
                    " |                     |   ",
                    " |                    / \\",
                    " |                   /   \\  ",
                    " |                 _/     \\_ " };
            for (int i = 0; i < errorArray0.length; i++) {
                System.out.println(errorArray0[i]);
            }
            for (int i = 0; i <= 2; i++) {
                System.out.println(" |");
            }
            System.out.println(" |__________");
            System.err.println("\n");
            System.out.println("FIN DEL JUEGO" + COLOR_DEFAULT);
            finDelJuego = false;
            System.err.println("\n");
            System.out.println(COLOR_DEFAULT + AMARILLO + "Tu puntaje actual es: " + COLOR_DEFAULT + MORADO_NEGRITA
                    + puntaje + COLOR_DEFAULT);
            System.err.println("\n");
        }
    }

    public static void main(String[] args) {
        menuInicial();
    }
}
