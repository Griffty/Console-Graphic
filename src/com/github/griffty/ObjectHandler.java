package com.github.griffty;

import java.util.ArrayList;

public class ObjectHandler {
    public static int consoleWidth;
    public static int consoleHeight;
    public static int consoleLength;
    public static Vector3Int initialOffset = new Vector3Int();
    public static Vector3Int initialRotation = new Vector3Int();
    public static Vector3Int center;
    public static float depthRatio;
    public static int ratio = 2;
    public static int R = 4;
    public static void reload(){
        center = new Vector3Int(consoleWidth/2, consoleHeight*ratio/2, consoleLength/2);
        depthRatio = (float)consoleLength / symbols.length;
        initialOffset = new Vector3Int();
        initialRotation = new Vector3Int();
        reloadShapes();
    }
    public static Object3D createNewObject(finishedShapes shape){
        reload();
        char [][][] pointOfThisObject = new char[consoleWidth][consoleHeight][consoleLength];
        for (int z = 0; z < consoleLength; z++) {
            for (int y = 0; y < consoleHeight; y++) {
                for (int x = 0; x < consoleWidth; x++) {
                    if (inSomeShape(shape, x, y, z)) {
                        int symbol = (int)(z / ObjectHandler.depthRatio);
                        pointOfThisObject[x][y][z] = symbols[symbol];
                    }else {
                        pointOfThisObject[x][y][z] = ' ';
                    }
                }
            }
        }
        return new Object3D(new Vector3Int(consoleWidth/2, consoleHeight/2, consoleLength/2), initialOffset, initialRotation, pointOfThisObject, null);
    }
    public static Object3D createNewObject(ArrayList<PointGriffty> allPoints){
        reload();
        ArrayList<PointGriffty> usedPoints = new ArrayList<>();
        char [][][] pointOfThisObject = new char[consoleWidth][consoleHeight][consoleLength];
        for (int z = 0; z < consoleLength; z++) {
            for (int y = 0; y < consoleHeight; y++) {
                for (int x = 0; x < consoleWidth; x++) {
                    pointOfThisObject[x][y][z] = ' ';
                }
            }
        }
        for (PointGriffty p1 : allPoints){
            for (PointGriffty p2 : p1.connectedPoints){
                if (p1 != p2) {
                    if (!usedPoints.contains(p1) || !usedPoints.contains(p2)) {
                        calculateBresenhamLine(pointOfThisObject, p1, p2);
                    }
                }
            }
            usedPoints.add(p1);
        }
        return new Object3D(new Vector3Int(consoleWidth/2, consoleHeight/2, consoleLength/2), initialOffset, initialRotation, pointOfThisObject, allPoints);
    }
    public static void calculateBresenhamLine(char[][][] pointOfThisObject,PointGriffty p1, PointGriffty p2){
        int x1 = p1.position.x, y1 = p1.position.y, z1 = p1.position.z;
        int x2 = p2.position.x, y2 = p2.position.y, z2 = p2.position.z;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dz = Math.abs(z2 - z1);
        int x = x1, y = y1, z = z1;
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int sz = z1 < z2 ? 1 : -1;
        if (dx >= dy && dx >= dz) {
            int err1 = dy - dx / 2;
            int err2 = dz - dx / 2;
            while (x != x2) {
                int symbol = (int)(z / depthRatio);
                pointOfThisObject[x][y][z] = symbols[symbol];
                if (err1 > 0) {
                    y += sy;
                    err1 -= dx;
                }
                if (err2 > 0) {
                    z += sz;
                    err2 -= dx;
                }
                err1 += dy;
                err2 += dz;
                x += sx;
            }
        } else if (dy >= dx && dy >= dz) {
            int err1 = dx - dy / 2;
            int err2 = dz - dy / 2;
            while (y != y2) {
                int symbol = (int)(z / depthRatio);
                pointOfThisObject[x][y][z] = symbols[symbol];
                if (err1 > 0) {
                    x += sx;
                    err1 -= dy;
                }
                if (err2 > 0) {
                    z += sz;
                    err2 -= dy;
                }
                err1 += dx;
                err2 += dz;
                y += sy;
            }
        } else {
            int err1 = dx - dz / 2;
            int err2 = dy - dz / 2;
            while (z != z2) {
                int symbol = (int)(z / depthRatio);
                pointOfThisObject[x][y][z] = symbols[symbol];
                if (err1 > 0) {
                    x += sx;
                    err1 -= dz;
                }
                if (err2 > 0) {
                    y += sy;
                    err2 -= dz;
                }
                err1 += dx;
                err2 += dy;
                z += sz;
            }
        }
        int symbol = (int)(z / depthRatio);
        pointOfThisObject[x][y][z] = symbols[symbol];
    }

