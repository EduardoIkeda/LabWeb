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
import com.uneb.labweb.enums.UserStatus;
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

    @Value("${api.database.password}")
    private String password;

    // Repositórios para acesso a dados
    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;
    private HealthCenterRepository healthCenterRepository;
    private PenaltyRepository penaltyRepository;
    private SpecialtyRepository specialtyRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Método principal da aplicação. Inicializa a aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringMarcacaoConsultaApplication.class, args);
    }

    /**
     * Inicializa o banco de dados com dados de teste, como usuários, especialidades,
     * postos de saúde, médicos e penalidades.
     * Executado apenas no perfil de teste ("test").
     *
     * @param self Instância atual da aplicação
     * @param appointmentRepository Repositório de consultas
     * @param doctorRepository Repositório de médicos
     * @param healthCenterRepository Repositório de postos de saúde
     * @param penaltyRepository Repositório de penalidades
     * @param specialtyRepository Repositório de especialidades
     * @param userRepository Repositório de usuários
     * @param passwordEncoder Codificador de senha
     * @return CommandLineRunner para inicializar dados
     */
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
            this.appointmentRepository = appointmentRepository;
            this.doctorRepository = doctorRepository;
            this.healthCenterRepository = healthCenterRepository;
            this.penaltyRepository = penaltyRepository;
            this.specialtyRepository = specialtyRepository;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;

            // Limpeza das tabelas no banco de dados
            this.userRepository.deleteAll();
            this.specialtyRepository.deleteAll();
            this.healthCenterRepository.deleteAll();
            this.doctorRepository.deleteAll();
            this.penaltyRepository.deleteAll();
            this.appointmentRepository.deleteAll();

            // Definindo formatadores para data e hora
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Criação e persistência de usuários
            User user1 = new User();
            user1.setSusCardNumber("012345678901234");
            user1.setName("Washington");
            user1.setCpf("01234567890");
            user1.setPhone("71982345678");
            user1.setEmail("email1@gmail.com");
            user1.setPassword(this.passwordEncoder.encode(this.password));
            user1.setAvatarUrl("https://i.imgur.com/m9rfeek.png");
            user1.setRole(Role.ADMIN);
            this.userRepository.save(user1);

            User user2 = new User();
            user2.setSusCardNumber("012345678901235");
            user2.setName("Eduardo");
            user2.setCpf("01234567891");
            user2.setPhone("71982345679");
            user2.setEmail("email2@gmail.com");
            user2.setPassword(this.passwordEncoder.encode(this.password));
            user2.setAvatarUrl("https://i.imgur.com/GWD83vj.png");
            user2.setRole(Role.ADMIN);
            this.userRepository.save(user2);

            User user3 = new User();
            user3.setSusCardNumber("012345678901236");
            user3.setName("Vinícius");
            user3.setCpf("01234567892");
            user3.setPhone("71982345670");
            user3.setEmail("email3@gmail.com");
            user3.setPassword(this.passwordEncoder.encode(this.password));
            user3.setAvatarUrl("https://i.imgur.com/PRNTYxH.png");
            user3.setRole(Role.ADMIN);
            this.userRepository.save(user3);

            User user4 = new User();
            user4.setSusCardNumber("012345678901237");
            user4.setName("Carol");
            user4.setCpf("01234567893");
            user4.setPhone("71982345671");
            user4.setEmail("email4@gmail.com");
            user4.setPassword(this.passwordEncoder.encode(this.password));
            user4.setAvatarUrl("https://i.imgur.com/OdjyMRI.jpeg");
            user4.setRole(Role.ADMIN);
            this.userRepository.save(user4);

            User user5 = new User();
            user5.setSusCardNumber("012345678901238");
            user5.setName("Dr. João Silva");
            user5.setCpf("01234567894");
            user5.setPhone("71982345672");
            user5.setEmail("email5@gmail.com");
            user5.setPassword(this.passwordEncoder.encode(this.password));
            user5.setAvatarUrl("https://i.imgur.com/deNyPdX.png");
            user5.setRole(Role.DOCTOR);
            this.userRepository.save(user5);

            User user6 = new User();
            user6.setSusCardNumber("012345678901239");
            user6.setName("Dra. Maria Oliveira");
            user6.setCpf("01234567895");
            user6.setPhone("71982345673");
            user6.setEmail("email6@gmail.com");
            user6.setPassword(this.passwordEncoder.encode(this.password));
            user6.setAvatarUrl("https://i.imgur.com/LooAP9k.png");
            user6.setRole(Role.DOCTOR);
            this.userRepository.save(user6);

            User user7 = new User();
            user7.setSusCardNumber("012345678901230");
            user7.setName("Dra. Vitória");
            user7.setCpf("01234567896");
            user7.setPhone("71982345674");
            user7.setEmail("email7@gmail.com");
            user7.setPassword(this.passwordEncoder.encode(this.password));
            user7.setAvatarUrl("https://i.imgur.com/JIKT5Wq.png");
            user7.setRole(Role.DOCTOR);
            this.userRepository.save(user7);

            User user8 = new User();
            user8.setSusCardNumber("012345678901111");
            user8.setName("Felipe Alves");
            user8.setCpf("01234567888");
            user8.setPhone("71982345666");
            user8.setEmail("email8@gmail.com");
            user8.setPassword(this.passwordEncoder.encode(this.password));
            user8.setAvatarUrl("https://i.imgur.com/MEP4uJt.png");
            user8.setRole(Role.CITIZEN);
            this.userRepository.save(user8);

            User user9 = new User();
            user9.setSusCardNumber("012345678902222");
            user9.setName("Pedro Ferreira");
            user9.setCpf("01234567999");
            user9.setPhone("71982345777");
            user9.setEmail("email9@gmail.com");
            user9.setPassword(this.passwordEncoder.encode(this.password));
            user9.setAvatarUrl("https://i.imgur.com/ZHLSVUT.png");
            user9.setRole(Role.CITIZEN);
            this.userRepository.save(user9);

            User user10 = new User();
            user10.setSusCardNumber("012345678903333");
            user10.setName("Aline Silva");
            user10.setCpf("01234567777");
            user10.setPhone("71982345555");
            user10.setEmail("email10@gmail.com");
            user10.setPassword(this.passwordEncoder.encode(this.password));
            user10.setAvatarUrl("https://i.imgur.com/xl5cLQd.png");
            user10.setRole(Role.CITIZEN);
            user10.setUserStatus(UserStatus.BLOCKED);
            this.userRepository.save(user10);

            // Criação e persistência de especialidades
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

            // Criação e persistência de postos de saúde
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
            healthCenter3.getSpecialties().add(specialty4);
            healthCenter3.getSpecialties().add(specialty5);
            this.healthCenterRepository.save(healthCenter3);

            // Criação e persistência de médicos
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
            doctor1.setUser(user5);
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
            doctor2.setUser(user6);
            this.doctorRepository.save(doctor2);

            Doctor doctor3 = new Doctor();
            doctor3.setCrm("13254-BA");
            doctor3.setStartWork(LocalTime.parse("08:00", timeFormatter));
            doctor3.setEndWork(LocalTime.parse("18:00", timeFormatter));
            doctor3.setWorkingDays(new HashSet<>(Set.of(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
            )));
            doctor3.getHealthCenters().add(healthCenter1);
            doctor3.getHealthCenters().add(healthCenter2);
            doctor3.getHealthCenters().add(healthCenter3);
            doctor3.getSpecialties().add(specialty4);
            doctor3.setUser(user7);
            this.doctorRepository.save(doctor3);

            // Criação e persistência de penalidades
            Penalty penalty1 = new Penalty();
            penalty1.setPenaltyStartDate(LocalDate.parse("14/12/2025", dateFormatter));
            penalty1.setPenaltyEndDate(LocalDate.parse("21/12/2025", dateFormatter));
            penalty1.setUser(user10);
            this.penaltyRepository.save(penalty1);

            // Criação e persistência de consultas
            Appointment appointment1 = new Appointment();
            appointment1.setAppointmentDateTime(LocalDateTime.parse("10/10/2024 14:30", dateTimeFormatter));
            appointment1.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment1.setDoctor(doctor1);
            appointment1.setHealthCenter(healthCenter1);
            appointment1.setSpecialty(specialty1);
            appointment1.setUser(user2);
            appointment1.setCancellationCount(2);
            this.appointmentRepository.save(appointment1);

            Appointment appointment2 = new Appointment();
            appointment2.setAppointmentDateTime(LocalDateTime.parse("03/09/2024 09:30", dateTimeFormatter));
            appointment2.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment2.setDoctor(doctor1);
            appointment2.setHealthCenter(healthCenter2);
            appointment2.setSpecialty(specialty3);
            appointment2.setUser(user3);
            appointment2.setCancellationCount(1);
            this.appointmentRepository.save(appointment2);

            Appointment appointment3 = new Appointment();
            appointment3.setAppointmentDateTime(LocalDateTime.parse("06/05/2024 17:00", dateTimeFormatter));
            appointment3.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment3.setDoctor(doctor2);
            appointment3.setHealthCenter(healthCenter2);
            appointment3.setSpecialty(specialty2);
            appointment3.setUser(user1);
            appointment3.setCancellationCount(0);
            this.appointmentRepository.save(appointment3);

            Appointment appointment4 = new Appointment();
            appointment4.setAppointmentDateTime(LocalDateTime.parse("18/05/2024 16:00", dateTimeFormatter));
            appointment4.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment4.setDoctor(doctor2);
            appointment4.setHealthCenter(healthCenter1);
            appointment4.setSpecialty(specialty5);
            appointment4.setUser(user4);
            appointment4.setCancellationCount(0);
            this.appointmentRepository.save(appointment4);

            Appointment appointment5 = new Appointment();
            appointment5.setAppointmentDateTime(LocalDateTime.parse("17/01/2025 10:00", dateTimeFormatter));
            appointment5.setAppointmentStatus(AppointmentStatus.SCHEDULED);
            appointment5.setDoctor(doctor2);
            appointment5.setHealthCenter(healthCenter3);
            appointment5.setSpecialty(specialty2);
            appointment5.setUser(user1);
            appointment5.setCancellationCount(3);
            this.appointmentRepository.save(appointment5);

            Appointment appointment6 = new Appointment();
            appointment6.setAppointmentDateTime(LocalDateTime.parse("17/01/2025 11:00", dateTimeFormatter));
            appointment6.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment6.setDoctor(doctor2);
            appointment6.setHealthCenter(healthCenter3);
            appointment6.setSpecialty(specialty5);
            // User null
            appointment6.setCancellationCount(0);
            this.appointmentRepository.save(appointment6);

            Appointment appointment7 = new Appointment();
            appointment7.setAppointmentDateTime(LocalDateTime.parse("13/01/2025 11:00", dateTimeFormatter));
            appointment7.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment7.setDoctor(doctor2);
            appointment7.setHealthCenter(healthCenter1);
            appointment7.setSpecialty(specialty5);
            // User null
            appointment7.setCancellationCount(0);
            this.appointmentRepository.save(appointment7);

            Appointment appointment8 = new Appointment();
            appointment8.setAppointmentDateTime(LocalDateTime.parse("06/01/2025 14:00", dateTimeFormatter));
            appointment8.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment8.setDoctor(doctor1);
            appointment8.setHealthCenter(healthCenter1);
            appointment8.setSpecialty(specialty3);
            // User null
            appointment8.setCancellationCount(0);
            this.appointmentRepository.save(appointment8);

            Appointment appointment9 = new Appointment();
            appointment9.setAppointmentDateTime(LocalDateTime.parse("20/01/2025 14:00", dateTimeFormatter));
            appointment9.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment9.setDoctor(doctor2);
            appointment9.setHealthCenter(healthCenter3);
            appointment9.setSpecialty(specialty5);
            // User null
            appointment9.setCancellationCount(0);
            this.appointmentRepository.save(appointment9);

            Appointment appointment10 = new Appointment();
            appointment10.setAppointmentDateTime(LocalDateTime.parse("20/01/2025 15:00", dateTimeFormatter));
            appointment10.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment10.setDoctor(doctor2);
            appointment10.setHealthCenter(healthCenter3);
            appointment10.setSpecialty(specialty5);
            // User null
            appointment10.setCancellationCount(0);
            this.appointmentRepository.save(appointment10);

            Appointment appointment11 = new Appointment();
            appointment11.setAppointmentDateTime(LocalDateTime.parse("07/01/2025 15:00", dateTimeFormatter));
            appointment11.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment11.setDoctor(doctor1);
            appointment11.setHealthCenter(healthCenter2);
            appointment11.setSpecialty(specialty3);
            // User null
            appointment11.setCancellationCount(0);
            this.appointmentRepository.save(appointment11);

            Appointment appointment12 = new Appointment();
            appointment12.setAppointmentDateTime(LocalDateTime.parse("08/01/2025 15:00", dateTimeFormatter));
            appointment12.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment12.setDoctor(doctor2);
            appointment12.setHealthCenter(healthCenter2);
            appointment12.setSpecialty(specialty2);
            // User null
            appointment12.setCancellationCount(0);
            this.appointmentRepository.save(appointment12);

            Appointment appointment13 = new Appointment();
            appointment13.setAppointmentDateTime(LocalDateTime.parse("22/01/2025 16:00", dateTimeFormatter));
            appointment13.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment13.setDoctor(doctor2);
            appointment13.setHealthCenter(healthCenter2);
            appointment13.setSpecialty(specialty2);
            // User null
            appointment13.setCancellationCount(0);
            this.appointmentRepository.save(appointment13);

            Appointment appointment14 = new Appointment();
            appointment14.setAppointmentDateTime(LocalDateTime.parse("06/01/2025 16:00", dateTimeFormatter));
            appointment14.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment14.setDoctor(doctor1);
            appointment14.setHealthCenter(healthCenter1);
            appointment14.setSpecialty(specialty1);
            // User null
            appointment14.setCancellationCount(0);
            this.appointmentRepository.save(appointment14);

            Appointment appointment15 = new Appointment();
            appointment15.setAppointmentDateTime(LocalDateTime.parse("29/01/2025 15:00", dateTimeFormatter));
            appointment15.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment15.setDoctor(doctor1);
            appointment15.setHealthCenter(healthCenter1);
            appointment15.setSpecialty(specialty1);
            // User null
            appointment15.setCancellationCount(0);
            this.appointmentRepository.save(appointment15);

            Appointment appointment16 = new Appointment();
            appointment16.setAppointmentDateTime(LocalDateTime.parse("30/01/2025 14:30", dateTimeFormatter));
            appointment16.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment16.setDoctor(doctor1);
            appointment16.setHealthCenter(healthCenter3);
            appointment16.setSpecialty(specialty1);
            // User null
            appointment16.setCancellationCount(0);
            this.appointmentRepository.save(appointment16);

            Appointment appointment17 = new Appointment();
            appointment17.setAppointmentDateTime(LocalDateTime.parse("15/01/2025 14:00", dateTimeFormatter));
            appointment17.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment17.setDoctor(doctor3);
            appointment17.setHealthCenter(healthCenter1);
            appointment17.setSpecialty(specialty4);
            // User null
            appointment17.setCancellationCount(1);
            this.appointmentRepository.save(appointment17);

            Appointment appointment18 = new Appointment();
            appointment18.setAppointmentDateTime(LocalDateTime.parse("15/01/2025 16:00", dateTimeFormatter));
            appointment18.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment18.setDoctor(doctor3);
            appointment18.setHealthCenter(healthCenter1);
            appointment18.setSpecialty(specialty4);
            // User null
            appointment18.setCancellationCount(0);
            this.appointmentRepository.save(appointment18);

            Appointment appointment19 = new Appointment();
            appointment19.setAppointmentDateTime(LocalDateTime.parse("16/01/2025 14:00", dateTimeFormatter));
            appointment19.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment19.setDoctor(doctor3);
            appointment19.setHealthCenter(healthCenter2);
            appointment19.setSpecialty(specialty4);
            // User null
            appointment19.setCancellationCount(0);
            this.appointmentRepository.save(appointment19);

            Appointment appointment20 = new Appointment();
            appointment20.setAppointmentDateTime(LocalDateTime.parse("03/02/2025 14:00", dateTimeFormatter));
            appointment20.setAppointmentStatus(AppointmentStatus.PENDING);
            appointment20.setDoctor(doctor3);
            appointment20.setHealthCenter(healthCenter3);
            appointment20.setSpecialty(specialty4);
            // User null
            appointment20.setCancellationCount(1);
            this.appointmentRepository.save(appointment20);

            Appointment appointment21 = new Appointment();
            appointment21.setAppointmentDateTime(LocalDateTime.parse("01/03/2024 09:30", dateTimeFormatter));
            appointment21.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment21.setDoctor(doctor1);
            appointment21.setHealthCenter(healthCenter2);
            appointment21.setSpecialty(specialty3);
            appointment21.setUser(user3);
            appointment21.setCancellationCount(1);
            this.appointmentRepository.save(appointment21);

            Appointment appointment22 = new Appointment();
            appointment22.setAppointmentDateTime(LocalDateTime.parse("01/06/2024 09:30", dateTimeFormatter));
            appointment22.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment22.setDoctor(doctor1);
            appointment22.setHealthCenter(healthCenter2);
            appointment22.setSpecialty(specialty3);
            appointment22.setUser(user1);
            appointment22.setCancellationCount(0);
            this.appointmentRepository.save(appointment22);

            Appointment appointment23 = new Appointment();
            appointment23.setAppointmentDateTime(LocalDateTime.parse("04/11/2024 09:30", dateTimeFormatter));
            appointment23.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment23.setDoctor(doctor1);
            appointment23.setHealthCenter(healthCenter2);
            appointment23.setSpecialty(specialty3);
            appointment23.setUser(user4);
            appointment23.setCancellationCount(2);
            this.appointmentRepository.save(appointment23);

            Appointment appointment24 = new Appointment();
            appointment24.setAppointmentDateTime(LocalDateTime.parse("16/12/2023 09:30", dateTimeFormatter));
            appointment24.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment24.setDoctor(doctor3);
            appointment24.setHealthCenter(healthCenter3);
            appointment24.setSpecialty(specialty4);
            appointment24.setUser(user2);
            appointment24.setCancellationCount(1);
            this.appointmentRepository.save(appointment24);

            Appointment appointment25 = new Appointment();
            appointment25.setAppointmentDateTime(LocalDateTime.parse("15/01/2024 09:30", dateTimeFormatter));
            appointment25.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment25.setDoctor(doctor3);
            appointment25.setHealthCenter(healthCenter3);
            appointment25.setSpecialty(specialty4);
            appointment25.setUser(user8);
            appointment25.setCancellationCount(0);
            this.appointmentRepository.save(appointment25);

            Appointment appointment26 = new Appointment();
            appointment26.setAppointmentDateTime(LocalDateTime.parse("19/04/2024 17:00", dateTimeFormatter));
            appointment26.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment26.setDoctor(doctor3);
            appointment26.setHealthCenter(healthCenter3);
            appointment26.setSpecialty(specialty4);
            appointment26.setUser(user9);
            appointment26.setCancellationCount(1);
            this.appointmentRepository.save(appointment26);

            Appointment appointment27 = new Appointment();
            appointment27.setAppointmentDateTime(LocalDateTime.parse("19/04/2023 17:00", dateTimeFormatter));
            appointment27.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment27.setDoctor(doctor2);
            appointment27.setHealthCenter(healthCenter2);
            appointment27.setSpecialty(specialty2);
            appointment27.setUser(user10);
            appointment27.setCancellationCount(0);
            this.appointmentRepository.save(appointment27);

            Appointment appointment28 = new Appointment();
            appointment28.setAppointmentDateTime(LocalDateTime.parse("04/12/2024 17:00", dateTimeFormatter));
            appointment28.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment28.setDoctor(doctor2);
            appointment28.setHealthCenter(healthCenter2);
            appointment28.setSpecialty(specialty2);
            appointment28.setUser(user8);
            appointment28.setCancellationCount(2);
            this.appointmentRepository.save(appointment28);

            Appointment appointment29 = new Appointment();
            appointment29.setAppointmentDateTime(LocalDateTime.parse("11/12/2024 17:00", dateTimeFormatter));
            appointment29.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment29.setDoctor(doctor2);
            appointment29.setHealthCenter(healthCenter2);
            appointment29.setSpecialty(specialty2);
            appointment29.setUser(user9);
            appointment29.setCancellationCount(0);
            this.appointmentRepository.save(appointment29);

            Appointment appointment30 = new Appointment();
            appointment30.setAppointmentDateTime(LocalDateTime.parse("18/12/2024 17:00", dateTimeFormatter));
            appointment30.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment30.setDoctor(doctor1);
            appointment30.setHealthCenter(healthCenter1);
            appointment30.setSpecialty(specialty1);
            appointment30.setUser(user10);
            appointment30.setCancellationCount(1);
            this.appointmentRepository.save(appointment30);

            Appointment appointment31 = new Appointment();
            appointment31.setAppointmentDateTime(LocalDateTime.parse("16/12/2024 08:30", dateTimeFormatter));
            appointment31.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment31.setDoctor(doctor1);
            appointment31.setHealthCenter(healthCenter1);
            appointment31.setSpecialty(specialty1);
            appointment31.setUser(user8);
            appointment31.setCancellationCount(0);
            this.appointmentRepository.save(appointment31);

            Appointment appointment32 = new Appointment();
            appointment32.setAppointmentDateTime(LocalDateTime.parse("20/12/2024 11:00", dateTimeFormatter));
            appointment32.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment32.setDoctor(doctor1);
            appointment32.setHealthCenter(healthCenter1);
            appointment32.setSpecialty(specialty1);
            appointment32.setUser(user9);
            appointment32.setCancellationCount(0);
            this.appointmentRepository.save(appointment32);

            Appointment appointment33 = new Appointment();
            appointment33.setAppointmentDateTime(LocalDateTime.parse("04/02/2024 17:00", dateTimeFormatter));
            appointment33.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment33.setDoctor(doctor2);
            appointment33.setHealthCenter(healthCenter1);
            appointment33.setSpecialty(specialty5);
            appointment33.setUser(user10);
            appointment33.setCancellationCount(1);
            this.appointmentRepository.save(appointment33);

            Appointment appointment34 = new Appointment();
            appointment34.setAppointmentDateTime(LocalDateTime.parse("03/02/2024 17:00", dateTimeFormatter));
            appointment34.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment34.setDoctor(doctor2);
            appointment34.setHealthCenter(healthCenter1);
            appointment34.setSpecialty(specialty5);
            appointment34.setUser(user8);
            appointment34.setCancellationCount(0);
            this.appointmentRepository.save(appointment34);

            Appointment appointment35 = new Appointment();
            appointment35.setAppointmentDateTime(LocalDateTime.parse("02/08/2024 17:00", dateTimeFormatter));
            appointment35.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment35.setDoctor(doctor2);
            appointment35.setHealthCenter(healthCenter1);
            appointment35.setSpecialty(specialty5);
            appointment35.setUser(user9);
            appointment35.setCancellationCount(1);
            this.appointmentRepository.save(appointment35);

            Appointment appointment36 = new Appointment();
            appointment36.setAppointmentDateTime(LocalDateTime.parse("02/08/2024 16:00", dateTimeFormatter));
            appointment36.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment36.setDoctor(doctor2);
            appointment36.setHealthCenter(healthCenter1);
            appointment36.setSpecialty(specialty5);
            appointment36.setUser(user10);
            appointment36.setCancellationCount(1);
            this.appointmentRepository.save(appointment36);

            Appointment appointment37 = new Appointment();
            appointment37.setAppointmentDateTime(LocalDateTime.parse("02/08/2024 15:00", dateTimeFormatter));
            appointment37.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment37.setDoctor(doctor2);
            appointment37.setHealthCenter(healthCenter2);
            appointment37.setSpecialty(specialty2);
            appointment37.setUser(user8);
            appointment37.setCancellationCount(0);
            this.appointmentRepository.save(appointment37);

            Appointment appointment38 = new Appointment();
            appointment38.setAppointmentDateTime(LocalDateTime.parse("02/08/2024 15:00", dateTimeFormatter));
            appointment38.setAppointmentStatus(AppointmentStatus.MISSED);
            appointment38.setDoctor(doctor2);
            appointment38.setHealthCenter(healthCenter2);
            appointment38.setSpecialty(specialty2);
            appointment38.setUser(user9);
            appointment38.setCancellationCount(0);
            this.appointmentRepository.save(appointment38);

            Appointment appointment39 = new Appointment();
            appointment39.setAppointmentDateTime(LocalDateTime.parse("02/07/2024 17:00", dateTimeFormatter));
            appointment39.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment39.setDoctor(doctor2);
            appointment39.setHealthCenter(healthCenter2);
            appointment39.setSpecialty(specialty2);
            appointment39.setUser(user10);
            appointment39.setCancellationCount(1);
            this.appointmentRepository.save(appointment39);

            Appointment appointment40 = new Appointment();
            appointment40.setAppointmentDateTime(LocalDateTime.parse("02/05/2024 16:00", dateTimeFormatter));
            appointment40.setAppointmentStatus(AppointmentStatus.ATTENDED);
            appointment40.setDoctor(doctor2);
            appointment40.setHealthCenter(healthCenter2);
            appointment40.setSpecialty(specialty2);
            appointment40.setUser(user8);
            appointment40.setCancellationCount(1);
            this.appointmentRepository.save(appointment40);
        };
    }
}
