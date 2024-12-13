package com.geullo.workercrafttable.Table.Games;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SuperGame extends Game{
    private List<KEYS> a = new ArrayList<>();
    public SuperGame(int storeLevel) {
        super(storeLevel);
        cursor = 0;
        init();
    }
    public KEYS getNowKeyCode() {
        return a.get(cursor);
    }
    public boolean next() {
        if (a.size()-1<1) {
            a.remove(0);
            return true;
        }
//        cursor++;
        a.remove(0);
        return false;
    }
    public List<KEYS> getKeys() {
        return a;
    }

    @Override
    public void init() {
        super.init();
        cursor = 0;
        a.clear();
        Random r = new Random();
        for (int i = 0; i<(int) storeLvl(2,0)+3; i++) {
            a.add(KEYS.values()[r.nextInt(KEYS.values().length)]);
        }
    }
    public static enum KEYS {
        KEY_LEFT(Keyboard.KEY_LEFT,0),
        KEY_RIGHT(Keyboard.KEY_RIGHT,1),
        KEY_UP(Keyboard.KEY_UP,2),
        KEY_DOWN(Keyboard.KEY_DOWN,3)
        ;
        public int key,co;
        KEYS(int key,int co) {
            this.key = key;
            this.co = co;
        }
    }
}
