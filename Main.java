import java.util.Arrays;
import java.util.Scanner;

public class Main {

  static String minusculas = "abcdefghijklmnopqrstuvwxyz";
  static String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  static String digitos = "0123456789";
  static String caracteresEspeciales = "+-.><!=&| ";
  static String pesos = "$";
  static String guionBajo = "_";
  static String alfabeto = minusculas + mayusculas + digitos + caracteresEspeciales + pesos + guionBajo;

  static String[] palabrasReservadas = new String[] { "do", "while", "for", "class", "return", "public", "protected" };

  static int cantidadEstados = 80;

  static int mt[][];

  public static void main(String[] args) {
    Scanner lee = new Scanner(System.in);

    mt = new int[cantidadEstados][alfabeto.length()];

    llenarMt();

    while (lee.hasNextLine()) {
      String x = lee.nextLine();
      System.out.printf("Cadena: \"%s\"\n", x);
      int estado = 0;
      int i = 0, simbolo;
      while (estado != -1 && i < x.length()) {
        simbolo = convierte(x.charAt(i));
        if (simbolo == -1) {
          estado = -1;
          break;
        } else {
          estado = mt[estado][simbolo];
          System.out.println(estado);
        }
        i++;
      }
      if (estado == -1) {
        System.out.println("Cadena rechazada");
      } else if (esEstadoPalabraReservada(estado)) {
        System.out.println("Palabra reservada");
      } else if (estado == 2) {
        System.out.println("Identificador");
      } else if (estado == 14 || estado == 73) {
        System.out.println("Operador Relacional");
      } else if (estado == 18 || estado == 21 || estado == 23) {
        System.out.println("Operador Logico");
      } else if (estado == 10) {
        System.out.println("Numero Entero");
      } else if (estado == 7 || estado == 12) {
        System.out.println("Numero Real");
      }
    }

  }

  public static int convierte(char y) {
    return alfabeto.indexOf(y);
  }

  public static boolean esFinal(int estado) {
    if (estado == 6) return true;
    if (estado == 33) return true;
    return false;
  }

  public static boolean esEstadoPalabraReservada(int i) {
    return i == 29 || i == 32 || i == 36 || i == 42 || i == 49 || i == 55 || i == 62 || i == 71;
  }

