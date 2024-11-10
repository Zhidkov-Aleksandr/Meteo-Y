import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherForecastApplication {
    private WeatherDataProvider weatherDataProvider;

    public WeatherForecastApplication(WeatherDataProvider provider) {
        this.weatherDataProvider = provider;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        double lat = 0.0;
        double lon = 0.0;

        while (true) {
            try {
                System.out.println("Вас приветсвует сервис для вывода метеорологических данных Meteo-Y");
                System.out.println("Введите широту (lat): ");
                lat = Double.parseDouble(scanner.nextLine());
                System.out.print("Введите долготу (lon): ");
                lon = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException var10) {
                System.out.println("Ошибка ввода. Пожалуйста, введите корректные числовые значения.");
            }
        }

        String response = this.weatherDataProvider.getWeatherData(lat, lon);
        if (response != null) {
            System.out.println("Ответ от API:\n" + response);
            Weather weather = new Weather(response);
            System.out.println("Температура: " + weather.getTemperature() + " °C");
            System.out.print("Введите период (от 1 до 7 дней): ");
            int daysLimit = 0;
            daysLimit = Integer.parseInt(scanner.nextLine());
            if (daysLimit < 1 || daysLimit > 7) {
                throw new IllegalArgumentException("Период должен быть от 1 до 7 дней.");
            }

            JSONArray forecasts = (new JSONObject(response)).getJSONArray("forecasts");
            weather.computeAverageTemperature(forecasts);
            System.out.println("Средняя температура за " + daysLimit + " дней: " + weather.getAverageTemperature() + " °C");
            System.out.println("Приятной Вам погоды и до новых встреч");

        }

        scanner.close();
    }

    public static void main(String[] args) {
        WeatherDataProvider provider = new YandexWeatherDataProvider();
        WeatherForecastApplication app = new WeatherForecastApplication(provider);
        app.run();
    }
}