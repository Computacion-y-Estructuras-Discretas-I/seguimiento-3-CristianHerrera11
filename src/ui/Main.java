package ui;

import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;
    private TablasHash tablasHash;
    

    public Main() {
        sc = new Scanner(System.in);

    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                    break;

                case 3:
                    System.out.println("Chao");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {

        int a = s.length();

        PilaGenerica<Character> pilaCaracter = new PilaGenerica<>(a);

        for(int i = 0; i < s.length(); i++){

            char caracter = s.charAt(i);

            if(caracter == '(' || caracter == '[' || caracter == '{'){

                pilaCaracter.Push(caracter);
            
            }

            if(caracter == ')' || caracter == ']' || caracter == '}'){


                char ultimo_caracter;

                try {
                    ultimo_caracter = pilaCaracter.Pop();
                } catch (Exception e) {
                    System.out.println("Error: símbolo de cierre '" + caracter + "' sin apertura correspondiente.");
                    return false;
                }

                if ((caracter == ')' && ultimo_caracter != '(') ||
                    (caracter == ']' && ultimo_caracter != '[') ||
                    (caracter == '}' && ultimo_caracter != '{')) {
                    System.out.println("Error: símbolo de cierre '" + caracter + "' no coincide con apertura '" + ultimo_caracter + "'.");
                    return false;
                }

               
            }
        
        }

        return true; // verdadero si coincide el primer caracter con el ultim caracter.

    }


    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     * @throws Exception 
     */

    public void encontrarParesConSuma(int[] numeros, int objetivo) throws Exception {

        int a = numeros.length;

        tablasHash = new TablasHash(a);

        // Elimino numeros repetidos
        int[] numerosUnicos = new int[a];
        int index = 0;
        for (int i = 0; i < a; i++) {
            boolean repetido = false;
            for (int j = 0; j < index; j++) {
                if (numeros[i] == numerosUnicos[j]) {
                    repetido = true;
                    break;
                }
            }
            if (!repetido) {
                numerosUnicos[index++] = numeros[i];
            }
        }

        // Insertar números únicos en la tabla hash (Llave indice, valor el numerito)
        for (int i = 0; i < index; i++) {
            tablasHash.insert(i, numerosUnicos[i]);
        }

        System.out.println("Pares que suman " + objetivo + ":");

        for (int i = 0; i < index; i++) {
            for (int j = i + 1; j < index; j++) {
                if (numerosUnicos[i] + numerosUnicos[j] == objetivo) {
                    System.out.println("(" + numerosUnicos[i] + "," + numerosUnicos[j] + ")");
                }else{
                    System.out.println("No se encontró ningun par que sume: " + objetivo);
                }
            }
        }
    }
    


    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
