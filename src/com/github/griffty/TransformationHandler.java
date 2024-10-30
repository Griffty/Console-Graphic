package com.github.griffty;

import java.util.ArrayList;

import static java.lang.Math.*;

public class TransformationHandler {
    public static int consoleWidth = 120;
    public static int consoleHeight = 28;
    public static int consoleLength = 60;
    private static final double degreesToRadians = PI/180;
    private static final int perspectiveFactor = 16;
    private static char[][] pixels;
    private static ArrayList<Object3D> objects3D;
    private static char[][][] movedShape;
    private static char[][][] finalShape;
    public static void reload(){
        pixels = new char[(int) consoleWidth][(int) consoleHeight];
        movedShape = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
        finalShape = new char[(int) consoleWidth][(int) consoleHeight][(int) consoleLength];
    }
    public static char[][] transformTo2D(ArrayList<Object3D> objects){
        TransformationHandler.objects3D = objects;
        fillEmpty();
        moveObject();
        rotateObject();
        applyPerspective();
        render();

        return pixels;
    }
    private static void fillEmpty(){
        for (int x = 0; x < consoleWidth; x++) {
            for (int y = 0; y < consoleHeight; y++) {
                pixels[x][y] = ' ';
                for (int z = 0; z < consoleLength; z++){
                    movedShape[x][y][z] = ' ';
                    finalShape[x][y][z] = ' ';
                }
            }
        }
    }
    private static void moveObject(){
        for (Object3D object3D : objects3D) {
            if (object3D.createdWithFormula) {
                for (int z = consoleLength - 1; z > -1; z--) {
                    for (int x = 0; x < consoleWidth; x++) {
                        for (int y = 0; y < consoleHeight; y++) {
                            if (object3D.pointOfThisObject[x][y][z] != ' ') {
                                movedShape[x + object3D.position.x][y + object3D.position.y][z + object3D.position.z] = object3D.pointOfThisObject[x][y][z];
                            }
                        }
                    }
                }
            } else {
                for (PointGriffty p : object3D.getInitialPoints()) {
                    p.offset = object3D.position;
                    p.updatePosition();
                }
            }
        }
    }
    private static void rotateObject(){
        for (Object3D object3D : objects3D) {
            if (object3D.createdWithFormula) {
                for (int z = consoleLength - 1; z > -1; z--) {
                    for (int x = 0; x < consoleWidth; x++) {
                        for (int y = 0; y < consoleHeight; y++) {
                            if (movedShape[x][y][z] != ' ') {
                                Vector3 vector3 = new Vector3(x, y, z);

                                vector3 = rotateOnX(vector3, object3D);
                                vector3 = rotateOnY(vector3, object3D);
                                vector3 = rotateOnZ(vector3, object3D);

                                int final_x = (int) vector3.x;
                                int final_y = (int) vector3.y;
                                int final_z = (int) vector3.z;
                                finalShape[(final_x)][final_y][(final_z)] = movedShape[x][y][z];
                            }
                        }
                    }
                }
            } else {
                for (PointGriffty p : object3D.getInitialPoints()) {
                    Vector3 vector3 = new Vector3(p.position);
                    vector3 = rotateOnX(vector3, object3D);
                    vector3 = rotateOnY(vector3, object3D);
                    vector3 = rotateOnZ(vector3, object3D);
                    p.position = vector3.convToInt();
                }
            }
        }
    }
    private static Vector3 rotateOnX(Vector3 vector3, Object3D object3D){
        double theta = object3D.rotation.x*degreesToRadians;
        double final_x = vector3.x;
        double final_y = (vector3.y - object3D.origin.y) * cos(theta) - (vector3.z - object3D.origin.z) * sin(theta);
        double final_z = (vector3.y - object3D.origin.y) * sin(theta) + (vector3.z - object3D.origin.z) * cos(theta);
        return new Vector3(final_x, final_y+ object3D.origin.y, final_z + object3D.origin.z);
    }
    private static Vector3 rotateOnY(Vector3 vector3, Object3D object3D){
        double theta = object3D.rotation.y*degreesToRadians;
        double final_x = (vector3.x - object3D.origin.x) * cos(theta) - (vector3.z - object3D.origin.z) * sin(theta);
        double final_y = vector3.y;
        double final_z = (vector3.x - object3D.origin.x) * sin(theta) + (vector3.z - object3D.origin.z) * cos(theta);
        return new Vector3(final_x + object3D.origin.x, final_y, final_z + object3D.origin.z);
    }
    private static Vector3 rotateOnZ(Vector3 vector3, Object3D object3D){
        double theta = object3D.rotation.z*degreesToRadians;
        double final_x = (vector3.x - object3D.origin.x) * Math.cos(theta) - (vector3.y- object3D.origin.y) * ObjectHandler.ratio * Math.sin(theta);
        double final_y = (vector3.x - object3D.origin.x) * Math.sin(theta) + (vector3.y - object3D.origin.y) * ObjectHandler.ratio * Math.cos(theta);
        double final_z = vector3.z;
        return new Vector3(final_x + object3D.origin.x, (final_y + object3D.origin.y) / ObjectHandler.ratio + consoleHeight / 4 + 1, final_z);
    }

    private static void drawLines(Object3D object3D){
        ArrayList<PointGriffty> usedPoints = new ArrayList<>();
        for (PointGriffty p1 : object3D.getInitialPoints()) {
            for (PointGriffty p2 : p1.connectedPoints) {
                if (p1 != p2) {
                    if (!usedPoints.contains(p1) || !usedPoints.contains(p2)) {
                        ObjectHandler.calculateBresenhamLine(finalShape, p1, p2);
                    }
                }
            }
            usedPoints.add(p1);
        }
    }

    private static void applyPerspective(){
        for (Object3D object3D : objects3D) {
            if (!object3D.createdWithFormula) {
                for (int i = 0; i < object3D.getInitialPoints().size(); i++) {
                    PointGriffty p = object3D.getInitialPoints().get(i);
                    double pointXOffset = p.position.x - object3D.origin.x;
                    double pointYOffset = p.position.y - object3D.origin.y;
                    double z = p.position.z;
                    pointXOffset /= Math.sqrt(z / perspectiveFactor);
                    pointYOffset /= Math.sqrt(z / perspectiveFactor);
                    p.position.x = (int) (object3D.origin.x + pointXOffset);
                    p.position.y = (int) (object3D.origin.y + pointYOffset);
                }
                drawLines(object3D);
            }
        }
    }

    public static void render(){
        for (int z = 0; z < consoleLength; z++){
            for (int x = 0; x < consoleWidth; x++) {
                for (int y = 0; y < consoleHeight; y++) {
                    if (pixels[x][y] == ' '){
                        pixels[x][y] = finalShape[x][y][z];
                    }
                }
            }
        }
    }
}


