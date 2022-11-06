package com.codingpupper3033.btekml.gui.screens;

import com.codingpupper3033.btekml.KeyInit;
import com.codingpupper3033.btekml.gui.BlockNameConverter;
import com.codingpupper3033.btekml.gui.buttons.IconButton;
import com.codingpupper3033.btekml.gui.widgets.BlockPreview;
import com.codingpupper3033.btekml.kmlfileprocessor.KMLParser;
import com.codingpupper3033.btekml.maphelpers.altitude.AltitudeProcessor;
import com.codingpupper3033.btekml.maphelpers.placemark.PlacemarkFactory;
import javafx.util.Pair;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import org.lwjgl.input.Keyboard;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

public class GuiDrawKML extends GuiScreen {
    // Parent
    private GuiScreen parentScreen;

    // File Name
    public static final int FILE_NAME_TEXT_BOX_ID = 1;
    public static final String FILE_NAME_TEXT_BOX_TEXT = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    private static final int[] FILE_NAME_TEXT_BOX_POS = {-155,0};
    public static final int[] FILE_NAME_TEXT_BOX_SIZE = {280,20};
    private GuiTextField fileNameTextBox;

    // Select File Button
    public static final int SELECT_FILE_BUTTON_ID = 2;
    public static final String SELECT_FILE_HOVER_TEXT = "Select File";
    private static final int[] SELECT_FILE_BUTTON_POS = {135, 0};
    private GuiButton selectFileButton;

    // Error Finding file
    public static final String ERROR_FINDING_FILE_TEXT = "Unable to find file location. Please try again.";
    public static final String ERROR_READING_FILE_TEXT = "Error reading file. Please try another.";
    public static final int ERROR_FILE_COLOR = 0xAA0000;

    private static final int[] ERROR_FILE_POS = {-145, 25};
    private String errorFileText = "";

    // Block Preview
    private static final int[] BLOCK_PREVIEW_POS = {-154, 36};
    private BlockPreview blockPreview;

    // Block Name
    public static final int BLOCK_NAME_TEXT_BOX_ID = 3;
    public static final int[] BLOCK_NAME_TEXT_BOX_POS = {-95,40};
    public static final int[] BLOCK_NAME_TEXT_BOX_SIZE = {220,20};
    private GuiTextField blockNameTextBox;

    // Block Name Help
    public static final int BLOCK_HELP_ID = 4;
    public static final String BLOCK_HELP_HOVER_TEXT = "Block used to trace outline";
    private static final int[] BLOCK_HELP_POS = {135, 40};
    private GuiButton blockHelpButton;

    // Allow API Checkbox
    public static final int API_CHECK_BOX_ID = 5;
    public static final int[] API_CHECK_BOX_POS = {-95,65};
    public static final String API_CHECK_BOX_TEXT = "Get ground level from the internet";
    private GuiCheckBox apiCheckBox;

    // Build! Button
    public static final int BUILD_BUTTON_ID = 6;
    public static final String BUILD_BUTTON_TEXT = "Build";
    public static final int[] BUILD_BUTTON_POS = {-50,85};
    public static final int[] BUILD_BUTTON_SIZE = {100,20};
    private GuiButton buildButton;

