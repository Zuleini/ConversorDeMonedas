import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] monedas = {"USD", "EUR", "ARS", "BOB", "COP", "DOP"};
        String[] descMonedas = {"Dolares", "Euros", "Peso Argentino", "Peso Boliviano", "Peso Colombiano", "Peso Dominicano"};
        String menuBaseTop = """
                *********************************************************
                BIENVENIDOS AL CONVERSOR DE MONEDAS!
                *********************************************************
                """;
        String menuDestinoTop = """
                *********************************************************
                MONEDA DE CAMBIO
                *********************************************************
                """;

        int opcion;
        do {
            System.out.println(menuBaseTop);
            for (int i = 0; i < descMonedas.length; i++) {
                System.out.printf("  %d - %s\n", i+1, descMonedas[i]);
            }
            System.out.println("  0 - Salir");
            System.out.println("Elija la Moneda que desea Convertir del Menu: ");
            opcion = scanner.nextInt();

            if (opcion > 0 && opcion-1 < descMonedas.length) {
                int opcionBase = opcion;
                System.out.println(menuDestinoTop);
                for (int i = 0; i < descMonedas.length; i++) {
                    if (i != opcionBase-1)
                      System.out.printf("  %d - %s\n", i+(i<opcionBase-1? 1 : 0), descMonedas[i]);
                }
                System.out.println("  0 - Salir");
                opcion = scanner.nextInt();
                if (opcion > 0 && opcion < descMonedas.length) {
                    convertirMoneda(monedas[opcionBase-1], monedas[opcion+(opcion>opcionBase-1? 1 : 0)-1], scanner);
                    System.out.println("  0 - Salir");
                    opcion = scanner.nextInt();
                }
            }
        } while (opcion != 0);
        System.out.println("**********************************************************");
        System.out.println(" ");
        System.out.println("GRACIAS POR USAR EL CONVERSOR DE MONEDAS!");
        System.out.println(" ");
        System.out.println("***********************************************************");
        scanner.close();
    }

    private static void convertirMoneda(String baseCode, String targetCode, Scanner scanner) {
        System.out.printf("Convirtiendo de %s a %s\n", baseCode, targetCode);
        System.out.println("Ingrese el monto total a convertir: ");
        double amount = scanner.nextDouble();
        ConsultaDeCambio conversion = new ConsultaDeCambio(baseCode, targetCode, amount);
        try {
            JsonObject conversionObject = conversion.convert();
            double result = conversionObject.get("conversion_result").getAsDouble();
            double rate = conversionObject.get("conversion_rate").getAsDouble();
            System.out.println(amount + " " + baseCode + " son " + result + " " + targetCode + " Tipo de Cambio: " + rate);
            System.out.println("********************************************************************");
            System.out.println("SU MONEDA A SIDO CONVERTIDA A LA OPCION DESEADA!");
            System.out.println("*********************************************************************");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }
}
