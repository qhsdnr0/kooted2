package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_working_years")
@Getter @Setter
public class UserWorkingYear {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @JsonManagedReference
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "working_year_id")
    @JsonManagedReference
    private WorkingYear workingYear;
}
