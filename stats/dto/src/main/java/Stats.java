import javax.persistence.*;

@Entity
@Table
public class Stats {
    @Id
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
