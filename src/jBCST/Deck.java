package jBCST;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author mundopacheco
 * @author rvelseg
 */

class Deck {

    Card[] cards;
    int size;
    String name;

    // Use given values
    Deck(String name, Manual man, int[][] values) {
	this.size = values.length;
        this.name = name;
        this.cards = new Card[size];

        for (int i = 0; i < values.length; i++) {
	    this.cards[i] = new Card(values[i]);
        }
    }

    // Use random values
    Deck(String name, Manual man, int size) {
	this.size = size;
        this.name = name;
        this.cards = new Card[size];

	int[] vals = new int[man.param_values.length];
	int[] max  = new int[man.param_values.length];
	for (int i = 0; i < man.param_values.length; i++) {
	    max[i] = man.param_values[i].length;
	}
	for (int i = 0; i < this.size; i++) {
	    for (int j = 0; j < man.param_values.length; j++) {
		vals[j] = ThreadLocalRandom.current().nextInt(0, max[j]);
	    }
	    this.cards[i] = new Card(vals);
	}
    }

    void print_oneline(Manual man) {
        System.out.print("\n");
        for (int i = 0; i < this.size; i++) {
            // System.out.print((i + 1) + ": ");
            this.cards[i].print_tight(man);
            System.out.print(" | ");
        }
        System.out.print("\n");
    }
}
