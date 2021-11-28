package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "working_years")
@Getter @Setter
public class WorkingYear {

    @Id @GeneratedValue
    private Long id;

    private String years;

    @OneToMany(mappedBy = "workingYear")
    @JsonBackReference
    private List<UserWorkingYear> userWorkingYears = new ArrayList<>();
}
