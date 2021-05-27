import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Links {
    LinkedList<Link> level_list = new LinkedList<>();
    LinkedList<Error_list> error_level_list = new LinkedList<>();

    Document doc;
    int global_level = 1;


    public void first_level(Link link) throws IOException, InterruptedException {
        // if(level_list.size()>=1000) {System.out.println("third break");return ;}
        LinkedList<Link> local_level_list = new LinkedList<>();
        sleep(100);
        try {
            doc = Jsoup.connect(link.getUrl())
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            //System.out.println(e.fillInStackTrace());
            error_level_list.add(new Error_list(link.getUrl(), link.getLevel(), String.valueOf(e.fillInStackTrace())));
        }
        if (doc != null) {
            Elements elements = doc.select("a");
            String str = String.valueOf(elements);
            String str_2 = "";
            Pattern pattern = Pattern.compile("\"(https:(.+?\"))");
            Matcher matcher = pattern.matcher(str);

            while (matcher.find()) {
                str_2 = str.substring(matcher.start(), matcher.end()) + "\n";
                str_2 = str_2.replaceAll("\"", "");
                str_2 = str_2.replaceAll("\n", "");
//                if (level_list.size() == 200) {
//                    System.out.println();
//                }
                if (not_image(str_2) && search_link(str_2)) {
                    if (level_list.size() < 500) {
                        level_list.add(new Link(str_2, global_level));
                    }
                }
            }
        }

    }


    public void next_level() throws IOException, InterruptedException {
        global_level++;

        int i_second = 0;
        for (int i = 0; i < level_list.size(); i++) {
            if (level_list.size() >= 500) {
                System.out.println("second break 2");
                return;
            } else if (level_list.get(i).getLevel() == global_level - 1) {
                first_level(level_list.get(i));
            }
        }
    }


    public boolean search_link(String url) {
        for (int i = 0; i < level_list.size(); i++) {
            if (level_list.get(i).getUrl().equals(url)) {
                return false;
            }
        }
        return true;
    }

    public boolean not_image(String href) {
        if (href.contains("png") || href.contains("img") || href.contains("gif") || href.contains("jpeg 2000") || href.contains("jp2") || href.contains("bmp")) {
            return false;
        }
        return true;
    }


}