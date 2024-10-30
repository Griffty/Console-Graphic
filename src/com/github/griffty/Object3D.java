package com.github.griffty;

import java.util.ArrayList;

class Object3D {
    public Vector3Int origin;
    public Vector3Int initialOrigin;
    public Vector3Int position;
    public Vector3Int rotation;
    public final char[][][] pointOfThisObject;
    private ArrayList<PointGriffty> initialPoints;
    public boolean createdWithFormula = false;

    Object3D(Vector3Int origin, Vector3Int position, Vector3Int rotation, char[][][] pointOfObject, ArrayList<PointGriffty> initialPoints) {
        this.origin = origin;
        this.rotation = rotation;
        this.position = position;
        this.pointOfThisObject = pointOfObject;
        if (initialPoints != null) {
            this.initialPoints = new ArrayList<>(initialPoints);
        }else {
            createdWithFormula = true;
        }
        initialOrigin = new Vector3Int(origin);
    }

    public void rotateOnX(float speed){
        rotation.x +=speed;
    }
    public void rotateOnY(double speed){
        rotation.y +=speed;
    }
    public void rotateOnZ(float speed){
        rotation.z +=speed;
    }
    public void moveOnX(float speed, boolean moveOrigin){
        if (moveOrigin){
            origin.x += speed;
        }
        position.x += speed;
    }
    public void moveOnY(float speed, boolean moveOrigin){
        if (moveOrigin){
            origin.y += speed;
        }
        position.y += speed;
    }
    public void moveOnZ(float speed, boolean moveOrigin){
        if (moveOrigin){
            origin.z += speed;
        }
        position.z += speed;
    }
    public void setOriginTo(OriginPositions object){
        switch (object){
            case OriginShiftedByPos ->{
                origin.x = position.x + initialOrigin.x;
                origin.y = position.y + initialOrigin.y;
                origin.z = position.z + initialOrigin.z;
            }
            case WorldCenter -> origin = ObjectHandler.center;
        }
    }

    public ArrayList<PointGriffty> getInitialPoints() {
        return initialPoints;
    }
    enum OriginPositions{
        WorldCenter,
        OriginShiftedByPos,
    }
}