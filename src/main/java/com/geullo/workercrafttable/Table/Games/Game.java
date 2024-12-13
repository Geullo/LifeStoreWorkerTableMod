package com.geullo.workercrafttable.Table.Games;

public abstract class Game {

    public int cursor = 0;
    public int storelevel = 1;
    public Game(int storeLevel) {
        this.storelevel = storeLevel;
    }

    public void init() {
        cursor = 0;
    }

    public float storeLvl(float a,float b) {
        return storelevel >= 3 ? a : b;
    }

    public void setStorelevel(int storelevel) {
        this.storelevel = storelevel;
    }

}
