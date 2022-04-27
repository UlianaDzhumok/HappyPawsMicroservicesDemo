package microservices.discount.dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "discounts")
public class Discount implements Serializable {

    @Id
    @Column(name = "phone")
    @NotBlank
    private String phone;

    @Column(name = "level")
    @NotNull
    private Level level;

    @Column(name = "set_number")
    @NotNull
    private int groomSetsNumber;

    public Discount(){
        super();
    }

    public Discount(String phone, Level level, int groomSetsNumber){
        this.phone=phone;
        this.level=level;
        this.groomSetsNumber=groomSetsNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getGroomSetsNumber() {
        return groomSetsNumber;
    }

    public void setGroomSetsNumber(int groomSetsNumber) {
        this.groomSetsNumber = groomSetsNumber;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "Phone='" + phone + '\'' +
                ", level=" + level +
                ", groomSetsNumber=" + groomSetsNumber +
                '}';
    }
}
