package Gysmoy;

// Elementos importados
import java.util.Scanner;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // Entradas de texto
        Scanner input = new Scanner(System.in);
        BufferedReader cadena = new BufferedReader(new InputStreamReader(System.in));
        
        // Declaracion de primeros valores y variables
        int cant, i, counterpass = 1;
        double Sbruto, Sneto, bonificacion, SNP;
        String reset = "\u001B[0m", red = "\u001B[31m", green = "\u001B[32m", cyan = "\u001B[36m";
        String RPass = "51d710d17a829ca73420224ab162ca8b9ae7f85ce66fcf81df3d80c96dc6aef7", pass;
        boolean ident;

        // Header
        for (i = 0; i < 50; i++){System.out.print("=");}
        System.out.println("\n" + cyan + "         ✢ ＭＯＬＩＮＯＳ ＣＯＭＯＬＳＡ ✢" + reset);
        for (i = 0; i < 50; i++){System.out.print("=");}
        System.out.println("");
        
        // Login - Password: 20Gys20TDC20Moy01
        /* Se necesita dar seguridad al programa para que
        solo personal autorizado tenga acceso a ella*/
        do {
            pass = JOptionPane.showInputDialog("☥ Iniciar sesión...\nContraseña", "20Gys20TDC20Moy01");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b: digest){sb.append(String.format("%02x", b & 0xff));}
            pass = sb.toString();
            if (!pass.equals(RPass)) {
                System.out.print(red + "¡Contraseña incorrecta!" + reset);
                System.out.println(green + "    " + (3 - counterpass) + " intentos restantes..." + reset);
            }
            else System.out.println(green + "¡Acceso permitido!" + reset);
            counterpass += 1;
            if (counterpass > 3){
                System.out.println(red + "¡Acceso denegado!" + reset);
                System.exit(0);
            }
        } while (!pass.equals(RPass));

        for (i = 0; i < 50; i++){System.out.print("=");}
        System.out.println("");

        // Petición de cantidad: Se usara en casi todo el programa para los bucles, por lo tanto se debe validar
        while (true){
            try {
                System.out.print("¿Cuántos trabajadores desea registrar?: ");
                cant = input.nextInt();
                System.out.println(green + "Se escogió agregar " + cant + " trabajadores" + reset);
                break;
            } catch (Exception e) {
                System.out.println(red + "  ✘ !Ingrese solo valores enteros¡" + reset);
                input.next();
            }
        }

        for (i = 0; i < 50; i++){System.out.print("=");}
        System.out.println("");

        // Declaración y dimensionamiento de los arreglos
        String apellidos[] = new String[cant], nombres[] = new String[cant], estado[] = new String[cant];
        int horas[] = new int[cant];
        double PagoxH[] = new double[cant];

        // Petición de datos
        for (i = 0; i < cant; i++){
            System.out.println(cyan + "Trabajador " + (i + 1) + reset);
            System.out.print("☍ Apellidos: ");
            apellidos[i] = cadena.readLine().toUpperCase().trim();
            System.out.print("☍ Nombres: ");
            nombres[i] = cadena.readLine().toUpperCase().trim();
            System.out.println("  Estado civil (RENIEC)");
            System.out.println("  ⤷ CASADO");
            System.out.println("  ⤷ SOLTERO");
            System.out.println("  ⤷ VIUDO");
            System.out.println("  ⤷ DIVORCIADO");

            // Validación de Estado civil: Crucial porque se toma en cuenta en el muestreo de datos
            String ecivil = "CASADOSOLTEROVIUDODIVORCIADO";
            do {
                System.out.print("☍ Opción: ");
                estado[i] = cadena.readLine().toUpperCase().trim();
                if (ecivil.contains(estado[i])) {
                    System.out.println(green + "  ✔ ¡Agregado correctamente!" + reset);
                    ident = true;
                } else {
                    System.out.println(red + "  ✘ !Escriba una opción correcta¡" + reset);
                    ident = false;
                }
            } while (ident == false);

            // Validación de horas: Necesariamente valor entero
            while (true){
                try {
                    System.out.print("☍ Horas trabajadas: ");
                    horas[i] = input.nextInt();
                    System.out.println(green + "  ✔ ¡Agregado correctamente!" + reset);
                    break;
                } catch (Exception e) {
                    System.out.println(red + "  ✘ !Ingrese solo valores enteros¡" + reset);
                    input.next();
                }
            }

            // Validación de pago: Necesariamente valor numérico
            while (true){
                try {
                    System.out.print("☍ Pago x hora: ");
                    PagoxH[i] = input.nextDouble();
                    System.out.println(green + "  ✔ ¡Agregado correctamente!" + reset);
                    break;
                } catch (Exception e) {
                    System.out.println(red + "  ✘ !Ingrese solo valores numéricos¡" + reset);
                    input.next();
                }
            }
            for (int j = 0; j < 50; j++){System.out.print("-");}
            System.out.println("");
        }
        System.out.println("");

        // Muestreo de datos del trabajador
        System.out.println(cyan + "   》 Ｒｅｐｏｒｔｅ ｄｅ ｔｒａｂａｊａｄｏｒｅｓ 《" + reset);
        System.out.println();
        for (i = 0; i < cant; i++) {
            System.out.println("♙ Trabajador " + apellidos[i] + " " + nombres[i]);
            Sbruto = horas[i] * PagoxH[i];
            System.out.printf("  ☍ Sueldo Bruto: %5.2f%n", Sbruto);
            SNP = Sbruto * (0.13);
            System.out.printf("  ☍ Descuento SNP: \u001B[31m- %5.2f\u001B[0m%n", SNP);

            // Bonificación familiar por ser CASADO
            if (estado[i].equals("CASADO")){
                bonificacion = Sbruto * 0.07;
                System.out.printf("  ☍ Bonificación: \u001B[32m+ %5.2f\u001B[0m%n", bonificacion);
            } else {
                bonificacion = 0.0;
            }
            Sneto = Sbruto - SNP + bonificacion;
            System.out.println("  ☍ Sueldo Neto: " + Sneto);
            System.out.println("");
        }
    }
}