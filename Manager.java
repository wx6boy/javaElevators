package com.company;


import java.util.ArrayList;
import java.util.Iterator;


public class Manager implements Runnable {
    private static Manager uniqueManager= new Manager();
    private ArrayList<Applications> listOfApplications;
    private Elevator Elevator1;
    private Elevator Elevator2;
    private Elevator Elevator3;
    private Thread Thread1;
    private Thread Thread2;
    private Thread Thread3;


    private Manager(){
        listOfApplications = new ArrayList<>();
        Elevator1 = new Elevator(1);
        Elevator2 = new Elevator(2);
        Elevator3 = new Elevator(3);
        Thread1 = new Thread(Elevator1);
        Thread2 = new Thread(Elevator2);
        Thread3 = new Thread(Elevator3);
    }

    public static Manager getManager() {
        return uniqueManager;
    }

    @Override
    public void run() {
        Thread1.start();
        Thread2.start();
        Thread3.start();
        try {
            Thread1.join();
            Thread2.join();
            Thread3.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public synchronized void push(Applications applications) {
        listOfApplications.add(applications);
    }

    public synchronized boolean isEmpty() { return listOfApplications.isEmpty(); }

    public synchronized Applications pop() {
        Applications applications = null;
        if (!listOfApplications.isEmpty()) {
            Iterator<Applications> it = listOfApplications.iterator();
            applications = it.next();
            it.remove();
        }
        return applications;
    }

    public synchronized ArrayList<Applications> popFromLevel(int level, Direction dir){
        ArrayList<Applications> list = new ArrayList<>();
        Iterator<Applications> it = listOfApplications.iterator();
        while (it.hasNext()){
            Applications applications = it.next();
            if (applications.getFloor() == level && applications.getDirection().equals(dir)){
                list.add(applications);
                it.remove();
            }
        }
        return list;
    }

    public ArrayList<Applications> getListOfApplications(){ return (ArrayList<Applications>) listOfApplications.clone(); }
}