package com.codingpupper3033.codebtekml.gui.screens;

import com.codingpupper3033.codebtekml.gui.widgets.ProgressBar;
import com.codingpupper3033.codebtekml.helpers.map.Placemark;
import com.codingpupper3033.codebtekml.helpers.map.placemark.DrawListener;
import com.codingpupper3033.codebtekml.helpers.map.placemark.PlacemarkFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

/**
 * Shows a loading screen while building
 * @author Joshua Miller
 */
public class GuiBuilding extends GuiScreen {

    public static final int[] DONE_POS = {0,90};
    public static final int DONE_COLOR = 0xFFFFFF;
    public static final String DONE_TEXT = "gui.done";
    public static final int[] PROGRESS_BAR_POS = {-150,100};
    public static final int[] PROGRESS_BAR_SIZE = {300,20};
    private final String blockName;
    private ProgressBar progressBar;
    private float progress = 0f;

    private boolean started = false;
    private boolean finished = false;

    public boolean doProgress = true;

    Placemark[] placemarks;

    public GuiBuilding(Placemark[] placemarks, String blockName) {
        this.placemarks = placemarks;
        this.blockName = blockName;
    }

    @Override
    public void initGui() {
        super.initGui();

        // Helper variables for adding gui elements
        int guiMiddleX = width/2;
        int guiStartY = height/6;

        progressBar = new ProgressBar(PROGRESS_BAR_POS[0]+guiMiddleX,PROGRESS_BAR_POS[1]+guiStartY,PROGRESS_BAR_SIZE[0], PROGRESS_BAR_SIZE[1]);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground(); // It greyish :)

        // Helper variables for adding gui elements
        int guiMiddleX = width/2;
        int guiStartY = height/6;

        if (!started) { // Start the drawing now
            started = true;
            new Thread(() -> { // New thread as to allow for loading screen
                if (doProgress) {
                    PlacemarkFactory.drawPlacemarks(placemarks, blockName, new DrawListener() {

                        @Override
                        public void subsectionDrawn(int subsection, int totalSubsections, int placemark, int totalPlacemarks) {
                            float singularPlacemarkPercent = 1f/totalPlacemarks;
                            float placemarkCompletion = 1f*subsection/totalSubsections;
                            progress = singularPlacemarkPercent*(placemark-1)+singularPlacemarkPercent*placemarkCompletion;
                        }
                    }); // Draw!
                } else {
                    PlacemarkFactory.drawPlacemarks(placemarks, blockName);
                }

                finished = true;

                // Done Drawing, close after a bit
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Minecraft.getMinecraft().displayGuiScreen(null);
            }).start();
        }

        // Draw progress
        if (doProgress) progressBar.drawProgress(progress);

        // Draw Done
        if (finished) drawCenteredString(Minecraft.getMinecraft().fontRenderer, I18n.format(DONE_TEXT)+"!", guiMiddleX+DONE_POS[0],guiStartY+DONE_POS[1], DONE_COLOR);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
