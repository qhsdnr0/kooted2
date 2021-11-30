package kooted.kooted.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "careers")
@Getter @Setter
public class Career {

    @Id @GeneratedValue
    private Long id;

    private String companyName;
    private String duty;
    private Boolean inOffice;

    private LocalDate dateOfJoining;
    private LocalDate dateOfResigning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
