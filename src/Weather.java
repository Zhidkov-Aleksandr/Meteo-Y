import org.json.JSONArray;
import org.json.JSONObject;

public class Weather {
    private double temperature;
    private double averageTemperature;

    public Weather(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject fact = jsonObject.getJSONObject("fact");
        this.temperature = fact.getDouble("temp");
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getAverageTemperature() {
        return this.averageTemperature;
    }

    public void computeAverageTemperature(JSONArray forecasts) {
        double totalTemperature = 0.0;

        for(int i = 0; i < forecasts.length(); ++i) {
            JSONObject forecast = forecasts.getJSONObject(i);
            double tempAvg = forecast.getJSONObject("parts").getJSONObject("day").getDouble("temp_avg");
            totalTemperature += tempAvg;
        }

        this.averageTemperature = totalTemperature / (double)forecasts.length();
    }
}
