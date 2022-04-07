import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiikideListiLooja {
    private String url;

    public RiikideListiLooja(String url) {
        this.url = url;
    }

    public List<String[]> looList() {
        final String url = this.url;

        List<String[]> riigid = new ArrayList<>();

        String[] ebasobivadKoodid = {"BOV", "CLF", "XUA", "MXV", "XSU", "SSP", "USN", "UYI", "CHE", "CHW"};
        String[] muudetavadKoodid = {"COU", "NIS", "LAKK", "SDP", "ZWL"};
        String[] muudetudKoodid = {"COP", "ILS", "LAK", "SDG", "ZWD"};


        try {
            final Document document = Jsoup.connect(url).get();

            int i = 1;
            int j = 0;
            for (Element row : document.select("table.table.table-bordered.downloads.tablesorter tr")) {
                if (row.select("td:nth-of-type(3)").text().equals("") || Arrays.asList(ebasobivadKoodid).contains(row.select("td:nth-of-type(3)").text())) {
                    continue;
                } else {
                    String riik = row.select("td:nth-of-type(1)").text();
                    String valuuta = row.select("td:nth-of-type(2)").text();

                    String kood = row.select("td:nth-of-type(3)").text();

                    if (Arrays.asList(muudetavadKoodid).contains(kood)) {
                        kood = muudetudKoodid[j];
                        j++;
                    }

                    String[] andmed = new String[4];
                    andmed[0] = String.valueOf(i);
                    andmed[1] = riik;
                    andmed[2] = valuuta;
                    andmed[3] = kood;
                    riigid.add(andmed);
                    i++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return riigid;
    }
}