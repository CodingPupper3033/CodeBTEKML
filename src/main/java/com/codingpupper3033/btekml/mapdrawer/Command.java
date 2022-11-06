package com.codingpupper3033.btekml.mapdrawer;

import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Command {
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
            Minecraft.getMinecraft().player.sendChatMessage(buffer.toString());

            buffer = new StringBuffer();
        }
    }
}