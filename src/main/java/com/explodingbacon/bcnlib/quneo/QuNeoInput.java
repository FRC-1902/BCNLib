package com.explodingbacon.bcnlib.quneo;

import com.explodingbacon.bcnlib.controllers.FakeButton;
import java.util.ArrayList;
import java.util.List;

public abstract class QuNeoInput extends FakeButton {

    private static List<QuNeoInput> quNeoInputs = new ArrayList<>();

    private final int note;
    private Type type = null;
    private int pressure = 0;

    public QuNeoInput(int note) {
        this.note = note;
        boolean taken = false;
        for (QuNeoInput i : quNeoInputs) {
            if (i.note == note) {
                taken = true;
                break;
            }
        }
        if (!taken) {
            quNeoInputs.add(this);
        }
    }

    public abstract void handleControlChange(String eventType, String data);

    public static QuNeoInput getInput(int note) {
        for (QuNeoInput i : quNeoInputs) {
            if (i.note == note) {
                return i;
            }
        }
        return null;
    }

    public class Rotary extends QuNeoInput {

        public Rotary(int note) {
            super(note);
        }

        @Override
        public void handleControlChange(String eventType, String data) {

        }
    }

    public enum Type {
        ROTARIES(new int[]{4, 5}, n -> new int[]{n + 12, n}),
        PAD(new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51}, n -> {
            int addAmt = (n - 36) * 3;
            return new int[]{59 + addAmt, 60 + addAmt, 61 + addAmt};
        }),
        BUTTON(11, 12, 13, 14, 15, 16, 17, 18), //TODO: formula for cc channels
        RHOMBUS(new int[]{19}, n-> new int[]{79}),
        VERTICAL_SLIDER(new int[]{6 ,7, 8, 9}, n -> {
            int vertID = n - 6;
            return new int[]{18 + vertID, n};
        }),
        HORIZONTAL_SLIDER(new int[]{0, 1, 2, 3}, n-> {
            return new int[]{12 + n, n};
        }),
        BIG_SLIDER(new int[]{10}, n -> new int[]{22, 10, 11}),
        UP_DOWN(new int[]{20, 21, 22, 23}, n -> {
            int updownID = n - 20;
            return new int[]{80 + updownID};
        });

        int[] ids;
        int[] ccChannels = {};

        Type(int... ids) {
            this.ids = ids;
        }

        Type(int[] ids, NoteToCC ccChannelSupplier) {
            this.ids = ids;
            List<Integer> ccChannelsList = new ArrayList<>();
            for (int i : ids) {
                for (int i2: ccChannelSupplier.get(i)) ccChannelsList.add(i2);
            }
            ccChannels = new int[ccChannelsList.size()];
            int index = 0;
            for (int i : ccChannelsList) {
                ccChannels[index] = i;
                index++;
            }
        }
    }
}
