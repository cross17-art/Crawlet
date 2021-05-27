public class Error_list {
    private int level;
    private String url;
    private String exception;

    Error_list(String url, int level, String exception) {
        this.level = level;
        this.url = url;
        this.exception = exception;
    }

    Error_list() {
    }

    public int getLevel() {
        return level;
    }

    public String getUrl() {
        return url;
    }

}
