import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Class to store patient information
class Patient {
    private String firstName;
    private String lastName;
    private String pesel;
    private String birthDate;
    private int age;
    private String phoneNumber;
    private String email;

    public Patient(String firstName, String lastName, String pesel, String birthDate, int age, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.birthDate = birthDate;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getPesel() { return pesel; }
    public String getLastName() { return lastName; }
    public String getFullInfo() {
        return firstName + " " + lastName + ", PESEL: " + pesel + ", Wiek: " + age + ", Telefon: " + phoneNumber + ", Email: " + email;
    }
}

// Class to store information about the doctor
class Doctor {
    private String firstName;
    private String lastName;
    private String id;
    private String phoneNumber;
    private String email;
    private List<String> specializations;
    private DoctorSchedule schedule;  // Doctor's schedule

    public Doctor(String firstName, String lastName, String id, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specializations = new ArrayList<>();
        this.schedule = new DoctorSchedule();
    }

    public void addSpecialization(String specialization) {
        specializations.add(specialization);
    }

    public List<String> getSpecializations() { return specializations; }
    public String getId() { return id; }
    public DoctorSchedule getSchedule() { return schedule; }

    public String getFullInfo() {
        return firstName + " " + lastName + ", ID: " + id + ", Telefon: " + phoneNumber + ", Email: " + email + ", Specjalizacje: " + specializations;
    }
}

//The class is designed to store and manage a doctor's work schedule.
class DoctorSchedule {
    private Map<DayOfWeek, WorkHours> schedule;

    public DoctorSchedule() {
        schedule = new EnumMap<>(DayOfWeek.class);
    }

    public void setDoctorSchedule(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        schedule.put(day, new WorkHours(startTime, endTime));
    }

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
}

// A class for managing patients and doctors
class ClinicManagementSystem {
    private List<Patient> patients;
    private List<Doctor> doctors;

    public ClinicManagementSystem() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public Patient findPatientByPesel(String pesel) {
        for (Patient patient : patients) {
            if (patient.getPesel().equals(pesel)) {
                return patient;
            }
        }
        return null;
    }

    public List<Patient> findPatientsByLastName(String lastName) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getLastName().equalsIgnoreCase(lastName)) {
                result.add(patient);
            }
        }
        return result;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public Doctor findDoctorById(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    public List<Doctor> findDoctorsBySpecialization(String specialization) {
        List<Doctor> result = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getSpecializations().contains(specialization)) {
                result.add(doctor);
            }
        }
        return result;
    }
}


