package url;

import org.jsoup.Jsoup;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by RENAUD on 06/10/2016.
 */
public class Main {
    public static void main(String[] args) {
        try {
            StringBuilder content = new StringBuilder();

            // create a url object
            URL url = new URL("http://google.fr/");

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();

            String codeSource = content.toString();
            System.out.println(codeSource);

            int debutBalise = codeSource.indexOf("<b class=gb1>");
            int finBalise = codeSource.indexOf("</b");
            String contentTag = codeSource.substring(debutBalise, finBalise);
            System.out.println(contentTag);


        } catch (java.io.IOException e) {
            System.out.println(e.toString());
        }

    }
}
