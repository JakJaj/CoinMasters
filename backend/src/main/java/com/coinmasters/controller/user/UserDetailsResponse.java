package com.coinmasters.controller.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponse {
    private Long userId;
    private Set<Long> adminOfGroups;
    private String name;
    private String mail;
    private String rolee;
}
