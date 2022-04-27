package microservices.booking.dao;

import microservices.booking.dto.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookingRepository extends CrudRepository<Booking, Integer> {
    List<Booking> findByPhone(String phone);
    List<Booking> findAll();
}
