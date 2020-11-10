import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static AntennaArray antennaArray;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of antennae");
        int numberOfAntennae = scanner.nextInt();
        antennaArray = new AntennaArray(numberOfAntennae, 90);
        double[] globalBest = antennaArray.generateRandomSolutions(numberOfAntennae);


        ArrayList<Particle> swarm = new ArrayList<>();

        double population = 20 + Math.sqrt(2);

        //Generates particles equal to the population size
        for (int i = 0; i < (int) population; i++) {
            double[] temp = antennaArray.generateRandomSolutions(numberOfAntennae);

            swarm.add(new Particle(temp));
            swarm.get(i).printVelocity();
            System.out.println();
        }
        System.out.println("Enter the number of cycles");
        int numberOfGenerations = scanner.nextInt();
        boolean running = true;
        int currentGeneration = 0;
        while (currentGeneration < numberOfGenerations) {
            globalBest = updateGlobalBest(swarm, globalBest);
             System.out.print("global best: ");
             printArray(globalBest);
            System.out.println("Global best cost: ");
            System.out.println(antennaArray.evaluate(globalBest));
            System.out.println();
            int i = 1;
            for (Particle particle : swarm) {

                 //r1 and r2 = random velocity generated via nextDouble()
                 double[] r1 = generateRValues(particle.position.length);
                 double[] r2 = generateRValues(particle.position.length);
                 //Changes velocity based on the formula, and then changes the position based on the velocity
                 particle.changeVelocity(particle.getPosition(), globalBest, r1, r2);
                   printArray(particle.getCurrentVelocity());
                 particle.position = particle.addArrays(particle.getPosition(), particle.currentVelocity);
                 //Updates personal best
                 System.out.println("Personal best of particle " + i + " :");
                 i++;
                 printArray(particle.personalBest);
                 System.out.println();
                 if (antennaArray.evaluate(particle.getPosition()) < antennaArray.evaluate(particle.personalBest) && antennaArray.is_valid(particle.getPosition())) {
                     particle.setPersonalBest(particle.getPosition());
                 }
             }

             currentGeneration++;
        }
        System.out.println("Global best: ");
        printArray(globalBest);

        System.out.println("Global best cost: ");
        System.out.println(antennaArray.evaluate(globalBest));
        System.out.println();



//        BEGIN
//        INITIALISE population;
//        REPEAT UNTIL ( termination condition IS satisfied ) DO
//        UPDATE global best;
//        FOR EACH ( particle in population ) DO
//        1. UPDATE velocity and position;
//        2. EVALUATE new position;
//        3. UPDATE personal best;
//        OD
//                OD
//        END

    }

    private static double[] generateRValues(int length) {
        Random random = new Random();
        double[] r = new double[length];
        for (int i = 0; i < r.length; i++) {
            r[i] = random.nextDouble();
        }
        return r;
    }

    public static double[] updateGlobalBest(ArrayList<Particle> swarm, double[] globalBest) {

        for (Particle particle : swarm  ) {
                if (antennaArray.is_valid(particle.getPosition())) {
                   if (antennaArray.evaluate(particle.getPersonalBest()) < antennaArray.evaluate(globalBest)) {
                        globalBest = particle.getPersonalBest();
                }
            }
        }

        return globalBest;
    }

    private static void printArray(double[] test) {
        for (int i = 0; i < test.length; i++) {
            if (i == 0) {
                System.out.print("[");
            }
            System.out.print(test[i]);
            if (i != test.length - 1) {
                System.out.print(", ");
            }
            if (i == test.length - 1) {
                System.out.print("]");
            }
        }
        System.out.println();
    }

}
