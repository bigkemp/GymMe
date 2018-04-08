package com.example.user.myapplication2;

/**
 * Created by pelleg on 1/31/2018.
 */

public class Exercise {

    private String exercise;
    private int set1;
    private int set2;
    private int set3;
    private int weight;
    private  int target=0;
    private String musclegroup;
    private String POS;
    private String toughness;

    public String getMusclegroup() {
        return musclegroup;
    }

    public void setMusclegroup( String musclegroup ) {
        this.musclegroup = musclegroup;
    }

    public String getPOS() {
        return POS;
    }

    public void setPOS( String POS ) {
        this.POS = POS;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness( String toughness ) {
        this.toughness = toughness;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget( int target ) {
        this.target = target;
    }

    public Exercise( String exercise, int set1, int set2, int set3, int weight,int target,String musclegroup,String POS, String toughness ) {
        this.setExercise(exercise); ;
        this.setSet1(set1);
        this.setSet2(set2);
        this.setSet3(set3);
        this.setWeight(weight);
        this.setTarget(target);
        this.setMusclegroup(musclegroup);
        this.setPOS(POS);
        this.setToughness(toughness);
    }


    public String getExercise() {
        return exercise;
    }

    public void setExercise( String exercise ) {
        this.exercise = exercise;
    }

    public int getSet1() {
        return set1;
    }

    public void setSet1( int set1 ) {
        this.set1 = set1;
    }

    public int getSet2() {
        return set2;
    }

    public void setSet2( int set2 ) {
        this.set2 = set2;
    }

    public int getSet3() {
        return set3;
    }

    public void setSet3( int set3 ) {
        this.set3 = set3;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight( int weight ) {
        this.weight = weight;
    }
}
