public class StatsMapper {

    public static Stats mapToNewStats(StatsDtoInput statsDtoInput) {
        Stats stats = new Stats();
        stats.setApp(statsDtoInput.getApp());
        stats.setIp(statsDtoInput.getIp());
        stats.setUri(statsDtoInput.getUri());
        stats.setTimestamp(statsDtoInput.getTimestamp());
        return stats;
    }
}
