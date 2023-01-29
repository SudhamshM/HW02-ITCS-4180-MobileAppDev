package com.example.hw02;

import java.io.Serializable;

public class Drink implements Serializable {
    double drinkSizeOz, alcoholPercent, valueA;
    Profile personWhoDrank;

    public Drink(double drinkSizeOz, double alcoholPercent, Profile personWhoDrank)
    {
        this.drinkSizeOz = drinkSizeOz;
        this.alcoholPercent = alcoholPercent;
        this.valueA = (drinkSizeOz * (alcoholPercent/100));
        this.personWhoDrank = personWhoDrank;
    }

    public Drink(double drinkSizeOz, double alcoholPercent)
    {
        this.drinkSizeOz = drinkSizeOz;
        this.alcoholPercent = alcoholPercent;
        this.valueA = (drinkSizeOz * (alcoholPercent/100));
    }

    public double getDrinkSizeOz()
    {
        return drinkSizeOz;
    }

    public void setDrinkSizeOz(double drinkSizeOz)
    {
        this.drinkSizeOz = drinkSizeOz;
    }

    public double getAlcoholPercent()
    {
        return alcoholPercent;
    }

    public void setAlcoholPercent(double alcoholPercent)
    {
        this.alcoholPercent = alcoholPercent;
    }

    public double getValueA()
    {
        return valueA;
    }

    public double getBacLevel()
    {
        if (this == null)
        {
            return -1;
        }
        return (this.valueA * 5.14 / (personWhoDrank.getWeight() * personWhoDrank.getValueR()));
    }

    public void setValueA(double valueA)
    {
        this.valueA = valueA;
    }

    public Profile getPersonWhoDrank()
    {
        return personWhoDrank;
    }

    public void setPersonWhoDrank(Profile personWhoDrank)
    {
        this.personWhoDrank = personWhoDrank;
    }

    @Override
    public String toString()
    {
        return "Drink{" +
                "drinkSizeOz=" + drinkSizeOz +
                ", alcoholPercent=" + alcoholPercent +
                ", valueA=" + valueA +
                '}';
    }
}
