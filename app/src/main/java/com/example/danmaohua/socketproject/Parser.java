package com.example.danmaohua.socketproject;


public class Parser {
    private static final int IDLE = 1;
    private static final int STX = 2;
    private static final int LEN = 3;
    private static final int FROM = 4;
    private static final int TO = 5;
    private static final int TYPE1 = 6;
    private static final int TYPE2 = 7;
    private static final int DATA = 8;
    private static final int FCS = 9;

    private static boolean msg_received;

    private Packet packet;

    private int states = IDLE;

    public Packet message_parse_char(byte b) {
        msg_received = false;

        switch (states + 1) {
            case STX:
                if (b == Packet.MESSAGE_STX) {
                    states = STX;
                }
                break;
            case LEN:
                packet = new Packet(b - 4);
                states = LEN;
                break;
            case FROM:
                packet.from = b;
                states = FROM;
                break;
            case TO:
                packet.to = b;
                states = TO;
                break;
            case TYPE1:
                packet.type1 = b;
                states = TYPE1;
                break;
            case TYPE2:
                packet.type2 = b;
                states = packet.len > 0 ? TYPE2 : DATA;
                break;
            case DATA:
                packet.payload.add(b);
                if (packet.payloadIsFilled()) {
                    states = DATA;
                }
                break;
            case FCS:
                if (b == packet.generateCRC()) {
                    msg_received = true;
                } else {
                    states = b == Packet.MESSAGE_STX ? STX : IDLE;
                }
                break;
        }
        return msg_received ? packet : null;
    }
}