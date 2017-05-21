import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.IOException;

/**
 * @author AArgento
 * @date 23 April 2017
 * @class CMSC350
 * @purpose Retrieves and returns contents of system clipboard
 */

abstract class CopyPaste implements ClipboardOwner{

    String getClipboard() {
        String clipboardContents = "";

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable contents = clipboard.getContents(null);
        boolean hasText = (contents != null) &&
                        contents.isDataFlavorSupported(DataFlavor.stringFlavor);

        if (hasText) try {
            return (String) contents.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }

        return clipboardContents;

    }//end getClipboard

}//end CopyPaste

