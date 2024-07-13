import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateService {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final String API_KEY = "TU_API_KEY";  // Reemplaza con tu API key
    private static final String[] CURRENCIES = {"PEN", "CLP", "COP", "ARS", "USD", "BRL"};

    public Map<String, Double> getExchangeRates(String baseCurrency) throws Exception {
        String urlStr = API_URL + baseCurrency + "?apikey=" + API_KEY;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();
        JsonObject ratesJson = jsonObject.getAsJsonObject("rates");

        Map<String, Double> rates = new HashMap<>();
        for (String currency : CURRENCIES) {
            if (ratesJson.has(currency)) { // Verifica si la API retorna el tipo de cambio para la moneda
                rates.put(currency, ratesJson.get(currency).getAsDouble());
            }
        }

        return rates;
    }


    public void showExchangeRates(Map<String, Double> rates) {
        System.out.println("\nTipos de cambio:");
        for (String currency : CURRENCIES) {
            if (rates.containsKey(currency)) {
                System.out.println("1 USD = " + rates.get(currency) + " " + currency);
            }
        }
    }


    public double convertCurrency(Map<String, Double> rates, String fromCurrency, String toCurrency, double amount) {
        if (!rates.containsKey(fromCurrency) || !rates.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Código de moneda inválido");
        }
        double rate = rates.get(toCurrency) / rates.get(fromCurrency);
        return amount * rate;
    }
}

