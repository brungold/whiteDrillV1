Logika

!!!!CZY PACJENT MOŻE MIEĆ UMÓWIONA WIĘCEJ NIŻ JEDNĄ WIZYTE.
ZABEZPIECZENIE ŻEBY KLIENT NIE NAWRZUCAŁ NIE WIADOMO ILE WIZYT (APPOINTMENT)?!?!?!?!

GET APPOINTMENTS -> zwróć najbliższa wolną datę
-> zwróć wolne daty od najbliższej wolnej daty


Schedule powinien korzystać ze wzorca prototype ?
chodzi o tworzenie wypenionych zasobów godzinami, ale będie to zależeć od wprowadzonych dat przez uzytkownika

SCHEDULE FINDALL 
potrzeba zwrotu kilku rodzajów schedule response nie tylko dto
dla klienta na zewnątrz trzeba wyksponowac tylko wolne godziny



#### ScheduleController:
GET /api/schedule/{id}
GET /api/schedule/{date}
GET /api/schedule/all(Paginacja)
GET /api/schedule/getScheduleWithAvailableSlots
GET /api/schedule/getAllScheduleWithAvailableSlots(Paginacja)
POST /api/schedule
##### **ScheduleController:** 
wyświetla wolne godziny w schedule 
wyświetla umówione Appointments (czy da się je ułożyc godzinach od najwcześniejszcyh)
wyświetla zabookowane godziny w schedule
-> schedul musi mieć przedziały czasowe, tylko jak je zaimplementować w jakiej formie - coś w stylu BaseEnity tyle ze z godzinami
schedule musi mieć 'godziny' ?? DOPRECYZOWAĆ

#### PatientController:
~~GET /api/patients/{id}~~
~~GET /api/patients~~
~~POST /api/patients~~
GET /api/patients/patient/{name}
PUT /api/patients/{id}
DELETE /api/patients/{id}
##### **Questioned ?? food for thought**
GET /api/patients/dentist/{name}

#### AppointmentController:
    WYSZUKAJ PIERWSZEJ WOLNEJ DATY WIZYTY

##### **Questioned ?? food for thought**


#### DentalWorkController
This controller handles CRUD operations for dental works.
Endpoints:
GET /api/dental-works/all: Retrieves all dental works.
GET /api/dental-works/id/{id}: Retrieves a dental work by ID.
POST /api/dental-works: Creates a new dental work.
DELETE /api/dental-works/id/{id}: Deletes a dental work by ID.
GET /api/dental-works/status/{status}: Retrieves dental works by status.
PUT /api/dental-works/id/{id}: Updates a dental work by ID.
##### **Questioned ?? food for thought**
GET /api/dental-works/type/{type}: Retrieves dental works by type. ???
GET /api/dental-works/color/{color}: Retrieves dental works by color.


#### DentistController
GET /api/dentists/{id}
POST /api/dentists
PUT /api/dentists/{id}
DELETE /api/dentists/{id}
GET /api/dentists
GET /api/dentists?address={address}
GET /api/dentists?clinic={clinic}



Logika
Dodawanie Appointment / Patient
Docelowo najpierw musi zostać dodany pacjent, zeby można było uwtowrzyć dla niego wizytę.
(Czy tworzyć opcję dla utworzenia pacjenta razem z wizyta -> chyba nie.)
Kiedy wpisywać price w Appointment. Na początku przytworzeniu rozumiem że z default = 0 ?


?????
czy trwanie, duratin jako encje ? 1h, 2h , 3h ,4h czy enum ?
duration raczej w formie minut 60 min, 120 min
przedziały czasowe raczej wszyte
?????

?????
Schedule. Dodawanie Appointment.
Sprawdzam czy o tej godzinie, jest juz jakiś appointment.
Jak zrobić przedział godzin? czy jako enum czy baze
czy dawać jako string 9 czy Long 9 ? czy może przedział
?????


curl -X 'POST' \
'http://localhost:8080/patients' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"firstName": "Jon",
"lastName": "Doe",
"birthDate": "1990-05-039",
"phone": "506002222",
"email": "jon.doe@gmail.com",
"patientGender": "MALE",
"pesel": "90050324878",
"addressDto": {
"postalCode": 02-111,
"city": "Warszawa",
"street": "duga",
"houseNumber": 1,
"apartmentNumber": 12
}
}'


Scheule
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


Zmiany w Schedule, np podstawowa zmiana w Appointment czy wystarczy że tylko przez AppoitnmentUpdater ?
