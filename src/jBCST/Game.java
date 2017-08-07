/**
 * This is a class intended to have only one instance, that is why all variables
 * and methods are static, in this way they work as having a global scope.
 */
package jBCST;

import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * The name of this class could probably be "test" but it could cause confusion
 * with the actual tests of the application, which should eventually be
 * included.
 *
 * @author mundopacheco
 * @author rvelseg
 */
class Game {

    static Deck mainOptionCards;
    static Deck queries;
    static int[] choices;
    static boolean[][] matchingCriteria;
    static boolean[][] csf_res;   // classification responses
    static int[] csf_total;
    static Time[] timeOfAnswers;
    static long[] millisecondsBetweenAnswers;

    // This is the index, in the array Manual.criteria_blocks, for the
    // current criterion.
    static int current_criterion = 0;

    // This is an array registering the index of the current
    // criterion, in the array Manual.criteria_blocks, when the i-th
    // choice was made.
    static int[] criterion;

    Game() {
        Game.mainOptionCards = new Deck("main option cards",
                jBCST.manual,
                jBCST.manual.reference);
        Game.queries = new Deck("queries",
                jBCST.manual,
                jBCST.manual.trials);
        Serialize.saveDeckReference();
        Serialize.saveDeckQueries();
    }

    static void run(boolean MonkeyMode) throws IOException {

        Game.choices = new int[Game.queries.size];
        Game.criterion = new int[Game.queries.size];
        Game.csf_res = new boolean[Game.queries.size][jBCST.manual.classifications.length];
        Game.csf_total = new int[jBCST.manual.classifications.length];
        Game.matchingCriteria = new boolean[Game.queries.size][jBCST.manual.param_names.length];
        Game.timeOfAnswers = new Time[Game.queries.size];
        Game.millisecondsBetweenAnswers = new long[Game.queries.size];

        for (int i = 0; i < Game.queries.size; i++) {
            Game.mainOptionCards.print_oneline(jBCST.manual);
            Game.queries.cards[i].print_tight(jBCST.manual);
            System.out.print("\n");
            System.out.println("Current criterion : "
                    + jBCST.manual.param_names[jBCST.manual.criteria_blocks[current_criterion]]);
            Game.choices[i] = Game.get_choice(i, MonkeyMode);
            Game.registerTime(i);
            Game.getMatchingCriteria(i);
            int j = 0;
            for (Classification classification : jBCST.manual.classifications) {
                Game.csf_res[i][j] = classification.check(
                        i, Game.queries, Game.mainOptionCards,
                        Game.queries.cards[i], Arrays.copyOf(choices, i + 1),
                        Game.current_criterion, jBCST.manual, Arrays.copyOf(csf_res, i + 1),
                        Game.matchingCriteria);

                System.out.println(jBCST.manual.classifications_name[j]
                        + " : " + csf_res[i][j]);
                j++;
            }
            if (!Game.csf_res[i][0] && !MonkeyMode) {
                Game.showErrorMessageDialog();
            }
            Game.update_criterion(i);
            Game.saveChanges(i);
        }

    }

    static void getTotals() {
//        System.out.println(jBCST.manual.classifications.length);
        for (int j = 0; j < jBCST.manual.classifications.length; j++) {
            int classification_total = 0;
            for (int i = 0; i < Game.queries.size; i++) {
                if (Game.csf_res[i][j]) {
                    classification_total += 1;
                }
            }
//            System.out.println(j);
            Game.csf_total[j] = classification_total;
        }
    }

    /**
     * Saves into Time array the time at which a card is selected. Then it
     * registers the time transpired between answers.
     */
    static void registerTime(int pos) {
        Game.timeOfAnswers[pos] = new Time(System.currentTimeMillis());
        Game.millisecondsBetweenAnswers[pos] = Game.timeBetweenAnswers(pos);
    }

    /**
     * Computes elapsed time between answers in milliseconds.
     */
    static long timeBetweenAnswers(int pos) {
        if (pos > 0) {
            return Game.timeOfAnswers[pos].getTime()
                    - Game.timeOfAnswers[pos - 1].getTime();
        }
        return Game.timeOfAnswers[pos].getTime()
                - jBCST.startTime;
    }

