/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jBCST.UserData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextField;
import jBCST.Serialize;
import jBCST.jBCST;

/**
 * @author mundopacheco
 * @author rvelseg
 */

/**
 * Class that implements User object
 */
public class User {

    private int trials;
    private String name;
    private String serializableName;
    private int age;
    private String group;
    private int number;

    /**
     * Method that initializes a new User from dataPanel, new user
     *
     * @param number
     * @param trials
     * @param name
     * @param serializableName
     * @param age
     * @param group
     */
    public User(int number, int trials, String name, String serializableName, int age, String group) {
        this.trials = trials;
        this.name = name;
        this.serializableName = serializableName;
        this.age = age;
        this.group = group;
        this.number = number;
    }

    /**
     * Method that creats a non null empty User.
     */
    public User() {
        this.number = 0;
        this.trials = 0;
        this.name = "";
        this.serializableName = "";
        this.age = 0;
        this.group = "";
    }

    /**
     * Method that initializes a User from a list of old Users in Users.csv.
     *
     * @param number
     * @param trials
     * @param name
     * @param age
     * @param group
     */
    public User(int number, int trials, String name, int age, String group) {
        this.number = number;
        this.trials = trials;
        this.name = name;
        this.serializableName = fixName(name);
        this.age = age;
        this.group = group;
    }

    /**
     * Number setter
     *
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Trials setter
     *
     * @param trials
     */
    public void setTrials(int trials) {
        this.trials = trials;
    }

    /**
     * name setter
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * age setter
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * group setter
     *
     * @param group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * tirals getter
     *
     * @return
     */
    public int getTrials() {
        return trials;
    }

    /**
     * name getter
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * serializable name getter
     *
     * @return
     */
    public String getSerializableName() {
        return serializableName;
    }

    /**
     * age getter
     *
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * group getter
     *
     * @return
     */
    public String getGroup() {
        return group;
    }

    /**
     * number getter
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     * Method that generates Java confirm dialog then creates a new User with
     * acquired data.
     */
    private static User dataPanel(boolean monkey) {
        int opcion = -1;
        int newTrials = jBCST.manual.trials;
        int newNumber = Serialize.users.quantity() + 1;
        String newGroup = "Control";
        String newName = "Name(s) Last Name";
        String newSerializableName = "";
        int nuevaEdad = 0;
        User user = null;

        while (opcion != 0 && !monkey) {
            JLabel lno = new JLabel("Number:");
            JTextField tno = new JTextField(String.valueOf(newNumber), 20);
            tno.setEditable(false);

            JLabel ltrials = new JLabel("Number of cards:");
            JTextField ttrials = new JTextField(String.valueOf(newTrials), 10);
            ttrials.setEditable(true);

            JLabel lgpo = new JLabel("Group:");
            JTextField tgpo = new JTextField(newGroup, 20);

            JLabel lnombre = new JLabel("Name:");
            JTextField tnombre = new JTextField(newName, 20);

            JLabel ledad = new JLabel("Age:");
            JTextField tedad = new JTextField(String.valueOf(nuevaEdad), 10);

            Object[] formulario = {lno, tno, ltrials, ttrials, lgpo, tgpo, lnombre, tnombre, ledad, tedad};

            opcion = showConfirmDialog(null, formulario,
                    "Data input", OK_CANCEL_OPTION);

            try {
                newNumber = Integer.parseInt(tno.getText());
                newGroup = tgpo.getText();
                newTrials = Integer.parseInt(ttrials.getText());
                newName = tnombre.getText();
                nuevaEdad = Integer.parseInt(tedad.getText());
                newSerializableName = fixName(newName);

                if (opcion != 0) {
                    showMessageDialog(null,
                            "Unable to add user",
                            "Exit", ERROR_MESSAGE);
                    System.exit(0);
                }

            } catch (NumberFormatException ex) {
                showMessageDialog(null,
                        "Pleass enter a number",
                        "Input valid amount", ERROR_MESSAGE);
                opcion = -1;
            } catch (IllegalArgumentException ex) {
                showMessageDialog(null,
                        ex.getMessage(),
                        "Input valid amount", ERROR_MESSAGE);
                opcion = -1;
            }
            if (nuevaEdad < 18) {
                int procede = JOptionPane.showConfirmDialog(null,
                        "You are about to register a minor. \n\tContinue?",
                        "Verify", JOptionPane.OK_CANCEL_OPTION);
                if (procede == JOptionPane.OK_OPTION) {
                    opcion = 0;
                } else {
                    opcion = -1;
                }
            }
            if (getSpecialCharacterCount(newSerializableName) || newSerializableName.length() > 50) {
                showMessageDialog(null, "Please only use alphanumeric characters and avoid using:"
                        + "\n :´[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]"
                        + "\n Use maximum of 50 characters counting spaces");
                opcion = -1;
            }
        }

        if (monkey) {
            newNumber = 0;
            newTrials = 64;
            newName = "MonkeyMode";
            newSerializableName = "MonkeyMode";
            nuevaEdad = 100;
            newGroup = "Monkey";
        }

        user = new User(newNumber, newTrials, newName, newSerializableName, nuevaEdad, newGroup);
        Serialize.users.addUser(user);
        return user;
    }

    @Override
    public String toString() {
        return "User number: " + this.number
                + "\nTrials amount: " + this.trials
                + "\nName: " + this.name
                + "\nAge: " + this.age
                + "\nGroup: " + this.group + "\n";
    }

    /**
     * Method that compares all the atributes of a this user with the atributes
     * of user @param.
     *
     * @param user
     * @return
     */
    public boolean equals(User user) {
        return this.age == user.age
                && this.group.equals(user.group)
                && (this.name.equals(user.name) || this.serializableName.equals(user.serializableName))
                && this.number == user.number
                && this.trials == user.trials;
    }

    /**
     * Method that removes tonic accent (Spanish language)
     *
     * @param name
     * @return
     */
    public static String fixName(String name) {
        name = name.replace('ñ', 'n');
        name = name.replace('Ñ', 'N');
        name = name.replace('á', 'a');
        name = name.replace('é', 'e');
        name = name.replace('í', 'i');
        name = name.replace('ó', 'o');
        name = name.replace('ú', 'u');
        name = name.replace('Á', 'A');
        name = name.replace('É', 'E');
        name = name.replace('Í', 'I');
        name = name.replace('Ó', 'O');
        name = name.replace('Ú', 'U');

        return name;
    }

    /**
     * Method that returns true when a String has a special char
     *
     * @param s
     * @return
     */
    public static boolean getSpecialCharacterCount(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("\\p{Punct}");
        Matcher m = p.matcher(s);
        boolean b = m.find();
        return b;
    }

    /**
     * Method that goes to call dataPanel and returns new User
     *
     * @param monkey
     * @return
     */
    public static User newUser(boolean monkey) {
        if (!monkey) {
            showMessageDialog(null,
                    "Register a new user.",
                    "New user", INFORMATION_MESSAGE);
        }
        User user = dataPanel(monkey);
        if (!monkey) {
            showMessageDialog(null,
                    "Registration has been successfully completed.\n\n" + user.toString(),
                    "User information", INFORMATION_MESSAGE);
        }
        return user;
    }

}
