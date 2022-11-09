package com.codingpupper3033.codebtekml.gui.screens;

import com.codingpupper3033.codebtekml.KeyInit;
import com.codingpupper3033.codebtekml.gui.buttons.IconButton;
import com.codingpupper3033.codebtekml.gui.widgets.BlockPreview;
import com.codingpupper3033.codebtekml.helpers.block.BlockNameConverter;
import com.codingpupper3033.codebtekml.helpers.kmlfile.KMLParser;
import com.codingpupper3033.codebtekml.helpers.map.Placemark;
import com.codingpupper3033.codebtekml.helpers.map.altitude.GoundLevelProcessor;
import com.codingpupper3033.codebtekml.helpers.map.placemark.PlacemarkFactory;
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
import java.util.Map;

/**
 * Gui Screen to bui;d the KMZ/KML file.
 * @author Joshua Miller
 */
public class GuiDrawKML extends GuiScreen {
    // Parent
    private GuiScreen parentScreen;

    // File Name Text Box
    public static final int FILE_NAME_TEXT_BOX_ID = 1;
    public static final String DEFAULT_FILE_NAME_TEXT_BOX_TEXT = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath(); // Default path is Desktop cause why not
    public static final int MAX_FILE_PATH_CHARACTERS = 260; // windows limit according to the first bing result, TODO maybe change to programmatic, but like very insignificant as you just shouldn't need this many characters bro.
    private static final int[] FILE_NAME_TEXT_BOX_POS = {-155,0};
    public static final int[] FILE_NAME_TEXT_BOX_SIZE = {280,20};
    private GuiTextField fileNameTextBox;

    // Select File Button
    public static final int SELECT_FILE_BUTTON_ID = 2;
    public static final String SELECT_FILE_HOVER_TEXT = "Select File";
    private static final int[] SELECT_FILE_BUTTON_POS = {135, 0};
    private GuiButton selectFileButton;

    // File Error String
    public static final String ERROR_FINDING_FILE_TEXT = "Unable to find file location. Please try again.";
    public static final String ERROR_READING_FILE_TEXT = "Error reading file. Please try another.";
    public static final int ERROR_FILE_COLOR = 0xAA0000; // Same as Minecraft dark red, but TODO get dark red programmatically?

    private static final int[] ERROR_FILE_POS = {-145, 25};
    private String errorFileText = "";

    // Block Preview
    private static final int[] BLOCK_PREVIEW_POS = {-154, 36};
    private BlockPreview blockPreview;

    // Block Name Text Box
    public static final int BLOCK_NAME_TEXT_BOX_ID = 3;
    public static final int[] BLOCK_NAME_TEXT_BOX_POS = {-95,40};
    public static final int[] BLOCK_NAME_TEXT_BOX_SIZE = {220,20};
    public final String defaultBlockName = "gold_block";
    public final String nullBlockName = "air";
    public boolean getBlockNameFromInventory = true; // When opening the GUI should it autopopulate with the held item?
    private GuiTextField blockNameTextBox;

    // Block Name Help Icon
    public static final int BLOCK_HELP_ID = 4;
    public static final String BLOCK_HELP_HOVER_TEXT = "Block used to trace outline";
    private static final int[] BLOCK_HELP_POS = {135, 40};
    private GuiButton blockHelpButton;

    // Allow API Checkbox
    public static final int API_CHECK_BOX_ID = 5;
    public static final int[] API_CHECK_BOX_POS = {-95,65};
    public static final String API_CHECK_BOX_TEXT = "Get ground level from the internet";
    private GuiCheckBox apiCheckBox;

    // Build Button
    public static final int BUILD_BUTTON_ID = 6;
    public static final String BUILD_BUTTON_TEXT = "Build";
    public static final int[] BUILD_BUTTON_POS = {-50,85};
    public static final int[] BUILD_BUTTON_SIZE = {100,20};
    private GuiButton buildButton;


