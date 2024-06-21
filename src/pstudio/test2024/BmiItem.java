package pstudio.test2024;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BmiItem {
    private String name;
    private int height;
    private int weight;
    private double bmi;

    private String level;
    private Date reg_date;

    public BmiItem(String name, int height, int weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.bmi = calculateBmi(height, weight);
        resetLevel();
        this.reg_date = new Date();
    }

    private double calculateBmi(int height, int weight) { // BMI를 계산하는 함수를 추가했습니다!
        if (height == 0)
            return 0;
        return weight / (height * 0.01 * height * 0.01);
    }

    public void resetLevel() {
        if (bmi < 18.5)
            this.level = "Underweight";
        else if (bmi < 25)
            this.level = "Healthy Weight";
        else if (bmi < 30)
            this.level = "Overweight";
        else
            this.level = "Obesity";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return String.format("%-15s", name) + " [h:" + height + ", w:" + weight + "] " + String.format("%.1f", bmi)
                + " - " + level + " - "
                + new SimpleDateFormat("HH:mm:ss").format(reg_date);
    }
}
