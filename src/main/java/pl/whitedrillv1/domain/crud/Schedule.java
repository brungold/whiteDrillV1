package pl.whitedrillv1.domain.crud;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.whitedrillv1.domain.crud.util.BaseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(generator = "schedule_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "schedule_id_seq",
            sequenceName = "schedule_id_seq",
            allocationSize = 1

    )
    private Long id;

    @Column(nullable = false)
    private LocalDate date; // Konkretny dzień, np. 2024-10-20

    @Column(nullable = false)
    private LocalTime startTime; // Godzina rozpoczęcia dostępności, np. 09:00

    @Column(nullable = false)
    private LocalTime endTime; // Godzina zakończenia dostępności, np. 17:00

    @ManyToOne(optional = false)
    private Dentist dentist; // Umożliwia przypisanie dostępności do konkretnego lekarza

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Appointment> appointments = new HashSet<>();

    //@Column(name = "booked_hours", columnDefinition = "integer[]") // Tablica w PostgreSQL
    private Set<Integer> bookedHours = new HashSet<>(); // Przechowuje zajęte godziny w formie unikalnych wartości

    @Column
    private String description;

    void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Usuwa podane godziny z zajętych.
     */
    public void removeBookedHours(Set<Integer> hours) {
        bookedHours.removeAll(hours); // Usuwa godziny ze zbioru
    }

    /*
    CREATE TABLE schedule (
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    dentist_id BIGINT NOT NULL,
    booked_hours INTEGER[], -- Tablica przechowująca godziny
    description TEXT
);
     */
}