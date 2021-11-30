package kooted.kooted.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class JobForm {

    private String job_group_name;

    private Long job_group_id;
    private String job_name;
}
