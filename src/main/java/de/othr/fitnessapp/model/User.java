package de.othr.fitnessapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name="user")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Email(message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.not.blank}")
    @Column(name = "EMAIL")
    private String email;

    @NotBlank(message = "{user.phone.not.blank}")
    @Column(name = "PHONE_NUMBER")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
