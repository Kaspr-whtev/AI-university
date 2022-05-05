import java.util.ArrayList;

public class airCondition {
    //"low", "optimal", "high"
    private double[] UT = {0,0,0};
    //"negative", "zero", "positive", "large"
    private double[] Tdif = {0,0,0,0};
    //"optimal", "humid"
    private double[] Td = {0,0};
    //"low", "regular"
    private double[] EV = {0,0};

    private final String[] CS = {"low", "medium", "fast"};
    private final String[] FS = {"low", "medium", "fast"};
    private final String[] MO = {"ac", "de"};
    private final String[] FN = {"away", "toward"};

    //fuzzifies crisp values
    public airCondition(double UT, double Tdif, double Td, double EV){
        this.UT[0] = UTcalc("low", UT);
        this.UT[1] = UTcalc("optimal", UT);
        this.UT[2] = UTcalc("high", UT);

        this.Tdif[0] = Tdifcalc("negative", Tdif);
        this.Tdif[1] = Tdifcalc("zero", Tdif);
        this.Tdif[2] = Tdifcalc("positive", Tdif);
        this.Tdif[3] = Tdifcalc("large", Tdif);

        this.Td[0] = Tdcalc("optimal", Td);
        this.Td[1] = Tdcalc("humid", Td);

        this.EV[0] = EVcalc("low", EV);
        this.EV[1] = EVcalc("regular", EV);
    }

    //calculates fuzzy values for each variable
    public static double UTcalc(String type, double UT){
        switch (type){
            case "low":
                if (UT <= 22)
                    return 1;
                else if (UT <= 25)
                    return (25-UT)/3;
                else return 0;
            case "optimal":
                if (UT >= 22 && UT <= 25)
                    return (UT-22)/3;
                else if (UT >= 25 && UT <= 28)
                    return (28-UT)/3;
                else return 0;
            case "high":
            default:
                if (UT >= 25 && UT <= 28)
                    return (UT-25)/3;
                else if (UT >= 28 && UT <= 30)
                    return 1;
                else return 0;
        }
    }
    public static double Tdifcalc(String type, double Tdif){
        switch (type){
            case "negative":
                if (Tdif <= -0.9 && Tdif >= -1)
                    return 1;
                else if (Tdif >= -0.9 && Tdif <= 0)
                    return -0.9*Tdif;
                else return 0;
            case "zero":
                if (Tdif >= -0.5 && Tdif <= 0)
                    return (Tdif+0.5)*2;
                else if (Tdif <= 0.5 && Tdif >= 0)
                    return (0.5-Tdif)*2;
                else return 0;
            case "positive":
                if (Tdif <= 1 && Tdif >= 0)
                    return Tdif;
                else if (Tdif >= 1 && Tdif <= 2)
                    return 2-Tdif;
                else return 0;
            case "large":
            default:
                if (Tdif >= 1 && Tdif <= 2)
                    return Tdif-1;
                else if (Tdif >= 2 && Tdif <= 3)
                    return 1;
                else return 0;
        }
    }
    public static double Tdcalc(String type, double Td){
        switch (type){
            case "optimal":
                if (Td <= 11 && Td >= 10)
                    return 1;
                else if (Td >= 11 && Td <= 14)
                    return (14-Td)/3;
                else return 0;
            case "humid":
            default:
                if (Td >= 12 && Td <= 15)
                    return (Td-12)/3;
                else if (Td >= 15 && Td <= 18)
                    return 1;
                else return 0;
        }
    }
    public static double EVcalc(String type, double EV){
        switch (type){
            case "low":
                if (EV <= 160 && EV >= 130)
                    return 1;
                else if (EV >= 160 && EV <= 180)
                    return (180-EV)/20;
                else return 0;
            case "regular":
            default:
                if (EV >= 170 && EV <= 190)
                    return (EV-170)/20;
                else if (EV >= 190 && EV <= 220)
                    return 1;
                else return 0;
        }
    }

    //'AND' and 'OR', implemented as min() and max() respectively
    private static double AND(double a, double b, double c, double d){
        return Math.min(Math.min(a,b), Math.min(c,d));
    }
    private static double OR(double a, double b){
        return Math.max(a,b);
    }

