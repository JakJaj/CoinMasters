package com.coinmasters.exceptions;

import com.coinmasters.controller.group.JoinGroupResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class CannotJoinGroupException extends IllegalArgumentException{
    private final JoinGroupResponse joinGroupResponse;
    private final HttpStatus status;

    public CannotJoinGroupException(String errorMessage, JoinGroupResponse joinGroupResponse, HttpStatus status){
        super(errorMessage);
        this.joinGroupResponse = joinGroupResponse;
        this.status = status;
    }
}
