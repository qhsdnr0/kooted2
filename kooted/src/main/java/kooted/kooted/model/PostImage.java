package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "post_images")
@Getter @Setter
public class PostImage {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 2000)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonManagedReference
    private Post post;
}
