/**
 * This is not an instantiable class, but a collection of methods,
 * that is why:
 *
 * - the name does not describe a kind of object, but an action
 *
 * - the class is final
 *
 * - all variables and methods are static.
 */

package jBCST;

//import java.awt.EventQueue;
//import java.io.File;
//import java.lang.reflect.InvocationTargetException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import static jBCST.jBCST.participant;

/**
 * @author mundopacheco
 * @author rvelseg
 */

public final class LoadOldRun {

    static boolean incomplete = true;
    static String fileName = "";
    static String filePath = "";
    static String oldRunTime = "";
    static String oldRunDate = "";
    static String oldRunName = "";

    // /**
    //  * Method that retrieves the name from a the name of a file. This
    //  * doesn't work.
    //  */
    // static String getName(String fileName, boolean incomplete) {
    //     int end = 0;
    //     for (int i = 0; i < fileName.length(); i++) {
    //         if (fileName.charAt(i) == '-') {
    //             end = i;
    //             break;
    //         }
    //     }
    //     if (incomplete) {
    //         return fileName.substring(3, end);
    //     } else {
    //         int begining = fileName.indexOf("Salida/") + 7;
    // 	    System.out.println(fileName);
    // 	    System.out.println(begining + "--" + end);
    //         return fileName.substring(begining, end);
    //     }
    // }

    // void getTimeAndDate(String fileName) {
    //     int[] pos = new int[2];
    //     boolean first = true;
    //     for (int i = 0; i < fileName.length(); i++) {
    //         if (fileName.charAt(i) == '-') {
    //             if (first) {
    //                 pos[0] = i;
    //                 first = false;
    //             } else {
    //                 pos[1] = i;
    //             }
    //         }
    //     }
    //     this.oldRunDate = fileName.substring(pos[0]+1, pos[1]);
    //     this.oldRunTime = fileName.substring(pos[1]+1, fileName.length()-4);
    // }

    // /**
    //  * Method that loads a test from a past user. Creates files when loading
    //  * incomplete test. Implements JFileChooser.
    //  *
    //  * @throws InterruptedException
    //  * @throws InvocationTargetException
    //  */
    // static void loadOldRun() {
    //     try {
    //         EventQueue.invokeAndWait(new Runnable() {
    //             @Override
    //             public void run() {
    //                 JButton open = new JButton();
    //                 JFileChooser fc = new JFileChooser();
    //                 FileNameExtensionFilter filter = new FileNameExtensionFilter(
    //                         "Archivos formato csv", "csv");
    //                 fc.setFileFilter(filter);
    //                 fc.setCurrentDirectory(new File("Salida/"));
    //                 fc.setDialogTitle("Elige la prueba que deseas reproducir");
    //                 int choise = fc.showOpenDialog(open);
    //                 if (choise == JFileChooser.APPROVE_OPTION) {
    //                     String name = fc.getSelectedFile().getName();
    //                     String cpath = fc.getSelectedFile().getPath();
    //                     if (name.startsWith("INC")) {
    //                         incomplete = true;
    //                         oldRunName = getName(name, incomplete);
    //                         fileName = "" + name;
    //                         filePath = "" + cpath;
    //                     } else {
    //                         oldRunName = getName(cpath, incomplete);
    //                     }}
    //                     // cant delete these lines otherwise java.lang.reflect.InvocationTargetException after choosing file
    //                     participant = Serialize.users.findByName(oldRunName);

    //                     // review has to be loaded here other wise java.lang.reflect.InvocationTargetException after choosing file
    //                     jBCST.review = Serialize.readSelectedCardFile(cpath);
    //                 }
    //             }
    //         });
    //     } catch (InvocationTargetException ex) {
    //         Logger.getLogger(LoadOldRun.class.getName()).log(Level.SEVERE, null, ex);
    //     } catch (InterruptedException ex) {
    //         Logger.getLogger(LoadOldRun.class.getName()).log(Level.SEVERE, null, ex);
    //     }
    // }
}
