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
        Scanner sc = new Scanner(System.in);
        int i = 0, compare = 0;
        boolean transmit = false;
        float p, x=0;
        
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
            i++;
        }
        
        //collision detection loop
        for(i = 0; i < senders.length - 1; i++){
            compare = senders[i + 1] - senders[i];
            if(transmit == true){
                packetDurations[i] = packetDurations[i] - 1;
                if(packetDurations[i] == 0){
                    transmit = false;
                    i++;
                }
                i--;
            }
            else{
                System.out.println("connection is free");
                x = randInt(1, 100);
                x = (x / 100);
                if(x < p){
                    System.out.println("Transmitting for " + packetDurations[i]);
                    transmit = true;
                }else{
                    transmit = false;
                    System.out.println("x > p, waiting...");
                    i--;
                }
            }
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

