import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.abs;


public class Main {

    /*
Simulation of a distributed processor load balancing algorithm.
The system has N identical processors (multithreading). On each of them there are new tasks (processes) with different frequencies and different requirements.
Simulation:
- each processor gets its processes,
- each process requires a specific, different share of the processor's computing power, e.g., 3%, 5%, 8%, etc. (you have to choose the process requirements strategy),
- N=about 50-100 and a long series of tasks to be performed (choose the parameters yourself, so that the whole works).
Input data(User should be able to specify):
- number of processors N,
- task length ?,
Results should include:
- The average CPU load (decide, reasonably, how it will be calculated).
- The average deviation from the value in previous point.*/
    public static void simulate(int[]processPowerShare,int[] processFrequency ,int numOfProcessors, int testLength){

        int[]processorPowerUsage = new int[processFrequency.length];
        int[]assignedProcessor = processPowerShareOrFrequency(processPowerShare.length,numOfProcessors+1);//random
        for (int i = 0; i < assignedProcessor.length; i++) {
            assignedProcessor[i]--;
        }
        boolean[] isDue = new boolean[processorPowerUsage.length];
        for (int i = 0; i < processorPowerUsage.length; i++) {
            processorPowerUsage[i]=0;
        }
        int[] avgUsage=new int[numOfProcessors];
        for (int i = 0; i < avgUsage.length; i++) {
            avgUsage[i]=0;
        }
        for (int i = 0; i < testLength; i++) {
            for (int j = 0; j < processPowerShare.length; j++) {
                if(i%processFrequency[j]==0){
                    isDue[j]=true;
                }
                if(isDue[j]&&processorPowerUsage[assignedProcessor[j]]+processPowerShare[j]<100) {
                    isDue[j]=false;
                    processorPowerUsage[assignedProcessor[j]]=processorPowerUsage[assignedProcessor[j]]+processPowerShare[j];

                }
            }
            for (int j = 0; j < numOfProcessors; j++) {
                System.out.print(processorPowerUsage[j]+" ");
                avgUsage[j]+=processorPowerUsage[j];
                processorPowerUsage[j]=0;
            }
            System.out.println();
        }
        int deviation=0;
        int avg=0;
        for (int i = 0; i < avgUsage.length; i++) {
            avgUsage[i]=avgUsage[i]/testLength;
            avg+=avgUsage[i];
        }
        avg/=numOfProcessors;
        for (int i = 0; i < numOfProcessors; i++) {
            deviation+=abs(avg-avgUsage[i]);
        }
        deviation/=numOfProcessors;
        System.out.println("Average processor usage: "+avg+"\nAverage deviation: "+deviation);
    }
    public static int[] processPowerShareOrFrequency(int taskLength, int max) {
        int[]processPower = new int[taskLength];
        Random random = new Random();
        for (int i = 0; i < taskLength; i++) {
            processPower[i] = (random.nextInt() % (max-1));
            if (processPower[i] < 0) processPower[i] *= -1;
            processPower[i]++;
        }
        return processPower;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of processors");
        int numOfProcessors = scanner.nextInt();
        System.out.println("Enter the length of the test");
        int testLength = scanner.nextInt();
        int numOfTasks = 100;
        int maxTaskLength=20;
        int maxProcessFrequency=10;
        simulate(processPowerShareOrFrequency(numOfTasks,maxTaskLength),processPowerShareOrFrequency(numOfTasks,maxProcessFrequency),numOfProcessors,testLength);
    }


}
