package microservices.payment.dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="date", columnDefinition = "TIMESTAMP")
    @FutureOrPresent
    private LocalDateTime date;

    @NotBlank
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "booking_id")
    private int bookingId;

    @NotNull
    @Column(name = "price")
    private double price;

    public Payment(){
    }

    public Payment(LocalDateTime date, String phone, int bookingId){
        this.date=date;
        this.phone=phone;
        this.bookingId=bookingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", date=" + date +
                ", phone='" + phone + '\'' +
                ", bookingId=" + bookingId +
                ", price=" + price +
                '}';
    }
}
