package kooted.kooted.model;

import lombok.Getter;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class AddInfo {

    private String college;

    @Column(precision = 20, scale = 3)
    private BigDecimal salary;
    private String introduction;
    private Boolean inOffice;
    private String companyName;
    private String duty;
    private LocalDate dateOfJoining;
    private LocalDate dateOfResigning;

    protected AddInfo() {}

    public AddInfo(String college, BigDecimal salary, String introduction, String companyName, Boolean inOffice,
                   String duty, LocalDate dateOfJoining, LocalDate dateOfResigning) {
        this.college = college;
        this.salary = salary;
        this.introduction = introduction;
        this.companyName = companyName;
        this.inOffice = inOffice;
        this.duty = duty;
        this.dateOfJoining = dateOfJoining;
        this.dateOfResigning = dateOfResigning;
    }
}
