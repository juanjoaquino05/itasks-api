package od.tellib.tasks.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(
        name = "usert",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_name_unique", columnNames = "name")
        }
)
public class User {
    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = SEQUENCE,
        generator = "user_sequence"
    )
    @Column(
        name = "id"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
        name = "created_at",
        nullable = false,
        columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    @OneToMany(
        mappedBy = "user",
        orphanRemoval = true,
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
        fetch = FetchType.LAZY
    )
    private List<Task> tasks = new ArrayList<>();
}
