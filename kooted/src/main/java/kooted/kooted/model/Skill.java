package kooted.kooted.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "skills")
@Getter @Setter
public class Skill {

    @Id @GeneratedValue
    @Column(name = "skill_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
