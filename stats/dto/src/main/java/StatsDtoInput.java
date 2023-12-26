import lombok.Data;

@Data
public class StatsDtoInput {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
