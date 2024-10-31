# java
Klasa DoctorSchedule jest przeznaczona do przechowywania i zarządzania tygodniowym harmonogramem pracy lekarza. Wykorzystuje ona dni tygodnia (DayOfWeek) jako klucze do przechowywania informacji o godzinach pracy. Przyjrzyjmy się bliżej tej klasie.

Klasa DoctorSchedule
Pole Schedule

    private Map<DayOfWeek, WorkHours> schedule;

schedule to mapa przechowująca godziny pracy dla każdego dnia tygodnia. Kluczem w tej mapie jest DayOfWeek (wyliczenie reprezentujące dni tygodnia). Wartością jest obiekt WorkHours, który przechowuje czas rozpoczęcia i zakończenia dnia roboczego. Konstruktor DoctorSchedule

    public DoctorSchedule() {
        schedule = new EnumMap<>(DayOfWeek.class);
    }
Konstruktor inicjalizuje pole harmonogramu jako EnumMap, które jest zoptymalizowane do przechowywania wyliczeń. W tym przypadku EnumMap jest używana z DayOfWeek do przechowywania wartości dla każdego dnia tygodnia.

Metody klasowe DoctorSchedule
Metoda setDoctorSchedule

    public void setDoctorSchedule(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        schedule.put(day, new WorkHours(startTime, endTime));
    }

setDoctorSchedule dodaje lub aktualizuje godziny pracy lekarza dla określonego dnia. Argumenty metody: day: obiekt DayOfWeek określający dzień tygodnia (na przykład MONDAY). startTime: czas rozpoczęcia (LocalTime). endTime: czas zakończenia (LocalTime). Sprawdź: Jeśli startTime jest późniejszy niż endTime, metoda rzuca wyjątek IllegalArgumentException, aby uniknąć nieprawidłowych wartości. Dodawanie godzin pracy: Używając schedule.put(day, new WorkHours(startTime, endTime)), metoda dodaje nowy obiekt WorkHours do harmonogramu.



Metoda getWeeklySchedule

    public void getWeeklySchedule() {
        System.out.println("Doctor's weekly schedule:");
        for (DayOfWeek day : DayOfWeek.values()) {
            WorkHours workHours = schedule.get(day);
            if (workHours != null) {
                System.out.println(day + ": " + workHours);
            } else {
                System.out.println(day + ": Day off");
            }
        }
    }

getWeeklySchedule wyświetla harmonogram pracy na dany tydzień. Metoda przechodzi przez wszystkie wartości DayOfWeek (tj. wszystkie dni tygodnia). Sprawdzanie harmonogramu: Jeśli harmonogram zawiera godziny pracy dla danego dnia (workHours != null), wyświetla dzień roboczy i odpowiadające mu godziny. Jeśli workHours ma wartość null (tj. harmonogram nie jest ustawiony na ten dzień), wyświetlany jest komunikat Day off, co oznacza dzień wolny.



Zagnieżdżona klasa WorkHours Klasa WorkHours jest wewnętrzną (zagnieżdżoną) klasą, która przechowuje godziny pracy dla określonego dnia.

    private static class WorkHours {
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkHours(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
}
Pola startTime i endTime są godzinami rozpoczęcia i zakończenia dnia roboczego, reprezentowanymi przez obiekty LocalTime. Konstruktor WorkHours Konstruktor pobiera startTime i endTime i inicjalizuje odpowiednie pola. Metoda toString Nadpisana metoda toString zwraca ciąg znaków w formacie startTime - endTime, co pozwala na łatwe wyświetlanie harmonogramu w przyjazny dla użytkownika sposób.


Przykład klasy Aby utworzyć i wyświetlić harmonogram lekarza:
DoctorSchedule schedule = new DoctorSchedule();

// Ustalenie harmonogramu na poniedziałek

    schedule.setDoctorSchedule(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));

// Wyświetlanie harmonogramu na tydzień

    schedule.getWeeklySchedule();

W tym przykładzie dla poniedziałku (MONDAY) godziny pracy są ustawione od 9:00 do 17:00, a metoda getWeeklySchedule() wyświetla harmonogram na cały tydzień.

Ten kod dodaje funkcjonalność do systemu zarządzania przychodnią, umożliwiając dodawanie lub przeglądanie harmonogramu pracy lekarza.
Akcje case 7 i case 8 są zaimplementowane w bloku switch menu głównego aplikacji. 
Przypadek 7: 
Przypisywanie godzin pracy dla lekarza Program żąda identyfikatora lekarza. 
Metoda clinic.findDoctorById(id) jest używana do znalezienia lekarza z wprowadzonym identyfikatorem. Jeśli lekarz zostanie znaleziony: Żądany jest dzień roboczy (day) oraz godziny rozpoczęcia i zakończenia pracy (startTime i endTime). DayOfWeek.valueOf(...) konwertuje wprowadzoną wartość do formatu DayOfWeek (np. MONDAY). LocalTime.parse(...) odczytuje czas w formacie HH:mm. Wywoływana jest metoda scheduleDoctor.getSchedule().setDoctorSchedule(day, startTime, endTime), która ustawia godziny pracy lekarza dla wybranego dnia. Zostanie wyświetlone potwierdzenie dodania godzin pracy. Jeśli lekarz o tym identyfikatorze nie zostanie znaleziony, zostanie wyświetlony komunikat "Lekarza nie znaleziono.". 
Przypadek 8: 
Wyświetlanie harmonogramu pracy lekarza Program żąda identyfikatora lekarza. Metoda clinic.findDoctorById(id) jest używana do znalezienia lekarza o wprowadzonym identyfikatorze. Jeśli lekarz zostanie znaleziony: Wywoływana jest metoda getWeeklySchedule(), która wyświetla harmonogram pracy lekarza na cały tydzień. Jeśli lekarz o podanym identyfikatorze nie zostanie znaleziony, zostanie wyświetlony komunikat "Lekarza nie znaleziono".




