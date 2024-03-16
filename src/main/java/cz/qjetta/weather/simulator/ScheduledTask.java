package cz.qjetta.weather.simulator;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cz.qjetta.weather.simulator.station.model.Metadata;
import cz.qjetta.weather.simulator.station.model.WeatherData;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

	private final String apiUrl = "http://localhost:9073/weather";

	private final RestTemplate restTemplate;

	@Scheduled(fixedRate = 15000// 15 seconds
			, initialDelay = 1000)
	public void sendRequest1() {
		String stationId = "s1";
		sendTemperature(stationId);
	}

	@Scheduled(fixedRate = 30000// 30 seconds
			, initialDelay = 1000)
	public void sendRequest2() {
		String stationId = "s2";
		sendTemperature(stationId);
	}

	@Scheduled(fixedRate = 45000// 45 seconds
			, initialDelay = 1000)
	public void sendRequest3() {
		String stationId = "s3";
		sendTemperature(stationId);
	}

	@Scheduled(fixedRate = 60000// 60 seconds
			, initialDelay = 1000)
	public void sendRequest4() {
		String stationId = "s4";
		sendTemperature(stationId);
	}

	private void sendTemperature(String stationId) {
		WeatherData weatherData = createWeatherData(stationId);
		ResponseEntity<String> responseEntity = restTemplate
				.postForEntity(apiUrl, weatherData, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
			System.out.println(stationId);
		} else {
			System.out.println("Failed to insert weather data. Status code: "
					+ responseEntity.getStatusCode());
		}
	}

	private WeatherData createWeatherData(String stationId) {
		return WeatherData.builder()
				.metadata(Metadata.builder().stationId(stationId).build())
				.humidity(getRandomInt(50, 90)).temp(getRandomDouble(-20, 40))
				.precipitation(getRandomDouble(0, 20))
				.timestamp(LocalDateTime.now()).build();
	}

	public static double getRandomDouble(double min, double max) {
		Random random = new Random();
		String str = String.format("%1.2f", random.nextDouble(min, max));
		return Double.valueOf(str);
	}

	public static int getRandomInt(int min, int max) {
		Random random = new Random();
		return random.nextInt(min, max);
	}
}