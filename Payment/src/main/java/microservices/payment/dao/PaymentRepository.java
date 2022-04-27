package microservices.payment.dao;

import microservices.payment.dto.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    List<Payment> findByPhone(String phone);
    List<Payment> findAll();
}
