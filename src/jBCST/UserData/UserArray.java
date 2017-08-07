/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jBCST.UserData;

import javax.swing.JOptionPane;

/**
 * @author mundopacheco
 * @author rvelseg
 */

/**
 * Objet that simulates an extendable array of the User class
 */
public class UserArray {

    /**
     * User Array that will simulate an extendable array.
     */
    private User[] userData;

    /**
     * Quantity of users in the UserArray
     */
    static public int quantity;

    /**
     * Method that initializes UserArray
     */
    public UserArray() {
        this.userData = new User[10];
        this.quantity = 0;
    }

    /**
     * Method that returns the array User[]
     *
     * @return
     */
    public User[] getData() {
        return userData;
    }

    /**
     * Method that returns the quantity of non null Users in a UserArray
     *
     * @return
     */
    public int quantity() {
        return this.quantity;
    }

    /**
     * Method that returns a User in the UserArray in @param position
     *
     * @param i
     * @return
     */
    public User getUser(int i) {
        return userData[i];
    }

    /**
     * Method that adds a User into the first empty position in userData
     *
     * @param user
     */
    public void addUser(User user) {
        if (quantity == userData.length) {
            aumentarTamanio();
        }
        if (!exists(user)) {
            userData[quantity++] = user;
        } else {
            JOptionPane.showMessageDialog(null, "No user added",
                    "Existing user", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Method that verifies if a user exists in user data
     * @param user
     * @return
     */
    public boolean exists(User user) {
        for (int i = 0; i < this.quantity(); i++) {
            if (this.userData[i].equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that deletes a User in userData
     *
     * @param i
     */
    public void deleteUser(int i) {
        if (i >= 0 && i < quantity) {
            this.moveEmptySpace(i);
        }
    }

    /**
     * Method that replaces old User in userData[@param i] with User @param e
     *
     * @param e
     * @param i
     */
    public void replaceUser(User e, int i) {
        if (e != null) {
            userData[i] = e;
        }
    }

    /**
     * Method that creates new array when old array is full.
     */
    public void aumentarTamanio() {
        User[] nuevo = new User[userData.length * 2];
        for (int i = 0; i < userData.length; i++) {
            nuevo[i] = userData[i];
        }
        userData = nuevo;
    }

    /**
     * Method that moves the empty position when User is deleted from userData.
     */
    private void moveEmptySpace(int emptySpace) {
        for (int i = emptySpace; i < this.quantity - 1; i++) {
            userData[i] = userData[i + 1];
        }
        userData[--quantity] = null;
    }

    /**
     * Method that compares the names of the Users in userData with a given
     * String name. Returns user if user is found in userData.
     *
     * @param name
     * @return
     */
    public User findByName(String name) {
        User user = new User();
        for (int i = 0; i < this.quantity(); i++) {
            if (userData[i].getSerializableName().trim().equals(name.trim())) {
                user = userData[i];
            }
        }
        return user;
    }

    @Override
    public String toString() {
        String list = "";
        for (int i = 0; i < this.quantity(); i++) {
            list += userData[i].toString() + "\n";
        }
        return list;
    }
}
