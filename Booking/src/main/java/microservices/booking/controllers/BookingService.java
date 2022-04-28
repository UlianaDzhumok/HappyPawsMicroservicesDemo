package microservices.booking.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import microservices.booking.dao.BookingRepository;
import microservices.booking.dto.Booking;
import microservices.booking.dto.Grooming;
import microservices.booking.dto.Species;
import microservices.booking.dto.Status;
import microservices.discount.dto.Discount;
import microservices.discount.dto.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@Service
@RequestMapping("/api/v1")
public class BookingService {

    @Autowired
    BookingRepository bookRepo;

    @Operation(summary = "Get all bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found bookings",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "404", description = "Bookings not found",
                    content = @Content) })
    @RequestMapping(value="/bookings", method = RequestMethod.GET)
    List<Booking> getAllBookings() {
        return bookRepo.findAll();
    }

    @Operation(summary = "Get a booking by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the booking",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content) })
    @GetMapping("/{id}")
    @RequestMapping("/bookings/{id}")
    Optional<Booking> getBookingById(@Parameter(description = "id of booking to be searched") @PathVariable("id") int id) {
        return bookRepo.findById(id);
    }

    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content)})
    @RequestMapping(value= "/bookings", method = RequestMethod.POST)
    ResponseEntity<Booking> insertBooking(@Valid @RequestBody Booking booking){
        double price=countPrice(booking.getServices(),booking.getPet().get(0).getSpecies(),booking.getPet().get(0).getWeight());
        int discount=getDiscount(booking.getPhone(), booking.getServices());
        booking.setPrice(price/100*(100-discount));
        booking.setStatus(Status.REGISTERED);

        Booking savedBooking=bookRepo.save(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

    @Operation(summary = "Update booking information by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Bookings not found",
                    content = @Content) })
    @RequestMapping(value="/bookings/{id}", method = RequestMethod.PUT)
    Booking updateBooking(@PathVariable("id") int id, @Valid  @RequestBody Booking booking){
        Optional<Booking> bookingToChange=bookRepo.findById(id);
        Booking existingBooking=bookingToChange.get();

        existingBooking.setDate(booking.getDate());
        existingBooking.setPhone(booking.getPhone());
        existingBooking.setFirstGrooming(booking.getFirstGrooming());
        existingBooking.setPet(booking.getPet());

        if(!existingBooking.getServices().toString().equals(booking.getServices().toString())) {
            existingBooking.setServices(booking.getServices());

            double price = countPrice(booking.getServices(), booking.getPet().get(0).getSpecies(), booking.getPet().get(0).getWeight());
            int discount = getDiscount(booking.getPhone(), booking.getServices());
            existingBooking.setPrice(price / 100 * (100 - discount));
            existingBooking.setStatus(Status.REGISTERED);
        }
        else{
            existingBooking.setServices(booking.getServices());
            existingBooking.setStatus(booking.getStatus());
        }

        Booking savedBooking=bookRepo.save(existingBooking);
        return savedBooking;
    }

    @Operation(summary = "Delete a booking by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content) })
    @RequestMapping(value="/bookings/{id}", method = RequestMethod.DELETE)
    Booking deleteBooking(@PathVariable("id") int id){
        Optional<Booking> bookingToDelete=bookRepo.findById(id);
        Booking existingBooking=bookingToDelete.get();
        bookRepo.delete(existingBooking);
        return existingBooking;
    }

    public double countPrice(String services, Species species, int weight){
        double sum=0;
        String prefix="";
        String[] petServices= services.split(",");

        if(petServices.length>0) {
            if (species.equals(Species.CAT)) {
                prefix="CAT_";
            } else {
                if(weight<=10){
                    prefix="SMALL_DOG";
                }
                if((weight>10)&(weight<=20)){
                    prefix="MEDIUM_DOG";
                }
                if((weight>20)&(weight<=45)){
                    prefix="LARGE_DOG";
                }
                if((weight>45)){
                    prefix="GIANT_DOG";
                }
            }

            ArrayList<String> enumServices = new ArrayList<>();
            for(String p:petServices) {
                enumServices.add(prefix+p);
            }

            for(String p:enumServices){
                boolean checkService=Arrays.stream(Grooming.values()).anyMatch(s ->s.name().equals(p));

                if(checkService){
                    sum = sum + Grooming.valueOf(p).getPrice();
                }
            }

        }

        return sum;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    public int getDiscount(String phone, String services){
        int discount=0;
        int groomSetsNumber=0;

        if(services.contains("WASH_GROOM_CUT")) groomSetsNumber=1;

        RestTemplate restTemplate= new RestTemplate();
        System.out.println("Routed to Discount Service");

        List<ServiceInstance> serviceInstances=discoveryClient.getInstances("discount");
        if(serviceInstances.size()!=0) {

            String serviceURI = String.format("%s/discounts/%s", serviceInstances.get(0).getUri().toString(), phone);
            ResponseEntity<Discount> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, Discount.class);

            if (!restExchange.hasBody()) {
                String servicePostURI = String.format("%s/discounts", serviceInstances.get(0).getUri().toString());
                ResponseEntity<Discount> addDiscount = restTemplate.postForEntity(servicePostURI,new Discount(phone, Level.BRONZE, 1),Discount.class);
                discount=Level.BRONZE.getPercentage();
            } else {
                Discount discountInfo = restExchange.getBody();

                List<Level> availableLevels= Arrays.asList(Level.values());
                Level currentLevel=Level.valueOf(discountInfo.getLevel().toString());

                discountInfo.setGroomSetsNumber(discountInfo.getGroomSetsNumber()+groomSetsNumber);

                if(availableLevels.indexOf(currentLevel)<2){
                    if(discountInfo.getGroomSetsNumber()>=Level.values()[availableLevels.indexOf(currentLevel)+1].getGoal()){
                        discountInfo.setLevel(Level.values()[availableLevels.indexOf(currentLevel)+1]);
                    }
                }


                String servicePutURI = String.format("%s/discounts/%s", serviceInstances.get(0).getUri().toString(),discountInfo.getPhone());
                restTemplate.put(servicePutURI, discountInfo);

                discount=discountInfo.getLevel().getPercentage();
            }
        }

        return discount;
    }
}