package pstudio.test2024;

import java.io.*;
import java.util.*;

public class BmiCRUD implements iCRUD {
    private ArrayList<BmiItem> list;

    public BmiCRUD() {
        this.list = new ArrayList<BmiItem>();
    }

    public void loadData() throws IOException {
        File file = new File("src/data.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            String[] lines = line.split("/");
            BmiItem item = new BmiItem(lines[0], Integer.valueOf(lines[1]), Integer.valueOf(lines[2]));
            list.add(item);
        }
        br.close();
    }

    public void saveData() throws IOException, UnknownFormatConversionException {
        File file = new File("src/data.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (BmiItem item : list) {
            bw.write(item.getName() + "/" + item.getHeight() + "/" + item.getWeight());
            bw.newLine();
        }
        bw.close();
    }

    @Override
    public BmiItem createItem() { //
        Scanner sc = new Scanner(System.in);

        System.out.print("Add a new record\n"
                + "Enter your name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter height and weight: ");
        int height = sc.nextInt();
        int weight = sc.nextInt();
        sc.nextLine();

        BmiItem newitem = new BmiItem(name, height, weight);
        System.out.println("New record created: " + newitem.toString());
        return newitem;
    }

    @Override
    public int addItem() {
        String name;
        int height, weight;
        Scanner sc = new Scanner(System.in);

        System.out.print("Add a new record\n"
                + "Enter your name: ");
        name = sc.nextLine().trim();
        ;

        System.out.print("Enter height and weight: ");
        height = sc.nextInt();
        ;
        weight = sc.nextInt();
        ;

        BmiItem newitem = new BmiItem(name, height, weight);
        this.list.add(newitem);
        System.out.println("Record added.");
        return 0;
    }

    @Override
    public int updateItem() {
        int height, weight;
        Scanner sc = new Scanner(System.in);

        System.out.print("Edit a record\n"
                + "Enter the name to edit: ");
        String name = sc.nextLine().trim();
        BmiItem item_found = findName(name);
        if (item_found == null) {
            System.out.println("Not found.");
            return 1;
        }

        System.out.println(item_found.toString());

        System.out.print("Enter height and weight: ");
        height = sc.nextInt();
        weight = sc.nextInt();
        item_found.setHeight(height);
        item_found.setWeight(weight);
        item_found.resetLevel();
        item_found.setReg_date(new Date());

        System.out.println(item_found.toString());
        System.out.println("Record updated.");
        return 0;
    }

