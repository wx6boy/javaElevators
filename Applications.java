package com.company;

public class Applications {
    private int floor;
    private Direction direction;
    public int next;


    public Applications(){
        this.floor = (int)(Math.random()*8) + 1;
        this.direction = Math.random() * 2 == 0.0 ? Direction.UP : Direction.DOWN;
        if(floor == 8){
            this.direction = Direction.DOWN;
        }
        if(floor == 1){
            this.direction = Direction.UP;
        }
    }


    public int getNext(){
        while( next == 0 || next == floor ){
            if(direction.equals(Direction.UP)){
                next = (int)(Math.random() * 8) + floor;
            }
            else{
                next = (int)(Math.random() * floor) + 1;
            }
        }
        return next;
    }


    public int getFloor(){
        return floor;
    }


    public Direction getDirection(){
        return direction;
    }
}
