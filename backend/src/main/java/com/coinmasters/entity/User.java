package com.coinmasters.entity;


import com.coinmasters.entity.UserGroup.UserGroup;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "password_salt")
    private String passwordSalt;

    @Column(name = "mail")
    private String mail;

    @OneToMany(mappedBy = "user")
    private Set<UserGroup> userGroups;

    public User(String name, String password, String passwordSalt, String mail){
        this.name = name;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.mail = mail;
    }
}
