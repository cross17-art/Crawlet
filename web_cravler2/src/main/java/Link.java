public class Link {
    private int level;
    private String url;

    Link(String url, int level) {
        this.level = level;
        this.url = url;
    }

    Link() {
    }

    public int getLevel() {
        return level;
    }

    public String getUrl() {
        return url;
    }

}
