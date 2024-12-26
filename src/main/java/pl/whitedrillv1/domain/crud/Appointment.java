package pl.whitedrillv1.domain.crud;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.whitedrillv1.domain.crud.AppointmentStatus;
import pl.whitedrillv1.domain.crud.Dentist;
import pl.whitedrillv1.domain.crud.Patient;
import pl.whitedrillv1.domain.crud.Schedule;
import pl.whitedrillv1.domain.crud.util.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Builder
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
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

    @Column(nullable = false)
    private Integer duration; // ??domyślnie 60 min może lepiej w godzinach liczyć aby później dodawać żeby wyliczyć zajmowane godziny ??

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private AppointmentStatus status;

    @Lob
    private String appointmentNotes;

    @ManyToOne//(optional = false)
    //@JoinColumn(name = "dentist_id", referencedColumnName = "id")
    private Dentist dentist;

    @ManyToOne(optional = false) // ????
    @JoinColumn(name = "idPatient", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule; // Nowa relacja z grafikiem

    @ElementCollection
    @CollectionTable(name = "appointment_reserved_hours", joinColumns = @JoinColumn(name = "appointment_id"))
    @Column(name = "reserved_hour")
    private Set<Integer> reservedHours = new HashSet<>();

    /**
     * Wylicza zajęte godziny na podstawie `appointmentTime` i `duration`.
     */
    public Set<Integer> calculateReservedHours() {
        int startHour = appointmentTime.getHour();
        return IntStream.range(startHour, startHour + duration)
                .boxed()
                .collect(Collectors.toSet());
    }
}