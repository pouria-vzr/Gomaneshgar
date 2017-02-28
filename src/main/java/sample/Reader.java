package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public List<String> readFile(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        java.io.Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        ArrayList<String> contents = new ArrayList<String>();

        String  line = "";
        while ((line = bufferedReader.readLine()) != null) {
            contents.add(line);
        }
        bufferedReader.close();
        return contents;
    }

}
