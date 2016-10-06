package url;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Example program to list links from a URL.
 */
public class GetManga {
    public static void main(String[] args) throws IOException,HttpStatusException {
        int numChap = 1;
        int numPage = 1;
        String nomManga = "boku-no-hero-academia-smash";
        getImage(numChap, numPage, nomManga, getNombreScan(nomManga));
    }

    private static int getNombreScan(String nomManga) {
        try {
            String url = "http://www.mangapanda.com/" + nomManga;

            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            String formatNomManga = "";
            String tabTemp[] = nomManga.split("-");

            for(int i = 0; i < tabTemp.length; i++){
                if (i ==0){
                    formatNomManga += tabTemp[i];
                } else {
                    formatNomManga = formatNomManga + " " + tabTemp[i];
                }
            }

            ArrayList nombreChap = new ArrayList<>();

            for (Element link : links) {
                int descManga = trim(link.text(), 35).toUpperCase().indexOf(formatNomManga.toUpperCase());
                if(descManga == 0){
                    nombreChap.add(trim(link.text(), 35).split(" "));
                }
            }

            Iterator<String[]> iterator = nombreChap.iterator();
            int nombreScan = 0;

            while (iterator.hasNext()){
                String[] test = iterator.next();
                int testScan = Integer.parseInt(test[test.length - 1]);
                if(nombreScan < testScan){
                    nombreScan = testScan;
                }
            }
            return nombreScan;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private static void getImage(int numChap, int numPage, String nomManga, int maxScan) {
        //ArrayList<String> allImages = new ArrayList<>();
        try{
            while (true) {
                String url = "http://www.mangapanda.com/" + nomManga + "/" + numChap + "/" + numPage;

                Document doc = Jsoup.connect(url).get();
                Elements media = doc.select("[src]");

                for (Element src : media) {
                    if (src.tagName().equals("img")) {
                        System.out.println(src.attr("abs:src"));
                        //allImages.add(src.attr("abs:src"));
                    }
                }
            //System.out.println(allImages.toString());
            numPage++;
            }
        }catch (HttpStatusException e) {
            numPage = 1;
            numChap++;
            if(maxScan >= numChap) {
                getImage(numChap,numPage,nomManga, maxScan);
            }
        } catch (Exception e){
           System.out.println(e.getMessage());
        }
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}