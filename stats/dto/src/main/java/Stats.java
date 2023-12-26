import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stats")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    private Long id;
    @Column
    private String app;
    @Column
    private String uri;
    @Column
    private String ip;
    @Column
    private String timestamp;


}
