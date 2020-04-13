package com.company;

public class NewApplications implements Runnable {
    private Manager manager;
    public NewApplications(){
        manager = Manager.getManager();
    }

    @Override
    public void run(){
        int i = 0;
        while(true){
            Applications application = new Applications();
            manager.push(application);
            System.out.println("new application from " + application.getFloor() + " to " + application.getNext());
            try{
                Thread.sleep(8000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            i++;
        }
    }
}
