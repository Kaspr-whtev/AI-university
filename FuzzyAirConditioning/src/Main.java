import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        airCondition airCon = new airCondition(24, -0.6, 13, 200);
        ArrayList<double[]> fuzzyOutputs = airCon.Interface();
        airCondition.printFuzzy(fuzzyOutputs);
        double[] crispOutputs = airCon.Defuzzify(fuzzyOutputs);
        airCondition.printCrisp(crispOutputs);
    }
}
