package com.uneb.labweb;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
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

            User user = this.self.initUsers();
            HealthCenter healthCenter = this.self.initHealthCenters();
            Specialty specialty = this.self.initSpecialties();
            Doctor doctor = this.self.initDoctors();
            this.self.initPenalties();
            this.self.initAppointments(doctor, healthCenter, specialty, user);
        };
    }

    public User initUsers() {
        this.userRepository.deleteAll();

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

        return user2;
    }

    public HealthCenter initHealthCenters() {
        this.healthCenterRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        HealthCenter healthCenter1 = new HealthCenter();
        healthCenter1.setName("Posto SUS 1");
        healthCenter1.setAddress("Endereço genérico 1");
        healthCenter1.setOpeningHour(LocalTime.parse("08:00", formatter));
        healthCenter1.setClosingHour(LocalTime.parse("18:00", formatter));
        this.healthCenterRepository.save(healthCenter1);

        HealthCenter healthCenter2 = new HealthCenter();
        healthCenter2.setName("Posto SUS 2");
        healthCenter2.setAddress("Endereço genérico 2");
        healthCenter2.setOpeningHour(LocalTime.parse("10:00", formatter));
        healthCenter2.setClosingHour(LocalTime.parse("20:00", formatter));
        this.healthCenterRepository.save(healthCenter2);

        return healthCenter1;
    }
 
    public Specialty initSpecialties() {

        this.specialtyRepository.deleteAll();

        List<HealthCenter> healthCenters = healthCenterRepository.findAll();

        Specialty specialty1 = new Specialty();
        specialty1.setName("Cardiologia");
        specialty1.getHealthCenters().add(healthCenters.get(0));
        this.specialtyRepository.save(specialty1);

        Specialty specialty2 = new Specialty();
        specialty2.setName("Psiquiatria"); 
        specialty2.getHealthCenters().add(healthCenters.get(1));
        this.specialtyRepository.save(specialty2);

        return specialty1;
    }

    public Doctor initDoctors() {
        this.doctorRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        List<HealthCenter> healthCenters = healthCenterRepository.findAll();
        List<Specialty> specialties = specialtyRepository.findAll();
        List<User> users = userRepository.findAll();

        Doctor doctor1 = new Doctor();
        doctor1.setCrm("12345-BA");
        doctor1.setStartWork(LocalTime.parse("08:00", formatter));
        doctor1.setEndWork(LocalTime.parse("18:00", formatter));
        doctor1.setWorkingDays(new HashSet<>(Set.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        )));
        doctor1.getHealthCenters().add(healthCenters.get(0));
        doctor1.getSpecialties().add(specialties.get(0));
        doctor1.setUser(users.get(0));
        this.doctorRepository.save(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setCrm("12345-SP");
        doctor2.setStartWork(LocalTime.parse("09:00", formatter));
        doctor2.setEndWork(LocalTime.parse("19:00", formatter));
        doctor2.setWorkingDays(new HashSet<>(Set.of(
                DayOfWeek.MONDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.FRIDAY
        )));
        doctor2.getHealthCenters().add(healthCenters.get(1));
        doctor2.getSpecialties().add(specialties.get(1));
        doctor2.setUser(users.get(1));
        this.doctorRepository.save(doctor2);

        return doctor1;
    }

    public void initPenalties() {
        this.penaltyRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<User> users = userRepository.findAll();

        Penalty penalty1 = new Penalty();
        penalty1.setPenaltyStartDate(LocalDate.parse("14/12/2025", formatter));
        penalty1.setPenaltyEndDate(LocalDate.parse("21/12/2025", formatter));
        penalty1.setUser(users.get(0));
        this.penaltyRepository.save(penalty1);

        Penalty penalty2 = new Penalty();
        penalty2.setPenaltyStartDate(LocalDate.parse("16/12/2025", formatter));
        penalty2.setPenaltyEndDate(LocalDate.parse("23/12/2025", formatter));
        penalty2.setUser(users.get(1));
        this.penaltyRepository.save(penalty2);
    }

    public void initAppointments(Doctor doctor, HealthCenter healthCenter, Specialty specialty, User user) {
        this.appointmentRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentDateTime(LocalDateTime.parse("27/12/2025 14:30", formatter));
        appointment1.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        appointment1.setDoctor(doctor);
        appointment1.setHealthCenter(healthCenter);
        appointment1.setSpecialty(specialty);
        appointment1.setUser(user);
        this.appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentDateTime(LocalDateTime.parse("16/12/2025 09:30", formatter));
        appointment2.setAppointmentStatus(AppointmentStatus.ATTENDED);
        appointment2.setDoctor(doctor);
        appointment2.setHealthCenter(healthCenter);
        appointment2.setSpecialty(specialty);
        appointment2.setUser(user);
        this.appointmentRepository.save(appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setAppointmentDateTime(LocalDateTime.parse("18/12/2025 17:00", formatter));
        appointment3.setAppointmentStatus(AppointmentStatus.MISSED);
        appointment3.setDoctor(doctor);
        appointment3.setHealthCenter(healthCenter);
        appointment3.setSpecialty(specialty);
        appointment3.setUser(user);
        this.appointmentRepository.save(appointment3);
    }
}