    public static double[] CSDimens(String type, double[] SC){
        //bottomRight, topRight, bottomLeft, topLeft, height
        double[] corners = new double[5];
        switch (type){
            case "low":
                corners[0] = 50;
                corners[1] = 50-20*SC[0];
                corners[2] = 0;
                corners[3] = 0;
                corners[4] = SC[0];
                return corners;
            case "medium":
                corners[0] = 80;
                corners[1] = 80-20*SC[1];
                corners[2] = 40;
                corners[3] = 20*SC[1]+40;
                corners[4] = SC[1];
                return corners;
            case "fast":
            default:
                corners[0] = 100;
                corners[1] = 100;
                corners[2] = 70;
                corners[3] = 20*SC[2]+70;
                corners[4] = SC[2];
                return corners;
        }
    }
    public static double[] FSDimens(String type, double[] FS){
        return CSDimens(type, FS);
    }
    public static double[] MODimens(String type, double[] MO){
        //bottomRight, topRight, bottomLeft, topLeft, height
        double[] corners = new double[5];
        switch (type){
            case "ac":
                corners[0] = 1;
                corners[1] = 1;
                corners[2] = 0;
                corners[3] = MO[0];
                corners[4] = MO[0];
                return corners;
            case "de":
            default:
                corners[0] = 1;
                corners[1] = 1 - MO[1];
                corners[2] = 0;
                corners[3] = 0;
                corners[4] = MO[0];
                return corners;
        }
    }
    public static double[] FNDimens(String type, double[] FN){
        //bottomRight, topRight, bottomLeft, topLeft, height
        double[] corners = new double[5];
        switch (type){
            case "away":
                corners[0] = 90;
                corners[1] = 90;
                corners[2] = 25;
                corners[3] = 25+50*FN[0];
                corners[4] = FN[0];
                return corners;
            case "toward":
            default:
                corners[0] = 75;
                corners[1] = 75 - 50*FN[1];
                corners[2] = 0;
                corners[3] = 0;
                corners[4] = FN[0];
                return corners;
        }
    }

    public ArrayList<double[]> Interface(){
        double[] CS = {0,0,0}; //low, medium, fast
        double[] FS = {0,0,0}; //low, medium, fast
        double[] MO = {0,0}; //ac, de
        double[] FN = {0,0}; //away, toward

        for (int n=0; n<UT.length; n++){
            double xUT = UT[n];
            for (int m=0; m<Tdif.length; m++){
                double xTdif = Tdif[m];
                for (int i=0; i<Td.length; i++){
                    double xTd = Td[i];
                    for (int j=0; j<EV.length; j++){
                        double xEV = EV[j];
                        double eval = AND(xUT, xTdif, xTd, xEV);
                        //if eval is 0 then nothing will change so that iteration can be skipped
                        if (eval == 0)
                            continue;
                        //for CS, FS and FN, due to minimal correlation between MO and the other 3,
                        // it is checked separately at the end of this if
                        //EV is regular
                        if (j == 1) { //rule 13-24 and 37-48
                            //Tdif is humid and Td is optimal
                            if (m == 1 && i == 0) { //rule 16-18
                                CS[0] = OR(CS[0], eval);
                                //UT is low
                                if (n == 0) { //rule 16
                                    FS[2] = OR(FS[2], eval);
                                    FN[1] = OR(FN[1], eval);
                                }
                                //UT is medium
                                else if (n == 1) { //rule 17
                                    FS[1] = OR(FS[1], eval);
                                    FN[1] = OR(FN[1], eval);
                                }
                                else { //rule 18
                                    FS[0] = OR(FS[0], eval);
                                    FN[0] = OR(FN[0], eval);
                                }
                            //Td is humid AND ((UT is medium AND Tdif is zero) OR (UT is high AND Tdif is positive))
                            } else if (i == 1 && ((n == 1 && m == 1) || (n == 2 && m == 2))) { //rule 41 and 45
                                CS[1] = OR(CS[1], eval);
                                FS[2] = OR(FS[2], eval);
                                FN[1] = OR(FN[1], eval);
                            //Td is optimal
                            } else if (i == 0){ //rule 13-15 and 19-24
                                //(UT is medium OR UT is high) AND Tdif is positive
                                if ((n == 1 || n == 2) && m == 2){ //rule 20 and 21
                                    CS[1] = OR(CS[1],eval);
                                    FS[1] = OR(FS[1], eval);
                                    FN[1] = OR(FN[1], eval);
                                //Tdif is positive OR Tdif is large
                                } else if (m == 2 || m == 3){ //rule 19 and 22-24
                                    CS[2] = OR(CS[2], eval);
                                    FS[2] = OR(FS[2], eval);
                                    FN[1] = OR(FN[1], eval);
                                } else { //rule 13-15
                                    CS[0] = OR(CS[0], eval);
                                    FS[0] = OR(FS[0], eval);
                                    FN[0] = OR(FN[0], eval);
                                }
//                            } else if (xTd == Td[1]) { //rule 37-40, 42-44 and 46-48
                            } else {
                                //Tdif is zero AND UT is high
                                if (m == 1 && n == 2){ //rule 42
                                    CS[1] = OR(CS[1],eval);
                                    FS[1] = OR(FS[1], eval);
                                    FN[1] = OR(FN[1], eval);
                                //Tdif is NOT negativ OR UT is low
                                } else if (m != 0 || n == 0){ //rule 37, 40, 43, 44 and 46-48
                                    CS[2] = OR(CS[2], eval);
                                    FS[2] = OR(FS[2], eval);
                                    FN[1] = OR(FN[1], eval);
                                } else { //rule 38, 39
                                    CS[0] = OR(CS[0], eval);
                                    FS[0] = OR(FS[0], eval);
                                    FN[0] = OR(FN[0], eval);
                                }
                            }
//                            else { //rule 1-12 and 25-36
//                                CS[0] = OR(CS[0], eval);
//                                FS[0] = OR(FS[0], eval);
//                                FN[0] = OR(FN[0], eval);
//                            }
                            //checking MO
                            //Td is humid AND (Tdif is negative OR Tdif is zero)
                            if (i == 1 && (m == 0 || m == 1)) //rule 37-42
                                MO[1] = OR(MO[1], eval);
                            else
                                MO[0] = OR(MO[0], eval);
                        } else { //rule 1-12 and 25-36
                            CS[0] = OR(CS[0], eval);
                            FS[0] = OR(FS[0], eval);
                            FN[0] = OR(FN[0], eval);
                            MO[0] = OR(MO[0], eval);
                        }
                    }
                }
            }
        }
        ArrayList<double[]> res = new ArrayList<>();
        res.add(CS);
        res.add(FS);
        res.add(MO);
        res.add(FN);
        return res;
    }

