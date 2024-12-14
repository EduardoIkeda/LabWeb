package com.uneb.labweb;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

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

@SpringBootApplication
public class SpringMarcacaoConsultaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMarcacaoConsultaApplication.class, args);
    }

    @Bean
    @Profile("test")
    @SuppressWarnings({"unused", "java:S125"})
    CommandLineRunner initDatabase(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            HealthCenterRepository healthCenterRepository,
            PenaltyRepository penaltyRepository,
            SpecialtyRepository specialtyRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            this.initHealthCenters(healthCenterRepository);
            this.initSpecialties(specialtyRepository);
            this.initUsers(userRepository, passwordEncoder);
            this.initDoctors(doctorRepository);
            this.initAppointments(appointmentRepository);
            this.initPenalties(penaltyRepository);          
        };
    }

    @SuppressWarnings("java:S125")
    public void initHealthCenters(HealthCenterRepository healthCenterRepository) {
        healthCenterRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        HealthCenter healthCenter1 = new HealthCenter();
        healthCenter1.setName("Posto SUS 1");
        healthCenter1.setAddress("Endereço genérico 1");
        healthCenter1.setOpeningHour(LocalTime.parse("08:00", formatter));
        healthCenter1.setClosingHour(LocalTime.parse("18:00", formatter));
        // healthCenter1.getAppointments().add(appointment);
        // healthCenter1.getDoctors().add(doctor);
        // healthCenter1.getSpecialties().add(specialty);
        healthCenterRepository.save(healthCenter1);

        HealthCenter healthCenter2 = new HealthCenter();
        healthCenter2.setName("Posto SUS 2");
        healthCenter2.setAddress("Endereço genérico 2");
        healthCenter2.setOpeningHour(LocalTime.parse("10:00", formatter));
        healthCenter2.setClosingHour(LocalTime.parse("20:00", formatter));
        // healthCenter2.getAppointments().add(appointment);
        // healthCenter2.getDoctors().add(doctor);
        // healthCenter2.getSpecialties().add(specialty);
        healthCenterRepository.save(healthCenter2);
    }

    @SuppressWarnings("java:S125")
    public void initSpecialties(SpecialtyRepository specialtyRepository) {
        specialtyRepository.deleteAll();

        Specialty specialty1 = new Specialty();
        specialty1.setName("Cardiologia");
        specialty1.setDescription("Descrição genérica 1");
        // specialty1.getAppointments().add(appointment);
        // specialty1.getDoctors().add(doctor);
        // specialty1.getHealthCenters().add(healthCenter);
        specialtyRepository.save(specialty1);

        Specialty specialty2 = new Specialty();
        specialty2.setName("Psiquiatria");
        specialty2.setDescription("Descrição genérica 2");
        // specialty2.getAppointments().add(appointment);
        // specialty2.getDoctors().add(doctor);
        // specialty2.getHealthCenters().add(healthCenter);
        specialtyRepository.save(specialty2);
    }

    @SuppressWarnings("java:S125")
    public void initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        userRepository.deleteAll();

        User user1 = new User();
        user1.setSusCardNumber("012345678901234");
        user1.setName("Washington");
        user1.setCpf("01234567890");
        user1.setPhone("5571982345678");
        user1.setEmail("email1@gmail.com");
        user1.setPassword(passwordEncoder.encode("12345"));
        user1.setRole(Role.ADMIN);
        // user1.getAppointments().add(appointment);
        // user1.setDoctor(doctor);
        // user1.getPenalties().add(penalty);
        userRepository.save(user1);

        User user2 = new User();
        user2.setSusCardNumber("012345678901235");
        user2.setName("Eduardo");
        user2.setCpf("01234567891");
        user2.setPhone("5571982345679");
        user2.setEmail("email2@gmail.com");
        user2.setPassword(passwordEncoder.encode("12346"));
        user2.setRole(Role.ADMIN);
        // user2.getAppointments().add(appointment);
        // user2.setDoctor(doctor);
        // user2.getPenalties().add(penalty);
        userRepository.save(user2);
    }

    @SuppressWarnings("java:S125")
    public void initDoctors(DoctorRepository doctorRepository) {
        doctorRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

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
        // doctor1.getAppointments().add(appointment);
        // doctor1.getHealthCenters().add(healthCenter);
        // doctor1.setSpecialty(specialty);
        // doctor1.setUser(user);
        doctorRepository.save(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setCrm("12345-SP");
        doctor2.setStartWork(LocalTime.parse("09:00", formatter));
        doctor2.setEndWork(LocalTime.parse("19:00", formatter));
        doctor2.setWorkingDays(new HashSet<>(Set.of(
                DayOfWeek.MONDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.FRIDAY
        )));
        // doctor2.getAppointments().add(appointment);
        // doctor2.getHealthCenters().add(healthCenter);
        // doctor2.setSpecialty(specialty);
        // doctor2.setUser(user);
        doctorRepository.save(doctor2);
    }

    @SuppressWarnings("java:S125")
    public void initAppointments(AppointmentRepository appointmentRepository) {
        appointmentRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentDateTime(LocalDateTime.parse("27/12/2024 14:30", formatter));
        appointment1.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        // appointment1.setDoctor(doctor);
        // appointment1.setHealthCenter(healthCenter);
        // appointment1.setSpecialty(specialty);
        // appointment1.setUser(user);
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentDateTime(LocalDateTime.parse("16/12/2024 09:30", formatter));
        appointment2.setAppointmentStatus(AppointmentStatus.ATTENDED);
        // appointment2.setDoctor(doctor);
        // appointment2.setHealthCenter(healthCenter);
        // appointment2.setSpecialty(specialty);
        // appointment2.setUser(user);
        appointmentRepository.save(appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setAppointmentDateTime(LocalDateTime.parse("18/12/2024 17:00", formatter));
        appointment3.setAppointmentStatus(AppointmentStatus.MISSED);
        // appointment3.setDoctor(doctor);
        // appointment3.setHealthCenter(healthCenter);
        // appointment3.setSpecialty(specialty);
        // appointment3.setUser(user);
        appointmentRepository.save(appointment3);
    }

    @SuppressWarnings("java:S125")
    public void initPenalties(PenaltyRepository penaltyRepository) {
        penaltyRepository.deleteAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Penalty penalty1 = new Penalty();
        penalty1.setPenaltyReason("Razão genérica 1");
        penalty1.setPenaltyStartDate(LocalDate.parse("14/12/2024", formatter));
        penalty1.setPenaltyEndDate(LocalDate.parse("21/12/2024", formatter));
        // penalty1.setUser(user);
        penaltyRepository.save(penalty1);

        Penalty penalty2 = new Penalty();
        penalty2.setPenaltyReason("Razão genérica 2");
        penalty2.setPenaltyStartDate(LocalDate.parse("16/12/2024", formatter));
        penalty2.setPenaltyEndDate(LocalDate.parse("23/12/2024", formatter));
        // penalty2.setUser(user);
        penaltyRepository.save(penalty2);
    }
}
