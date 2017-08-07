/**
 * This is a class intended to have only one instance, that is why all
 * variables and methods are static, in this way they work as having a
 * global scope.
 */

package jBCST;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jBCST.UserData.User;

/**
 * @author mundopacheco
 * @author rvelseg
 */
public class jBCST {

    static ImageIcon bee = new ImageIcon("ImagesCardName/bee.jpg");
    static User participant;
    static long startTime = 0;
    static boolean finPorTiempo = false;
    static int repetitions = 0;
    static final int maxTrials = 128;
    static String path = "Output/SelectedCards";
    static String[][] review;
    static Timer timer = new Timer();
    static final int MINUTE = 1000 * 60;// in milli-seconds.
    static public Manual manual;

    /**
     * Method that generates a list of the Users in Salida/Users.csv
     *
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    static void showUsers() throws
            InterruptedException,
            InvocationTargetException,
            IOException {
        String titulo = "Subject List";
        JTextArea listado = new JTextArea(35, 40);
        for (int i = 0; i < Serialize.users.quantity(); i++) {
            listado.append(Serialize.users.getUser(i) + "\n");
        }
        listado.setEditable(false);
        JScrollPane contenedor = new JScrollPane(listado);
        JOptionPane.showMessageDialog(null, contenedor, titulo,
                JOptionPane.INFORMATION_MESSAGE);
        jBCST.main(new String[0]);
    }

    /**
     * Thread that checks if 10 minutes have passed.
     */
    static class TaskVerify extends TimerTask {

        @Override
	public void run() {
            JOptionPane.showMessageDialog(null, "10 minutes have transpired",
                    "10 minute warning", 0, bee);
            jBCST.finPorTiempo = true;
        }
    }

    /**
     * Method that creates a String array with random integers (1 to 4) ment to
     * create a random answer in Frame.
     */
    static void monkeyMode() {
        jBCST.review = new String[jBCST.manual.trials][1];
        for (int i = 0; i < jBCST.manual.trials; i++) {
            jBCST.review[i][0] = "" + (new Random().nextInt(4) + 1);
        }
    }

    /**
     * Menu with options
     *
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    static void runMenu() throws
            InterruptedException,
            InvocationTargetException,
            IOException {

        String opcion = "";
        String[] opciones = {
	    "Perform test",
            "Show user list",
	    "Monkey Mode",
	    "Exit"};

        opcion = (String) JOptionPane.showInputDialog(null,
                "What would you like to do?", "Choose",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (opcion == null || "Exit".equals(opcion)) { // Exit on CLOSE  WINDOW
            System.exit(0);
        } else if (opcion.equals(opciones[0])) { // Perform test
            jBCST.participant = User.newUser(false);
            jBCST.manual.trials = jBCST.participant.getTrials();
            Serialize.load();
            jBCST.startTime = System.currentTimeMillis();
	    jBCST.timer.schedule(new TaskVerify(), 10 * jBCST.MINUTE);
	    try {
                Game thegame = new Game();
                Game.run(false);
            } catch (IOException ex) {
                Logger.getLogger(jBCST.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
		jBCST.timer.cancel();
		jBCST.timer.purge();
		System.out.println("timer killed.");
	    }
            ///////////////////////////////////
        }
//        else if (opcion.equals(opciones[5])) { // read old test
//            // LoadOldRun.loadOldRun();
//            // jBCST.manual.trials = Serialize.length;
//            // if (LoadOldRun.incomplete) {
//            //     Serialize.userFolder = "Output/" + LoadOldRun.oldRunName;
//            //     Game.setTotalFromFile();
//            //     Serialize.serializeResults();
//            //     Serialize.replaceCardsFile(LoadOldRun.filePath);
//            //     JOptionPane.showMessageDialog(null, "Se crearon los Resultados y el "
//            //             + "Registro del archivo seleccionado.",
//            //             "Archivos Creados", 0, bee);
//            // }
//            // System.exit(0);
//            ////////////////////////////
//	    JOptionPane.showMessageDialog(null,
//	      "Not working.",
//	      "", 0, jBCST.bee);
//	    jBCST.main(new String[0]);
//        }
        else if (opcion.equals(opciones[1])) { //
            jBCST.showUsers();
            ////////////////////////////
        } else if (opcion.equals(opciones[2])) { // Monkey mode
            jBCST.participant = User.newUser(true);
            Serialize.load();
            jBCST.monkeyMode();
            jBCST.startTime = System.currentTimeMillis();
            Game thegame = new Game();
            Game.run(true);
            ////////////////////////////
        }
    }

    /**
     * @param args
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException the command line arguments
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static void main(String[] args) throws
            IOException,
            InterruptedException,
            InvocationTargetException {

        if (jBCST.repetitions == 0) {
	    jBCST.manual = new Manual();
            Serialize.verifyDataFiles();
            Serialize.loadUserArray();
        }
        jBCST.repetitions++;
        jBCST.runMenu();
    }
}
