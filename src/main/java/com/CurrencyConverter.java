package com;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class CurrencyConverter {
   
    public static void main (String[] args) throws IOException{
    
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter the base currency:");
        String baseCurrency = scanner.nextLine();
        System.out.println("Enter the target currency:");
        String targetCurrency = scanner.nextLine();
        System.out.println("Enter the amount you wish to convert:");
        BigDecimal amount = scanner.nextBigDecimal();
    
        String urlString = "https://v6.exchangerate-api.com/v6/YOUR-API-KEY/latest/" + baseCurrency.toUpperCase();
    
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(urlString)
            .get()
            .build();

        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();
        JSONObject jsonObject = new JSONObject(stringResponse);
        if(jsonObject.has("conversion_rates")) {
            JSONObject conversionRatesObject = jsonObject.getJSONObject("conversion_rates");
            if(conversionRatesObject.has(targetCurrency.toUpperCase())) {
                BigDecimal rate = conversionRatesObject.getBigDecimal(targetCurrency.toUpperCase());
                BigDecimal result = rate.multiply(amount);
                System.out.println("The converted amount is: " + result);
            } else {
                System.out.println("The target currency is not supported.");
            }
        } else {
            System.out.println("The 'conversion_rates' object does not exist in the JSON response.");
        }
    }
}