    static void getMatchingCriteria(int i) {
        for (int j = 0; j < jBCST.manual.param_names.length; j++) {
            if (Game.mainOptionCards.cards[choices[i] - 1].param_values[j]
                    == Game.queries.cards[i].param_values[j]) {
                Game.matchingCriteria[i][j] = true;
            } else {
                Game.matchingCriteria[i][j] = false;
            }
        }
    }

    static void update_criterion(int i) {
        boolean thereIsNext
                = Game.current_criterion < jBCST.manual.criteria_blocks.length;
        if (i < Game.queries.size) {
            Game.criterion[i]
                    = jBCST.manual.criteria_blocks[current_criterion];
            int right_choices_block = 0;
            int j = i;
            while (j >= 0) {
                if (Game.criterion[j] == Game.criterion[i]
                        && Game.csf_res[j][0]) {
                    right_choices_block += 1;
                    j--;
                } else {
                    break;
                }
            }
            if (right_choices_block
                    == jBCST.manual.block_size && thereIsNext) {
                Game.current_criterion++;
                System.out.println(
                        "Criterion changed to "
                        + jBCST.manual.param_names[jBCST.manual.criteria_blocks[current_criterion]]);
            }
        }
    }

    static int get_choice(int i, boolean MonkeyMode) throws IOException {
        int choice = 0;
        if (MonkeyMode) {
            return Integer.parseInt(jBCST.review[i][0]);
        }
        String curr = Game.queries.cards[i].StringParamVal(jBCST.manual) + ".png";
        ImageIcon current = new ImageIcon("ImagesCardName/" + curr);
        ImageIcon icon1 = new ImageIcon("ImagesCardName/"
                + Game.mainOptionCards.cards[0].StringParamVal(jBCST.manual) + ".png");
        ImageIcon icon2 = new ImageIcon("ImagesCardName/"
                + Game.mainOptionCards.cards[1].StringParamVal(jBCST.manual) + ".png");
        ImageIcon icon3 = new ImageIcon("ImagesCardName/"
                + Game.mainOptionCards.cards[2].StringParamVal(jBCST.manual) + ".png");
        ImageIcon icon4 = new ImageIcon("ImagesCardName/"
                + Game.mainOptionCards.cards[3].StringParamVal(jBCST.manual) + ".png");
        Object[] options = {icon1, icon2, icon3, icon4};
        choice = JOptionPane.showOptionDialog(null, null, "Choose",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, current,
                options, options[0]);
        choice++;
//        System.out.println("choice: " + choice);
        return choice;
    }

    static void saveChanges(int i) {
        System.out.println(i + " of " + Game.queries.size);
        Game.getTotals();
        Serialize.appendSelectedCard(i);
        Serialize.serializeResults();
        if (i+1 == Game.queries.size) {
            LoadOldRun.incomplete = false;
            Serialize.serializeResults();
            Serialize.replaceCardsFile(Serialize.slectedCardsPathAndName, "SelectedCards", ".csv");
            Serialize.replaceCardsFile(Serialize.resultsPathAndName, "Results", ".txt");
            JOptionPane.showMessageDialog(null,
                    "Thank you " + jBCST.participant.getName(),
                    "Mission accomplished!", 0, jBCST.bee);
            System.exit(0);
        }
    }

    static void showErrorMessageDialog() {
        JOptionPane errorMessage = new JOptionPane(new ImageIcon("ImagesCardName/wrong.png"));
        final JDialog error = errorMessage.createDialog("Error");
        error.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        errorMessage.remove(errorMessage.getComponent(1));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                error.setVisible(false);
            }
        }).start();
        error.setVisible(true);
    }

    static void setTotalFromFile() {
	// TODO: reimplement this.

        // Game.total_right_choices = Integer.parseInt(jBCST.review[Serialize.length - 1][1]);
        // Game.total_perseverance = Integer.parseInt(jBCST.review[Serialize.length - 1][2]);
        // Game.failKeepingSet = Integer.parseInt(jBCST.review[Serialize.length - 1][3]);
    }
}
