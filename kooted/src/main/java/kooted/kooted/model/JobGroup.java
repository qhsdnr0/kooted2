package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_groups")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter
public class JobGroup {

    @Id @GeneratedValue
    @Column(name = "job_group_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "jobGroup")
    @JsonBackReference
    private List<Job> jobs = new ArrayList<>();
}
