package com.explodingbacon.bcnlib.midi;

import javax.sound.midi.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MidiAPI {
    private Transmitter t = new Transmitter();
    private Receiver mReceiver = new Receiver();
    private Map<String, List<MidiAPIListener>> registrationMap = new HashMap<>();
    private MessageDigest md;

    public static final int CONTROL_CHANGE = -80;
    public static final int NOTE_ON = -112;
    public static final int NOTE_OFF = -128;

    public MidiAPI(String deviceName) {
        MidiDevice device = null;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing MessageDigest", e);
        }

        Boolean foundDevice = false;
        for(MidiDevice.Info info : infos) {
            if(info.getName().equals(deviceName)) {
                foundDevice = true;
                //System.out.println("Found a matching device");

                try {
                    device = MidiSystem.getMidiDevice(info);
                    device.getTransmitter().setReceiver(mReceiver);

                    System.out.println("Found a readable device: " + info);

                    device.open();
                } catch (MidiUnavailableException ignored) {
                    try {
                        device = MidiSystem.getMidiDevice(info);
                        t.setReceiver(device.getReceiver());

                        System.out.println("Found a writable device: " + info);

                        device.open();
                    } catch (MidiUnavailableException e) {
                        throw new MidiDeviceNotFoundException("There was an error trying to open the MIDI device.", e);
                    }
                }
            }
        }

        if(!foundDevice) throw new MidiDeviceNotFoundException("We were unable to find a device that you specified.");
    }

    @FunctionalInterface
    public interface MidiAPIListener {
        void onMidiEvent(int eventType, int channel, byte note, byte data);
    }

    public void registerListener(MidiAPIListener listener, int type, int channel, int... notes) {
        for(int n : notes) {
            String hash = hashOfMessage(type, channel, n);

            registrationMap.putIfAbsent(hash, new ArrayList<>());
            registrationMap.get(hash).add(listener);
        }
    }

    public void sendCC(byte note, byte data) {
        t.sendCC(note, data);
    }

    public void sendCC(int channel, byte note, byte data) {
        t.sendCC(channel, note, data);
    }

    public void sendNote(byte channel, Boolean on, byte note) {
        t.sendNote(on, note, channel);
    }

    public void sendNote(byte channel, Boolean on, byte note, byte velocity) {
        t.sendNote(on, channel, note, velocity);
    }

    private String hashOfMessage(int type, int channel, int note) {
        md.reset();
        String input = "t"+type+"c"+channel+"n"+note;
        return Arrays.toString(md.digest(input.getBytes()));
    }

    private class Transmitter implements javax.sound.midi.Transmitter {
        private javax.sound.midi.Receiver r;

        @Override
        public void setReceiver(javax.sound.midi.Receiver receiver) {
            this.r = receiver;
        }

        @Override
        public javax.sound.midi.Receiver getReceiver() {
            return r;
        }

        @Override
        public void close() {

        }

        public void sendCC(byte note, byte data) {
            sendCC(1, note, data);
        }

        public void sendCC(int channel, byte note, byte data) {
            try {
                MidiMessage m = new ShortMessage(CONTROL_CHANGE, channel, note, data);
                r.send(m, System.currentTimeMillis());
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            }
        }

        public void sendNote(Boolean on, byte note, byte channel) {
            sendNote(on, 1, note, channel);
        }

        public void sendNote(Boolean on, int channel, byte note, byte velocity) {
            try {
                MidiMessage m = new ShortMessage(on ? NOTE_ON : NOTE_OFF, channel, note, velocity);
                r.send(m, System.currentTimeMillis());
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            }
        }
    }

    private class Receiver implements javax.sound.midi.Receiver {

        @Override
        public void send(MidiMessage message, long timeStamp) { //TODO: Figure out channels
            byte[] msg = message.getMessage();
            int messageType = msg[0] == -80 ? CONTROL_CHANGE : msg[0] == -112 ? NOTE_ON : msg[0] == -128 ? NOTE_OFF : 0;
            String messageHash = hashOfMessage(messageType, 1, msg[1]);
            if(registrationMap.get(messageHash) != null) {
                for (MidiAPIListener l : registrationMap.get(messageHash)) {
                    l.onMidiEvent(messageType, 1, msg[1], msg[2]);
                }
            } else if(messageType == NOTE_ON) {
                System.out.println("Null entry for hash " + messageHash);
            }
        }

        @Override
        public void close() {

        }
    }

    private class MidiDeviceNotFoundException extends RuntimeException {
        MidiDeviceNotFoundException(String cause) {
            super(cause);
        }

        MidiDeviceNotFoundException(String cause, Throwable e) {
            super(cause, e);
        }
    }
}
