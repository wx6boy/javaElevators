package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Elevator implements Runnable {
    private int current_floor;
    private int id;
    private int floor;
    private boolean work;
    private ArrayList<Applications> curApplications;
    private Direction direction;
    private Applications mainApplication;
    private final static Object obj = new Object();

    Elevator(int id){
        curApplications = new ArrayList<>();
        this.id = id;
        work = true;
        current_floor = 1;
        direction = Direction.WAIT;
    }

    @Override
    public void run(){
        while(work){
            while(!Manager.getManager().isEmpty()){
                synchronized (obj){
                    try{
                        mainApplication = Manager.getManager().pop();
                        floor =mainApplication.getFloor();
                        Manager.getManager().push(mainApplication);
                    } catch (NullPointerException e){
                        floor = current_floor;
                        continue;
                    }
                    while(current_floor != floor){
                        if(!Manager.getManager().getListOfApplications().isEmpty()){
                            move();
                        }
                    }
                }
                try{
                    Applications application = Manager.getManager().pop();
                    if(application.getFloor() == current_floor){
                        curApplications.add(application);
                        floor = application.getNext();
                    }
                    else{
                        Manager.getManager().push(application);
                    }
                } catch (NullPointerException e){
                    floor = 0;
                    continue;
                }
                while(current_floor != floor){
                    move();
                    ArrayList<Applications> list = Manager.getManager().popFromLevel(current_floor, direction);
                    if(list != null){
                        for(Applications application: list){
                            if((application.getNext() > floor && direction.equals(Direction.UP)) ||
                                    (application.getNext() < floor && direction.equals(Direction.DOWN))){
                                floor = application.getNext();
                            }
                            curApplications.add(application);
                        }
                    }
                    Iterator<Applications> it = curApplications.iterator();
                    while(it.hasNext()){
                        Applications application = it.next();
                        try {
                            if(application.getNext() == current_floor){
                                it.remove();
                            }
                        } catch(NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    private void move(){
        if(current_floor < floor){
            current_floor++;
        }
        else{
            current_floor--;
        }
        System.out.println("Lift with id " + this.id + " is on " + current_floor);
        try{
            Thread.sleep(1500);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
