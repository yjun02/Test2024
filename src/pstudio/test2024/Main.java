package pstudio.test2024;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Main test2024 = new Main();
        test2024.run();
    }

    public void printMenu() {
        System.out.print("\n[Menu] " +
                "1. Add " +
                "2. Delete " +
                "3. Update " +
                "4. List " +
                "5. Save " +
                "6. Report " +
                "7. Find " +
                "8. Statistics " +
                "0. Quit > ");
    }

    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        BmiCRUD manager = new BmiCRUD();
        try {
            manager.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean quit = false;
        do {
            printMenu();
            int menu = sc.nextInt();
            switch (menu) {
                case 1:
                    manager.addItem();
                    break;

                case 2:
                    manager.deleteItem();
                    break;

                case 3:
                    manager.updateItem();
                    break;

                case 4:
                    manager.printAll();
                    break;

                case 5:
                    manager.saveData();
                    break;

                case 6:
                    manager.printReport();
                    break;

                case 7:
                    manager.printItem();
                    break;
                case 8:
                    manager.prinstatistics();
                    break;

                case 0:
                    quit = true;
                    break;

                default:
                    System.out.println("Wrong number.\n");
                    break;
            }
        } while (!quit);
    }
}