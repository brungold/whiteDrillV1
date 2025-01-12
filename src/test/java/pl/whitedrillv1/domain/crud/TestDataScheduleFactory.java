package pl.whitedrillv1.domain.crud;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

interface TestDataScheduleFactory {

    default List<Schedule> createSixSchedules() {
        List<Schedule> schedules = new ArrayList<>();

        // Harmonogramy od 9:00 do 17:00 dla różnych dni
        schedules.add(createScheduleForDate(LocalDate.of(2025, 5, 22), LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedules.add(createScheduleForDate(LocalDate.of(2025, 5, 23), LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedules.add(createScheduleForDate(LocalDate.of(2025, 5, 24), LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedules.add(createScheduleForDate(LocalDate.of(2025, 5, 25), LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedules.add(createScheduleForDate(LocalDate.of(2025, 5, 26), LocalTime.of(9, 0), LocalTime.of(17, 0)));
        schedules.add(createScheduleForDate(LocalDate.of(2025, 5, 27), LocalTime.of(9, 0), LocalTime.of(17, 0)));

        return schedules;
    }

    default Schedule createScheduleForDate(LocalDate date, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = new Schedule();
        schedule.setId((long) date.hashCode()); // Generowanie unikalnego ID
        schedule.setDate(date);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setBookedHours(new HashSet<>()); // Początkowo brak zajętych godzin
        return schedule;
    }

}