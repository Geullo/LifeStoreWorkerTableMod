package com.geullo.workercrafttable.Table.Games;

import java.util.Random;

public class MoksuGame extends Game{

    public int maximum;

    public MoksuGame(int storelevel) {
        super(storelevel);
    }

    @Override
    public void init() {
        cursor = 0;
        maximum = new Random().nextInt((int) storeLvl(15f,30f))+1;
    }

    public String check() {
        cursor++;
        if (cursor == maximum) {
            return "11";
        }
        return "01";
    }
}
