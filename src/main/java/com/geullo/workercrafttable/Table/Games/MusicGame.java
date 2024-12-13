package com.geullo.workercrafttable.Table.Games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MusicGame extends Game{

    private List<Note> notes = new ArrayList<>();
    private int noteLength = 3;

    public MusicGame(int storelevel) {
        super(storelevel);
        new Note("DO",0XffFF4C4C,0.707f);
        new Note("RE",0xffFE962D,0.793f);
        new Note("MI",0xffF4FE2D,0.890f);
        new Note("FA",0xff39D047,0.943f);
        new Note("SOL",0xff2956FF,1.059f);
        new Note("LA",0xff3221BE,1.189f);
        new Note("SI",0xffA73DEC,1.334f);
        new Note("DO_UP",0xffFF56F0,1.414f);
    }


    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public void init() {
        noteLength = (int) storeLvl(2f,3f);
        notes = new ArrayList<>();
        cursor = 1;
        for (int i = 0; i < new Random().nextInt(noteLength)+1; i++) {
            notes.add(Note.getRandom());
        }
    }

    public String check(Note inputNote) {
        if (!notes.get(cursor-1).name.equals(inputNote.getSameNote().name)) return "00";
        if (cursor == notes.size()) {
            notes.get(cursor-1).finished = true;
            return "11";
        }
        notes.get(cursor-1).finished = true;
        cursor++;
        return "01";
    }

    public static class Note {
        public static ArrayList<Note> values = new ArrayList<>();
        public String name;
        public int color;
        public float pitch;
        public boolean finished = false;

        public Note(Note note) {
            name = note.name;
            color = note.color;
            finished = note.finished;
            pitch = note.pitch;
        }

        public Note(String name ,int color, float pitch) {
            this.name = name;
            this.color = color;
            this.pitch = pitch;
            if (values.stream().noneMatch(v->v.name.equals(name))) values.add(this);
        }

        public static Note getRandom() {
            return values.get(new Random().nextInt(values.size())).copy();
        }

        public Note copy() {
            return new Note(this);
        }

        @Override
        public String toString() {
            return "Note{" +
                    "name='" + name + '\'' +
                    ", color=" + color +
                    ", finished=" + finished +
                    '}';
        }

        public Note getSameNote() {
            return values.stream().filter(n->n.name.equals(name)).collect(Collectors.toList()).get(0);
        }

        public static Note getSameNote(Note note) {
            return values.stream().filter(n->n.name.equals(note.name)).collect(Collectors.toList()).get(0);
        }

        public static int indexOf(Note note) {
            return values.indexOf(note.getSameNote());
        }
    }
}
