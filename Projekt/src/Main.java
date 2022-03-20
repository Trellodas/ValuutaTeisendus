import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        final String url = "https://www.countries-ofthe-world.com/world-currencies.html";
        // String url2 = "https://www.xe.com/currencyconverter/convert/?Amount=1&From=&To=EUR";

        List<String[]> riigid = new ArrayList<>();

        String kood = "EUR";
        try {
            final Document document = Jsoup.connect(url).get();

            int i = 1;
            for (Element row : document.select("table.codes tr")) {
                if (row.select("td:nth-of-type(3)").text().equals("")) {
                    continue;
                }
                else {
                    String riik = row.select("td:nth-of-type(1)").text();
                    String valuuta = row.select("td:nth-of-type(2)").text();
                    if (!row.select("td:nth-of-type(3)").text().equals("none"))
                        kood = row.select("td:nth-of-type(3)").text();
                    else if (riik.equals("Cook Islands (New Zealand)"))
                        kood = "NZD";
                    else if (riik.equals("Faroe Islands (Denmark)"))
                        kood = "DKK";
                    String[] andmed = new String[4];
                    andmed[0] = String.valueOf(i);
                    andmed[1] = riik;
                    andmed[2] = valuuta;
                    andmed[3] = kood;
                    riigid.add(andmed);
                    i++;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        for (String[] strings : riigid) {
            System.out.println(Arrays.toString(strings));
        }
//        kood = "PLN";
//        StringBuilder urlC = new StringBuilder(url2);
//        int i = urlC.indexOf("=", "https://www.xe.com/currencyconverter/convert/?Amount=1&From".length());
//        urlC.replace(i, i + 1, "=" + kood);
//        System.out.println(urlC);
//        try {
//            final Document document = Jsoup.connect(urlC.toString()).get();
//
//            for (Element row : document.select("div.unit-rates___StyledDiv-sc-1dk593y-0.dEqdnx p")) {
//                if (row.select("p").text().equals("")) {
//                    continue;
//                }
//                else {
//                    String väärtus = row.select("p").text();
//                    System.out.println(väärtus);
//                    break;
//                }
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}