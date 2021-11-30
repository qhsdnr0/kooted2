package kooted.kooted.controller;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CompanyForm {

    private String name;
    private String location;
    private BigDecimal salary;
    private int employee_number;
    private String description;
}
