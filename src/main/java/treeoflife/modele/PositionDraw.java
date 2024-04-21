package treeoflife.modele;

import javafx.geometry.Pos;

import java.util.ArrayList;

public class PositionDraw {

    public static ArrayList<Position> GetAroundPositions(Position centerPosition,double radius,int positionCount){
        ArrayList<Position> positions = new ArrayList<>();
        double angle = 360/positionCount;
        for (int i = 0; i < positionCount; i++) {
            double x =  (centerPosition.getX() + radius * Math.cos(Math.toRadians(angle * (double) i)));
            double y = (centerPosition.getY() + radius * Math.sin(Math.toRadians(angle * (double)i)));
            positions.add(new Position(x,y));
        }
        return positions;
    }

    public static ArrayList<Position> GetAroundPositions(Position centerPosition,Position parentPosition,double radius,int positionCount){
        ArrayList<Position> positions = new ArrayList<>();
        //generate positions not overlap with parent position
        double angle = (double) 270 / (double)(positionCount+1);
        double parentAngle = Math.toDegrees(Math.atan2(centerPosition.getY() - parentPosition.getY(), centerPosition.getX() - parentPosition.getX()));
        System.out.println("centerPosition: " + centerPosition.getX() + " " + centerPosition.getY());
        System.out.println("parentAngle: " + parentAngle);
        for (int i = 1; i <= positionCount; i++) {
            double x =  (centerPosition.getX() + radius * Math.cos(Math.toRadians(angle * (double) i  - parentAngle - 135)));
            double y = (centerPosition.getY() + radius * Math.sin(Math.toRadians(angle * (double)i + 180- parentAngle - 135)));
            positions.add(new Position(x,y));
        }
        return positions;
    }
}
