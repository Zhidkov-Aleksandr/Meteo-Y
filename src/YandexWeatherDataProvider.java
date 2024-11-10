import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class YandexWeatherDataProvider implements WeatherDataProvider {
    private static final String API_KEY = "6ed6927a-e72d-4dfb-82da-7d27a19b27fb"; // если мой ключ перестанет работать, введите пожалуйста свой

    public YandexWeatherDataProvider() {
    }

    public String getWeatherData(double lat, double lon) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon)).header("X-Yandex-API-Key", "6ed6927a-e72d-4dfb-82da-7d27a19b27fb").GET().build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.out.println("Ошибка: " + response.statusCode());
                return null;
            } else {
                return (String) response.body();
            }
        } catch (InterruptedException | IOException var8) {
            System.out.println("Ошибка при выполнении запроса: " + var8.getMessage());
            return null;
        }
    }
}