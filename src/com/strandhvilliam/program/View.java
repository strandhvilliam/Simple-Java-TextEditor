package com.strandhvilliam.program;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class View extends JFrame {

    private final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    protected JComboBox<String> fontComboBox = new JComboBox<>(fonts);
    private final Integer[] fontSizes = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
    protected JComboBox<Integer> fontSizeComboBox = new JComboBox<>(fontSizes);
    protected JButton saveButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_save_24px.png"));
    protected JButton openButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_folder_24px.png"));
    protected JButton newButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_new_document_24px.png"));
    protected JButton printButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_print_24px.png"));
    protected JButton redoButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_redo_24px.png"));
    protected JButton undoButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_undo_24px.png"));
    protected JToggleButton boldButton = new JToggleButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_bold_24px_1.png"));
    protected JToggleButton italicButton = new JToggleButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_italic_24px.png"));
    protected JToggleButton underlineButton = new JToggleButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_underline_24px.png"));
    protected JToggleButton strikeButton = new JToggleButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_strikethrough_24px.png"));
    protected JButton textColorButton = new JButton("             Choose Color             ");
    protected JButton leftAlignButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_align_left_24px.png"));
    protected JButton centerAlignButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_align_center_24px.png"));
    protected JButton rightAlignButton = new JButton("", new ImageIcon("src/com/strandhvilliam/program/icons/icons8_align_right_24px.png"));
    protected JPanel colorStatusPanel = new JPanel();
    protected JTextPane textPane = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    private final Controller controller = new Controller(this);
    protected UndoManager undoManager = new UndoManager();

    protected boolean isModified = false;

    public View() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        saveButton.addActionListener(e -> controller.saveButtonClicked());
        saveButton.setToolTipText("Save");
        JPanel settingsPanel = new JPanel();
        settingsPanel.add(saveButton);

        openButton.addActionListener(e -> controller.openButtonClicked());
        openButton.setToolTipText("Open");
        settingsPanel.add(openButton);

        newButton.addActionListener(e -> controller.newButtonClicked());
        newButton.setToolTipText("New File");
        settingsPanel.add(newButton);

        printButton.addActionListener(e -> controller.printButtonClicked());
        printButton.setToolTipText("Print");
        settingsPanel.add(printButton);

        undoButton.addActionListener(e -> controller.undoButtonClicked());
        undoButton.setToolTipText("Undo");
        settingsPanel.add(undoButton);

        redoButton.addActionListener(e -> controller.redoButtonClicked());
        redoButton.setToolTipText("Redo");
        settingsPanel.add(redoButton);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(settingsPanel, BorderLayout.WEST);

        add(northPanel, BorderLayout.NORTH);

        // -------STYLE PANEL---------
        JPanel stylePanel = new JPanel();
        stylePanel.setLayout(new GridBagLayout());

        JPanel fontPanel = new JPanel();
        fontPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 8;
        c.insets = new Insets(5, 0, 0, 0);
        c.gridwidth = GridBagConstraints.REMAINDER;
        fontComboBox.addActionListener(e -> controller.fontComboBoxClicked());
        fontPanel.add(fontComboBox, c);

        c.gridwidth = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.LINE_START;
        fontSizeComboBox.setSelectedIndex(4);
        fontSizeComboBox.addActionListener(e -> controller.fontSizeComboBoxClicked());
        fontPanel.add(fontSizeComboBox, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 0, 5, 0);
        stylePanel.add(fontPanel, c);

        JPanel propertyPanel = new JPanel();
        propertyPanel.setLayout(new GridLayout(2, 2, 8, 8));
        boldButton.addItemListener(e -> controller.boldButtonToggled());
        propertyPanel.add(boldButton);
        italicButton.addItemListener(e -> controller.italicButtonToggled());
        propertyPanel.add(italicButton);
        underlineButton.addItemListener(e -> controller.underlineButtonToggled());
        propertyPanel.add(underlineButton);
        strikeButton.addItemListener(e -> controller.strikeButtonToggled());
        propertyPanel.add(strikeButton);
        c.insets = new Insets(10, 0, 10, 0);
        stylePanel.add(propertyPanel, c);

        JPanel alignPanel = new JPanel();
        alignPanel.setLayout(new GridLayout(1, 3, 8, 8));
        leftAlignButton.addActionListener(e -> controller.leftAlignButtonClicked());
        alignPanel.add(leftAlignButton);
        centerAlignButton.addActionListener(e -> controller.centerAlignButtonClicked());
        alignPanel.add(centerAlignButton);
        rightAlignButton.addActionListener(e -> controller.rightAlignButtonClicked());
        alignPanel.add(rightAlignButton);
        c.insets = new Insets(10, 0, 10, 0);
        stylePanel.add(alignPanel, c);

        JPanel colorChooserPanel = new JPanel();
        colorChooserPanel.setLayout(new GridBagLayout());
        textColorButton.addActionListener(e -> controller.textColorButtonClicked());
        colorStatusPanel.setBackground(Color.BLACK);
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        colorChooserPanel.add(textColorButton, c);

        c.ipady = 40;
        c.insets = new Insets(5, 0, 0, 0);
        colorChooserPanel.add(colorStatusPanel, c);
        stylePanel.add(colorChooserPanel);

        JPanel eastPanel = new JPanel();
        eastPanel.add(stylePanel);
        add(eastPanel, BorderLayout.EAST);

        // -------TEXT AREA---------
        textPane.setFont(new Font("Arial", Font.PLAIN, 12));
        textPane.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        textPane.getDocument().addUndoableEditListener(e -> {
            undoManager.addEdit(e.getEdit());
        });
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isModified = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isModified = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isModified = true;
            }
        });
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()) {
                    controller.undoButtonClicked();
                } else if (e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown()) {
                    controller.redoButtonClicked();
                } else if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown() && e.isShiftDown()) {
                    controller.redoButtonClicked();
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
