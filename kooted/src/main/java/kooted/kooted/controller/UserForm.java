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
    private Boolean inOffice;
    private String companyName;
    private String duty;
    private LocalDate dateOfJoining;
    private LocalDate dateOfResigning;

    private Long jobId;
    private Long workingYearId;
}
