package kooted.kooted.controller;

import kooted.kooted.model.AddInfo;
import kooted.kooted.model.UserWorkingYear;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class UserForm {

    private String college;
    private BigDecimal salary;
    private String introduction;
    private Boolean in_office;
    private String company_name;
    private String duty;
    private LocalDate date_of_joining;
    private LocalDate date_of_resigning;

    private Long job_id;
    private Long working_year;
}
