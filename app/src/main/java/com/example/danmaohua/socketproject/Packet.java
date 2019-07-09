package com.example.danmaohua.socketproject;

public class Packet {
    public static final byte MESSAGE_STX = (byte) 0xFE;

    public int len;

    public int from;

    public int to;

    public int type1;

    public int type2;

    public Payload payload;

    public int fcs;

    public Packet(int payloadLength) {
        len = payloadLength;
        payload = new Payload(payloadLength);
    }

    public boolean payloadIsFilled() {
        return payload.size() >= len;
    }

    public byte generateCRC() {
//        byte result = (byte) ((byte) from ^ (byte) to ^ (byte) type1 ^ (byte) type2)
        byte result = (byte) ((byte) from + (byte) to + (byte) type1 + (byte) type2);
        payload.resetIndex();
        final int payloadSize = payload.size();
        for (int i = 0; i < payloadSize; i++) {
//            result ^= payload.getByte();
            result += payload.getByte();
        }
        fcs = result;
        return result;
    }

    public void toBytes(byte[] bytes) {
        bytes[0] = (byte) 0xfe;
        bytes[1] = (byte) (len + 4);
        bytes[2] = (byte) from;
        bytes[3] = (byte) to;
        bytes[4] = (byte) type1;
        bytes[5] = (byte) type2;
        payload.resetIndex();
        final int payloadSize = payload.size();
        for (int i = 0; i < payloadSize; i++) {
            bytes[i + 6] = payload.getByte();
        }
        bytes[payloadSize + 6] = (byte) fcs;
    }
}
