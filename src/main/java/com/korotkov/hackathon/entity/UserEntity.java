package com.korotkov.hackathon.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {
    @Id
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean enabled;


    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }
}