    /**
     * Instantiates a new Gui for drawing kml/kmz files.
     *
     * @param parentScreen the parent screen
     */
    public GuiDrawKML(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        super.initGui();

        // Helper variables for adding gui elements
        int guiMiddleX = width/2;
        int guiStartY = height/6;

        String blockName = defaultBlockName; // Default block name in case nothing in hands
        if (getBlockNameFromInventory) { // Tries setting the block to held block
            Map.Entry mainHand = BlockNameConverter.getBlockNameAndMeta(mc.player.getHeldItemMainhand());
            Map.Entry offHand = BlockNameConverter.getBlockNameAndMeta(mc.player.getHeldItemOffhand());

            if (!mainHand.getKey().toString().equalsIgnoreCase(nullBlockName)) { // check if block in main hand
                blockName = BlockNameConverter.getBlockCombinedName(mainHand);
            } else if (!offHand.getKey().toString().equalsIgnoreCase(nullBlockName)) { // How 'bout offhand?
                blockName = BlockNameConverter.getBlockCombinedName(offHand);
            } //shit, no srry dude
        }

        // File Name Text Box
        fileNameTextBox = new GuiTextField(FILE_NAME_TEXT_BOX_ID, mc.fontRenderer, guiMiddleX+FILE_NAME_TEXT_BOX_POS[0],guiStartY+FILE_NAME_TEXT_BOX_POS[1], FILE_NAME_TEXT_BOX_SIZE[0], FILE_NAME_TEXT_BOX_SIZE[1]);
        fileNameTextBox.setText(DEFAULT_FILE_NAME_TEXT_BOX_TEXT);
        fileNameTextBox.setMaxStringLength(MAX_FILE_PATH_CHARACTERS);

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
        GoundLevelProcessor.defaultProcessor.enabled = true;
        addButton(apiCheckBox);

        // Build Button
        buildButton = new GuiButton(BUILD_BUTTON_ID, guiMiddleX+BUILD_BUTTON_POS[0],guiStartY+BUILD_BUTTON_POS[1], BUILD_BUTTON_SIZE[0], BUILD_BUTTON_SIZE[1], BUILD_BUTTON_TEXT);
        addButton(buildButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground(); // It greyish :)

        // Helper variables for adding gui elements
        int guiMiddleX = width/2;
        int guiStartY = height/6;

        // Draw File Location Text Box
        fileNameTextBox.drawTextBox();

        // Draw File Error
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

        if (fileNameTextBox.getVisible()) { // Hide error if you are changing the file. Basically be less annoying
            errorFileText = "";
        }

        // Block Name Text Box
        blockNameTextBox.textboxKeyTyped(typedChar, keyCode);
        if (blockNameTextBox.isFocused()) { // Update Block Preview to match the text box
            blockPreview.setBlockName(blockNameTextBox.getText());
        }

        // ------
        if (!fileNameTextBox.isFocused() && !blockNameTextBox.isFocused()) { // Not selecting any box
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

        switch (button.id) { // Who did it?
            case SELECT_FILE_BUTTON_ID: // Selecting a file time
                // Make open file dialog not look like as shit
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                FileNameExtensionFilter filter = new FileNameExtensionFilter("kmz or kml file","kmz","kml"); // Files generally supported (but not enforced)

                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                fc.setFileFilter(filter); // Set filter to kmz/kml

                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // Set file name textbox to file name from dialog
                    fileNameTextBox.setText(fc.getSelectedFile().getAbsolutePath());
                }
                break;
            case API_CHECK_BOX_ID: // Api setting
                GoundLevelProcessor.defaultProcessor.enabled = apiCheckBox.isChecked(); // Can my boi process altitudes (using the internet)
                break;
            case BUILD_BUTTON_ID: // Do ya thing!
                build();
                break;
        }
    }

    /**
     * Using the values from the text boxes, try parsing and building the file.
     */
    public void build() {
        new Thread(() -> { // New thread as to allow for loading screen TODO Make loading screen
            Document[] documents;

            try {
                documents = KMLParser.parse(new File(fileNameTextBox.getText())); // TODO Add loading/parsing text (maybe below build button) to the screen to signify it is doing stuff even if it looks frozen
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

            Placemark[] placemarks = PlacemarkFactory.getPlacemarks(documents);

            // Process Altitudes
            PlacemarkFactory.proccessPlacemarks(placemarks);

            close(); // Should have successfully processed the file, gui is not needed now
            PlacemarkFactory.drawPlacemarks(placemarks, blockNameTextBox.getText()); // Draw!
        }).start();
    }

    // Bye bye!
    public void close() {
        mc.displayGuiScreen(parentScreen);
    }
}
