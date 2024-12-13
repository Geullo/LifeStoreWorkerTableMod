package com.geullo.workercrafttable.Table.Games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegongGame extends Game{

    private List<SegongSequence> sequenceList = new ArrayList<>();
    public int length = 4;


    public SegongGame(int storelevel) {
        super(storelevel);
    }

    @Override
    public void init() {
        cursor = 1;
        this.length = (int) storeLvl(2,4);
        Random r = new Random();
        sequenceList = new ArrayList<>();
        int n = r.nextInt(length)+1;
        for (int i = 0; i < length; i++) {
            int finalN = n;
            if (sequenceList.stream().anyMatch(z->z.id == finalN)) {
                n = r.nextInt(length)+1;
                i--;
            }
            else sequenceList.add(new SegongSequence(n));
        }
    }

    public String check(int current) {
        if (sequenceList.get(current).id == cursor) {
            if (sequenceList.get(current).id == length) {
                sequenceList.get(current).finished = true;
                return "11";
            }
            sequenceList.get(current).finished = true;
            cursor++;
            return "01";
        }
        return "00";
    }

    public List<SegongSequence> getSequences() {
        return sequenceList;
    }

    public static class SegongSequence {
        private int id,posId;
        private boolean finished = false;

        public SegongSequence(int id) {
            this.id = id;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        public int getId() {
            return id;
        }

        public boolean getFinished() {
            return finished;
        }

        public void setPosId(int posId) {
            this.posId = posId;
        }

        public int getPosId() {
            return posId;
        }

        @Override
        public String toString() {
            return "SegongSequence{" +
                    "id=" + id +
                    ", finished=" + finished +
                    '}';
        }
    }

}
