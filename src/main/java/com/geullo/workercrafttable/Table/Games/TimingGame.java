package com.geullo.workercrafttable.Table.Games;


import com.geullo.workercrafttable.util.Reference;
import com.geullo.workercrafttable.util.Utils;

import java.util.Random;

public class TimingGame {
    private Double maxScope = 100.0;
    private Double minScope = 0.0;
    private double arrange;

    public TimingGame(double arrange) {
        if (arrange>=90) throw new IllegalArgumentException("LIFE-"+ Reference.MOD_ID.toUpperCase() +" :: A range greater than 90 cannot be entered.");
        Random random = new Random();
        minScope = minScope + (maxScope - minScope) * random.nextDouble();
        while (minScope > maxScope - arrange)
        {
            minScope = 0 + (maxScope - 0) * random.nextDouble();
        }
        maxScope = Math.min(minScope + arrange, 100.0);
        this.arrange = arrange;
    }

    public double getMinScope(){
        return minScope;
    }
    public double getMaxScope(){
        return maxScope;
    }
    public double getArrange(){
        return arrange;
    }
    public double getArrange(double barSize){
        return Utils.percent(barSize, arrange);
    }
    public double getMinScope(double barSize){
        return Utils.percent(barSize, minScope);
    }
    public double getMaxScope(double barSize){
        return Utils.percent(barSize, maxScope);
    }

    public boolean isBetween(double percentage){
        return Utils.between(maxScope, minScope,percentage);
    }
    public boolean isBetween(double cursor,double cursorSize,double barSize){
        return Utils.between(getMaxScope(barSize), getMinScope(barSize),cursor);
    }
}
