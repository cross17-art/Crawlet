import com.mysql.jdbc.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class crawler {
    LinkedList<Error_list> error_lists = new LinkedList<>();

    private String reg_1 = "Tesla";
    private String reg_2 = "Musk";
    private String reg_3 = "Gigafactory";
    private String reg_4 = "Elon Musk";

    private int term_1 = 0;
    private int term_2 = 0;
    private int term_3 = 0;
    private int term_4 = 0;


    BDconnection bDconnection;
    Connection connection;
    Document doc;
    //String url="https://en.wikipedia.org/wiki/Elon_Musk";

    public crawler() throws ClassNotFoundException, SQLException {
        bDconnection = new BDconnection();
        connection = (Connection) bDconnection.getConnection();
    }


    public void start(String url, int level_url) throws IOException, SQLException, InterruptedException {
        sleep(2000);
        try {
            doc = (Document) Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
           // System.err.println(e);
        }
        String str = doc.text();




        Pattern pattern = Pattern.compile("\\b" + reg_1 + "\\b|\\b" + reg_2 + "\\b|\\b" + reg_3 + "\\b|\\b" + reg_4 + "\\b");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            if (str.substring(matcher.start(), matcher.end()).equals(reg_1)) {
                term_1++;
            }
            if (str.substring(matcher.start(), matcher.end()).equals(reg_2)) {
                term_2++;
            }
            if (str.substring(matcher.start(), matcher.end()).equals(reg_3)) {
                term_3++;
            }
            if (str.substring(matcher.start(), matcher.end()).equals(reg_4)) {
                term_4++;
            }
        }
        bDconnection.Insert_db(url, term_1, term_2, term_3, term_4, level_url);
        term_1 = 0;
        term_2 = 0;
        term_3 = 0;
        term_4 = 0;
    }













    public void start_file(String url, int level_url) {
        try {
            doc = (Document) Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            System.err.println(e);
            error_lists.add(new Error_list(url, level_url, String.valueOf(e.fillInStackTrace())));

        }
        String str = doc.text();
        Pattern pattern = Pattern.compile("\\b" + reg_1 + "\\b|\\b" + reg_2 + "\\b|\\b" + reg_3 + "\\b|\\b" + reg_4 + "\\b");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            if (str.substring(matcher.start(), matcher.end()).equals(reg_1)) {
                term_1++;
            }
            if (str.substring(matcher.start(), matcher.end()).equals(reg_2)) {
                term_2++;
            }
            if (str.substring(matcher.start(), matcher.end()).equals(reg_3)) {
                term_3++;
            }
            if (str.substring(matcher.start(), matcher.end()).equals(reg_4)) {
                term_4++;
            }
        }
        String total = "";
        total = "url=" + url + "   " + "level url " + level_url + "   " + reg_1 + " " + term_1 + "   " + reg_2 + " " + term_2 + "   " + reg_3 + " " + term_3 + "   " + reg_4 + " " + term_4 + "\n";
        try (FileWriter writer = new FileWriter("notes3.txt", false)) {
            writer.append(total);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        term_1 = 0;
        term_2 = 0;
        term_3 = 0;
        term_4 = 0;

    }


}
