package microservices.booking.dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="pets")
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="species")
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Species species;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="breed")
    @NotNull
    private String breed;

    @Column(name="age")
    @NotNull
    private int age;

    @Column(name="weight")
    @NotBlank
    private int weight;

    @Column(name="owners_phone")
    @NotBlank
    private String ownersPhone;


    public Pet(){
    }

    public Pet(Species species, String name, String breed, int age, int weight, String ownersPhone){
        this.species=species;
        this.name=name;
        this.breed=breed;
        this.age=age;
        this.weight=weight;
        this.ownersPhone=ownersPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getOwnersPhone() {
        return ownersPhone;
    }

    public void setOwnersPhone(String ownersPhone) {
        this.ownersPhone = ownersPhone;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", species=" + species +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", weight='"+ weight + '\'' +
                ", ownersPhone='" + ownersPhone + '\'' +
                '}';
    }

}
