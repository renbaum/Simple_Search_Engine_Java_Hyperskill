package search;

import java.util.Scanner;

public class Menu{
    private DataSet data;

    public Menu(DataSet data){
        this.data = data;
    }

    public void print(){
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    void execute(){
        Scanner sc = new Scanner(System.in);
        int entry = -1;
        System.out.println();
        do{
            print();
            entry = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (entry) {
                case 1:
                    data.search();
                    break;
                case 2:
                    data.printAll();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }
            System.out.println();

        }while(entry != 0);
        System.out.println("Bye!");
        sc.close();
    }
}
