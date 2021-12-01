package kooted.kooted.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bookmarks")
@Getter @Setter
public class BookMark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonManagedReference
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
}
