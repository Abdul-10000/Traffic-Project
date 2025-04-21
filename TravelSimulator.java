import java.util.*;

public class TravelSimulator {

    // Transfer times (in minutes)
    static final int ROBOTAXI_TO_BUS_TRANSFER = 5;
    static final int BUS_TO_TRAIN_TRANSFER = 10;
    static final int TRAIN_TO_METRO_TRANSFER = 15;

    // Random generator for travel variability
    static Random rand = new Random();

    public static int simulateRobotaxi(double km) {
        double base = (km / 40.0) * 60; // 40 km/h avg
        int delay = rand.nextInt(6); // 0-5 minutes
        return (int)Math.round(base + delay);
    }

    public static int simulateBus(double km) {
        double base = (km / 60.0) * 60; // 60 km/h avg
        int delay = rand.nextInt(11); // 0-10 minutes
        return (int)Math.round(base + delay);
    }

    public static int simulateTrain(double km) {
        double base = (km / 90.0) * 60; // 90 km/h avg
        int delay = rand.nextInt(4); // 0-3 minutes
        return (int)Math.round(base + delay);
    }

    public static int simulateTrip(double robotaxiKm, double busKm, double trainKm, boolean useRobotaxi, boolean useBus, boolean useTrain) {
        int totalTime = 0;

        if (useRobotaxi) {
            int rTime = simulateRobotaxi(robotaxiKm);
            totalTime += rTime;
            System.out.println("Robotaxi: " + rTime + " min");
            totalTime += ROBOTAXI_TO_BUS_TRANSFER;
        }

        if (useBus) {
            int bTime = simulateBus(busKm);
            totalTime += bTime;
            System.out.println("Bus: " + bTime + " min");
            totalTime += BUS_TO_TRAIN_TRANSFER;
        }

        if (useTrain) {
            int tTime = simulateTrain(trainKm);
            totalTime += tTime;
            System.out.println("Train: " + tTime + " min");
            totalTime += TRAIN_TO_METRO_TRANSFER;
        }

        return totalTime;
    }

    public static void runScenario() {
        double robotaxiKm = 5.0;
        double busKm = 15.0;
        double trainKm = 60.0;

        int runs = 10;
        int[] times = new int[runs];

        System.out.println("Simulating travel: Urbana → Washington D.C.\n");

        for (int i = 0; i < runs; i++) {
            System.out.println("Run #" + (i + 1));
            int time = simulateTrip(robotaxiKm, busKm, trainKm, true, true, true);
            times[i] = time;
            System.out.println("Total Travel Time: " + time + " minutes\n");
        }

        int sum = 0;
        int max = 0;

        for (int time : times) {
            sum += time;
            if (time > max) max = time;
        }

        double avg = sum / (double) runs;

        System.out.println("---- Summary ----");
        System.out.printf("Average Travel Time: %.1f minutes\n", avg);
        System.out.println("Maximum Travel Time: " + max + " minutes");
    }

    public static void main(String[] args) {
        runScenario();
    }
}
