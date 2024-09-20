package tech.bankapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Account> accounts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<SecurityLog> securityLogs;

}
