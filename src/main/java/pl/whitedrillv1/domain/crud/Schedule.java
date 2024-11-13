package pl.whitedrillv1.domain.crud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.whitedrillv1.domain.crud.util.BaseEntity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(generator = "schedule_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "schedule_id_seq",
            sequenceName = "schedule_id_seq",
            allocationSize = 1

    )

    @Column(nullable = false)
    private LocalDate date; // Konkretny dzień, np. 2024-10-20

    @Column(nullable = false)
    private LocalTime startTime; // Godzina rozpoczęcia dostępności, np. 09:00

    @Column(nullable = false)
    private LocalTime endTime; // Godzina zakończenia dostępności, np. 17:00

    @ManyToOne(optional = false)
    @JoinColumn(name = "dentist_id", referencedColumnName = "id")
    private Dentist dentist; // Umożliwia przypisanie dostępności do konkretnego lekarza

    @Column(nullable = false)
    private Duration duration; // np. Duration.ofMinutes(30)

    @Column
    private String description;

}