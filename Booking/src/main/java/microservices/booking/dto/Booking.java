package microservices.booking.dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "bookings")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(columnDefinition = "TIMESTAMP")
    @FutureOrPresent
    private LocalDateTime date;

    @Column(name = "phone")
    @NotBlank
    private String phone;

    private boolean firstGrooming;

    @Column(name="pet", length = 1000)
    @NotNull
    private ArrayList<Pet> pet;

    @NotBlank
    private String services;

    @NotNull
    private double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public Booking(){
    }

    public Booking(LocalDateTime date, String phone, boolean firstGrooming, ArrayList<Pet> pet, String services, double price){
        this.date=date;
        this.phone=phone;
        this.firstGrooming=firstGrooming;
        this.pet = pet;
        this.services=services;
        this.price=price;
        this.status=Status.REGISTERED;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    public void setDate(LocalDateTime date){
        this.date=date;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public boolean getFirstGrooming(){
        return this.firstGrooming;
    }

    public void setFirstGrooming(boolean firstGrooming){
        this.firstGrooming=firstGrooming;
    }

    public ArrayList<Pet> getPet(){
        return this.pet;
    }

    public void setPet(ArrayList<Pet> pet){
        this.pet=pet;
    }

    public String getServices(){
        return this.services;
    }

    public void setServices(String services){
        this.services=services;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price=price;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Status status){
        this.status=status;
    }


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date=" + date +
                ", phone='" + phone + '\'' +
                ", firstGrooming=" + firstGrooming +
                ", pet=" + pet +
                ", services='" + services + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }


}
