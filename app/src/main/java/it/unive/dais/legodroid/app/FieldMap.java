package it.unive.dais.legodroid.app;

public class FieldMap {

    private int M; //x
    private int N; //y

    private int actualM;
    private int actualN;

    private int startM;
    private int startN;

    private Robot.Direction actualDirection;

    public FieldMap(int M, int N, Robot.Direction actualDirection){
        this.M = M;
        this.N = N;
        this.actualDirection = actualDirection;

    }

    public void moveForward(){
        switch (actualDirection){
            case NORTH:
                this.N++;
                break;
            case EAST:
                this.M++;
                break;
            case SOUTH:
                this.N--;
                break;
            case WEST:
                this.M--;
                break;
        }
    }

    public void turnRight(){
        switch (actualDirection){
            case NORTH:
                this.actualDirection = Robot.Direction.EAST;
                break;
            case EAST:
                this.actualDirection = Robot.Direction.SOUTH;
                break;
            case SOUTH:
                this.actualDirection = Robot.Direction.WEST;
                break;
            case WEST:
                this.actualDirection = Robot.Direction.NORTH;
                break;
        }
    }

    public void turnLeft(){
        switch (actualDirection){
            case NORTH:
                this.actualDirection = Robot.Direction.WEST;
                break;
            case WEST:
                this.actualDirection = Robot.Direction.SOUTH;
                break;
            case SOUTH:
                this.actualDirection = Robot.Direction.EAST;
                break;
            case EAST:
                this.actualDirection = Robot.Direction.NORTH;
                break;
        }
    }


}
