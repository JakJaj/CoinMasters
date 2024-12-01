package com.coinmasters.entity.UserGroup;

import com.coinmasters.entity.Group;
import com.coinmasters.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_groups")
@Builder
@ToString
public class UserGroup {

    @EmbeddedId
    private UserGroupId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "join_date")
    private String joinDate;

}
