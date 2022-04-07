import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Riik {
    private String kood;

    public Riik(String kood) {
        this.kood = kood;
    }

    public String getKood() {
        return kood;
    }

    private double võrdlus1ga(Riik teineRiik) {
        String teiseRiigiKood = teineRiik.getKood();
        String väärtus = "";
        String url2 = "https://www.xe.com/currencyconverter/convert/?Amount=1&From=&To=" + kood;
        StringBuilder urlC = new StringBuilder(url2);
        int j = urlC.indexOf("=", "https://www.xe.com/currencyconverter/convert/?Amount=1&From".length());
        urlC.replace(j, j + 1, "=" + teiseRiigiKood);
        try {
            final Document document = Jsoup.connect(urlC.toString()).get();

            for (Element row : document.select("div.unit-rates___StyledDiv-sc-1dk593y-0.dEqdnx p")) {
                if (row.select("p").text().equals("")) {
                    continue;
                } else {
                    väärtus = row.select("p").text();
                    väärtus = väärtus.split(" ")[3].replace(",", "");
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Double.parseDouble(väärtus);
    }

    public double võrdlusSuvaliseSummaga(double summa, Riik teineRiik) {
        return summa * võrdlus1ga(teineRiik);
    }
}
