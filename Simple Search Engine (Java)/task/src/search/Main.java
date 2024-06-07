package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        parser.add(args);
        parser.addParameter("--data", "");
        DataSet dataset = new DataSet(parser.getValue("--data"));

        Menu menu = new Menu(dataset);
        menu.execute();

    }
}
