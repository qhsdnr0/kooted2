package kooted.kooted.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class JobForm {

    private String jobGroupName;

    private Long jobGroupId;
    private String jobName;
}
