package com.geullo.workercrafttable.Table.Games;

public class DesignGame extends Game {
    public int speed = 2,maximum = 80;
    public boolean clicked = false;

    public DesignGame(int storeLevel) {
        super(storeLevel);
    }
    public String check() {
        cursor+=storeLvl(speed*1.25f,speed);
        if (maximum <= cursor) {
            cursor = 0;
            return "11";
        }
        return "00";
    }
}
