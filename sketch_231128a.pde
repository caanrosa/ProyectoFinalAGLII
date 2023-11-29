import java.util.Random;
import javax.swing.JOptionPane;

  // Matriz para el laberinto
  int[][] lab;

  // Método setup para inicializar y resolver el laberinto
  void setup() {
    int n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño del laberinto (n):"));
    lab = new int[n][n];
    
    // Generar laberinto aleatorio
    Random rand = new Random();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        lab[i][j] = rand.nextInt(2); // 0 o 1
      }
    }
    
    mostrarLaberintoVistaPrevia(-1, -1, -1, -1);
    
    int filaInicial = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila del punto inicial:"));
    int colInicial = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna del punto inicial:"));
    
    int filaFinal = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila del punto final:"));
    int colFinal = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna del punto final:"));
    
    boolean salidaEncontrada = false;
    
    JOptionPane.showMessageDialog(null, "Resolviendo laberinto...");
    if (resolverLaberinto(filaInicial, colInicial, filaFinal, colFinal)) {
      salidaEncontrada = true;
      mostrarLaberinto(true);
    } else {
      mostrarLaberinto(false);
    }
    
    if (salidaEncontrada) {
      JOptionPane.showMessageDialog(null, "¡Se encontró una salida en el laberinto!");
    } else {
      JOptionPane.showMessageDialog(null, "No se encontró salida en el laberinto.");
    }
  }

  // Método draw, no usado en este contexto
  void draw() {
  }

  // Función para mostrar el laberinto con emojis según la representación
  void mostrarLaberinto(boolean encontrado) {
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
                                  encontrado ? "Camino encontrado" : "No hay camino posible",
                                  JOptionPane.INFORMATION_MESSAGE);
  }

  // Función para mostrar una vista previa del laberinto
  void mostrarLaberintoVistaPrevia(int filaIni, int colIni, int filaFin, int colFin) {
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

  // Función recursiva para resolver el laberinto
  boolean resolverLaberinto(int filaActual, int colActual, int filaFinal, int colFinal) {
    int filas = lab.length;
    int columnas = lab[0].length;
    
    if (filaActual == filaFinal && colActual == colFinal) {
      lab[filaActual][colActual] = 9;
      return true;
    }
    
    if (filaActual >= 0 && colActual >= 0 && filaActual < filas && colActual < columnas
        && lab[filaActual][colActual] == 1) {
      lab[filaActual][colActual] = 9;
      
      if (resolverLaberinto(filaActual + 1, colActual, filaFinal, colFinal)) {
        return true;
      }
      if (resolverLaberinto(filaActual - 1, colActual, filaFinal, colFinal)) {
        return true;
      }
      if (resolverLaberinto(filaActual, colActual + 1, filaFinal, colFinal)) {
        return true;
      }
      if (resolverLaberinto(filaActual, colActual - 1, filaFinal, colFinal)) {
        return true;
      }
      
      lab[filaActual][colActual] = 1;
      return false;
    }
    
    return false;
  }
