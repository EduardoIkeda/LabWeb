package com.uneb.labweb;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.uneb.labweb.enums.AppointmentStatus;
import com.uneb.labweb.enums.Role;
import com.uneb.labweb.model.Appointment;
import com.uneb.labweb.model.Doctor;
import com.uneb.labweb.model.HealthCenter;
import com.uneb.labweb.model.Penalty;
import com.uneb.labweb.model.Specialty;
import com.uneb.labweb.model.User;
import com.uneb.labweb.repository.AppointmentRepository;
import com.uneb.labweb.repository.DoctorRepository;
import com.uneb.labweb.repository.HealthCenterRepository;
import com.uneb.labweb.repository.PenaltyRepository;
import com.uneb.labweb.repository.SpecialtyRepository;
import com.uneb.labweb.repository.UserRepository;

@Transactional
@SpringBootApplication
public class SpringMarcacaoConsultaApplication {

    private SpringMarcacaoConsultaApplication self;
    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;
    private HealthCenterRepository healthCenterRepository;
    private PenaltyRepository penaltyRepository;
    private SpecialtyRepository specialtyRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Value("${api.database.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(SpringMarcacaoConsultaApplication.class, args);
    }

    @Bean
    @Profile("test")
    CommandLineRunner initDatabase(
            SpringMarcacaoConsultaApplication self,
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            HealthCenterRepository healthCenterRepository,
            PenaltyRepository penaltyRepository,
            SpecialtyRepository specialtyRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            this.self = self;
            this.appointmentRepository = appointmentRepository;
            this.doctorRepository = doctorRepository;
            this.healthCenterRepository = healthCenterRepository;
            this.penaltyRepository = penaltyRepository;
            this.specialtyRepository = specialtyRepository;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;

            // Limpando todas as entidades

            this.userRepository.deleteAll();
            this.specialtyRepository.deleteAll();
            this.healthCenterRepository.deleteAll();
            this.doctorRepository.deleteAll();
            this.penaltyRepository.deleteAll();
            this.appointmentRepository.deleteAll();

            // Formatadores

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Inicialização dos usuários

            User user1 = new User();
            user1.setSusCardNumber("012345678901234");
            user1.setName("Washington");
            user1.setCpf("01234567890");
            user1.setPhone("5571982345678");
            user1.setEmail("email1@gmail.com");
            user1.setPassword(this.passwordEncoder.encode(this.password));
            user1.setRole(Role.ADMIN);
            this.userRepository.save(user1);

            User user2 = new User();
            user2.setSusCardNumber("012345678901235");
            user2.setName("Eduardo");
            user2.setCpf("01234567891");
            user2.setPhone("5571982345679");
            user2.setEmail("email2@gmail.com");
            user2.setPassword(this.passwordEncoder.encode(this.password));
            user2.setRole(Role.ADMIN);
            this.userRepository.save(user2);

            // Inicialização das especialidades

            Specialty specialty1 = new Specialty();
            specialty1.setName("Cardiologia");
            this.specialtyRepository.save(specialty1);

            Specialty specialty2 = new Specialty();
            specialty2.setName("Oftalmologia");
            this.specialtyRepository.save(specialty2);

            Specialty specialty3 = new Specialty();
            specialty3.setName("Nutrição");
            this.specialtyRepository.save(specialty3);

            Specialty specialty4 = new Specialty();
            specialty4.setName("Neurologia");
            this.specialtyRepository.save(specialty4);

            Specialty specialty5 = new Specialty();
            specialty5.setName("Psiquiatria");
            this.specialtyRepository.save(specialty5);

            // Inicialização dos postos de saúde

            HealthCenter healthCenter1 = new HealthCenter();
            healthCenter1.setName("Pituba");
            healthCenter1.setAddress("Av. Num sei das quantas");
            healthCenter1.setOpeningHour(LocalTime.parse("08:00", timeFormatter));
            healthCenter1.setClosingHour(LocalTime.parse("18:00", timeFormatter));
            healthCenter1.getSpecialties().add(specialty1);
            healthCenter1.getSpecialties().add(specialty3);
            healthCenter1.getSpecialties().add(specialty4);
            healthCenter1.getSpecialties().add(specialty5);
            this.healthCenterRepository.save(healthCenter1);

            HealthCenter healthCenter2 = new HealthCenter();
            healthCenter2.setName("Itinga");
            healthCenter2.setAddress("Av. ABCD");
            healthCenter2.setOpeningHour(LocalTime.parse("10:00", timeFormatter));
            healthCenter2.setClosingHour(LocalTime.parse("20:00", timeFormatter));
            healthCenter2.getSpecialties().add(specialty2);
            healthCenter2.getSpecialties().add(specialty3);
            healthCenter2.getSpecialties().add(specialty4);
            this.healthCenterRepository.save(healthCenter2);

            HealthCenter healthCenter3 = new HealthCenter();
            healthCenter3.setName("Stella Maris");
            healthCenter3.setAddress("Av. ECV Colossal");
            healthCenter3.setOpeningHour(LocalTime.parse("09:00", timeFormatter));
            healthCenter3.setClosingHour(LocalTime.parse("19:00", timeFormatter));
            healthCenter3.getSpecialties().add(specialty1);
            healthCenter3.getSpecialties().add(specialty2);
            healthCenter3.getSpecialties().add(specialty5);
            this.healthCenterRepository.save(healthCenter3);

            // Inicialização dos médicos

            Doctor doctor1 = new Doctor();
            doctor1.setCrm("12345-BA");
            doctor1.setStartWork(LocalTime.parse("08:00", timeFormatter));
            doctor1.setEndWork(LocalTime.parse("18:00", timeFormatter));
            doctor1.setWorkingDays(new HashSet<>(Set.of(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
            )));
            doctor1.getHealthCenters().add(healthCenter1);
            doctor1.getHealthCenters().add(healthCenter2);
            doctor1.getSpecialties().add(specialty1);
            doctor1.getSpecialties().add(specialty3);
            doctor1.setUser(user1);
            this.doctorRepository.save(doctor1);

            Doctor doctor2 = new Doctor();
            doctor2.setCrm("12345-SP");
            doctor2.setStartWork(LocalTime.parse("09:00", timeFormatter));
            doctor2.setEndWork(LocalTime.parse("19:00", timeFormatter));
            doctor2.setWorkingDays(new HashSet<>(Set.of(
                    DayOfWeek.MONDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.FRIDAY
            )));
            doctor2.getHealthCenters().add(healthCenter1);
            doctor2.getHealthCenters().add(healthCenter2);
            doctor2.getHealthCenters().add(healthCenter3);
            doctor2.getSpecialties().add(specialty2);
            doctor2.getSpecialties().add(specialty5);
            doctor2.setUser(user2);
            this.doctorRepository.save(doctor2);

            // Inicialização das penalidades

            Penalty penalty1 = new Penalty();
            penalty1.setPenaltyStartDate(LocalDate.parse("14/12/2025", dateFormatter));
            penalty1.setPenaltyEndDate(LocalDate.parse("21/12/2025", dateFormatter));
            penalty1.setUser(user1);
            this.penaltyRepository.save(penalty1);

            Penalty penalty2 = new Penalty();
            penalty2.setPenaltyStartDate(LocalDate.parse("16/12/2025", dateFormatter));
            penalty2.setPenaltyEndDate(LocalDate.parse("23/12/2025", dateFormatter));
            penalty2.setUser(user2);
            this.penaltyRepository.save(penalty2);

            // Inicialização das consultas

            Appointment appointment1 = new Appointment();
            appointment1.setAppointmentDateTime(LocalDateTime.parse("27/12/2025 14:30", dateTimeFormatter));
            appointment1.setAppointmentStatus(AppointmentStatus.SCHEDULED);
            appointment1.setDoctor(doctor1);
            appointment1.setHealthCenter(healthCenter1);
            appointment1.setSpecialty(specialty1);
            appointment1.setUser(user2);
            this.appointmentRepository.save(appointment1);

            Appointment appointment2 = new Appointment();
            appointment2.setAppointmentDateTime(LocalDateTime.parse("16/12/2025 09:30", dateTimeFormatter));
            appointment2.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment2.setDoctor(doctor1);
            appointment2.setHealthCenter(healthCenter2);
            appointment2.setSpecialty(specialty3);
            appointment2.setUser(user2);
            this.appointmentRepository.save(appointment2);

            Appointment appointment3 = new Appointment();
            appointment3.setAppointmentDateTime(LocalDateTime.parse("18/12/2025 17:00", dateTimeFormatter));
            appointment3.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment3.setDoctor(doctor2);
            appointment3.setHealthCenter(healthCenter2);
            appointment3.setSpecialty(specialty2);
            appointment3.setUser(user1);
            this.appointmentRepository.save(appointment3);

            Appointment appointment4 = new Appointment();
            appointment4.setAppointmentDateTime(LocalDateTime.parse("18/12/2025 16:00", dateTimeFormatter));
            appointment4.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment4.setDoctor(doctor2);
            appointment4.setHealthCenter(healthCenter1);
            appointment4.setSpecialty(specialty5);
            // User null
            this.appointmentRepository.save(appointment4);

            Appointment appointment5 = new Appointment();
            appointment5.setAppointmentDateTime(LocalDateTime.parse("17/12/2025 10:00", dateTimeFormatter));
            appointment5.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment5.setDoctor(doctor2);
            appointment5.setHealthCenter(healthCenter3);
            appointment5.setSpecialty(specialty2);
            // User null
            this.appointmentRepository.save(appointment5);

            Appointment appointment6 = new Appointment();
            appointment6.setAppointmentDateTime(LocalDateTime.parse("17/12/2025 11:00", dateTimeFormatter));
            appointment6.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment6.setDoctor(doctor2);
            appointment6.setHealthCenter(healthCenter3);
            appointment6.setSpecialty(specialty5);
            // User null
            this.appointmentRepository.save(appointment6);
        };
    }
}
