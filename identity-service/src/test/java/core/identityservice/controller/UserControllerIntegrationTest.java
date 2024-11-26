package core.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import core.identityservice.dto.request.AuthenticationRequest;
import core.identityservice.dto.request.UserCreationRequest;
import core.identityservice.dto.response.UserResponse;
import core.identityservice.entity.User;
import core.identityservice.enums.Role;
import core.identityservice.exception.ErrorCode;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.HashSet;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:13.2");

    @DynamicPropertySource
    static void configuraDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driverClassName", POSTGRE_SQL_CONTAINER::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }


    @Autowired
    private MockMvc mockMvc;

    private UserCreationRequest request;
    private UserResponse userResponse;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;


    @BeforeEach
    void initData() {
        LocalDate dob = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest.builder()
                .username("johndoe")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("23e91564-324e-4177-93c2-e103c3502f05")
                .username("johndoe")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .roles(null)
                .build();

    }


    private String getAdminToken() throws Exception {

        var roles = new HashSet<String>();
        roles.add(Role.ADMIN.name());
        User user = User.builder()
                .username("admin")
                .password("admin")
                .build();

        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(authenticationRequest);

        var jsonResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return JsonPath.read(jsonResponse, "$.result.token");
    }

    @Test
    public void createUser_validRequest_success() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);


        // When, Then
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code")
                        .value(1000)

                )
                .andExpect(MockMvcResultMatchers.jsonPath("result.username")
                        .value("johndoe")
                )
                .andExpect(MockMvcResultMatchers.jsonPath("result.firstName")
                        .value("John")
                )
                .andExpect(MockMvcResultMatchers.jsonPath("result.lastName")
                        .value("Doe")
                )
                .andExpect(MockMvcResultMatchers.jsonPath("result.dob")
                        .value("1990-01-01")
                );
        log.info("Result: {}", response.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void createUser_invalidRequest_fail_with_username() throws Exception {
        // Given
        request.setUsername("john");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 5 characters")
                );
    }

    @Test
    public void createUser_invalidRequest_fail_with_password() throws Exception {
        // Given
        request.setPassword("123456");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1004))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Password must be at least 8 characters")
                );
    }

    @Test
    public void createUser_invalidRequest_fail_with_dob() throws Exception {
        // Given
        request.setDob(LocalDate.of(2010, 1, 1));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1008))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Your age must be at least 16")
                );
    }

    @Test
    public void createUser_invalidRequest_fail_with_firstName() throws Exception {
        // Given
        request.setFirstName("J");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(ErrorCode.valueOf("INVALID_FIRST_NAME").getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("First name must be at least 2 characters")
                );
    }

    @Test
    public void createUser_invalidRequest_fail_with_lastName() throws Exception {
        // Given
        request.setLastName("D");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(ErrorCode.valueOf("INVALID_LAST_NAME").getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Last name must be at least 2 characters")
                );
    }

//    @Test
//    public void getAllUser_validRequest_success() throws Exception {
//        // Given
//        String token = getAdminToken();
//
//        log.info("Token: {}", token);
//
//
//
//        // When, Then
//        var response = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/users/all")
//                        .header("Authorization", "Bearer " + token))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
//
//        log.info("Result: {}", response.andReturn().getResponse().getContentAsString());
//
//    }


}