    public GuiDrawKML(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        super.initGui();

        int guiMiddleX = width/2;
        int guiStartY = height/6;

        String blockName = "gold"; // Default
        { // Get items in hand
            Map.Entry mainHand = BlockNameConverter.getBlockNameAndMeta(mc.player.getHeldItemMainhand());
            Map.Entry offHand = BlockNameConverter.getBlockNameAndMeta(mc.player.getHeldItemOffhand());

            String nullName = "air";

            if (!mainHand.getKey().toString().toLowerCase().equals(nullName)) {
                blockName = BlockNameConverter.getBlockCombinedName(mainHand);
            } else if (!offHand.getKey().toString().toLowerCase().equals(nullName)) {
                blockName = BlockNameConverter.getBlockCombinedName(offHand);
            }
        }

        // File Name Text Box
        fileNameTextBox = new GuiTextField(FILE_NAME_TEXT_BOX_ID, mc.fontRenderer, guiMiddleX+FILE_NAME_TEXT_BOX_POS[0],guiStartY+FILE_NAME_TEXT_BOX_POS[1], FILE_NAME_TEXT_BOX_SIZE[0], FILE_NAME_TEXT_BOX_SIZE[1]);
        fileNameTextBox.setText(FILE_NAME_TEXT_BOX_TEXT);
        fileNameTextBox.setMaxStringLength(260);

        // Select File Button
        selectFileButton = new IconButton(IconButton.ICON.FILE, SELECT_FILE_BUTTON_ID, guiMiddleX+ SELECT_FILE_BUTTON_POS[0],guiStartY+ SELECT_FILE_BUTTON_POS[1]);
        addButton(selectFileButton);

        // Block Preview
        blockPreview = new BlockPreview(guiMiddleX+BLOCK_PREVIEW_POS[0], guiStartY+BLOCK_PREVIEW_POS[1],50,50, blockName);

        // Block Name Text Box
        blockNameTextBox = new GuiTextField(BLOCK_NAME_TEXT_BOX_ID, mc.fontRenderer, guiMiddleX+BLOCK_NAME_TEXT_BOX_POS[0], guiStartY+BLOCK_NAME_TEXT_BOX_POS[1], BLOCK_NAME_TEXT_BOX_SIZE[0], BLOCK_NAME_TEXT_BOX_SIZE[1]);

        blockNameTextBox.setText(blockName);
        blockNameTextBox.setMaxStringLength(64);

        // Block Help
        blockHelpButton = new IconButton(IconButton.ICON.HELP, BLOCK_HELP_ID, guiMiddleX+BLOCK_HELP_POS[0],guiStartY+BLOCK_HELP_POS[1]);
        addButton(blockHelpButton);

        // API Check Box
        apiCheckBox = new GuiCheckBox(API_CHECK_BOX_ID, guiMiddleX+API_CHECK_BOX_POS[0], guiStartY+API_CHECK_BOX_POS[1], API_CHECK_BOX_TEXT, true);
        AltitudeProcessor.defaultProcessor.enabled = true;
        addButton(apiCheckBox);

        // Build Button
        buildButton = new GuiButton(BUILD_BUTTON_ID, guiMiddleX+BUILD_BUTTON_POS[0],guiStartY+BUILD_BUTTON_POS[1], BUILD_BUTTON_SIZE[0], BUILD_BUTTON_SIZE[1], BUILD_BUTTON_TEXT);
        addButton(buildButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int guiMiddleX = width/2;
        int guiStartY = height/6;

        // Draw File location Text Box
        fileNameTextBox.drawTextBox();

        // Draw can't find file error
        drawString(mc.fontRenderer, errorFileText, guiMiddleX+ ERROR_FILE_POS[0],guiStartY+ ERROR_FILE_POS[1], ERROR_FILE_COLOR);

        // Render Selected Block
        blockPreview.drawPreview(mc);

        // Block Name Text box
        blockNameTextBox.drawTextBox();

        // Buttons
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Select File Tooltip
        if (mouseX >= guiMiddleX+ SELECT_FILE_BUTTON_POS[0] && mouseX < guiMiddleX+ SELECT_FILE_BUTTON_POS[0]+20 && mouseY >= guiStartY+ SELECT_FILE_BUTTON_POS[1] && mouseY < guiStartY+ SELECT_FILE_BUTTON_POS[1]+20) {
            drawHoveringText(SELECT_FILE_HOVER_TEXT, mouseX, mouseY);
        }

        // Block Help Tooltip
        if (mouseX >= guiMiddleX+ BLOCK_HELP_POS[0] && mouseX < guiMiddleX+ BLOCK_HELP_POS[0]+20 && mouseY >= guiStartY+ BLOCK_HELP_POS[1] && mouseY < guiStartY+ BLOCK_HELP_POS[1]+20) {
            drawHoveringText(BLOCK_HELP_HOVER_TEXT, mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        // File Name Text Box
        fileNameTextBox.textboxKeyTyped(typedChar, keyCode);

        if (fileNameTextBox.getVisible()) { // Hide error if you are changing it
            errorFileText = "";
        }

        // Block Name Text Box
        blockNameTextBox.textboxKeyTyped(typedChar, keyCode);

        if (blockNameTextBox.isFocused()) {
            blockPreview.setBlockName(blockNameTextBox.getText());
        }

        // Not Selecting Box
        if (!fileNameTextBox.isFocused() && !blockNameTextBox.isFocused()) { // To close, text boxes can't be selected
            if (keyCode == KeyInit.openKMLMenuKeybind.getKeyCode()) { // Close
                close();
            }

            if (keyCode == Keyboard.KEY_RETURN) { // Build
                build();
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        // File location Text Box
        fileNameTextBox.updateCursorCounter();

        // Block Name Text Box
        blockNameTextBox.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        // File location Text Box
        fileNameTextBox.mouseClicked(mouseX, mouseY, mouseButton);

        // Block Name Text Box
        blockNameTextBox.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        switch (button.id) {
            case SELECT_FILE_BUTTON_ID:
                // Make it not look like shit
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                FileNameExtensionFilter filter = new FileNameExtensionFilter("kmz or kml file","kmz","kml");

                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                fc.setFileFilter(filter); // Set filter to kmz/kml

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // Set textbox w/ file name to file name
                    fileNameTextBox.setText(fc.getSelectedFile().getAbsolutePath());

                }
                break;
            case API_CHECK_BOX_ID:
                AltitudeProcessor.defaultProcessor.enabled = apiCheckBox.isChecked();
                break;
            case BUILD_BUTTON_ID:
                build();
                break;
        }
    }

    public void build() {
        Document[] documents;
        try {
            documents = KMLParser.parse(new File(fileNameTextBox.getText()));
        } catch (IOException e) {
            errorFileText = ERROR_FINDING_FILE_TEXT;
            return;
        } catch (ParserConfigurationException e) {
            errorFileText = ERROR_READING_FILE_TEXT;
            return;
        } catch (SAXException e) {
            errorFileText = ERROR_READING_FILE_TEXT;
            return;
        }

        close();

        // Draw!
        new Thread(() -> {
            PlacemarkFactory.drawPlacemarks(documents, blockNameTextBox.getText());
        }).start();
    }

    public void close() {
        mc.displayGuiScreen(parentScreen);
    }
}
