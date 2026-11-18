package it.fpc.helpdesk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TechnicianResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String department;
}
