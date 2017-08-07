package jBCST;

/**
 * @author mundopacheco
 * @author rvelseg
 */

class Card {

    int[] param_values;

    Card(int[] values) {
        this.param_values = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            this.param_values[i] = values[i];
        }
    }

    void print_tight(Manual man) {
        for (int i = 0; i < param_values.length; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            System.out.print(man.param_values[i][this.param_values[i]]);
        }
    }

    String StringParamVal(Manual man) {
        String s = "";
        for (int i = 0; i < param_values.length; i++) {
            s += man.param_values[i][this.param_values[i]];
        }
        return s;
    }
}