    public double[] Defuzzify(ArrayList<double[]> fuzzyOut){
        ArrayList<double[]> CSDim = new ArrayList<>();
        ArrayList<double[]> FSDim = new ArrayList<>();
        ArrayList<double[]> MODim = new ArrayList<>();
        ArrayList<double[]> FNDim = new ArrayList<>();

        //get dimensions of each of the shapes created by the fuzzy output and the graphs of the output variables
        for (int n=0; n<CS.length; n++) {
            CSDim.add(CSDimens(CS[n], fuzzyOut.get(0)));
            FSDim.add(FSDimens(FS[n], fuzzyOut.get(1)));
            if (n < MO.length) {
                MODim.add(MODimens(MO[n], fuzzyOut.get(2)));
                FNDim.add(FNDimens(FN[n], fuzzyOut.get(3)));
            }
        }

        //calculate the area of each centre of sum for each output variable
        //CS, FS, MO, FN
        double[] crispOut = new double[4];
        crispOut[0] = findCentre(CSDim);
        crispOut[1] = findCentre(FSDim);
        crispOut[2] = findCentre(MODim);
        crispOut[3] = findCentre(FNDim);

        return crispOut;
    }

    private double findCentre(ArrayList<double[]> dimens){
        double numerator = 0;
        double denominator = 0;
        for (double[] dim : dimens){
            double area = centreOfSum.trapezoidArea(dim[0],dim[1],dim[2],dim[3],dim[4]);
            double centre = centreOfSum.centreOfArea(dim[0],dim[1],dim[2],dim[3]);
            numerator += area*centre;
            denominator += area;
        }
        return numerator/denominator;
    }

    public static void printFuzzy(ArrayList<double[]> list){
        System.out.println("CS:");
        System.out.println("Low: "+list.get(0)[0]+" Medium: "+list.get(0)[1]+" Fast: "+list.get(0)[2]);
        System.out.println("FS:");
        System.out.println("Low: "+list.get(1)[0]+" Medium: "+list.get(1)[1]+" Fast: "+list.get(1)[2]);
        System.out.println("MO:");
        System.out.println("AC: "+list.get(2)[0]+" DE: "+list.get(2)[1]);
        System.out.println("FN:");
        System.out.println("Away: "+list.get(3)[0]+" Towards: "+list.get(3)[1]);

    }
    public static void printCrisp(double[] crisp){
        System.out.println("The Compressor should work at " + crisp[0] + "% speed");
        System.out.println("The Fan should work at " + crisp[1] + "% speed");
        System.out.println("The AC unit should operate in " + crisp[2] + " part as a Dehumidifier");
        System.out.println("The Fins should be directed " + crisp[3] + " degrees away from the occupant");
    }
}
