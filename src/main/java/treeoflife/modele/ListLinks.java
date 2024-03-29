package treeoflife.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListLinks {

    //arrayList of links
    private final ArrayList<Link> links;

    public ArrayList<Link> getLinks() {
        return links;
    }

    public ListLinks() {
        this.links = new ArrayList<>();
    }

    public void LoadLinks(String filePath) throws IOException {
        //open file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            //skip first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // 以逗号分隔每行的数据
                String[] values = line.split(",");
                links.add(new Link(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
            }
        }
    }
}