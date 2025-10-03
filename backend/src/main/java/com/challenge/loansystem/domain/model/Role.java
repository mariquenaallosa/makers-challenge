package com.challenge.loansystem.domain.model;

import com.challenge.loansystem.domain.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Entity @Table(name="roles")
@Getter @Setter
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserRoleEnum name;
}
