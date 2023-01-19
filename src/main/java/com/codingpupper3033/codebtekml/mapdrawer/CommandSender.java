package com.codingpupper3033.codebtekml.mapdrawer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Sends Minecraft commands to the game
 * @author Joshua Miller
 */
public class CommandSender {
    static final boolean DEBUG_MODE = false;
    static PrintStream out = new PrintStream(new CommandOutputStream());

    private static class CommandOutputStream extends OutputStream {
        StringBuffer buffer = new StringBuffer();
        @Override
        public void write(int b) throws IOException {
            char c = (char) b;

            if (c == '\n') {
                output();
            } else {
                buffer.append(c);
            }
        }

        private void output() {
            String command = buffer.toString();
            command = command.replace("minecraft:",""); // World Edit doesn't know what minecraft: is ._.

            if (!DEBUG_MODE) {
                Minecraft.getMinecraft().player.sendChatMessage(command);
            } else {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(command.replaceAll("\r","")));
            }

            buffer = new StringBuffer();
        }
    }
}