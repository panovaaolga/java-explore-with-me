import lombok.Data;

@Data
public class StatsDtoOutput {
    private String app;
    private String uri;
    private Integer hits;
}
