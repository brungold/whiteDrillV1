package pl.whitedrillv1.domain.crud;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.whitedrillv1.domain.crud.util.BaseEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class Patient extends BaseEntity{

    @Id
    @GeneratedValue(generator = "patient_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "patient_id_seq",
            sequenceName = "patient_id_seq",
            allocationSize = 1

    )
    private Long id;

    //@NotNull
    //@Size(min = 1, max = 50)
    private String firstName;

    //@NotNull
    //@Size(min = 1, max = 50)
    private String lastName;

    private String maidenName;
    private String language;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private PatientGender patientGender;

    private LocalDate birthDate;

    private String pesel;

    private String nip;

    private String phone;

    @Email
    private String email;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Appointment> appointments = new HashSet<>();

    public Patient(String firstName, String lastName, String maidenName, String language, String nationality, PatientGender patientGender, LocalDate birthDate, String pesel, String nip, String phone, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.maidenName = maidenName;
        this.language = language;
        this.nationality = nationality;
        this.patientGender = patientGender;
        this.birthDate = birthDate;
        this.pesel = pesel;
        this.nip = nip;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}


