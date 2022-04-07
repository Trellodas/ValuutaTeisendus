import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Scanner;

public class Peaklass {
    public static void main(String[] args) {
        RiikideListiLooja riikideListiLooja = new RiikideListiLooja("https://www.iban.ee/currency-codes");

        List<String[]> riigid = riikideListiLooja.looList();

        for (String[] riik : riigid) {
            System.out.println(riik[0] + ": " + riik[1]);
        }


        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("=".repeat(100));
            System.out.println("Sisesta riigi ees olev number (lõpetamiseks vajuta ENTER klahvi): ");
            String number = scan.nextLine();
            if (number.equals("")) {
                System.out.println("Programm lõpetas töö.");
                break;
            }
            String nimi = riigid.get(Integer.parseInt(number) - 1)[1];
            System.out.println("Sisestatud riik on: " + nimi);
            String valuuta = riigid.get(Integer.parseInt(number) - 1)[2];
            System.out.println("Sisestatud riigi valuuta on: " + valuuta);


            String kood = riigid.get(Integer.parseInt(number) - 1)[3];
            String url2 = "https://www.xe.com/currencyconverter/convert/?Amount=1&From=&To=EUR";
            StringBuilder urlC = new StringBuilder(url2);
            int j = urlC.indexOf("=", "https://www.xe.com/currencyconverter/convert/?Amount=1&From".length());
            urlC.replace(j, j + 1, "=" + kood);
            //System.out.println(urlC);
            try {
                final Document document = Jsoup.connect(urlC.toString()).get();

                for (Element row : document.select("div.unit-rates___StyledDiv-sc-1dk593y-0.dEqdnx p")) {
                    if (row.select("p").text().equals("")) {
                        continue;
                    } else {
                        String väärtus = row.select("p").text();
                        System.out.println("Euro võrdluses selle riigi valuutaga: " + väärtus);
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println();
            System.out.println("Kas soovid selle riigi valuutat võrrelda mingi teise riigi valuutaga? (jah/ei)");
            String kas = scan.nextLine();
            if (kas.toUpperCase().equals("JAH")) {
                System.out.println("Sisesta teise riigi number:");
                String number2 = scan.nextLine();

                String nimi2 = riigid.get(Integer.parseInt(number2) - 1)[1];
                System.out.println("Sisestatud riik on: " + nimi2);
                String valuuta2 = riigid.get(Integer.parseInt(number2) - 1)[2];
                System.out.println("Sisestatud riigi valuuta on: " + valuuta2);
                String kood2 = riigid.get(Integer.parseInt(number2) - 1)[3];

                System.out.println("Sisesta summa:");
                String summa = scan.nextLine();
                System.out.println(valuuta + " võrdlus " + valuuta2 + "iga:");

                Riik riik1 = new Riik(kood);
                Riik riik2 = new Riik(kood2);

                System.out.println(summa + " " + kood + " = " + riik1.võrdlusSuvaliseSummaga(Double.parseDouble(summa), riik2) + " " + kood2);

            }
            else {
                continue;
            }
        }

    }
}
