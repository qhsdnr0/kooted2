package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String name;
    private String password;
    private Long kakaoId;

    @Embedded
    private AddInfo addInfo;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<BookMark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Resume> resumes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<UserWorkingYear> userWorkingYears = new ArrayList<>();
}
