package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter
public class Company {

    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    private String name;

    @Column(length = 5000)
    private String location;

    @Column(precision = 20, scale = 3)
    private BigDecimal salary;

    private int employeeNumber;

    @Column(length = 2000)
    private String description;

    @OneToMany(mappedBy = "company")
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    @JsonBackReference
    private List<CompanyTag> companyTags = new ArrayList<>();
}
