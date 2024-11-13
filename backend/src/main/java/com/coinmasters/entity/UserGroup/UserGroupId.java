package com.coinmasters.entity.UserGroup;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserGroupId implements Serializable {
    private Long userId;
    private Long groupId;
}
