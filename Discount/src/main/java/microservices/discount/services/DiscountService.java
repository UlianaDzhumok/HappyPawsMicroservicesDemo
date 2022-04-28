package microservices.discount.services;

import microservices.discount.dao.DiscountRepository;
import microservices.discount.dto.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Service
@RequestMapping("/")
public class DiscountService {

    @Autowired
    DiscountRepository dscntRepo;

    @RequestMapping(value="/discounts", method = RequestMethod.GET)
    List<Discount> getAllDiscounts() {
        return dscntRepo.findAll();
    }

    @RequestMapping("/discounts/{phone}")
    Discount getDiscountById(@PathVariable("phone") String phone) {
        if(dscntRepo.findByPhone(phone)==null){
            return null;
        }
        else {
            return dscntRepo.findByPhone(phone);
        }
    }

    @RequestMapping(value= "/discounts", method = RequestMethod.POST)
    ResponseEntity<Discount> insertDiscount(@Valid @RequestBody Discount discount){
        Discount savedDiscount=dscntRepo.save(discount);
        return new ResponseEntity<>(savedDiscount, HttpStatus.OK);
    }

    @RequestMapping(value="/discounts/{phone}", method = RequestMethod.PUT)
    Discount updateDiscount(@PathVariable("phone") String phone, @Valid @RequestBody Discount discount){
        Discount levelToChange=dscntRepo.findByPhone(phone);
        Discount existingLevel=levelToChange;
        existingLevel.setPhone(discount.getPhone());
        existingLevel.setLevel(discount.getLevel());
        existingLevel.setGroomSetsNumber(discount.getGroomSetsNumber());
        Discount savedLevel=dscntRepo.save(existingLevel);
        return savedLevel;
    }

    @RequestMapping(value="/discounts/{phone}", method = RequestMethod.DELETE)
    Discount deleteBooking(@PathVariable("phone") String phone){
        Discount discountToDelete=dscntRepo.findByPhone(phone);
        Discount existingDiscount=discountToDelete;
        dscntRepo.delete(existingDiscount);
        return existingDiscount;
    }

}