    public static boolean inSomeShape(finishedShapes shape, float x, float y, float z){
        switch (shape){
            case Square -> {
                return inSquare(x, y, z);
            }
            case Circle -> {
                return inCircle(x, y, z);
            }
            case Annulus -> {
                return inDonut2D(x, y, z);
            }
            case Sphere -> {
                return inSphere(x, y, z);
            }
            case Torus -> {
                return inTorus(x, y, z);
            }
            default -> {
                return false;
            }
        }
    }

    private static boolean inSquare(float x, float y , float z) {
        if (z == center.z) {
            y = y * ratio;
            float consoleH = consoleHeight * ratio;

            return x > (float) consoleWidth / 4 && x < (consoleWidth - (float) consoleWidth / 4) && y > consoleH / 4 && y < consoleH - consoleH / 4;
        }
        return false;
    }

    private static boolean inCircle(float x, float y, float z){
        if (z == center.z) {
            y = y * ratio;

            double distance = Math.sqrt(Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2));
            return distance < R;
        }
        return false;
    }

    private static boolean inDonut2D(float x, float y, float z){
        if (z== center.z) {
            y = y * ratio;

            float r = R / 2f;
            double distance = Math.sqrt(Math.pow(R + r - Math.sqrt(Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2)), 2));
            return distance < R;
        }
        return false;
    }

    private static boolean inSphere(float x, float y, float z){
        y = y *ratio;

        double distance = Math.sqrt(Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2) + Math.pow(z - center.z, 2));
        return (int)distance == R;
    }

    private static boolean inTorus(float x, float y, float z){
        y = y *ratio;

        float r = R/1.5f;
        double distance = Math.sqrt(Math.pow(R + r - Math.sqrt(Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2)), 2) + Math.pow(z - center.z, 2));
        return (int) distance == R;
    }

    public static final char[] symbols = new char[]{'@','&','$','#','%','№','=','*','!',';',':','"','~','-',',','.'};
    public enum finishedShapes {
        Square, //2d
        Circle, //2d
        Annulus, //2d
        Sphere, //3d
        Torus, //3d


        Other,
        Multi,
    }

    public enum FinishedFiguresNames{
        Cube,
        Pyramid,
        Hexagonal_Prism,

    }
    public static ArrayList<ArrayList<PointGriffty>> finishedFigures = new ArrayList<>();
    private static void reloadShapes(){
        finishedFigures = new ArrayList<>(){
            {
                add(new ArrayList<>(){
                    {
                        add(new PointGriffty(consoleWidth/2 - R*2, consoleHeight/2 - R, consoleLength/2 - R*2));
                        add(new PointGriffty(consoleWidth/2 - R*2, consoleHeight/2 + R, consoleLength/2 - R*2));
                        add(new PointGriffty(consoleWidth/2 + R*2, consoleHeight/2 - R, consoleLength/2 - R*2));
                        add(new PointGriffty(consoleWidth/2 + R*2, consoleHeight/2 + R, consoleLength/2 - R*2));

                        add(new PointGriffty(consoleWidth/2 - R*2, consoleHeight/2 - R, consoleLength/2 + R*2));
                        add(new PointGriffty(consoleWidth/2 - R*2, consoleHeight/2 + R, consoleLength/2 + R*2));
                        add(new PointGriffty(consoleWidth/2 + R*2, consoleHeight/2 - R, consoleLength/2 + R*2));
                        add(new PointGriffty(consoleWidth/2 + R*2, consoleHeight/2 + R, consoleLength/2 + R*2));

                        PointGriffty.connectPoint(get(0), get(1));
                        PointGriffty.connectPoint(get(0), get(2));
                        PointGriffty.connectPoint(get(1), get(3));
                        PointGriffty.connectPoint(get(2), get(3));

                        PointGriffty.connectPoint(get(4), get(5));
                        PointGriffty.connectPoint(get(4), get(6));
                        PointGriffty.connectPoint(get(5), get(7));
                        PointGriffty.connectPoint(get(6), get(7));

                        PointGriffty.connectPoint(get(0), get(4));
                        PointGriffty.connectPoint(get(1), get(5));
                        PointGriffty.connectPoint(get(2), get(6));
                        PointGriffty.connectPoint(get(3), get(7));
                    }
                });
                add(new ArrayList<>(){
                    {
                        add(new PointGriffty(consoleWidth/2 - R*2, consoleHeight/2 + R, consoleLength/2 - R*2));
                        add(new PointGriffty(consoleWidth/2 + R*2, consoleHeight/2 + R, consoleLength/2 - R*2));

                        add(new PointGriffty(consoleWidth/2 - R*2, consoleHeight/2 + R, consoleLength/2 + R*2));
                        add(new PointGriffty(consoleWidth/2 + R*2, consoleHeight/2 + R, consoleLength/2 + R*2));

                        add(new PointGriffty(consoleWidth/2, consoleHeight/2 - R, consoleLength/2));

                        PointGriffty.connectPoint(get(0), get(1));
                        PointGriffty.connectPoint(get(0), get(2));
                        PointGriffty.connectPoint(get(3), get(1));
                        PointGriffty.connectPoint(get(3), get(2));

                        PointGriffty.connectPoint(get(4), get(0));
                        PointGriffty.connectPoint(get(4), get(1));
                        PointGriffty.connectPoint(get(4), get(2));
                        PointGriffty.connectPoint(get(4), get(3));
                    }
                });
                add(new ArrayList<>(){
                    {
                        add(new PointGriffty((consoleWidth/2 + R*2), consoleHeight/2 + R, (consoleLength/2 - R*3) ));
                        add(new PointGriffty((consoleWidth/2 - R*2), consoleHeight/2 + R, (consoleLength/2 - R*3) ));
                        add(new PointGriffty((consoleWidth/2 - R*3), consoleHeight/2 + R, (consoleLength/2 - R*2) ));
                        add(new PointGriffty((consoleWidth/2 - R*3), consoleHeight/2 + R, (consoleLength/2 + R*2) ));
                        add(new PointGriffty((consoleWidth/2 - R*2), consoleHeight/2 + R, (consoleLength/2 + R*3) ));
                        add(new PointGriffty((consoleWidth/2 + R*2), consoleHeight/2 + R, (consoleLength/2 + R*3) ));
                        add(new PointGriffty((consoleWidth/2 + R*3), consoleHeight/2 + R, (consoleLength/2 + R*2) ));
                        add(new PointGriffty((consoleWidth/2 + R*3), consoleHeight/2 + R, (consoleLength/2 - R*2) ));

                        add(new PointGriffty((consoleWidth/2 + R*2), consoleHeight/2 - R, (consoleLength/2 - R*3) ));
                        add(new PointGriffty((consoleWidth/2 - R*2), consoleHeight/2 - R, (consoleLength/2 - R*3) ));
                        add(new PointGriffty((consoleWidth/2 - R*3), consoleHeight/2 - R, (consoleLength/2 - R*2) ));
                        add(new PointGriffty((consoleWidth/2 - R*3), consoleHeight/2 - R, (consoleLength/2 + R*2) ));
                        add(new PointGriffty((consoleWidth/2 - R*2), consoleHeight/2 - R, (consoleLength/2 + R*3) ));
                        add(new PointGriffty((consoleWidth/2 + R*2), consoleHeight/2 - R, (consoleLength/2 + R*3) ));
                        add(new PointGriffty((consoleWidth/2 + R*3), consoleHeight/2 - R, (consoleLength/2 + R*2) ));
                        add(new PointGriffty((consoleWidth/2 + R*3), consoleHeight/2 - R, (consoleLength/2 - R*2) ));

                        PointGriffty.connectPoint(get(0), get(1));
                        PointGriffty.connectPoint(get(0), get(7));
                        PointGriffty.connectPoint(get(2), get(1));
                        PointGriffty.connectPoint(get(2), get(3));
                        PointGriffty.connectPoint(get(4), get(3));
                        PointGriffty.connectPoint(get(4), get(5));
                        PointGriffty.connectPoint(get(6), get(7));
                        PointGriffty.connectPoint(get(6), get(5));

                        PointGriffty.connectPoint(get(8), get(9));
                        PointGriffty.connectPoint(get(8), get(15));
                        PointGriffty.connectPoint(get(10), get(9));
                        PointGriffty.connectPoint(get(10), get(11));
                        PointGriffty.connectPoint(get(12), get(11));
                        PointGriffty.connectPoint(get(12), get(13));
                        PointGriffty.connectPoint(get(14), get(15));
                        PointGriffty.connectPoint(get(14), get(13));

                        PointGriffty.connectPoint(get(0), get(8));
                        PointGriffty.connectPoint(get(1), get(9));
                        PointGriffty.connectPoint(get(2), get(10));
                        PointGriffty.connectPoint(get(3), get(11));
                        PointGriffty.connectPoint(get(4), get(12));
                        PointGriffty.connectPoint(get(5), get(13));
                        PointGriffty.connectPoint(get(6), get(14));
                        PointGriffty.connectPoint(get(7), get(15));
                    }
                });
            }
        };
    }
}
