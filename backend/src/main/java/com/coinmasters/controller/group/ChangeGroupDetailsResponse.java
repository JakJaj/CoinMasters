package com.coinmasters.controller.group;

import com.coinmasters.dto.GroupDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeGroupDetailsResponse {
    private String status;
    private String message;
    private GroupDTO group;
}
