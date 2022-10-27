package com.strandhvilliam.program;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.Scanner;

public class Controller {

    private final View view;

    public Controller(View view) {
        this.view = view;
    }

    public void saveButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnVal = fileChooser.showSaveDialog(view);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                saveFile(fileChooser.getSelectedFile());
                view.isModified = false;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void openButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnVal = fileChooser.showOpenDialog(view);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                if (view.isModified) {
                    int result = JOptionPane.showConfirmDialog(view, "Do you want to save changes?", "Save changes", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        saveButtonClicked();
                    }
                }
                openFile(fileChooser.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void newButtonClicked() {
        if (view.isModified) {
            int result = JOptionPane.showConfirmDialog(
                    view, "You have not saved your file. Do you want to save it?",
                    "Save file?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveButtonClicked();
                view.textPane.setText("");
            } else if (result == JOptionPane.NO_OPTION) {
                view.textPane.setText("");
                view.isModified = false;
            }
        } else{
            view.textPane.setText("");
        }
    }

    public void printButtonClicked() {
        System.out.println("Print button clicked");
        try {
            view.textPane.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    public void undoButtonClicked() {
        if (view.undoManager.canUndo()) {
            view.undoManager.undo();
        }
    }

    public void redoButtonClicked() {
        if (view.undoManager.canRedo()) {
            view.undoManager.redo();
        }
    }


    private void saveFile(File selectedFile) throws IOException {
        if (!selectedFile.toString().endsWith(".txt")) {
            selectedFile = new File(selectedFile.toString() + ".txt");
        }
        if (!selectedFile.exists()) {
            selectedFile.createNewFile();
        }

        try (BufferedWriter bf = new BufferedWriter(new FileWriter(selectedFile))) {
            String text = view.textPane.getText();
            bf.write(text);
            view.isModified = false;
        }
    }

    public void openFile(File selectedFile) throws IOException {
        try (Scanner scanner = new Scanner(selectedFile)) {
            view.textPane.setText("");
            while(scanner.hasNextLine()) {
                view.textPane.getDocument().insertString(view.textPane.getDocument().getLength(), scanner.nextLine() + "\n", null);
            }
            view.isModified = false;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void fontComboBoxClicked() {
        String fontName = view.fontComboBox.getSelectedItem().toString();
        int fontSize = Integer.parseInt(view.fontSizeComboBox.getSelectedItem().toString());
        SimpleAttributeSet font = new SimpleAttributeSet();
        StyleConstants.setFontFamily(font, fontName);
        StyleConstants.setFontSize(font, fontSize);
        view.textPane.setCharacterAttributes(font, false);
        view.textPane.requestFocus();

    }

    //TODO : fix caret size and initialfontsize
    public void fontSizeComboBoxClicked() {
        SimpleAttributeSet size = new SimpleAttributeSet();
        int fontSize = Integer.parseInt(view.fontSizeComboBox.getSelectedItem().toString());
        StyleConstants.setFontSize(size, fontSize);
        view.textPane.setCharacterAttributes(size, false);
        view.textPane.requestFocus();
    }

    public void boldButtonToggled() {
        SimpleAttributeSet bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, view.boldButton.isSelected());
        view.textPane.setCharacterAttributes(bold, false);
        view.textPane.requestFocus();
    }

    public void italicButtonToggled() {
        SimpleAttributeSet italic = new SimpleAttributeSet();
        StyleConstants.setItalic(italic, view.italicButton.isSelected());
        view.textPane.setCharacterAttributes(italic, false);
        view.textPane.requestFocus();
    }

    public void underlineButtonToggled() {
        SimpleAttributeSet underline = new SimpleAttributeSet();
        StyleConstants.setUnderline(underline, view.underlineButton.isSelected());
        view.textPane.setCharacterAttributes(underline, false);
        view.textPane.requestFocus();
    }

    public void strikeButtonToggled() {
        SimpleAttributeSet strike = new SimpleAttributeSet();
        StyleConstants.setStrikeThrough(strike, view.strikeButton.isSelected());
        view.textPane.setCharacterAttributes(strike, false);
        view.textPane.requestFocus();
    }

    public void leftAlignButtonClicked() {
        SimpleAttributeSet leftAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(leftAlign, StyleConstants.ALIGN_LEFT);
        view.textPane.setParagraphAttributes(leftAlign, false);
        view.textPane.requestFocus();
    }

    public void centerAlignButtonClicked() {
        SimpleAttributeSet centerAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAlign, StyleConstants.ALIGN_CENTER);
        view.textPane.setParagraphAttributes(centerAlign, false);
        view.textPane.requestFocus();
    }

    public void rightAlignButtonClicked() {
        SimpleAttributeSet rightAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
        view.textPane.setParagraphAttributes(rightAlign, false);
        view.textPane.requestFocus();
    }

    public void textColorButtonClicked() {
        Color color = JColorChooser.showDialog(view, "Choose a color", Color.BLACK);
        SimpleAttributeSet textColor = new SimpleAttributeSet();
        StyleConstants.setForeground(textColor, color);
        view.textPane.setCharacterAttributes(textColor, false);
        view.textPane.requestFocus();
        view.colorStatusPanel.setBackground(color);
    }
}
