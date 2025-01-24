package com.coinmasters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long groupId;
    private String groupName;
    private String goal;
    private String currency;
    private String joinCode;
}
