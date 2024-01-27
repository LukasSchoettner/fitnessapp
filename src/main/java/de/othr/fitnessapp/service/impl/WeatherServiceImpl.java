package de.othr.fitnessapp.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.othr.fitnessapp.service.WeatherServiceI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Configuration
@Log4j2
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherServiceI {
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public String getTempForecast(String date) {
        String apiUrl = String.format("https://api.brightsky.dev/weather?lat=%s&lon=%s&date=%s", "49.02", "12.1", date);

        RestTemplate restTemplate = restTemplateBuilder.build();

        Optional<WeatherApiResponse> response;
        String averageTemp = "";

        try {
            response = Optional.ofNullable(restTemplate.getForObject(apiUrl, WeatherApiResponse.class));
        } catch (RestClientException e) {
            return String.format("The temperature forecast for the date %s is not available yet.", date);
        }

        if (response.isPresent()) {
            OptionalDouble averageTempOptional = response.get().getWeather()
                                                                .stream()
                                                                .mapToDouble(WeatherInfo::getTemperature)
                                                                .average();

            if (averageTempOptional.isPresent()) {
                DecimalFormat df = new DecimalFormat("#.#");
                averageTemp = String.valueOf(df.format(averageTempOptional.getAsDouble()));
            }
        }
        return String.format("The average temperature in Regensburg on %s will be %s °C", date, averageTemp);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherApiResponse {
        private List<WeatherInfo> weather;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherInfo {
        private Double temperature;
    }

}
