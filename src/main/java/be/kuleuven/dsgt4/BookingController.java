package be.kuleuven.dsgt4;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import static reactor.core.publisher.Mono.fromCallable;

@RestController
public class BookingController {
    @Autowired
    Firestore db;

    private final FlightService flightService;
//    private final CarRentalService carRentalService;
    private final HotelService hotelService;

    public BookingController(FlightService flightService,
//                             CarRentalService carRentalService,
                             HotelService hotelService) {

        this.flightService = flightService;
//        this.carRentalService = CarRentalService;
        this.hotelService = hotelService;

    }

    @PostMapping("/booking")
    public Mono<ResponseEntity<UserBookingMessage>> reserveTrip(@RequestBody BookingRequest bookingRequest) {
        String email = bookingRequest.getEmail();
        LocalDate startDate = LocalDate.parse(bookingRequest.getStartDate());
        int adults = bookingRequest.getAdults();
        int children = bookingRequest.getChildren();

        UUID buuid = UUID.randomUUID();
        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIDNIGHT);
        LocalDate endDate = startDate.plusDays(100);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MIDNIGHT);

        System.out.println("endDateTime" + endDateTime.toString());

        Mono<ResponseEntity<UserBookingMessage>> result = flightService.getAvailableFlight("BRU", "KEF", startDateTime.toString(), endDateTime.toString(), (adults + children))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No available flights for the selected date and route")))
                .flatMap(availableFlight -> Mono.just(availableFlight)
                            .flatMap(flightService::getAvailableCheapestSeat)
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No available seat for the selected flight")))
                            .flatMap(flightService::reserveSeat)
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "The seat for the selected flight has been reserved by others")))
                        .map(flightMessage -> Tuples.of(availableFlight, flightMessage)) // Pass along availableFlight and flightMessage
                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "booking failed for the selected flight and seat")))
                .flatMap(tuple -> hotelService.getAvailableHotel("KEF", (adults + children))
                                    .flatMap(availableHotel -> Mono.just(availableHotel)
                                    .flatMap(hotel -> hotelService.getCheapestRoom(hotel.getId(), startDate.toString(), endDate.toString())
                                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No available room for the selected Hotel")))
                                            .flatMap(hotelService::reserveCheapestRoom)
                                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "The room for the selected hotel has been reserved by others"))))
                                    )
                                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "booking failed for the selected hotel and room")))
                                    .map(hotelMessage -> Tuples.of(tuple.getT1(), tuple.getT2(), hotelMessage)) // Pass along availableFlight, flightMessage, and hotelMessage
                )
                .flatMap(tuple -> {
                    UserBookingMessage ubm = new UserBookingMessage(buuid, LocalDateTime.now(), email, tuple.getT1(), tuple.getT2(), tuple.getT3());
                    return fromCallable(() -> {
                        this.db.collection("userbookingmessage").document(ubm.getId().toString()).set(ubm.toDoc()).get();
                        return ResponseEntity.ok(ubm);
                    });
                });

        return result.onErrorResume(throwable -> {
            if (throwable instanceof ExecutionException) {
                return Mono.error(throwable);
            } else {
                return Mono.error(new RuntimeException(throwable));
            }
        });
    }

    @PostMapping("/confirm")
    public Mono<ResponseEntity<UserBookingMessage>> confirmTrip(@RequestBody UserBookingMessage userBookingMessage) {

        UUID buuid = userBookingMessage.getId();
        String flightLink = userBookingMessage.getFlightMessage().getBookingLink().getHref();
        String hotelLink = userBookingMessage.getHotelMessage().getBookingLink().getHref();

        Mono<ResponseEntity<UserBookingMessage>> result = flightService.bookByLink(flightLink)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "flight book failed")))
                .flatMap(flightBookingMessage -> hotelService.bookByLink(hotelLink)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "hotel book failed")))
                        .flatMap(hotelBookingMessage -> {

                            return fromCallable(() -> {
                                Map<String, Object> updateContent = UserBookingMessage.updateBookingMessage(flightBookingMessage, hotelBookingMessage);
                                this.db.collection("userbookingmessage").document(buuid.toString()).update(updateContent).get();
                                Map<String, Object> data = this.db.collection("userbookingmessage").document(buuid.toString()).get().get().getData();
                                UserBookingMessage ubm = UserBookingMessage.fromDoc(data);

                                return ResponseEntity.ok(ubm);
                            });
                        })
                );

        return result.onErrorResume(throwable -> {
            if (throwable instanceof ExecutionException) {
                return Mono.error(throwable);
            } else {
                return Mono.error(new RuntimeException(throwable));
            }
        });

    }

    @GetMapping("/getUserOrders")
    public List<UserBookingMessage> getUserOrders(@RequestParam String email) throws InterruptedException, ExecutionException {
        List<UserBookingMessage> userOrders = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        // Retrieve all documents from the "userbookingmessage" collection
        List<QueryDocumentSnapshot> documents = db.collection("userbookingmessage").get().get().getDocuments();

        // Extract orders from the documents
        for (QueryDocumentSnapshot document : documents) {
            Map<String, Object> data = document.getData();
            UserBookingMessage order = UserBookingMessage.fromDoc(data);
            if (order != null && order.getEmail().equals(email)) {
                userOrders.add(order);
            }
        }

        return userOrders;
    }

}

