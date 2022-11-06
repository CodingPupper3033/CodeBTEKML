package com.codingpupper3033.btekml.mapdrawer;

import com.codingpupper3033.btekml.maphelpers.Coordinate;

public class WorldEdit {
    private static final String TPLL_COMMAND = "/tpll %s %s %s";

    public static final String POS_COMMAND = "//pos%s";
    public static final String SEL_COMMAND = "//sel %s";

    public static final String SET_COMMAND = "//set %s";
    public static final String LINE_COMMAND = "//line %s";

    public static final String AIR_BLOCK = "air";


    public static void send(String command){
        Command.out.println(command);
    }

    public static void sendAll(String[] commands) {
        for (String command : commands) {
            send(command);
        }
    }

    // World Edit
    public static void tpll(Coordinate coordinate) {
        String command;
        try {
            command = String.format(TPLL_COMMAND, coordinate.getLatitude(),coordinate.getLongitude(), coordinate.getAltitude()-.5);
        } catch (Exception e) {
            command = String.format(TPLL_COMMAND, coordinate.getLatitude(),coordinate.getLongitude(),"");
        }

        send(command);
    }

    public static void pos(int numb) {
        send(String.format(POS_COMMAND, numb));
    }

    public static void set(Coordinate pos, String blockName) {
        tpll(pos);

        pos(1);
        pos(2);

        send(String.format(SET_COMMAND, blockName));

        clearSelection();
    }

    public static void sel(String method) {
        send(String.format(SEL_COMMAND, method));
    }

    public static void line(Coordinate start, Coordinate end, String blockName) {
        tpllDelete(start);
        tpllDelete(end);

        tpll(start);
        pos(1);
        tpll(end);
        pos(2);

        send(String.format(LINE_COMMAND, blockName));
        clearSelection();
    }


    // Friendlier
    public static void clearSelection() {
        sel("");
    }

    public static void deleteBlock(Coordinate coordinate) {
        set(coordinate, AIR_BLOCK);
    }

    public static void tpllDelete(Coordinate coordinate) {
        deleteBlock(coordinate);
        tpll(coordinate);
    }
}
