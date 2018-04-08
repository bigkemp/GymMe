package com.example.user.myapplication2;

/**
 * Created by pelleg on 1/31/2018.
 */

public class Measurement {

    private int waist;
    private int arms;
    private int thighs;
    private int weight;
    private int hips;
    private String date;

    public Measurement( int arms, int hips, int thighs, int waist, int weight,String date ) {
        this.setArms(arms);
        this.setHips(hips);
        this.setDate(date);
        this.setThighs(thighs);
        this.setWaist(waist);
        this.setWeight(weight);
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist( int waist ) {
        this.waist = waist;
    }

    public int getArms() {
        return arms;
    }

    public void setArms( int arms ) {
        this.arms = arms;
    }

    public int getThighs() {
        return thighs;
    }

    public void setThighs( int thighs ) {
        this.thighs = thighs;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight( int weight ) {
        this.weight = weight;
    }

    public int getHips() {
        return hips;
    }

    public void setHips( int hips ) {
        this.hips = hips;
    }
}