    @Override
    public int deleteItem() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Delete a record\n"
                + "Enter the name to remove: ");
        String name = sc.nextLine().trim();

        BmiItem item_found = findName(name);
        if (item_found == null) {
            System.out.println("Not found.");
            return 1;
        }

        System.out.println(item_found.toString());

        this.list.remove(item_found);
        System.out.println("Record deleted.");
        return 0;
    }

    public BmiItem findName(String name) {
        for (BmiItem item : this.list) {
            if (item.getName().equals(name))
                return item;
        }
        return null;

    }

    @Override
    public int printItem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word > ");
        String name = sc.nextLine().trim();
        int x = 0;
        for (BmiItem item : this.list) {
            if (item.getName().contains(name)) {
                System.out.println(item.toString());
                x++;
            }
        }

        if (x == 0) {
            System.out.println("Not found.");
        } else {
            System.out.println(x + " records found.");
        }
        return 0;
    }

    public int printAll() {
        Scanner sc = new Scanner(System.in);
        System.out.print("sort by [1]name ascending, [2]name descending > ");
        int selection = sc.nextInt();

        if (selection == 1 || selection == 2) {
            sortByName();
        } else {
            System.out.println("Wrong number.");
            return 0;
        }

        if (selection == 2) {
            this.reverseList();
        }

        System.out.println("Total " + this.list.size() + " records");
        for (BmiItem item : this.list) {
            System.out.println(item.toString());
        }

        return 0;
    }

    public void sortByBmi() {
        Collections.sort(this.list, new BmiItemBmiComparator());
    }

    public void sortByName() {
        Collections.sort(this.list, new BmiItemNameComparator());
    }

    public void reverseList() {
        Collections.reverse(this.list);
    }

    public void printReport() {

        if (list.isEmpty()) {
            System.out.println("No records here.");
            return;
        }

        double minBmi[] = { -1.0, -1.0, -1.0, -1.0 };
        double maxBmi[] = { -1.0, -1.0, -1.0, -1.0 };
        int[] levelCount = { 0, 0, 0, 0 };

        for (BmiItem item : list) {
            double bmi = item.getBmi();

            switch (item.getLevel()) {
                case "Underweight":
                    levelCount[0]++;
                    if ((minBmi[0] == -1.0) || (minBmi[0] > bmi)) {
                        minBmi[0] = bmi;
                    }
                    if ((maxBmi[0] == -1.0) || (maxBmi[0] < bmi)) {
                        maxBmi[0] = bmi;
                    }
                    break;

                case "Healthy Weight":
                    levelCount[1]++;
                    if ((minBmi[1] == -1.0) || (minBmi[1] > bmi)) {
                        minBmi[1] = bmi;
                    }
                    if ((maxBmi[1] == -1.0) || (maxBmi[1] < bmi)) {
                        maxBmi[1] = bmi;
                    }
                    break;

                case "Overweight":
                    levelCount[2]++;
                    if ((minBmi[2] == -1.0) || (minBmi[2] > bmi)) {
                        minBmi[2] = bmi;
                    }
                    if ((maxBmi[2] == -1.0) || (maxBmi[2] < bmi)) {
                        maxBmi[2] = bmi;
                    }
                    break;

                case "Obesity":
                    levelCount[3]++;
                    if ((minBmi[3] == -1.0) || (minBmi[3] > bmi)) {
                        minBmi[3] = bmi;
                    }
                    if ((maxBmi[3] == -1.0) || (maxBmi[3] < bmi)) {
                        maxBmi[3] = bmi;
                    }
                    break;
            }
        }

        int percentage[] = new int[4];
        for (int i = 0; i < 4; i++) {
            percentage[i] = (int) ((levelCount[i] * 1.0) / (this.list.size() * 1.0) * 100.0);
        }

        System.out.println("Total " + this.list.size() + " records");
        System.out.println("Underweight - " + levelCount[0] + " persons (" + (String.format("%.1f", minBmi[0])) + " ~ "
                + (String.format("%.1f", maxBmi[0])) + ") - "
                + percentage[0] + "%");

        System.out
                .println("Healthy Weight - " + levelCount[1] + " persons (" + (String.format("%.1f", minBmi[1])) + " ~ "
                        + (String.format("%.1f", maxBmi[1])) + ") - "
                        + percentage[1] + "%");

        System.out.println("Overweight - " + levelCount[2] + " persons (" + (String.format("%.1f", minBmi[2])) + " ~ "
                + (String.format("%.1f", maxBmi[2])) + ") - "
                + percentage[2] + "%");

        System.out.println("Obesity - " + levelCount[3] + " persons (" + (String.format("%.1f", minBmi[3])) + " ~ "
                + (String.format("%.1f", maxBmi[3])) + ") - "
                + percentage[3] + "%");

    }

    public void prinstatistics() {
        if (list.isEmpty()) {
            System.out.println("No records here.");
            return;
        }

        double totalBmi = 0;
        double minBmi = list.get(0).getBmi();
        double maxBmi = list.get(0).getBmi();

        for (BmiItem item : list) {
            double bmi = item.getBmi();
            totalBmi += bmi;
            if (bmi < minBmi)
                minBmi = bmi;
            if (bmi > maxBmi)
                maxBmi = bmi;
        }

        double avgBmi = totalBmi / list.size();

        System.out.println("BMI Statistics:");
        System.out.printf("Average BMI: %.2f\n", avgBmi);
        System.out.printf("Minimum BMI: %.2f\n", minBmi);
        System.out.printf("Maximum BMI: %.2f\n", maxBmi);
    }

}

class BmiItemBmiComparator implements Comparator<BmiItem> {
    @Override
    public int compare(BmiItem o1, BmiItem o2) {
        if (o1.getBmi() < o2.getBmi())
            return -1;
        else
            return 1;
    }
}

class BmiItemNameComparator implements Comparator<BmiItem> {
    @Override
    public int compare(BmiItem o1, BmiItem o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
