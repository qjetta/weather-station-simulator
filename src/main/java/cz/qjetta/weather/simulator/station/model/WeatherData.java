package cz.qjetta.weather.simulator.station.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherData {
	private LocalDateTime timestamp;
	private double temp;
	private int humidity;
	private int wind;
	private double precipitation;
	private Metadata metadata;

}