public class Main {
    public static void main(String[] args) {
        ClinicManagementSystem clinic = new ClinicManagementSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Witamy w systemie zarządzania kliniką!");

        // Adding a test patient and doctor to the demonstration
        clinic.addPatient(new Patient("Anna", "Kowalska", "12345678901", "1985-04-12", 38, "123456789", "anna.k@example.com"));
        Doctor doctor = new Doctor("Piotr", "Nowak", "D001", "987654321", "piotr.n@example.com");
        doctor.addSpecialization("Kardiolog");
        clinic.addDoctor(doctor);

        // Główne menu programu
        while (true) {
            System.out.println("\nWybierz działanie:");
            System.out.println("1. Dodaj nowego pacjenta");
            System.out.println("2. Znajdź pacjenta według numeru PESEL");
            System.out.println("3. Znajdź pacjentów według nazwiska");
            System.out.println("4. Dodaj nowego lekarza");
            System.out.println("5. Znajdź lekarza według ID");
            System.out.println("6. Znajdź lekarzy według specjalizacji");
            System.out.println("7. Przypisz godziny pracy lekarzowi");
            System.out.println("8. Pokaż grafik pracy lekarza");
            System.out.println("0. Wyjście");

            int choice = scanner.nextInt();
            scanner.nextLine();  // We retrieve the remainder of the line

            switch (choice) {
                case 1:
                    System.out.println("Wprowadź dane pacjenta (imię, nazwisko, PESEL, data urodzenia, wiek, telefon, email):");
                    String firstName = scanner.nextLine();
                    String lastName = scanner.nextLine();
                    String pesel = scanner.nextLine();
                    String birthDate = scanner.nextLine();
                    int age = scanner.nextInt();
                    scanner.nextLine();  // We retrieve the remainder of the line
                    String phone = scanner.nextLine();
                    String email = scanner.nextLine();
                    clinic.addPatient(new Patient(firstName, lastName, pesel, birthDate, age, phone, email));
                    System.out.println("Pacjent dodany!");
                    break;
                case 2:
                    System.out.println("Wprowadź PESEL pacjenta:");
                    pesel = scanner.nextLine();
                    Patient patient = clinic.findPatientByPesel(pesel);
                    if (patient != null) {
                        System.out.println("Znaleziony pacjent: " + patient.getFullInfo());
                    } else {
                        System.out.println("Pacjenta nie znaleziono.");
                    }
                    break;
                case 3:
                    System.out.println("Wprowadź nazwisko do wyszukania:");
                    lastName = scanner.nextLine();
                    List<Patient> patients = clinic.findPatientsByLastName(lastName);
                    if (!patients.isEmpty()) {
                        System.out.println("Znalezieni pacjenci:");
                        for (Patient p : patients) {
                            System.out.println(p.getFullInfo());
                        }
                    } else {
                        System.out.println("Pacjentów nie znaleziono.");
                    }
                    break;
                case 4:
                    System.out.println("Wprowadź dane lekarza (imię, nazwisko, ID, telefon, email):");
                    firstName = scanner.nextLine();
                    lastName = scanner.nextLine();
                    String id = scanner.nextLine();
                    phone = scanner.nextLine();
                    email = scanner.nextLine();
                    Doctor newDoctor = new Doctor(firstName, lastName, id, phone, email);
                    System.out.println("Wprowadź specjalizacje lekarza (wpisz 'stop' aby zakończyć):");
                    while (true) {
                        String specialization = scanner.nextLine();
                        if (specialization.equalsIgnoreCase("stop")) break;
                        newDoctor.addSpecialization(specialization);
                    }
                    clinic.addDoctor(newDoctor);
                    System.out.println("Lekarz dodany!");
                    break;
                case 5:
                    System.out.println("Wprowadź ID lekarza:");
                    id = scanner.nextLine();
                    Doctor foundDoctor = clinic.findDoctorById(id);
                    if (foundDoctor != null) {
                        System.out.println("Znaleziony lekarz: " + foundDoctor.getFullInfo());
                    } else {
                        System.out.println("Lekarza nie znaleziono.");
                    }
                    break;
                case 6:
                    System.out.println("Wprowadź specjalizację do wyszukania:");
                    String specialization = scanner.nextLine();
                    List<Doctor> doctors = clinic.findDoctorsBySpecialization(specialization);
                    if (!doctors.isEmpty()) {
                        System.out.println("Znalezieni lekarze:");
                        for (Doctor d : doctors) {
                            System.out.println(d.getFullInfo());
                        }
                    } else {
                        System.out.println("Lekarzy nie znaleziono.");
                    }
                    break;
                case 7:
                    System.out.println("Wprowadź ID lekarza:");
                    id = scanner.nextLine();
                    Doctor scheduleDoctor = clinic.findDoctorById(id);
                    if (scheduleDoctor != null) {
                        System.out.println("Wprowadź dzień tygodnia (np. MONDAY):");
                        DayOfWeek day = DayOfWeek.valueOf(scanner.nextLine().toUpperCase());
                        System.out.println("Wprowadź godzinę rozpoczęcia (np. 09:00):");
                        LocalTime startTime = LocalTime.parse(scanner.nextLine());
                        System.out.println("Wprowadź godzinę zakończenia (np. 17:00):");
                        LocalTime endTime = LocalTime.parse(scanner.nextLine());
                        scheduleDoctor.getSchedule().setDoctorSchedule(day, startTime, endTime);
                        System.out.println("Godziny pracy dodane dla dnia " + day);
                    } else {
                        System.out.println("Lekarza nie znaleziono.");
                    }
                    break;
                case 8:
                    System.out.println("Wprowadź ID lekarza:");
                    id = scanner.nextLine();
                    Doctor scheduleViewDoctor = clinic.findDoctorById(id);
                    if (scheduleViewDoctor != null) {
                        scheduleViewDoctor.getSchedule().getWeeklySchedule();
                    } else {
                        System.out.println("Lekarza nie znaleziono.");
                    }
                    break;
                case 0:
                    System.out.println("Wyjście z programu.");
                    return;
                default:
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }
}
