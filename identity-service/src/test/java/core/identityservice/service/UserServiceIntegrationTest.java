package core.identityservice.service;

import core.identityservice.dto.request.UserCreationRequest;
import core.identityservice.dto.response.UserResponse;
import core.identityservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@Testcontainers
public class UserServiceIntegrationTest {


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
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("123456")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("cf0600f538b3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        user = User.builder()
                .id("cf0600f538b3")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    public void createUser_validRequest_success() {
        // Given
//        when(userRepository.existsByUsername(anyString())).thenReturn(false);
//        when(userRepository.save(any())).thenReturn(user);

        // When
        var response = userService.createRequest(request);

        // Then
        Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
        Assertions.assertThat(response.getUsername()).isEqualTo("john");

    }

//    @Test
//    public void createUser_userExisted_fail() {
//        // Given
//        when(userRepository.existsByUsername(anyString())).thenReturn(true);
//
//        // When
//        var exception = assertThrows(AppException.class,
//                () -> userService.createRequest(request));
//
//        Assertions.assertThat(exception.getMessage()).isEqualTo("User already exist");
//    }
}
