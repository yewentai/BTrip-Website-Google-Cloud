package be.kuleuven.dsgt4;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.cloud.firestore.Firestore;

import reactor.core.publisher.Mono;

@RestController
public class FlightController {
    @Autowired
    Firestore db;

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    public Mono <List<FlightDTO>> getFlightsByDate(@RequestParam("startDate") String startDate,
                                               @RequestParam("adults") int adults,
                                               @RequestParam("children") int children,
                                               @RequestParam("totalPrice") double totalPrice) {
        LocalDateTime date = LocalDateTime.parse(startDate);
        LocalDateTime nextDate = date.plusDays(1);
        String endDate = nextDate.toString();
        return flightService.getAllFlights("BRU","LAX",startDate,endDate);
    }

//    @RequestMapping(value = "/book", method = RequestMethod.POST)
//    public String bookFlight(
//            @RequestParam("flight") String flight,
//            @RequestParam("email") String email) throws ExecutionException, InterruptedException {
//        UUID buuid = UUID.randomUUID();
//        System.out.println(flight);
//        JSONObject flightJson = new JSONObject(flight);
//        FlightDTO flightDTO = new FlightDTO(
//                flightJson.getString("departureTime"),
//                flightJson.getString("arrivalTime"),
//                flightJson.getString("duration"),
//                flightJson.getDouble("price")
//        );
//        UserBookingMessage ubm = new UserBookingMessage(buuid, LocalDateTime.now(), email, flightDTO);
//        this.db.collection("userbookingmessage").document(ubm.getId().toString()).set(ubm.toDoc()).get();
//
//        String message = "Booking successful for " + email + " on flight " + flightDTO.getDepartureTime() + " with the price of " + flightDTO.getPrice()*100 + " DKK";
//        return message;
//    }
}
