package core.identityservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255)")
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255)")
    String email;
    @Column(name = "email_verified", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean emailVerified;

    @ManyToMany
    Set<Role> roles;
}
