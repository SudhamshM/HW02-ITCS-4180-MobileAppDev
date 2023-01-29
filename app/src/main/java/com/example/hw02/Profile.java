package com.example.hw02;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {
    String gender;
    double weight;
    double valueR;

    public double getBacLevel()
    {
        return bacLevel;
    }

    public void setBacLevel(double bacLevel)
    {
        this.bacLevel = bacLevel;
    }

    public ArrayList<Drink> getDrinksList()
    {
        return drinksList;
    }

    public void setDrinksList(ArrayList<Drink> drinksList)
    {
        this.drinksList = drinksList;
    }

    double bacLevel;
    ArrayList<Drink> drinksList;

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public double getValueR()
    {
        return valueR;
    }

    public void setValueR(double valueR)
    {
        this.valueR = valueR;
    }

    public Profile()
    {
    }

    public Profile(String gender, double weight, double valueR)
    {
        this.gender = gender;
        this.weight = weight;
        this.valueR = valueR;
        this.drinksList = new ArrayList<>();
    }

    public double getTotalBac()
    {
        double bac = 0;
        for (Drink drink: this.drinksList)
        {
            bac += drink.getBacLevel();
        }
        setBacLevel(bac);
        return bac;
    }

    public void addDrink(Drink newDrink)
    {
        this.drinksList.add(newDrink);
        getTotalBac();
    }
}
