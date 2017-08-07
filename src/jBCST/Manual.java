package jBCST;

/**
 * @author mundopacheco
 * @author rvelseg
 */

public class Manual {

    /**
     * The number of trials given by default to the subject. This
     *  value can be changed in the dialog for subject registration.
     */
    public int trials = 15;

    /**
     * The names of the parameters for cards. These names are also the
     * names of the elegible criterions to evaluate right
     * choices. Don't use blank spaces in these names.
     */
    String[] param_names = {"color", "figure", "amount"};

    /**
     * The values that the parameters of the cards can have.
     */
    String[][] param_values = {
        {"Red", "Blue", "Yellow", "Green"},
        {"Star", "Circle", "Square", "Triangle"},
        {"1", "2", "3", "4"}};

    /**
     * The number of consecutive righ answers that will produce a
     * criterion change.
     */
    int block_size = 5;

    /**
     * The indexes for the criteria used. In this example, the first
     * criterion will be figure, because here the first entry is 1,
     * and param_names[1] is "figure". After a certain amount of right
     * choices, the criterion will change to color, since here the
     * second entry is 0, and param_names[0] is "color".
     */
    int[] criteria_blocks = {1, 0, 2, 1, 2, 0};

    /**
     * These numbers define the cards used as possible choices for
     * trials, these are cards always visible, that never change.
     */
    int[][] reference = {{3, 3, 3},
			 {2, 2, 2},
			 {1, 1, 1},
			 {0, 0, 0}};

    /**
     * These are the names of the evaluations (or classifications) to
     * be made over the subject answers. The first must be always the
     * right-choice evaluation. The order is important.
     */
    String[] classifications_name = {"Hit",
				     "Perseverance",
				     "Fail to keep set"}; // add other evaluations as needed.

    /**
     * Acronyms for the classifications above, used in
     * tables. Correspondence to "names" dependes on the order. Spaces
     * are not allowed in these acronyms.
     */
    String[] classifications_acronym = {"H",
					"P",
					"FKS"};

    /**
     * An empty array of functions, filled in the constructor, see
     * below.
     */
    Classification[] classifications;

    /**
     * The actual function to evaluate if a given answer is right.
     */
    boolean RightChoice(int pos, Deck reference, Card query,
			int[] choices, int current_criterion, Manual man) {
	// The index of what the subject choose.
        int choice = choices[pos] - 1;
	// The index of the parameter currently used criterion.
        int i = man.criteria_blocks[current_criterion];
	// If the specified parameter of the chosen card equals this
	// same parameter in the card given as trial.
        if (reference.cards[choice].param_values[i]
	    == query.param_values[i]) {
            return true;
        } else {
	    return false;
	}
    }

    /**
     * The function to evaluate the second classification. In this
     * case, to evaluate if an error is perseverative.
     */
    boolean Perseverative(int pos, Manual man, boolean[][] csf_res, boolean[][] matchingCriteria) {
        int temp = pos - 1;
        if (pos > 0) {
            if (!csf_res[pos][0] && !csf_res[temp][0]) {
//                System.out.println("prev: " +  temp + ", pos: " + pos);
                for (int j = 0; j < man.param_names.length; j++) {
//                    System.out.println("Criterion " + j + ": "
//                            + matchingCriteria[temp][j] + " " + matchingCriteria[pos][j]);
                    if (matchingCriteria[temp][j] && matchingCriteria[pos][j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * The function to evaluate the third classification.
     */
    boolean FailKeepingSet(int pos, boolean[][] csf_res) {
	int streak_min_length = 5;
	if (pos >= streak_min_length && !csf_res[pos][0]) {
	    for (int i=pos-1; i>=pos-streak_min_length; i--) {
		if (!csf_res[i][0]) {
		    return false;
		}
	    }
	    return true;
	}
	return false;
    }

    // Add other functions here to evaluate whatever you need.

    /**
     * This is the constructor. The main purpose here is to organize
     * the functions previously declared, in an array (to be able to
     * iterate). Each function can use a number of variables
     * containing information on the test and the answers of the
     * subject, see Interfaces.java for details.
     */
    Manual() {
        this.classifications = new Classification[]{
            new Classification() {
                public boolean check(int pos, Deck queries, Deck reference, Card query, int[] choices,
                        int current_criterion, Manual man, boolean[][] csf_res, boolean[][] matchingCriteria) {
                    return RightChoice(pos, reference, query, choices, current_criterion, man);
                }
            },
            new Classification() {
                public boolean check(int pos, Deck queries, Deck reference, Card query, int[] choices,
                        int current_criterion, Manual man, boolean[][] csf_res, boolean[][] matchingCriteria) {
                    return Perseverative(pos, man, csf_res, matchingCriteria);
                }
            },
            new Classification() {
                public boolean check(int pos, Deck queries, Deck reference, Card query, int[] choices,
                        int current_criterion, Manual man, boolean[][] csf_res, boolean[][] matchingCriteria) {
                    return FailKeepingSet(pos, csf_res);
                }
            }
	    // continue this array of functions with your own functions as needed.
        };
    }
}
