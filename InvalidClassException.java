import static javax.swing.JOptionPane.*;

/**
 *
 * @author AArgento
 * @date 23 April 2017
 * @class CMSC 350
 * @purpose  Define custom exception class to be called if invalid class is submitted
 *
 */

class InvalidClassException extends Exception {

    InvalidClassException(){
        showMessageDialog(null, "Use ComboBox To Select Valid Class");
    }

} //end NumberFormatExpression