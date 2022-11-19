package com.codingpupper3033.codebtekml.mapdrawer;

import com.codingpupper3033.codebtekml.helpers.map.Coordinate;

/**
 * Commands able to be sent to the Minecraft Game
 * @author Joshua Miller
 */
public class MinecraftCommands {
    private static final String TPLL_COMMAND = "/tpll %s %s %s";

    public static final String POS_COMMAND = "//pos%s";
    public static final String SEL_COMMAND = "//sel %s";
    public static SEL_MODES currentSelMode = null;
    public enum SEL_MODES {
        CUBOID("cuboid"),
        EXTEND("extend"),
        POLY("poly"),
        ELLIPSOID("ellipsoid"),
        SPHERE("sphere"),
        CYL("cyl"),
        CONVEX("convex");

        private final String name;

        SEL_MODES(String name) {

            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static final String SET_COMMAND = "//set %s";
    public static final String LINE_COMMAND = "//line %s";

    public static final String AIR_BLOCK = "air";


    /**
     * Sends a singular command
     * @param command String of the command to send
     */
    public static void send(String command){
        CommandSender.out.println(command);
    }

    /**
     * Sends all commands in list
     * @param commands Strings of command to send
     */
    public static void sendAll(String[] commands) {
        for (String command : commands) {
            send(command);
        }
    }

    // BTE
    public static void tpll(Coordinate coordinate) {
        String command;
        try {
            command = String.format(TPLL_COMMAND, coordinate.getLatitude(),coordinate.getLongitude(), coordinate.getAltitude()-.5);
        } catch (Exception e) {
            command = String.format(TPLL_COMMAND, coordinate.getLatitude(),coordinate.getLongitude(),"");
        }

        send(command);
    }


    // World Edit
    public static void pos(int numb) {
        send(String.format(POS_COMMAND, numb));
    }

    public static void set(Coordinate pos, String blockName) {
        tpll(pos);

        sel(SEL_MODES.CUBOID);
        pos(1);
        pos(2);

        send(String.format(SET_COMMAND, blockName));

        clearSelection();
    }

    public static void sel(String method) {
        send(String.format(SEL_COMMAND, method));
    }

    public static void sel(SEL_MODES mode) {
        if (mode != currentSelMode) sel(mode.getName());
    }

    public static void line(Coordinate start, Coordinate end, String blockName) {
        tpllDelete(start);
        tpllDelete(end);

        sel(SEL_MODES.CUBOID);
        tpll(start);
        pos(1);
        tpll(end);
        pos(2);

        send(String.format(LINE_COMMAND, blockName));
        clearSelection();
    }


    // Friendlier Commands
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
