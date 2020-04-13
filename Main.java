package com.company;


public class Main{

    public static void main(String[] args) {
        NewApplications newApplications = new NewApplications();
        Manager manager = Manager.getManager();
        Thread ElevatorThread = new Thread(manager);
        Thread ApplicationThread = new Thread(newApplications);
        ApplicationThread.start();
        ElevatorThread.start();
        try{
            ApplicationThread.join();
            ElevatorThread.join();
        } catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
