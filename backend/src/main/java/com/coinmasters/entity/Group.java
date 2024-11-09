package com.coinmasters.entity;

import com.coinmasters.entity.UserGroup.UserGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "goal")
    private String goal;

    @Column(name = "currency")
    private String currency;

    @Column(name = "join_code")
    private String joinCode;

    @ManyToOne
    @JoinColumn(name = "admin_user_id")
    private User adminUserId;

    @OneToMany(mappedBy = "group")
    private Set<UserGroup> userGroups;

    public Group(String goal, String currency, String joinCode, User adminUserId){
        this.goal = goal;
        this.currency = currency;
        this.joinCode = joinCode;
        this.adminUserId = adminUserId;
    }
}
