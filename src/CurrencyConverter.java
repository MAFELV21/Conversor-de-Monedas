import java.util.*;

public class CurrencyConverter {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ExchangeRateService rateService = new ExchangeRateService();
    private static final ConversionHistory history = new ConversionHistory();
    private static final String[] CURRENCIES = {"PEN", "CLP", "COP", "ARS", "USD", "BRL"};

    public static void main(String[] args) {
        try {
            Map<String, Double> rates = rateService.getExchangeRates("USD");

            while (true) {
                showMenu();
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> rateService.showExchangeRates(rates);
                    case 2 -> performConversion(rates);
                    case 3 -> history.showHistory();
                    case 4 -> {
                        System.out.println("Saliendo del programa...");
                        return;
                    }
                    default -> System.out.println("Opción inválida. Inténtalo de nuevo.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        System.out.println("\nConversor de Monedas");
        System.out.println("1. Mostrar tipos de cambio");
        System.out.println("2. Convertir moneda");
        System.out.println("3. Ver historial de conversiones");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void performConversion(Map<String, Double> rates) {
        try {
            System.out.println("Seleccione su moneda de origen (USD, PEN, CLP, COP, ARS, BRL): ");
            String fromCurrency = scanner.nextLine().toUpperCase();
            System.out.println("Seleccione su moneda de destino (USD, PEN, CLP, COP, ARS, BRL): ");
            String toCurrency = scanner.nextLine().toUpperCase();
            System.out.print("Ingrese la cantidad a convertir: ");
            double amount = Double.parseDouble(scanner.nextLine());

            double convertedAmount = rateService.convertCurrency(rates, fromCurrency, toCurrency, amount);
            System.out.println(amount + " " + fromCurrency + " = " + convertedAmount + " " + toCurrency);

            history.addRecord(amount + " " + fromCurrency + " = " + convertedAmount + " " + toCurrency);
        } catch (Exception e) {
            System.out.println("Error en la conversión: " + e.getMessage());
        }
    }
}
