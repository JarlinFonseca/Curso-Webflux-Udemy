package com.debuggeandoideas.eats_hub_catalog;

import com.debuggeandoideas.eats_hub_catalog.enums.PriceEnum;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class EatsHubCatalogApplication implements CommandLineRunner {

	@Autowired
	private Repo repo;

	@Autowired
	private RestaurantCatalogService restaurantCatalogService;

	public static void main(String[] args) {
		SpringApplication.run(EatsHubCatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*		this.repo.findAll()
				.doOnNext(System.out::println)
				.subscribe();*/

/*		this.restaurantCatalogService.readAll()
				.doOnNext(System.out::println)
				.subscribe();*/


	/*	this.restaurantCatalogService.readByCuisineType("Mexican")
				.doOnNext(System.out::println)
				.subscribe();*/

/*		this.restaurantCatalogService.readByName("Steakhouse 212")
				.doOnNext(System.out::println)
				.subscribe();*/

/*		this.restaurantCatalogService.readByPriceRangeIn(List.of(PriceEnum.CHEAP, PriceEnum.MEDIUM))
				.doOnNext(System.out::println)
				.subscribe();*/

		this.restaurantCatalogService.readByCity("New York")
				.doOnNext(System.out::println)
				.subscribe();
	}

}
