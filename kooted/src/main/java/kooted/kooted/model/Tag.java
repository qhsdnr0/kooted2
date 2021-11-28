package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter @Setter
public class Tag {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag")
    @JsonBackReference
    private List<CompanyTag> companyTags = new ArrayList<>();
}
