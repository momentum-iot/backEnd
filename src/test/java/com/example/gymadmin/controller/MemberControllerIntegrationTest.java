package com.example.gymadmin.controller;

import com.example.gymadmin.model.Member;
import com.example.gymadmin.repo.PaymentRepo;
import com.example.gymadmin.repo.CheckInRepo;
import com.example.gymadmin.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private CheckInRepo checkInRepo;

    private Member createdMember;

    @BeforeEach
    void setup() {
        // Limpieza previa de registros
        memberService.list().forEach(m -> memberService.delete(m.getId()));

        // Crear miembro base
        Member m = new Member();
        m.setFirstName("Carlos");
        m.setLastName("Sánchez");
        m.setEmail("carlos@example.com");
        m.setMembership("Gold");
        m.setStatus("activo");
        m.setPhone("999999999");
        createdMember = memberService.save(m);
    }

    @Test
    void shouldListMembers() throws Exception {
        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void shouldGetMemberById() throws Exception {
        mockMvc.perform(get("/api/members/" + createdMember.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("carlos@example.com")))
                .andExpect(jsonPath("$.firstName", is("Carlos")));
    }

    @Test
    void shouldCreateMember() throws Exception {
        String json = """
            {
              "firstName": "Nuevo",
              "lastName": "Miembro",
              "email": "nuevo@example.com",
              "membership": "Silver",
              "status": "activo",
              "phone": "988888888"
            }
        """;

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Nuevo")))
                .andExpect(jsonPath("$.membership", is("Silver")));
    }

    @Test
    void shouldUpdateMember() throws Exception {
        String json = """
            {
              "firstName": "Carlos",
              "lastName": "Actualizado",
              "email": "carlos@example.com",
              "membership": "Gold",
              "status": "activo",
              "phone": "999999999"
            }
        """;

        mockMvc.perform(put("/api/members/" + createdMember.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Actualizado")));
    }

    @Test
    void shouldDeleteMember() throws Exception {
        mockMvc.perform(delete("/api/members/" + createdMember.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnPaymentsForMember() throws Exception {
        mockMvc.perform(get("/api/members/" + createdMember.getId() + "/payments"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCheckinsForMember() throws Exception {
        mockMvc.perform(get("/api/members/" + createdMember.getId() + "/checkins"))
                .andExpect(status().isOk());
    }
}
