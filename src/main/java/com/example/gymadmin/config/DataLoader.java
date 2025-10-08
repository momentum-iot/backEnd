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
    CommandLineRunner seed(MemberRepo memberRepo, PlanRepo planRepo, CheckInRepo checkInRepo, PaymentRepo paymentRepo,
                           EquipmentRepo equipmentRepo) {
        return args -> {
            if (planRepo.count() == 0) {
                Plan basic = new Plan();
                basic.setName("Básico");
                basic.setPrice(79.0);
                basic.setDuration("mensual");
                basic.setPerks(List.of("Acceso general", "1 evaluación física"));

                Plan premium = new Plan();
                premium.setName("Premium");
                premium.setPrice(129.0);
                premium.setDuration("mensual");
                premium.setPerks(List.of("Acceso general", "Zona de pesas", "Clases ilimitadas", "Plan personalizado"));

                Plan student = new Plan();
                student.setName("Estudiante");
                student.setPrice(59.0);
                student.setDuration("mensual");
                student.setPerks(List.of("Acceso general", "Clases limitadas (8/mes)"));

                planRepo.saveAll(List.of(basic, premium, student));
            }

            if (memberRepo.count() == 0) {
                // === Miembros originales ===
                Member m1 = makeMember("Lucía", "Gómez", "lucia.gomez@example.com", "Premium", "activo", "999111222", "2003-05-02");
                Member m2 = makeMember("Carlos", "Ramos", "carlos.ramos@example.com", "Básico", "sin pagar", "988333999", "2002-11-10");
                Member m3 = makeMember("Ana", "Salazar", "ana.salazar@example.com", "Estudiante", "activo", "955222777", "2004-02-21");
                Member m4 = makeMember("Diego", "Paredes", "diego.paredes@example.com", "Premium", "retirado", "977123456", "2000-09-14");

                // === 20 miembros adicionales ===
                List<Member> extra = List.of(
                        makeMember("Sofía", "Luna", "sofia.luna@example.com", "Básico", "activo", "933111222", "2001-07-15"),
                        makeMember("Mateo", "Fernández", "mateo.fernandez@example.com", "Premium", "sin pagar", "922333444", "2002-12-09"),
                        makeMember("Valentina", "Rojas", "valentina.rojas@example.com", "Estudiante", "activo", "944555666", "2005-06-18"),
                        makeMember("Andrés", "García", "andres.garcia@example.com", "Básico", "retirado", "955666777", "1999-08-30"),
                        makeMember("Camila", "Lopez", "camila.lopez@example.com", "Premium", "activo", "911888999", "2003-03-25"),
                        makeMember("Sebastián", "Torres", "sebastian.torres@example.com", "Estudiante", "sin pagar", "955999111", "2005-10-10"),
                        makeMember("María", "Castillo", "maria.castillo@example.com", "Básico", "activo", "977333111", "2001-01-20"),
                        makeMember("José", "Flores", "jose.flores@example.com", "Premium", "activo", "933222111", "2000-04-08"),
                        makeMember("Gabriela", "Mendoza", "gabriela.mendoza@example.com", "Básico", "sin pagar", "922444333", "2004-07-29"),
                        makeMember("Luis", "Ruiz", "luis.ruiz@example.com", "Estudiante", "activo", "955555444", "2006-01-12"),
                        makeMember("Daniela", "Suárez", "daniela.suarez@example.com", "Premium", "retirado", "955777888", "1998-11-21"),
                        makeMember("Miguel", "Vargas", "miguel.vargas@example.com", "Básico", "activo", "966111222", "2001-09-10"),
                        makeMember("Paula", "Navarro", "paula.navarro@example.com", "Estudiante", "sin pagar", "977666999", "2003-10-02"),
                        makeMember("Javier", "Ortega", "javier.ortega@example.com", "Premium", "activo", "955222888", "2000-02-14"),
                        makeMember("Isabella", "Reyes", "isabella.reyes@example.com", "Básico", "activo", "922333888", "2005-03-09"),
                        makeMember("Tomás", "Sánchez", "tomas.sanchez@example.com", "Premium", "sin pagar", "944777222", "2002-06-05"),
                        makeMember("Natalia", "Cruz", "natalia.cruz@example.com", "Estudiante", "activo", "933888444", "2004-12-03"),
                        makeMember("Martín", "Silva", "martin.silva@example.com", "Básico", "retirado", "977555333", "1999-11-11"),
                        makeMember("Renata", "Morales", "renata.morales@example.com", "Premium", "activo", "922444777", "2003-09-23"),
                        makeMember("Santiago", "Pérez", "santiago.perez@example.com", "Estudiante", "sin pagar", "955888000", "2005-04-30"))
                ;

                memberRepo.saveAll(List.of(m1, m2, m3, m4));
                memberRepo.saveAll(extra);

                // === Check-ins ===
                checkInRepo.saveAll(List.of(
                        ci(m1.getId(), "2025-10-06T06:50:00-05:00"),
                        ci(m2.getId(), "2025-10-05T19:12:00-05:00"),
                        ci(m3.getId(), "2025-10-05T17:44:00-05:00"),
                        ci(extra.get(0).getId(), "2025-10-06T09:10:00-05:00"),
                        ci(extra.get(1).getId(), "2025-10-06T10:22:00-05:00"),
                        ci(extra.get(3).getId(), "2025-10-05T18:15:00-05:00")
                ));

                // === Pagos ===
                Payment p1 = new Payment();
                p1.setMemberId(m2.getId());
                p1.setDueDate(LocalDate.parse("2025-10-05"));
                p1.setAmount(79.0);
                p1.setStatus("pendiente");

                Payment p2 = new Payment();
                p2.setMemberId(m1.getId());
                p2.setDueDate(LocalDate.parse("2025-11-01"));
                p2.setAmount(129.0);
                p2.setStatus("programado");

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

    // 🔹 Helper para crear miembros
    private Member makeMember(String first, String last, String email, String membership, String status, String phone, String birth) {
        Member m = new Member();
        m.setFirstName(first);
        m.setLastName(last);
        m.setEmail(email);
        m.setMembership(membership);
        m.setStatus(status);
        m.setPhone("+51 " + phone);
        m.setJoinDate(LocalDate.now().minusDays((int) (Math.random() * 100))); // aleatorio reciente
        m.setBirthday(LocalDate.parse(birth));
        m.setEmergencyContact("Contacto " + last + " (+51 9" + (int) (900000000 + Math.random() * 99999999) + ")");
        m.setHeight(160 + (int) (Math.random() * 30));
        m.setWeight(50.0 + Math.random() * 30);
        m.setGoals(List.of("Fuerza", "Resistencia"));
        m.setAvatar("https://randomuser.me/api/portraits/" +
                (Math.random() > 0.5 ? "women/" : "men/") +
                (int) (Math.random() * 90) + ".jpg");
        return m;
    }

    private CheckIn ci(String memberId, String ts) {
        CheckIn c = new CheckIn();
        c.setMemberId(memberId);
        c.setTimestamp(OffsetDateTime.parse(ts));
        return c;
    }
}
