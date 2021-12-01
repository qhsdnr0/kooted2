package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resumes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter
public class Resume {

    @Id @GeneratedValue
    @Column(name = "resume_id")
    private Long id;

    private String college;
    private String title;
    private String status;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Career> careers = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Skill> skills = new ArrayList<>();
}
