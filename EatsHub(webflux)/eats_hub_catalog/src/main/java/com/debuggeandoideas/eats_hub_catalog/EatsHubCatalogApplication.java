package com.debuggeandoideas.eats_hub_catalog;

import com.debuggeandoideas.eats_hub_catalog.collections.ReservationCollection;
import com.debuggeandoideas.eats_hub_catalog.enums.PriceEnum;
import com.debuggeandoideas.eats_hub_catalog.repositories.ReservationRepository;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.ReservationCrudService;
import com.debuggeandoideas.eats_hub_catalog.services.definitions.RestaurantCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class EatsHubCatalogApplication implements CommandLineRunner {

	@Autowired
	private Repo repo;

	@Autowired
	private RestaurantCatalogService restaurantCatalogService;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservationCrudService reservationCrudService;

	public static void main(String[] args) {
		SpringApplication.run(EatsHubCatalogApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
*//*		this.repo.findAll()
				.doOnNext(System.out::println)
				.subscribe();*//*

*//*		this.restaurantCatalogService.readAll()
				.doOnNext(System.out::println)
				.subscribe();*//*


	*//*	this.restaurantCatalogService.readByCuisineType("Mexican")
				.doOnNext(System.out::println)
				.subscribe();*//*

*//*		this.restaurantCatalogService.readByName("Steakhouse 212")
				.doOnNext(System.out::println)
				.subscribe();*//*

*//*		this.restaurantCatalogService.readByPriceRangeIn(List.of(PriceEnum.CHEAP, PriceEnum.MEDIUM))
				.doOnNext(System.out::println)
				.subscribe();*//*

//		this.restaurantCatalogService.readByCity("New York")
//				.doOnNext(System.out::println)
//				.subscribe();
		System.out.println("=== STARTING RESERVATION INSERT TESTS ===\n");

		final var parrillaModernaID = "0ee619ba-e95f-4103-99f7-ee9cdf831d90";
		final var cafeNostalgiaID = "be33011c-13dd-45b9-a60e-e9adb8f4e022";


		final var sarahReservation = createTestReservation(
				parrillaModernaID,
				"Sarah Johnson",
				4,
				"2025-06-15",
				"19:30",
				"Window table preferred"
		);

		final var michaelReservation = createTestReservation(
				parrillaModernaID,
				"Michael Davis",
				2,
				"2025-06-16",
				"20:00",
				"Anniversary dinner - romantic table"
		);

		final var emmaReservation = createTestReservation(
				cafeNostalgiaID,
				"Emma Wilson",
				6,
				"2025-06-17",
				"18:00",
				"Family birthday celebration"
		);

		final var sarahReservationCreated = reservationCrudService.createReservation(sarahReservation)
				.block();

		System.out.println("Sarah reservation: " + sarahReservationCreated.getId());

		final var michaelReservationCreated = reservationCrudService.createReservation(michaelReservation)
				.block();
		System.out.println("Michael reservation: " + michaelReservationCreated.getId());

		final var emmaReservationCreated = reservationCrudService.createReservation(emmaReservation)
				.block();
		System.out.println("Emma reservation: " + emmaReservationCreated.getId());

		System.out.println("=== FINISHED RESERVATION INSERT TESTS ===");

		System.out.println("=== INIT RESERVATION UPDATE TESTS ===");

		final var michaelReservationToUpdate = reservationCrudService.readByReservationId(michaelReservationCreated.getId()).block();

		michaelReservationToUpdate.setDate("20:30");
		michaelReservationToUpdate.setPartySize(3);

		final var michaelReservationUpdated = this.reservationRepository.save(michaelReservationToUpdate).block();

		System.out.println("michael reservation updated: " + michaelReservationUpdated.getDate());
		System.out.println("michael reservation updated: " + michaelReservationUpdated.getPartySize());

		System.out.println("=== FINISHED RESERVATION INSERT TESTS ===");


		Thread.sleep(60000);
		System.out.println("=== INIT RESERVATION DELETE TESTS ===");
		this.reservationCrudService.deleteReservation(michaelReservationCreated.getId()).block();
		System.out.println("=== FINISHED RESERVATION DELETE TESTS ===");


	}*/

	@Override
	public void run(String... args) throws Exception {


		final var parrillaModernaID = "0ee619ba-e95f-4103-99f7-ee9cdf831d90";
		final var unavailableID = "dfcbe98d-392b-4b93-9a49-27005223d15d";


        /*final var michaelReservation = createTestReservation(
				parrillaModernaID,
                "Michael Davis",
                2,
                "2025-06-16",
                "19:00",
                "Anniversary dinner - romantic table"
        );

        final var michaelReservationCreated = reservationCrudService.createReservation(michaelReservation)
        .block();

        System.out.println("michaelReservationCreated: " + michaelReservationCreated.getId());*/

		final var michaelReservationToUpdate = reservationCrudService.readByReservationId(UUID.fromString("6d5f1c96-c739-4774-b78d-612c2271c2b4")).block();

		michaelReservationToUpdate.setTime("15:30");
		michaelReservationToUpdate.setPartySize(3);

		final var michaelReservationUpdated = this.reservationCrudService.updateReservation(UUID.fromString("6d5f1c96-c739-4774-b78d-612c2271c2b4"), michaelReservationToUpdate).block();

		System.out.println("michael reservation updated: " + michaelReservationUpdated.getDate());
		System.out.println("michael reservation updated: " + michaelReservationUpdated.getPartySize());

	}


	private ReservationCollection createTestReservation(String restaurantId, String customerName,
	                                                    int partySize, String date, String time, String notes) {
		return ReservationCollection.builder()
				.id(UUID.randomUUID())
				.restaurantId(restaurantId)
				.customerName(customerName)
				.partySize(partySize)
				.date(date)
				.time(time)
				.notes(notes)
				.build();
	}


}
