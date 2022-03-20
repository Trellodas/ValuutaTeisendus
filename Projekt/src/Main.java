import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        final String url = "https://www.countries-ofthe-world.com/world-currencies.html";
        String url2 = "https://www.xe.com/currencyconverter/convert/?Amount=1&From=&To=EUR";

        String kood = "EUR";
        try {
            final Document document = Jsoup.connect(url).get();

            for (Element row : document.select("table.codes tr")) {
                if (row.select("td:nth-of-type(3)").text().equals("")) {
                    continue;
                }
                else {
                    String riik = row.select("td:nth-of-type(1)").text();
                    String valuuta = row.select("td:nth-of-type(2)").text();
                    kood = row.select("td:nth-of-type(3)").text();
                    System.out.println("Valisite riigi: " + riik + " " + valuuta + " " + kood);
                    break;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Sisesta kood:");
        String koodA = scan.nextLine();
        kood = koodA;
        StringBuilder urlC = new StringBuilder(url2);
        int i = urlC.indexOf("=", "https://www.xe.com/currencyconverter/convert/?Amount=1&From".length());
        urlC.replace(i, i + 1, "=" + kood);
        try {
            final Document document = Jsoup.connect(urlC.toString()).get();

            for (Element row : document.select("div.unit-rates___StyledDiv-sc-1dk593y-0.dEqdnx p")) {
                if (row.select("p").text().equals("")) {
                    continue;
                }
                else {
                    String väärtus = row.select("p").text();
                    System.out.println("Võrdlus euroga: " + väärtus);
                    break;
                }
            }
        }
        catch (Exception ex) {
        ex.printStackTrace();
        }
    }
}
