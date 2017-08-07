/**
 * This is not an instantiable class, but a collection of methods, that is why:
 *
 * - the name does not describe a kind of object, but an action
 *
 * - the class is final
 *
 * - all variables and methods are static.
 */
package jBCST;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import jBCST.UserData.User;
import jBCST.UserData.UserArray;

/**
 * @author mundopacheco
 * @author rvelseg
 */
public final class Serialize {

    static String userFolder;
    static String usersPath = "Output/Users/Users.csv";
    static String slectedCardsPathAndName;
    static String resultsPathAndName;
    static int length = 0;
    /**
     * usersFile is an empty UserArray with size 10
     */
    public static UserArray users = new UserArray();

    static DateFormat df = new SimpleDateFormat("-dd.MM.yyyy-HH.mm.ss");

    /**
     * Method that checks if the necessary files and folders are available. It
     * is able to create the files that are non image files but exits when
     * images folder aren't available.
     */
    static void verifyDataFiles() {

        File Output = new File("Output");
        File usersFolder = new File("Output/Users");
        File images = new File("ImagesCardName/");

        boolean OutputAvailable = Output.exists();
        boolean imagesAvailable = images.exists();

        if (!imagesAvailable) {
            JOptionPane.showMessageDialog(null,
                    "The images folder was not found, the program will end.\n"
                    + "Please manually add the folder to the"
                    + " directory where the application is",
                    "Images not found", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        if (!OutputAvailable) {
            JOptionPane.showMessageDialog(null,
                    "The necessary files will be created for execution",
                    "One or more directories were not found",
                    JOptionPane.INFORMATION_MESSAGE);
            ///////// Crear Archivos y/o carpetas
            if (!OutputAvailable) {
                Output.mkdir();
                usersFolder.mkdir();
            }
            try {
                FileOutputStream file = new FileOutputStream(usersPath, false);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "An error occurred, the file Users.csv could not be created",
                        "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }

    static void load() {
        Serialize.generateUserFolder();
        Serialize.serializeUsers();
        slectedCardsPathAndName = generateSelectedCardFile();
        resultsPathAndName = generateReultsFile();
    }

    static String results(boolean incomplete) {
        if (incomplete) {
            String resultados = "";
            resultados += "\nThe test was applied on the day " + LoadOldRun.oldRunDate
                    + " at " + LoadOldRun.oldRunTime + "\n\n"
                    + jBCST.participant.getName() + "'s test was not concluded \""
                    + jBCST.participant.getTrials() + " cards where to be used. "
                    + jBCST.manual.trials + " cards where used. "
                    + "\nResults.\n\n";

            for (int j = 0; j < jBCST.manual.classifications.length; j++) {
                resultados += jBCST.manual.classifications_name[j] + " Total: "
                        + Game.csf_total[j] + ".\n";
            }
            resultados += "\n";

            return resultados;

        } else {

            Date now = new Date();
            int minutes = (int) ((System.currentTimeMillis() - jBCST.startTime) / 60000);
            int seconds = (int) (((System.currentTimeMillis() - jBCST.startTime) / 1000) % 60);
            String resultados = "";

            resultados += now + "\n"
                    + "\"" + jBCST.participant.getName()
                    + "\" was evalueated with " + jBCST.manual.trials + " cards."
                    + "\n\nResults.\n "
                    + "\n\nTotal time: " + minutes + ":" + seconds + " (m:s).\n\n";

            for (int j = 0; j < jBCST.manual.classifications.length; j++) {
                resultados += jBCST.manual.classifications_name[j]
                        + " Total: " + Game.csf_total[j] + ".\n";
            }
            resultados += "\n";

            if (jBCST.finPorTiempo) {
                resultados += "\nTest ran for longer than 10 minutes.\n";
            }

            return resultados;
        }
    }

    /**
     * Creates file with results.
     */
    static void serializeResults() {
        String now = df.format(new Date());
        String resultados = results(LoadOldRun.incomplete);
        try {
//            userFolder + "/Results" + now + ".txt"
            FileOutputStream file = new FileOutputStream(resultsPathAndName, false);
            OutputStreamWriter puente = new OutputStreamWriter(file, "UTF-8");
            BufferedWriter escritor = new BufferedWriter(puente);
            escritor.write(resultados);
            escritor.newLine();
            escritor.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, the data could not be updated correctly."
                    + "\nMake sure the file exists and is in the correct directory.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    static void replaceCardsFile(String oldFile, String newName, String ext) {
        String now = df.format(new Date());
        String newFile = userFolder + "/" + newName + now + ext;
        File oldfile = new File(oldFile);
        File newfile = new File(newFile);
        oldfile.renameTo(newfile);
    }

    /**
     * Method that generates the file where selected card will be saved.
     *
     * @return Name of the file (it's purpose is to use a single file)
     */
    static String generateSelectedCardFile() {
        String now = df.format(new Date());
//        String incSelCards = "Output/INC" + jBCST.participant.getSerializableName() + now + ".csv";
        String incSelCards = userFolder + "/INC" + jBCST.participant.getSerializableName() + now + ".csv";
        try {
            FileOutputStream file = new FileOutputStream(incSelCards, false);
            return incSelCards;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, files could not be created correctly.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return null;
    }

    static String generateReultsFile() {
        String now = df.format(new Date());
//        String incSelCards = "Output/INC" + jBCST.participant.getSerializableName() + now + ".csv";
        String incRes = userFolder + "/INC" + jBCST.participant.getSerializableName() + now + ".txt";
        try {
            FileOutputStream file = new FileOutputStream(incRes, false);
            return incRes;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, files could not be created correctly.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return null;
    }

    /**
     * Reads and saves into two dimension array the data in a given a file ment
     * for test reconstruction.
     *
     * @param path
     * @return
     */
    static String[][] readSelectedCardFile(String path) {
        String[][] readData = new String[jBCST.maxTrials][6];
        try {
            String read = "";
            FileInputStream fileInput = new FileInputStream(path);
            InputStreamReader puenteInput = new InputStreamReader(fileInput, "UTF-8");
            BufferedReader lector = new BufferedReader(puenteInput);
            while ((read = lector.readLine()) != null) {
                String[] inLine = read.split("\\,");
                readData[length][0] = inLine[0];//Carta elegida
                readData[length][1] = inLine[1];//Aciertos
                readData[length][2] = inLine[2];//persev
                readData[length][3] = inLine[3];//err mant
                readData[length][4] = inLine[4];//resp corr
                readData[length][5] = inLine[5];//time bet answ
                length++;
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, the data could not be updated correctly."
                    + "\nMake sure the file exists and is in the correct directory.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
        }

        return Arrays.copyOf(readData, length);
    }

    /**
     * Generates a single String with the usersFile in @users
     *
     * @return
     */
    static String userList() {
        String userList = "";
        for (int i = 0; i < users.quantity(); i++) {
            userList += users.getUser(i).getNumber() + ","
                    + users.getUser(i).getTrials() + ","
                    + users.getUser(i).getName() + ","
                    + users.getUser(i).getAge() + ","
                    + users.getUser(i).getGroup() + "\n";
        }
        return userList.trim();
    }

    /**
     * Prints to Users.csv the usersFile in @users.
     */
    static void serializeUsers() {
        String userList = userList();

        try {
            FileOutputStream file = new FileOutputStream(usersPath, false);
            OutputStreamWriter puente = new OutputStreamWriter(file, "UTF-8");
            BufferedWriter escritor = new BufferedWriter(puente);
            escritor.write(userList);
            escritor.newLine();
            escritor.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, the data could not be updated correctly."
                    + "\nMake sure the file exists and is in the correct directory.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    static void saveDeckQueries() {
        String data = "";
        data += "\"trial index\",";
        for (int j = 0; j < jBCST.manual.param_names.length; j++) {
            if (j != 0) {
                data += ",";
            }
            data += jBCST.manual.param_names[j];
        }
        data += "\n";
        for (int i = 0; i < Game.queries.size; i++) {
            data += i + ",";
            for (int j = 0; j < jBCST.manual.param_names.length; j++) {
                if (j != 0) {
                    data += ",";
                }
                data += Game.queries.cards[i].param_values[j];
            }
            data += "\n";
        }
        try {
            String now = df.format(new Date());
            File file = new File(userFolder + "/queries" + now + ".csv");
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, the data could not be updated correctly.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    static void saveDeckReference() {
        String data = "";
        data += "\"choice index\",";
        for (int j = 0; j < jBCST.manual.param_names.length; j++) {
            if (j != 0) {
                data += ",";
            }
            data += jBCST.manual.param_names[j];
        }
        data += "\n";
        for (int i = 0; i < Game.mainOptionCards.size; i++) {
            data += i + ",";
            for (int j = 0; j < jBCST.manual.param_names.length; j++) {
                if (j != 0) {
                    data += ",";
                }
                data += Game.mainOptionCards.cards[i].param_values[j];
            }
            data += "\n";
        }
        try {
            String now = df.format(new Date());
            File file = new File(userFolder + "/reference" + now + ".csv");
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, the data could not be updated correctly.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    static void appendSelectedCard(int pos) {
        String data = "";
        if (pos == 0) {
            data += "\"trial index\","
                    + "\"choice index\","
                    + "\"criterion index\","
                    + "\"time between answers [ms]\"";
            for (int j = 0; j < jBCST.manual.classifications.length; j++) {
                data += ","
                        + jBCST.manual.classifications_acronym[j];
            }
            for (int j = 0; j < jBCST.manual.param_names.length; j++) {
                data += ",\"MC " + jBCST.manual.param_names[j] + "\"";
            }
            data += "\n";
        }
        if (pos < Game.queries.size) {
            data += pos
                    + "," + (Game.choices[pos] - 1) //TODO: refactor to save indexes in this array
                    + "," + Game.criterion[pos]
                    + "," + Game.millisecondsBetweenAnswers[pos];
        }
        for (int j = 0; j < jBCST.manual.classifications.length; j++) {
            data += "," + Game.csf_res[pos][j];
        }
        for (int j = 0; j < jBCST.manual.param_names.length; j++) {
            data += "," + Game.matchingCriteria[pos][j];
        }
        data += "\n";
        try {
            File file = new File(Serialize.slectedCardsPathAndName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, the data could not be updated correctly.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Loads the data from Users.csv.
     */
    static void loadUserArray() {
        try {
            String leida;
            FileInputStream file = new FileInputStream(usersPath);
            InputStreamReader puente = new InputStreamReader(file, "UTF-8");
            BufferedReader lector = new BufferedReader(puente);

            while ((leida = lector.readLine()) != null) {
                String[] valores = leida.split("\\,");
                if (!valores[0].equals("")) {
                    int number = Integer.parseInt(valores[0]); // int number
                    int trials = Integer.parseInt(valores[1]); // int trials
                    String name = valores[2]; // String name
                    int age = Integer.parseInt(valores[3]); // int age
                    String group = valores[4]; // String group
                    User user = new User(number, trials, name, age, group);
                    users.addUser(user);
                }
            }
            lector.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred, IO exception could not load the users correctly"
                    + "\nMake sure the file exists and is in the correct directory.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "An error has occurred, File not found could not load the users correctly"
                    + "\nMake sure the file exists and is in the correct directory.",
                    "Irrecoverable error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Method that creates the folder with users info
     */
    static void generateUserFolder() {
        String now = df.format(new Date());
        userFolder = "Output/" + jBCST.participant.getSerializableName();
        File mainUserFolder = new File(userFolder);
        mainUserFolder.mkdir();
    }
}
