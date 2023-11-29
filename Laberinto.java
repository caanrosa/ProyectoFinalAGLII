import java.util.Random;
import javax.swing.JOptionPane;

public class Laberinto {

    /**
     * Muestra la representación visual del laberinto.
     * 
     * @param lab        Laberinto a mostrar
     * @param encontrado Indica si se encontró una salida en el laberinto
     */
    public static void mostrarLaberinto(int[][] lab, boolean encontrado) {
        StringBuilder labStr = new StringBuilder();
        for (int[] fila : lab) {
            for (int valor : fila) {
                if (valor == 9) {
                    labStr.append("\uD83D\uDFE1 "); // Camino encontrado
                } else if (valor == 1) {
                    labStr.append("\u2B1C "); // Pared
                } else {
                    labStr.append("\u2B1B "); // Espacio vacío
                }
            }
            labStr.append("\n");
        }

        JOptionPane.showMessageDialog(null, labStr.toString(),
                encontrado ? "¡Salida encontrada!" : "No se encontró salida", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra una vista previa del laberinto con los puntos inicial y final
     * marcados.
     * 
     * @param lab     Laberinto a mostrar
     * @param filaIni Fila del punto inicial
     * @param colIni  Columna del punto inicial
     * @param filaFin Fila del punto final
     * @param colFin  Columna del punto final
     */
    public static void mostrarLaberintoVistaPrevia(int[][] lab, int filaIni, int colIni, int filaFin, int colFin) {
        StringBuilder labStr = new StringBuilder();
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (i == filaIni && j == colIni) {
                    labStr.append("\uD83D\uDEE0 "); // Punto inicial
                } else if (i == filaFin && j == colFin) {
                    labStr.append("\uD83D\uDEEC "); // Punto final
                } else if (lab[i][j] == 1) {
                    labStr.append("\u2B1C "); // Pared
                } else {
                    labStr.append("\u2B1B "); // Espacio vacío
                }
            }
            labStr.append("\n");
        }

        JOptionPane.showMessageDialog(null, labStr.toString(), "Vista previa del laberinto",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Resuelve recursivamente el laberinto.
     * 
     * @param lab        Laberinto a resolver
     * @param filaActual Fila actual
     * @param colActual  Columna actual
     * @param filaFinal  Fila del punto final
     * @param colFinal   Columna del punto final
     * @return true si se encontró una solución, false de lo contrario
     */
    public static boolean resolverLaberinto(int[][] lab, int filaActual, int colActual, int filaFinal, int colFinal) {
        int filas = lab.length;
        int columnas = lab[0].length;

        if (filaActual == filaFinal && colActual == colFinal) {
            lab[filaActual][colActual] = 9; // Marca la salida encontrada
            return true;
        }

        if (filaActual >= 0 && colActual >= 0 && filaActual < filas && colActual < columnas
                && lab[filaActual][colActual] == 1) {
            lab[filaActual][colActual] = 9; // Marca la posición visitada

            if (resolverLaberinto(lab, filaActual + 1, colActual, filaFinal, colFinal)) {
                return true;
            }
            if (resolverLaberinto(lab, filaActual - 1, colActual, filaFinal, colFinal)) {
                return true;
            }
            if (resolverLaberinto(lab, filaActual, colActual + 1, filaFinal, colFinal)) {
                return true;
            }
            if (resolverLaberinto(lab, filaActual, colActual - 1, filaFinal, colFinal)) {
                return true;
            }

            lab[filaActual][colActual] = 1; // Deshace la marca
            return false;
        }

        return false;
    }

    public static void main(String[] args) {
        Random rand = new Random();

        String input = JOptionPane.showInputDialog("Ingrese el tamaño del laberinto (n):");
        int n = Integer.parseInt(input);

        int[][] lab = new int[n][n];

        // Generar laberinto aleatorio
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                lab[i][j] = rand.nextInt(2); // 0 o 1
            }
        }

        mostrarLaberintoVistaPrevia(lab, -1, -1, -1, -1);

        int filaInicial = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila del punto inicial:"));
        int colInicial = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna del punto inicial:"));

        int filaFinal = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila del punto final:"));
        int colFinal = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna del punto final:"));

        boolean salidaEncontrada = false;

        JOptionPane.showMessageDialog(null, "Resolviendo laberinto...");
        if (resolverLaberinto(lab, filaInicial, colInicial, filaFinal, colFinal)) {
            salidaEncontrada = true;
            mostrarLaberinto(lab, true);
        } else {
            mostrarLaberinto(lab, false);
        }

        if (salidaEncontrada) {
            JOptionPane.showMessageDialog(null, "¡Se encontró una salida en el laberinto!");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró salida en el laberinto.");
        }
    }
}
