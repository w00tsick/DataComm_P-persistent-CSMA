/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datacomm_project;

import java.math.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author tgw55
 */
public class Datacomm_project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] senders = new int[100];
        int[] packetDurations = new int[100];
        int[] packetStack = new int[100];
        int[] durationStack = new int[100];
        Scanner sc = new Scanner(System.in);
        int i = 0, compare = 0, j =0, totalDuration = 0, k =0,
                stackCounter = 0, u = 0, priorityCounter = 0;
        boolean transmit = false;
        float p, x=0;
        double idleCounter = 0, totalcycles = 0;
        double average;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("./output.xls"));
        } catch (IOException e) {
            System.err.println(e);
        }
        
        System.out.println("Please enter a desired p value: ");
        p = sc.nextFloat();
        
        while(i < senders.length -1){
            senders[i] = randInt(1, 10000);
            packetDurations[i] = randInt(1, 100);
            i++;
        }
        
        quickSort(senders, 0, senders.length -1);
        
        i = 0;
        
        while(i < senders.length -1){
            System.out.println("sender locations: " + senders[i]);
            System.out.println("sender durations: " + packetDurations[i]);
            totalDuration += packetDurations[i];
            System.out.println("total duration: " + totalDuration);
            i++;
        }
        
        int[] connectionLength = new int[totalDuration + senders[senders.length -1]];
        System.out.println("total connection length: " + connectionLength.length);

        
        //collision detection loop
        for(i = 0; i < connectionLength.length - 1; i++){
            if(!transmit){
                if(stackCounter > 0){
                    u = 0;
                    u = u + priorityCounter;
                    while(packetStack[u] != 0){
                        System.out.println("Test priority Queue: "
                                + packetStack[u]);
                        x = randInt(1, 100);
                        x = (x / 100);
                        if(x < p){
                            System.out.println("transmitting priority queue: " + 
                                    packetStack[u]);
                            transmit = true;
                            priorityCounter++;
                            stackCounter--;
                            System.out.println("Packets waiting: " + stackCounter);
                            break;
                        }
                        else{
                            System.out.println("moving to next in priority");
                            u++;
                            stackCounter--;
                        }
                    }
                }
                if(!transmit){
                    idleCounter++;
                    //write data to excel file
    /*try {
        writer.write(idleCounter + "\t");
    } catch (IOException e) {
        System.err.println(e);
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }*/
                }
            }
            else{
                if(packetDurations[k] == 0){
                    System.out.println("Connection is now free");
                    transmit = false;
                    k++;
                }else{
                    System.out.println("sending: " + packetDurations[k]);
                    packetDurations[k]--;
                }
            }
            if(senders[j] == i && j < 99){
                System.out.println("Test connection at: " + senders[j]);
                
                x = randInt(1, 100);
                x = (x / 100);
                
                if((x < p) && transmit != true){
                    transmit = true;
                }else if((x < p) && transmit == true){
                    System.out.println("Connection is busy, throw on stack");
                    packetStack[stackCounter] = senders[j];
                    stackCounter++;
                    System.out.println("Packets waiting: " + stackCounter);
                }else{
                    System.out.println("x > p, waiting...");
                    i--;
                    j--;
                }
                j++;
            }
            totalcycles++;
            //write data to excel file
            
            average = idleCounter/totalcycles;
            try {
        
        writer.write(average + "\t");
        writer.write(stackCounter + "\n");
    } catch (IOException e) {
        System.err.println(e);
    } /*finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }*/
            if(j == 100){
                break;
            }
        }
        System.out.println("Total cycle count: " + totalcycles);
        System.out.println("Total idle cycles: " + idleCounter);
        try {
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
                System.err.println(e);
            }
        }
        
    public static int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    public static int partition(int arr[], int left, int right)
    {
        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];
     
        while (i <= j) {
            while (arr[i] < pivot)
                i++;
            while (arr[j] > pivot)
                j--;
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
      }
      return i;
    }
 
    public static void quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }
}

