package de.gothaer.math;

public class MultipliziererImpl implements Multiplizierer {
    @Override
    public long multiplizier(int a,int b) {
        long result = 0;


        for (int i = 0; i < a; i++) {
            result += b;
        }

        return result;
    }
}
