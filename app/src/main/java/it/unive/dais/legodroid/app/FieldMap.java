package it.unive.dais.legodroid.app;

public class FieldMap {

    private int M; //x
    private int N; //y
    private int Mact;
    private int Nact;

    private int Msafe;
    private int Nsafe;


    private String[][] map;

    private Robot.Direction actualDirection;
    private Robot.Direction startDirection;

    public FieldMap(int M, int N, int Mact, int Nact, Robot.Direction actualDirection){
        this.M = M;
        this.N = N;
        this.Mact = Mact;
        this.Nact = Nact;

        this.Msafe = Mact;
        this.Nsafe = Nact;

        this.actualDirection = actualDirection;
        this.startDirection = actualDirection;

        map = new String[N][M];
        for(int i=0; i<N;i++)
            for(int j=0; j<M; j++)
                map[i][j] = " ";
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

    public int getM() {
        return M;
    }
    public int getN() {
        return N;
    }
    public int getMact() {
        return Mact;
    }
    public int getNact() {
        return Nact;
    }
    public int getMsafe(){
        return Msafe;
    }
    public int getNsafe(){
        return Nsafe;
    }

    public Robot.Direction getActualDirection(){return actualDirection;}
    public Robot.Direction getStartDirection(){return startDirection;}

    public Robot.Direction getOppositeDirection(){
        turnLeft();
        turnLeft();
        return actualDirection;
    }


}
