package com.github.griffty;

import java.util.ArrayList;

class Vector3 {
    double x = 0;
    double y = 0;
    double z = 0;
    Vector3(){}
    Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector3(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector3(Vector3Int vector){
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }
    Vector3(Vector3 vector){
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }
    public Vector3Int convToInt(){
        return new Vector3Int(x, y, z);
    }

    @Override
    public String toString() {
        return "V3(" + x + ", " + y + ", " + z + ")";
    }
}

class Vector3Int {
    int x = 0;
    int y = 0;
    int z = 0;
    Vector3Int(){}
    Vector3Int(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Vector3Int(double x, double y, double z){
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
    }
    Vector3Int(Vector3Int vector){
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }
    Vector3Int(Vector3 vector){
        this.x = (int) vector.x;
        this.y = (int) vector.y;
        this.z = (int) vector.z;
    }
}

class PointGriffty {
    public final Vector3Int initialPosition;
    public Vector3Int offset = new Vector3Int();
    public Vector3Int position = new Vector3Int();
    public ArrayList<PointGriffty> connectedPoints;

    PointGriffty(int x, int y, int z){
        initialPosition = new Vector3Int(x, y, z);
        connectedPoints = new ArrayList<>();
    }
    PointGriffty(int x, int y, int z, ArrayList<PointGriffty> allConnections){
        initialPosition = new Vector3Int(x, y, z);
        connectedPoints = allConnections;
    }

    public void updatePosition() {
        position.x = initialPosition.x + offset.x;
        position.y = initialPosition.y + offset.y;
        position.z = initialPosition.z + offset.z;
    }
    public static void connectPoint(PointGriffty first, PointGriffty second){
        if (first.connectedPoints.contains(second) || second.connectedPoints.contains(first)) {
            System.out.print("Cannot connect points");
            Settings.sc.next();
            return;
        }
        first.connectedPoints.add(second);
        second.connectedPoints.add(first);
    }

}
