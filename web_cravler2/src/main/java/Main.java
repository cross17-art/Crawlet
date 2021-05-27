import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Main {


    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {

        BDconnection bDconnection = new BDconnection();
        bDconnection.getConnection();


        Links links = new Links();
        Link main_link = new Link("https://en.wikipedia.org/wiki/Elon_Musk", 1);

        System.out.println("size 1 " + links.level_list.size());

        links.first_level(main_link);

        for(int i=0;i<8;i++) {
            if(links.level_list.size()>=100){
                System.out.println("first break");
                break;
            }else{

            links.next_level();
            }
        }

        System.out.println(links.level_list.size());
        crawler crawler = new crawler();

        long w=System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();

        System.out.println("time: "+Calendar.getInstance().getTime()+"     start");

        for(int i=0;i<links.level_list.size();i++){

            crawler.start(links.level_list.get(i).getUrl(),links.level_list.get(i).getLevel());
            System.out.println("time: "+Calendar.getInstance().getTime()+"  i= "+i);


        }
        System.out.println("Второй цикл"+(double) (System.currentTimeMillis() - w));



    }


}