  public static void llenarMt() {
    Arrays.stream(mt).forEach(a -> Arrays.fill(a, -1));
    mt[0][alfabeto.indexOf('w')] = 24;
    mt[24][alfabeto.indexOf('h')] = 25;
    mt[25][alfabeto.indexOf('i')] = 26;
    mt[26][alfabeto.indexOf('l')] = 27;
    mt[27][alfabeto.indexOf('e')] = 28;
    mt[28][alfabeto.indexOf(' ')] = 29; // aceptada

    mt[0][alfabeto.indexOf('d')] = 30;
    mt[30][alfabeto.indexOf('o')] = 31;
    mt[31][alfabeto.indexOf(' ')] = 32; // aceptada

    mt[0][alfabeto.indexOf('f')] = 33;
    mt[33][alfabeto.indexOf('o')] = 34;
    mt[34][alfabeto.indexOf('r')] = 35;
    mt[35][alfabeto.indexOf(' ')] = 36; // aceptada

    mt[0][alfabeto.indexOf('c')] = 37;
    mt[37][alfabeto.indexOf('l')] = 38;
    mt[38][alfabeto.indexOf('a')] = 39;
    mt[39][alfabeto.indexOf('s')] = 40;
    mt[40][alfabeto.indexOf('s')] = 41;
    mt[41][alfabeto.indexOf(' ')] = 42; // aceptada

    mt[0][alfabeto.indexOf('r')] = 43;
    mt[43][alfabeto.indexOf('e')] = 44;
    mt[44][alfabeto.indexOf('t')] = 45;
    mt[45][alfabeto.indexOf('u')] = 46;
    mt[46][alfabeto.indexOf('r')] = 47;
    mt[47][alfabeto.indexOf('n')] = 48;
    mt[48][alfabeto.indexOf(' ')] = 49; // aceptada

    // TODO: review
    // mt[0][alfabeto.indexOf('f')] = 50;
    // mt[50][alfabeto.indexOf('l')] = 51;
    
    mt[33][alfabeto.indexOf('l')] = 51;
    mt[51][alfabeto.indexOf('o')] = 52;
    mt[52][alfabeto.indexOf('a')] = 53;
    mt[53][alfabeto.indexOf('t')] = 54;
    mt[54][alfabeto.indexOf(' ')] = 55; // aceptada

    mt[0][alfabeto.indexOf('p')] = 56;

    mt[56][alfabeto.indexOf('u')] = 57;
    mt[57][alfabeto.indexOf('b')] = 58;
    mt[58][alfabeto.indexOf('l')] = 59;
    mt[59][alfabeto.indexOf('i')] = 60;
    mt[60][alfabeto.indexOf('c')] = 61;
    mt[61][alfabeto.indexOf(' ')] = 62; // aceptada

    mt[56][alfabeto.indexOf('r')] = 63;
    mt[63][alfabeto.indexOf('o')] = 64;
    mt[64][alfabeto.indexOf('t')] = 65;
    mt[65][alfabeto.indexOf('e')] = 66;
    mt[66][alfabeto.indexOf('c')] = 67;
    mt[67][alfabeto.indexOf('t')] = 68;
    mt[68][alfabeto.indexOf('e')] = 69;
    mt[69][alfabeto.indexOf('d')] = 70;
    mt[70][alfabeto.indexOf(' ')] = 71; // aceptada

    mt[0][alfabeto.indexOf('_')] = 3;
    String identificadores = minusculas + mayusculas + digitos + pesos + guionBajo;
    for (char c : identificadores.toCharArray()) {
      mt[3][alfabeto.indexOf(c)] = 1;
    }

    String identificadoresSinGuionBajo = minusculas + mayusculas + pesos;
    for (char c : identificadoresSinGuionBajo.toCharArray()) {
      int simbolo = alfabeto.indexOf(c);
      if (mt[0][simbolo] == -1) {
        mt[0][simbolo] = 1;
      }
    }

    for (char c : identificadores.toCharArray()) {
      mt[1][alfabeto.indexOf(c)] = 1;
    }
    mt[1][alfabeto.indexOf(' ')] = 2; //aceptada

    String cadenaIdentificadores = minusculas + mayusculas + digitos + pesos + guionBajo;
    for(int i = 24 ; i <= 71 ; i++) {
      if (esEstadoPalabraReservada(i)) {
        continue;
      }
      for (int j = 0 ; j < cadenaIdentificadores.length() ; j++) {
        char c = cadenaIdentificadores.charAt(j);
        int simbolo = alfabeto.indexOf(c);
        if (mt[i][simbolo] == -1) {
          mt[i][simbolo] = 1;
        }
      }
    }

    mt[0][alfabeto.indexOf('>')] = 13;
    mt[0][alfabeto.indexOf('<')] = 13;
    mt[13][alfabeto.indexOf(' ')] = 14; //aceptada

    mt[0][alfabeto.indexOf('!')] = 15;
    mt[0][alfabeto.indexOf('=')] = 15;
    mt[15][alfabeto.indexOf('=')] = 72;
    mt[72][alfabeto.indexOf(' ')] = 73; //aceptada

    mt[0][alfabeto.indexOf('&')] = 16;
    mt[16][alfabeto.indexOf('&')] = 17;
    mt[17][alfabeto.indexOf(' ')] = 18; //aceptada

    mt[0][alfabeto.indexOf('|')] = 19;
    mt[19][alfabeto.indexOf('|')] = 20;
    mt[20][alfabeto.indexOf(' ')] = 21; //aceptada

    mt[0][alfabeto.indexOf('!')] = 22;
    mt[22][alfabeto.indexOf(' ')] = 23; //aceptada

    mt[0][alfabeto.indexOf('+')] = 4;
    mt[0][alfabeto.indexOf('-')] = 4;

    for(char c: digitos.toCharArray()) {
      mt[0][alfabeto.indexOf(c)] = 8;
      mt[4][alfabeto.indexOf(c)] = 8;
      mt[8][alfabeto.indexOf(c)] = 8;

      mt[5][alfabeto.indexOf(c)] = 6;
      mt[6][alfabeto.indexOf(c)] = 6;

      mt[9][alfabeto.indexOf(c)] = 11;
      mt[11][alfabeto.indexOf(c)] = 11;
    }
    mt[8][alfabeto.indexOf(' ')] = 10; //aceptada
    mt[6][alfabeto.indexOf(' ')] = 7; //aceptada
    mt[11][alfabeto.indexOf(' ')] = 12; //aceptada

    mt[8][alfabeto.indexOf('.')] = 5;
    mt[8][alfabeto.indexOf('e')] = 9;
    mt[8][alfabeto.indexOf('E')] = 9;

    mt[6][alfabeto.indexOf('e')] = 9;
    mt[6][alfabeto.indexOf('E')] = 9;

  }

}