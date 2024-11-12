package pl.whitedrillv1.domain.crud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.whitedrillv1.domain.crud.util.BaseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(generator = "address_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "address_id_seq",
            sequenceName = "address_id_seq",
            allocationSize = 1

    )
    private Long id;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private AppointmentStatus status; // Przykład wartości: 'scheduled', 'completed', 'cancelled'

    @Lob
    private String treatmentDescription;

    @ManyToOne(optional = false) // ????
    @JoinColumn(name = "idPatient", referencedColumnName = "id")
    private Patient patient;

}