package moriakoff.kafka.pension.dao.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class ClientInfo {

    @Id
    private Integer id;

    private Double balance;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    private Boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactAddress contactAddress;


    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
