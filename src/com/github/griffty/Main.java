package com.github.griffty;

import java.util.ArrayList;
import java.util.Date;

public class Main { // зробити кастомний шейп в меню вибору і до нього привязати меню створення точок
    public static float consoleWidth = 120;
    public static float consoleHeight = 28;
    public static float consoleLength = 60;
    public static double FPS = 0;
    public static ObjectHandler.finishedShapes shape = ObjectHandler.finishedShapes.Torus;
    private static final ArrayList<Object3D> allObjects = new ArrayList<>();
    private static final boolean debug = false;
    private static final boolean timer = true;
    Main(){
        updateSettings();
        MakeShape();
        while (true) {
            clear();
            draw();
            move();
            refresh();
        }
    }
    private void updateSettings(){
        TimerGriffty.start();
        Settings.updateData();
        TransformationHandler.reload();
        ObjectHandler.reload();
        TimerGriffty.updateSettingsTime = TimerGriffty.end();
    }
    private void MakeShape(){
        TimerGriffty.start();
        if (shape == ObjectHandler.finishedShapes.Other) {
            allObjects.add(ObjectHandler.createNewObject(ObjectHandler.finishedFigures.get(ObjectHandler.FinishedFiguresNames.Cube.ordinal())));
            allObjects.get(0).rotation.y = 15;
        }else if (shape == ObjectHandler.finishedShapes.Multi){
        /*
        * Add all Objects, and also their initial parameters
        */

            allObjects.add(ObjectHandler.createNewObject(ObjectHandler.finishedFigures.get(ObjectHandler.FinishedFiguresNames.Cube.ordinal())));
            allObjects.get(0).position.x = 0;
            allObjects.get(0).setOriginTo(Object3D.OriginPositions.OriginShiftedByPos);


            allObjects.add(ObjectHandler.createNewObject(ObjectHandler.finishedFigures.get(ObjectHandler.FinishedFiguresNames.Pyramid.ordinal())));
            allObjects.get(1).position.y = -ObjectHandler.R * 2;

            allObjects.add(ObjectHandler.createNewObject(ObjectHandler.finishedFigures.get(ObjectHandler.FinishedFiguresNames.Pyramid.ordinal())));
            allObjects.get(2).position.y = ObjectHandler.R * 2;

        }
        else {
            allObjects.add(ObjectHandler.createNewObject(shape));
            allObjects.get(0).rotation.x = 20;
        }
        TimerGriffty.MakeShapeTime = TimerGriffty.end();
    }
    private static void clear() {
        TimerGriffty.start();
        try {
            System.out.print("\u001b[H");
            System.out.flush();
        } catch (final Exception e) {
            System.out.print("clearConsole() error");
            Settings.sc.next();
        }
        TimerGriffty.clearTime = TimerGriffty.end();
    }
    private static void move(){
        TimerGriffty.start();
        /*
         * Add and movements or origin transformations
         */
        for (Object3D obj : allObjects){

                obj.rotateOnY(8);
        }

        TimerGriffty.moveTime = TimerGriffty.end();
    }
    static double starTime = new Date().getTime();
    private static void draw(){
        TimerGriffty.start();
        if (debug){
            System.out.print("x:" + allObjects.get(0).position.x+" y:" + allObjects.get(0).position.y
                    +" z:" + allObjects.get(0).position.z+ " Rx: " + allObjects.get(0).rotation.x
                    + " Ry: " + allObjects.get(0).rotation.y + " Rz: " + allObjects.get(0).rotation.z
                    + " FPS:" + FPS +" Orx: " + allObjects.get(0).origin.x + " Ory: "
                    + allObjects.get(0).origin.y + " Orz: " + allObjects.get(0).origin.z);
        }else if (timer){
            System.out.print("make " + TimerGriffty.MakeShapeTime + "update " + TimerGriffty.updateSettingsTime + "clear " + TimerGriffty.clearTime + "move " + TimerGriffty.moveTime + "draw " + TimerGriffty.drawTime + "refresh " + TimerGriffty.refreshTime);
        }
        starTime = new Date().getTime();
        char[][] pixels = TransformationHandler.transformTo2D(allObjects);
        for (int y = 0; y < consoleHeight; y++) {
            for (int x = 0; x < consoleWidth; x++) {
                System.out.print(pixels[x][y]);
            }
            System.out.print("\n");
        }
        TimerGriffty.drawTime = TimerGriffty.end();
    }

    private static void refresh(){
        TimerGriffty.start();
        try {

        } catch (Exception ignored) {
            System.out.print("Thread.sleep error");
            Settings.sc.next();
        }
        TimerGriffty.refreshTime = TimerGriffty.end();
    }
    public static void main(String[] args) {
        new Main();
    }
}
