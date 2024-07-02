package be.kuleuven.dsgt4;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;


@RestController
public class HotelController {
        @Autowired
        Firestore db;

        private final HotelService hotelService;

        public HotelController(HotelService hotelService) {
                this.hotelService = hotelService;
        }

        @GetMapping("/hotels")
        public Flux<HotelDTO> getHotelsByDate(@RequestParam("location") String location) {
                return hotelService.getAllHotels(location);
        }
}
