package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    private LocalDate deadline;

    private int workingYear;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @Column(precision = 20, scale = 3)
    private BigDecimal applicantReward;

    @Column(precision = 20, scale = 3)
    private BigDecimal recommenderReward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @JsonManagedReference
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonManagedReference
    private Company company;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<BookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<PostImage> postImages = new ArrayList<>();
}
