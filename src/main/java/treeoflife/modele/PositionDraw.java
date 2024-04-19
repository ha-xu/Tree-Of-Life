package treeoflife.modele;

import java.util.ArrayList;

public class PositionDraw {

    public static ArrayList<Position> GetAroundPositions(Position centerPosition,double radius,int positionCount){
        ArrayList<Position> positions = new ArrayList<>();
        int angle = 360/positionCount;
        for (int i = 0; i < positionCount; i++) {
            int x = (int) (centerPosition.getX() + radius * Math.cos(Math.toRadians(angle * i)));
            int y = (int) (centerPosition.getY() + radius * Math.sin(Math.toRadians(angle * i)));
            positions.add(new Position(x,y));
        }
        return positions;
    }
}
