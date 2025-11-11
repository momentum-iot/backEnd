package com.example.gymadmin.config;

import com.example.gymadmin.model.*;
import com.example.gymadmin.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seed(UserRepo userRepo, PlanRepo planRepo, CheckInRepo checkInRepo, PaymentRepo paymentRepo,
                           EquipmentRepo equipmentRepo) {
        return args -> {
            // === Planes ===
            if (planRepo.count() == 0) {
                Plan basico = new Plan();
                basico.setName("Básico");
                basico.setPrice(79.0);
                basico.setDuration("mensual");
                basico.setPerks(List.of("Acceso general", "1 evaluación física"));

                Plan premium = new Plan();
                premium.setName("Premium");
                premium.setPrice(129.0);
                premium.setDuration("mensual");
                premium.setPerks(List.of("Acceso general", "Zona de pesas", "Clases ilimitadas", "Plan personalizado"));

                planRepo.saveAll(List.of(basico, premium));
            }

            // === Usuarios ===
            if (userRepo.count() == 0) {
                User u1 = makeUser("Lucía", "Gómez", "lucia.gomez@example.com", Membership.PREMIUM, Status.ACTIVO, "999111222", "2003-05-02");
                User u2 = makeUser("Carlos", "Ramos", "carlos.ramos@example.com", Membership.BASICO, Status.SIN_PAGAR, "988333999", "2002-11-10");
                User u3 = makeUser("Ana", "Salazar", "ana.salazar@example.com", Membership.BASICO, Status.ACTIVO, "955222777", "2004-02-21");
                User u4 = makeUser("Diego", "Paredes", "diego.paredes@example.com", Membership.PREMIUM, Status.RETIRADO, "977123456", "2000-09-14");

                List<User> extra = List.of(
                        makeUser("Sofía", "Luna", "sofia.luna@example.com", Membership.BASICO, Status.ACTIVO, "933111222", "2001-07-15"),
                        makeUser("Mateo", "Fernández", "mateo.fernandez@example.com", Membership.PREMIUM, Status.SIN_PAGAR, "922333444", "2002-12-09"),
                        makeUser("Camila", "Lopez", "camila.lopez@example.com", Membership.PREMIUM, Status.ACTIVO, "911888999", "2003-03-25"),
                        makeUser("Andrés", "García", "andres.garcia@example.com", Membership.BASICO, Status.RETIRADO, "955666777", "1999-08-30"),
                        makeUser("Valentina", "Rojas", "valentina.rojas@example.com", Membership.BASICO, Status.ACTIVO, "944555666", "2005-06-18")
                );

                userRepo.saveAll(List.of(u1, u2, u3, u4));
                userRepo.saveAll(extra);

                // === Check-ins ===
                checkInRepo.saveAll(List.of(
                        ci(u1, "2025-10-06T06:50:00-05:00"),
                        ci(u2, "2025-10-05T19:12:00-05:00"),
                        ci(u3, "2025-10-05T17:44:00-05:00"),
                        ci(extra.get(0), "2025-10-06T09:10:00-05:00"),
                        ci(extra.get(1), "2025-10-06T10:22:00-05:00")
                ));

                // === Pagos ===
                Payment p1 = new Payment();
                p1.setUser(u2);
                p1.setDueDate(LocalDate.parse("2025-10-05"));
                p1.setAmount(79.0);
                p1.setPaymentStatus(PaymentStatus.PENDIENTE);

                Payment p2 = new Payment();
                p2.setUser(u1);
                p2.setDueDate(LocalDate.parse("2025-11-01"));
                p2.setAmount(129.0);
                p2.setPaymentStatus(PaymentStatus.PAGADO);

                paymentRepo.saveAll(List.of(p1, p2));

                // === Equipos ===
                Equipment e1 = new Equipment();
                e1.setName("Cinta de correr");
                e1.setStatus("operativo");
                e1.setNextService(LocalDate.parse("2025-12-01"));

                Equipment e2 = new Equipment();
                e2.setName("Bicicleta estacionaria");
                e2.setStatus("mantenimiento");
                e2.setNextService(LocalDate.parse("2025-10-10"));

                Equipment e3 = new Equipment();
                e3.setName("Banco de pecho");
                e3.setStatus("operativo");
                e3.setNextService(LocalDate.parse("2026-01-15"));

                equipmentRepo.saveAll(List.of(e1, e2, e3));
            }
        };
    }

    // 🔹 Helper para crear usuarios
    private User makeUser(String first, String last, String email, Membership membership, Status status, String phone, String birth) {
        User u = new User();
        u.setName(first);
        u.setLastName(last);
        u.setEmail(email);
        u.setMembership(membership);
        u.setStatus(status);
        u.setPhone("+51 " + phone);
        u.setJoinDate(LocalDate.now().minusDays((int) (Math.random() * 100)));
        u.setBirthday(LocalDate.parse(birth));
        u.setEmergencyContact("Contacto " + last + " (+51 9" + (int) (900000000 + Math.random() * 99999999) + ")");
        u.setHeight(160 + (int) (Math.random() * 30));
        u.setWeight(50.0 + Math.random() * 30);
        u.setAvatar("https://randomuser.me/api/portraits/" +
                (Math.random() > 0.5 ? "women/" : "men/") +
                (int) (Math.random() * 90) + ".jpg");
        u.setPassword("123456"); // 🔒 Contraseña por defecto para evitar error de validación
        return u;
    }

    // 🔹 Helper para crear check-ins
    private CheckIn ci(User user, String ts) {
        CheckIn c = new CheckIn();
        c.setUser(user);
        c.setTimestamp(OffsetDateTime.parse(ts));
        return c;
    }
}
