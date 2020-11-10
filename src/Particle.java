import java.util.Random;

public class Particle {
    Random random;
    double[] position;
    double[] currentVelocity;
    double[] personalBest;


    public Particle(double[] position) {
        AntennaArray antennaArray = new AntennaArray(position.length, 90);
        this.position = position;
        double[] temp = antennaArray.generateRandomSolutions(position.length);
        initialiseVelocity(position, temp);
        personalBest = position;
    }

    public void setPersonalBest(double[] personalBest) {
        this.personalBest = personalBest;
    }

    //returns current position of particle
    public double[] getPosition() {
        return position;
    }

    private void initialiseVelocity(double[] position, double[] temp) {
        currentVelocity = new double[position.length];
        for (int i = 0; i < position.length; i++) {
            currentVelocity[i] = position[i] - temp[i];
            currentVelocity[i] *= 0.5;
        }
    }

    public double[] getCurrentVelocity() {
        return currentVelocity;
    }

    public double[] getPersonalBest() {
        return personalBest;
    }

    //Adds to each element in an array using a double parameter
    public double[] addPosition (double[] antennaArray, double currentVelocity) {
        double[] temp = new double[antennaArray.length];
        for (int i = 0; i < antennaArray.length; i++) {
            temp[i] = (antennaArray[i] + currentVelocity);
        }
        return temp;

    }

    //Adds two arrays together and returns the new resulting array
    public double[] addArrays (double[] a, double[] b) {
        double[] temp = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            temp[i] = a[i] + b[i];
        }
        return temp;
    }

    //Multiplies all elements in an array based on a double parameter
    private double[] multiplyPosition(double[] antennaArray, double multiplyBy) {
        double[] temp = new double[antennaArray.length];
        for (int i = 0; i < antennaArray.length; i++) {
            temp[i] = antennaArray[i] * multiplyBy;
        }

        return temp;
    }

    private double[] multiplyArrays(double[] antennaArray, double[] r) {
        double[] temp = new double[antennaArray.length];
        for (int i = 0; i < antennaArray.length; i++) {
            temp[i] = antennaArray[i] * r[i];
        }
        return temp;
    }

    //Subtracts an array using the values of another array
    private double[] subtractPosition (double[] personalBest, double[] position) {
        double[] temp = new double[personalBest.length];
        for (int i = 0; i < personalBest.length ; i++) {
            //temp[i] = position[i] - personalBest[i];
        }
        return temp;
    }

    public void printVelocity() {
        for (int i = 0; i < currentVelocity.length; i++) {
            System.out.print(currentVelocity[i] + ", ");
        }
        System.out.println();
    }

    // velocity = (Inertia * v) +  Φ1 * r1 * (Pi(t) - Xi(t)) + Φ2 * r2 * (G(t) - X(t));
    //currentVelocity = (inertia * currentVelocity) + psi * r1 * (personalBest - position) + psi * r2 * (globalBest - position);
    public void changeVelocity(double[] antennaArray, double[] globalBest, double[] r1, double[] r2) {
       double inertia = 0.721;
       double psi = 1.1193;

        //Series of variables to keep track of temporary calculations

        //inertia * velocity
        double[] inVel = multiplyPosition(currentVelocity, inertia);

        //cognitive
        double[] cognitive = cognitive(psi, r1, personalBest, position );

        //social
        double[] social = social(psi, r2, globalBest);

        //invel + cognitive
        double[] cognitiveInvel = addArrays(inVel, cognitive);


        //The sum of all parts of the formuala
        currentVelocity = addArrays(cognitiveInvel, social);
    }

    //psi * r1 * (personal best - current position)
    public double[] cognitive(double psi, double[] r1, double[] personalBest, double[] position) {
        //psi * r1
        double[] psiR1 = multiplyPosition(r1, psi);

        //Personal best - Current Particle Position
        double[] pBestDifference = subtractPosition(personalBest, position);

        return multiplyArrays(pBestDifference, psiR1);
    }

    //psi * r2 * (global best - position)
    public double[] social(double psi, double r2[], double[] globalBest) {
        //psi * r2;
        double[] psiR2 =  multiplyPosition( r2, psi);

        //Global best - Current Particle Position
        double[] gBestPosDifference = subtractPosition(globalBest, position);

        return multiplyArrays(gBestPosDifference, psiR2);
    }


}
