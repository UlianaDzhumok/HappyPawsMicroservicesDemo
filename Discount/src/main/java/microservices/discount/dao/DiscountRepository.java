package microservices.discount.dao;

import microservices.discount.dto.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DiscountRepository extends CrudRepository<Discount, Integer> {
    Discount findByPhone(String phone);
    List<Discount> findAll();
}
