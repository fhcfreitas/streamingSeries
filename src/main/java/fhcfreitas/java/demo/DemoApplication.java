package fhcfreitas.java.demo;

import fhcfreitas.java.demo.main.Main;
import fhcfreitas.java.demo.model.EpisodeData;
import fhcfreitas.java.demo.model.SeasonData;
import fhcfreitas.java.demo.model.SeriesData;
import fhcfreitas.java.demo.repository.SeriesRepository;
import fhcfreitas.java.demo.service.ConsumingAPI;
import fhcfreitas.java.demo.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private SeriesRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.menu();
	}
}
