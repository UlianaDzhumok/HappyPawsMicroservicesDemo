package microservices.payment.services;

import microservices.booking.dto.Booking;
import microservices.booking.dto.Status;
import microservices.payment.dao.PaymentRepository;
import microservices.payment.dto.Payment;
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
import java.util.List;
import java.util.Optional;

@RestController
@Service
@RequestMapping("/api/v1")
public class PaymentService {

    @Autowired
    PaymentRepository payRepo;

    @RequestMapping(value="/payments", method = RequestMethod.GET)
    List<Payment> getAllPayments() {
        return payRepo.findAll();
    }

    @RequestMapping("/payments/{id}")
    Optional<Payment> getPaymentById(@PathVariable("id") int id) {
        return payRepo.findById(id);
    }

    @RequestMapping(value= "/payments", method = RequestMethod.POST)
    ResponseEntity<Payment> insertPayment(@Valid @RequestBody Payment payment){
        Booking bookingInfo=getBookingInfo(payment.getBookingId());
        payment.setPrice(bookingInfo.getPrice());
        updateBookingStatus(bookingInfo, Status.PAID);

        Payment savedPayment=payRepo.save(payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.OK);
    }

    @RequestMapping(value="/payments/{id}", method = RequestMethod.PUT)
    Payment updatePayment(@PathVariable("id") int id, @Valid @RequestBody Payment payment){
        Optional<Payment> paymentToChange=payRepo.findById(id);
        Payment existingPayment=paymentToChange.get();
        existingPayment.setBookingId(payment.getBookingId());
        existingPayment.setDate(payment.getDate());
        existingPayment.setPhone(payment.getPhone());

        Booking bookingInfo=getBookingInfo(existingPayment.getBookingId());
        existingPayment.setPrice(bookingInfo.getPrice());
        updateBookingStatus(bookingInfo, Status.PAID);

        Payment savedPayment=payRepo.save(existingPayment);
        return savedPayment;
    }

    @RequestMapping(value="/payments/{id}", method = RequestMethod.DELETE)
    Payment deletePayment(@PathVariable("id") int id){
        Optional<Payment> paymentToDelete=payRepo.findById(id);
        Payment existingPayment=paymentToDelete.get();

        Booking bookingInfo=getBookingInfo(existingPayment.getBookingId());
        updateBookingStatus(bookingInfo, Status.REGISTERED);

        payRepo.delete(existingPayment);
        return existingPayment;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    public Booking getBookingInfo(int id){
        List<ServiceInstance> serviceInstances=discoveryClient.getInstances("booking");

        RestTemplate restTemplate= new RestTemplate();
        System.out.println("Routed to Booking Service");

        String serviceURI = String.format("%s/bookings/%d", serviceInstances.get(0).getUri().toString(), id);
        ResponseEntity<Booking> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, Booking.class);
        return restExchange.getBody();
    }

    public void updateBookingStatus(Booking bookingInfo, Status status){
        List<ServiceInstance> serviceInstances=discoveryClient.getInstances("booking");

        RestTemplate restTemplate= new RestTemplate();
        System.out.println("Routed to Booking Service");

        String servicePutURI = String.format("%s/bookings/%d", serviceInstances.get(0).getUri().toString(),bookingInfo.getId());
        bookingInfo.setStatus(status);
        restTemplate.put(servicePutURI, bookingInfo);
    }
}
