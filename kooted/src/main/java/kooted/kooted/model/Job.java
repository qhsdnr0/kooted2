package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter
public class Job {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_group_id")
    @JsonManagedReference
    private JobGroup jobGroup;

    @OneToMany(mappedBy = "job")
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "job")
    @JsonBackReference
    private List<UserWorkingYear> userWorkingYears = new ArrayList<>();
}
