package pl.whitedrillv1.domain.crud;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
import java.util.HashSet;
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
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne(optional = false)
    private Dentist dentist; // Umożliwia przypisanie dostępności do konkretnego lekarza

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "schedule_booked_hours", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "booked_hour")
    private Set<Integer> bookedHours = new HashSet<>();

    @Column
    private String description;

    void addAppointment(final Appointment appointment) {
        appointments.add(appointment);
    }

    void addReservedHoursFromAppointment(final Appointment appointment) {
        this.bookedHours.addAll(appointment.getReservedHours());
    }

    public void removeBookedHours(Set<Integer> hours) {
        bookedHours.removeAll(hours); // Usuwa godziny ze zbioru
    }
}