package org.eclipse.paho;

import MQTT.MQTTClient;

import java.util.Scanner;

public class App {


    public static void main( String[] args ){
        System.out.println("****************************");
        System.out.println("Testbed Java Client");
        System.out.println("****************************");
        boolean exit = false;
        MQTTClient mqtt = new MQTTClient("tcp://" + enterIP() + ":1883");
        while (!exit) {
            try{
                Thread.sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
            printMenu();
            if (selectMenu() == 1) {
                mqtt.publish(enterMessage(), "test");
            } else {
                System.out.println("Disconnecting...");
                mqtt.disconnect();
                exit = true;
            }
        }
        System.exit(0);
    }

    private static String enterIP(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the IP of the Broker:");
        return scanner.nextLine();
    }

    private static String enterMessage(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the message to test:");
        return scanner.nextLine();
    }

    private static void printMenu(){
        System.out.println();
        System.out.println("Select an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disconnect");
        System.out.println();
    }

    private static int selectMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the option:");
        return scanner.nextInt();
    }
}
