package pstudio.test2024;

import java.io.*;
import java.util.*;

public class BmiCRUD implements iCRUD{
    private ArrayList<BmiItem> list;
    public BmiCRUD() {
        this.list = new ArrayList<BmiItem>();
    }

    public void loadData() throws IOException {
        File file = new File("src/data.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;

        while((line = br.readLine()) != null) {
            String[] lines = line.split("/");
            BmiItem item = new BmiItem(lines[0], Integer.valueOf(lines[1]), Integer.valueOf(lines[2]));
            list.add(item);
        }
        br.close();
    }
    @Override
    public Object createItem() {
        return null;
    }

    @Override
    public int addItem() {
        String name;
        int height, weight;
        Scanner sc = new Scanner(System.in);

        System.out.print("Add a new record\n"
                + "Enter your name: ");
        name = sc.nextLine().trim();;

        System.out.print("Enter height and weight: ");
        height = sc.nextInt();;
        weight = sc.nextInt();;

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
        if(item_found == null) {
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
        if(item_found == null) {
            System.out.println("Not found.");
            return 1;
        }

        System.out.println(item_found.toString());

        this.list.remove(item_found);
        System.out.println("Record deleted.");
        return 0;
    }

    public BmiItem findName(String name){
        for (BmiItem item : this.list) {
            if (item.getName().equals(name))
                return item;
        }
        return null;

    }

    @Override
    public int printItem() {
        return 0;
    }

    public int printAll() {
        Scanner sc = new Scanner(System.in);

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
