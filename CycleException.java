import static javax.swing.JOptionPane.*;

/**
 *
 * @author AArgento
 * @date 23 April 2017
 * @class CMSC 350
 * @purpose  Define custom exception class to be called if circular dependency is found.
 *
 */

class CycleException extends Exception {

    CycleException(){
        showMessageDialog(null, "Circular Dependency Detected " +
                "\nReenter Input File and Choose Different Class.");

        CMSC350Project4 CM = new CMSC350Project4();
        CM.classSelection.setSelectedItem("");
   }

} //CycleException
