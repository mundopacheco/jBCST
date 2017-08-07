package jBCST;

/**
 * @author mundopacheco
 * @author rvelseg
 */

interface Classification {

    boolean check(int pos,
		  Deck queries,
		  Deck mainOptionCards,
		  Card query,
		  int[] choices,
		  int current_criterion,
		  Manual man,
		  boolean[][] csf_res,
		  boolean[][] matchingCriteria
    );
}

/**
 * pos: After position. Is the number of trial given to the subject,
 * starting in zero.
 *
 * queries: All the queries than have and will be given to the subject.
 *
 * mainOptionCards: Cards used as possible choices for trials, these
 * are cards always visible, that never change.
 *
 * query: The current card given as trial to the subject, this equals
 * queries[pos].
 *
 * choices: An array containing the previous choices and the current
 * choice, length of this array is pos+1.
 *
 * current_criterion: The index of the current parameter (of the
 * cards) used as criterion.
 *
 * man: The manual, containing the main customizable options.
 *
 * csf_res: The results of the classifications, previous to the one
 * being evaluated. A classification ca depend on the value of
 * previous classifications, for example to know if an error is
 * perseverative you need to know this is an actual error.
 *
 * matchingCriteria: A choice of the subject can match more than one
 * criteria (parameters) of the card given as trial. For example, both
 * cards can have the same color and the same figure. This is an array
 * of booleans describing the matching criteria of the present and
 * previous trials.
 */
