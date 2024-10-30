package com.github.griffty;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

import static java.awt.BorderLayout.PAGE_END;

public class Settings extends JFrame {
    public static final Scanner sc = new Scanner(System.in);
    public static final String Folder = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+ "\\ConsoleGraphic\\";
    public static int Width = 120;
    public static int Height = 28;
    public static int Length = 60;
    public static int Start_x = 0;
    public static int Start_y = 0;
    public static int Start_z = 0;
    public static int Rotation_x = 0;
    public static int Rotation_y = 0;
    public static int Rotation_z = 0;
    public static int R = 10;
    public static int ratio = 2;
    public static ObjectHandler.finishedShapes shape = ObjectHandler.finishedShapes.Torus;

    private enum PanelsNames{
        Width,
        Height,
        Length,
        StartX,
        StartY,
        StartZ,
        RotationX,
        RotationY,
        RotationZ,
        Radius,
        Ratio,
        Shape,
    }
    Settings(){
        updateData();
        initGui();
        setTitle("Settings");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private final JPanel[] panels = new JPanel[12];
    private static final JSpinner[] spinners = new JSpinner[10];
    private static final JComboBox<String>[] comboBoxes = new JComboBox[2];
    private void initGui(){
        //Settings Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(600, 450));
        mainPanel.setLayout(new GridLayout(4, 3, 20, 10));
        add(mainPanel);
        for (int i = 0; i < panels.length; i ++){
            panels[i] = new JPanel();
            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
            panels[i].add(Box.createVerticalGlue());
            panels[i].add(Box.createVerticalGlue());
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
            panels[i].add(newPanel);
            panels[i].add(Box.createVerticalGlue());
            panels[i].add(Box.createVerticalGlue());
            mainPanel.add(panels[i]);
            panels[i] = newPanel;
        }
        setUpPanel(PanelsNames.Width, "Width:", Width, 1, 1000, 0);
        setUpPanel(PanelsNames.Height, "Height:", Height, 1, 1000, 1);
        setUpPanel(PanelsNames.Length, "Depth:", Length, 1, 1000, 2);
        setUpPanel(PanelsNames.StartX, "Start X:", Start_x, -1000, 1000, 3);
        setUpPanel(PanelsNames.StartY, "Start Y:", Start_y, -1000, 1000, 4);
        setUpPanel(PanelsNames.StartZ, "Start Z:", Start_z, -1000, 1000, 5);
        setUpPanel(PanelsNames.RotationX, "Rotation X:", Rotation_x, -180, 180, 6);
        setUpPanel(PanelsNames.RotationY, "Rotation Y:", Rotation_y, -180, 180, 7);
        setUpPanel(PanelsNames.RotationZ, "Rotation Z:", Rotation_z, -180, 180, 8);
        setUpPanel(PanelsNames.Radius, "Radius:", R, 1, 100, 9);
        setUpPanel(PanelsNames.Ratio, "Ratio:", new String[]{"18f/9f"},0);
        setUpPanel(PanelsNames.Shape, "Shape:", new String[]{"Rectangle", "Circle", "Annulus", "Sphere", "Torus", "Other", "Multi"},1);
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        add(bottomPanel, PAGE_END);
        bottomPanel.add(Box.createHorizontalGlue());
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            updateData();
            resetData();
        });
        bottomPanel.add(resetButton);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveData());
        bottomPanel.add(saveButton);
        JButton saveAndStartButton = new JButton("Save And Start");
        saveAndStartButton.addActionListener(e -> {
            saveData();
            try {
                Process p = Runtime.getRuntime().exec("cmd /c start wt java Main");
                p.waitFor();
            }catch (Exception exception){
                System.out.print("Cannot start CMD");
            }
        });
        bottomPanel.add(saveAndStartButton);
        bottomPanel.add(Box.createHorizontalGlue());
    }
    private void setUpPanel(PanelsNames panelName, String text, int val, int min,int max, int spinnerCount){
        JPanel panel = panels[panelName.ordinal()];
        JLabel label = new JLabel(text);
        panel.add(label);
        SpinnerModel spinnerModel = new SpinnerNumberModel(val, min, max, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(new Dimension(50, 10));
        spinners[spinnerCount] = spinner;
        panel.add(spinner);
    }
    private void setUpPanel(PanelsNames panelName, String text, String[] answers, int boxCount){
        JPanel panel = panels[panelName.ordinal()];
        JLabel label = new JLabel(text);
        panel.add(label);
        JComboBox<String> comboBox = new JComboBox<>(answers);
        comboBoxes[boxCount] = comboBox;
        if (boxCount == 0) {
            comboBox.setSelectedItem(switch ((int) ratio) {
                default -> "18f/9f";
            });
        }
        if (boxCount == 1) {
            comboBox.setSelectedItem(switch (shape) {
                case Square -> "Rectangle";
                case Circle -> "Circle";
                case Annulus -> "Annulus";
                case Sphere -> "Sphere";
                case Torus -> "Torus";
                case Other -> "Other";
                case Multi -> "Multi";
            });
        }
        panel.add(comboBox);
    }
    public static void updateData(){
        File folder = new File(Folder);
        if (folder.mkdir()){System.out.println("Folder created");}
        File file = new File(Folder + "settings.txt");
        if (file.exists()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                String s = in.readLine();
                Width = Integer.parseInt(s);
                s = in.readLine();
                Height = Integer.parseInt(s);
                s = in.readLine();
                Length = Integer.parseInt(s);
                s = in.readLine();
                Start_x = Integer.parseInt(s);
                s = in.readLine();
                Start_y = Integer.parseInt(s);
                s = in.readLine();
                Start_z = Integer.parseInt(s);
                s = in.readLine();
                Rotation_x = Integer.parseInt(s);
                s = in.readLine();
                Rotation_y = Integer.parseInt(s);
                s = in.readLine();
                Rotation_z = Integer.parseInt(s);
                s = in.readLine();
                R = Integer.parseInt(s);
                s = in.readLine();
                if (s.equals("18f/9f")) {
                    ratio = 2;
                } else {
                    System.out.print("Cannot update ration, set 18/9 as default");
                }
                s = in.readLine();
                switch (s) {
                    case "Rectangle" -> shape = ObjectHandler.finishedShapes.Square;
                    case "Circle" -> shape = ObjectHandler.finishedShapes.Circle;
                    case "Annulus" -> shape = ObjectHandler.finishedShapes.Annulus;
                    case "Sphere" -> shape = ObjectHandler.finishedShapes.Sphere;
                    case "Torus" -> shape = ObjectHandler.finishedShapes.Torus;
                    case "Other" -> shape = ObjectHandler.finishedShapes.Other;
                    case "Multi" -> shape = ObjectHandler.finishedShapes.Multi;
                    default -> System.out.print("Error, cannot use this shape, set Torus as default");
                }
                moveData();
            } catch (Exception e) {
                System.out.print("Cannot update settings");
                sc.nextLine();
            }
        }
    }
    private static void moveData(){
        Main.consoleWidth = Width;
        Main.consoleHeight = Height;
        Main.consoleLength = Length;
        TransformationHandler.consoleWidth = Width;
        TransformationHandler.consoleHeight = Height;
        TransformationHandler.consoleLength = Length;
        ObjectHandler.consoleWidth = Width;
        ObjectHandler.consoleHeight = Height;
        ObjectHandler.consoleLength = Length;

        ObjectHandler.initialOffset = new Vector3Int(Start_x, Start_y, Start_z);
        ObjectHandler.initialRotation = new Vector3Int(Rotation_x, Rotation_y, Rotation_z);

        ObjectHandler.R = R;

        ObjectHandler.ratio = ratio;

        Main.shape = shape;
    }
    public static void saveData(){
        File folder = new File(Folder);
        if (folder.mkdir()){System.out.println("Folder created");}
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(Folder + "settings.txt"));
            for (JSpinner spinner : spinners) {
                Object value = spinner.getValue();
                out.write(value.toString());
                out.newLine();
            }
            for (JComboBox<String> comboBox : comboBoxes) {
                out.write(comboBox.getItemAt(comboBox.getSelectedIndex()));
                out.newLine();
            }
            out.write("version 0.1");
            out.close();
        }catch (Exception e){
            System.out.print("Cannot save settings");
        }
    }
    private static void resetData(){
        spinners[0].setValue(Width);
        spinners[1].setValue(Height);
        spinners[2].setValue(Length);
        spinners[3].setValue(Start_x);
        spinners[4].setValue(Start_y);
        spinners[5].setValue(Start_z);
        spinners[6].setValue(Rotation_x);
        spinners[7].setValue(Rotation_y);
        spinners[8].setValue(Rotation_z);
        spinners[9].setValue(R);
        comboBoxes[0].setSelectedItem(switch ((int) ratio){
            default -> "18f/9f";
        });
        comboBoxes[1].setSelectedItem(switch (shape){
            case Square -> "Rectangle";
            case Circle -> "Circle";
            case Annulus -> "Annulus";
            case Sphere -> "Sphere";
            case Torus -> "Torus";
            case Other -> "Other";
            case Multi -> "Multi";
        });
    }
    public static void main(String[] args) {
        new Settings();
    }
}
