/* import all required java libraries */
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author AArgento
 * @date 23 April 2017
 * @class CMSC350
 * @purpose Define all components required for GUI. Initialize GUI. Monitor GUI for user input. Provide classes for
 *          Popup Menu.
 */

public class CMSC350Project4 extends JFrame {

    private JTextField textInput;
    private String clipboard = "";
    JComboBox classSelection;

    /* constructor */
    CMSC350Project4() {

        /* define JPanels */
        JPanel panelInput = new JPanel();
        JPanel panelClass = new JPanel();
        JPanel panelRecomp = new JPanel();

        /* define input components */
        JLabel labelInput = new JLabel("Input file name:");
        textInput = new JTextField();
        textInput.setPreferredSize(new Dimension(250, 25));
        textInput.addMouseListener(new ClickListener());
        textInput.setToolTipText("Enter valid text file including address here");


        /* define class components */
        JLabel labelClass = new JLabel("Class to recompile:");
        String[] selectionOptions = new String[]{"", "ClassA", "ClassB", "ClassC", "ClassD", "ClassE", "ClassF",
                "ClassG", "ClassH", "ClassI"};
        JComboBox <String> classSelection = new JComboBox<>(selectionOptions);
        classSelection.setPreferredSize(new Dimension(250, 25));
        classSelection.setToolTipText("Select Class from Combo Box");

        /* define buttons */
        JButton buttonGraph = new JButton("Build Directed Graph");
        JButton buttonOrder = new JButton("Topological Order");

        /* define recompilation components */
        JLabel labelRecompile = new JLabel("");
        panelRecomp.setBorder (new TitledBorder (new EtchedBorder(EtchedBorder.LOWERED), "Recompilation Order" ));
        panelRecomp.setPreferredSize(new Dimension(560, 175));

        /* add components to JPanels */
        panelInput.add(labelInput, BorderLayout.CENTER);
        panelInput.add(textInput, BorderLayout.CENTER);
        panelInput.add(buttonGraph, BorderLayout.CENTER);
        panelClass.add(labelClass, BorderLayout.CENTER);
        panelClass.add(classSelection, BorderLayout.CENTER);
        panelClass.add(buttonOrder, BorderLayout.CENTER);
        panelRecomp.add(labelRecompile, BorderLayout.CENTER);

        /* define container to group panels */
        Container containerPnlGrp = new Container();
        containerPnlGrp.setLayout(new BoxLayout(containerPnlGrp, BoxLayout.Y_AXIS));
        containerPnlGrp.add(panelInput);
        containerPnlGrp.add(panelClass);
        containerPnlGrp.add(panelRecomp);

        /* Create a frame that will be used to properly display all the components in the GUI */
        JFrame frameMainGUI = new JFrame ();
        frameMainGUI.setTitle("Class Dependency Graph");
        frameMainGUI.setPreferredSize(new Dimension(580, 280));
        frameMainGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMainGUI.add(containerPnlGrp);
        frameMainGUI.pack ();
        frameMainGUI.setLocationRelativeTo(null);
        frameMainGUI.setVisible(true);

        /*
        Monitors Build Directed Graph button for mouse events, calls Create method from DirectedGraph and displays either
        a success message or an error message base on if graph was created successfully. Checks for valid file input.
        */
        buttonGraph.addActionListener((ActionEvent ae) -> {
            String fileName = textInput.getText();
            String extention = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            String textExtention = "txt";

                if ((fileName.length() == 0) || (!extention.equals(textExtention))) {
                    showMessageDialog(null, "File Did Not Open",
                            "Failure", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean create = DirectedGraph.Create(fileName);

                    if (create)
                        showMessageDialog(null, "Graph Built Sucessfully",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    else
                        showMessageDialog(null, "Unknown Error \nPlease submit different input",
                                "Failure", JOptionPane.ERROR_MESSAGE);
                }
        }); //end buttonGraph actionListener

        /*
        Monitors Topological Order button for mouse events, calls Sort method from DirectedGraph and sets
        Recompilation Order label in GUI to correct order of precedence.
        */
        buttonOrder.addActionListener((ActionEvent ae1) -> {
            labelRecompile.setText("");
            String source = (String) classSelection.getSelectedItem();

            if (source.isEmpty()) new InvalidClassException();

            ArrayList<String> precedence;
            precedence = DirectedGraph.Sort(source);
            assert precedence != null;

            if (!precedence.isEmpty()) {
                int i = 0;
                while (i < precedence.size()) {
                    String recompileClass = precedence.get(i);
                    labelRecompile.setText(labelRecompile.getText() + " " + recompileClass);
                    i++;
                }
            }
        }); //end buttonOrder actionListener

    }//end constructor

    /* defines components of popup menu and sets text of textInput to that of the system clipboard */
    class PopupMenu extends JPopupMenu {
        JMenuItem paste;

        PopupMenu(){
            paste = new JMenuItem("Paste");
            add(paste);
            paste.addActionListener((ActionEvent e) -> {
                CopyPaste CP = new CopyPaste() {
                    @Override
                    public void lostOwnership(Clipboard clipboard, Transferable contents) {
                    }
                };

                clipboard = CP.getClipboard();
                textInput.setText(clipboard);
            });
        }
    }// end PopupMenu

    /* monitors for mouse click events and launches popup menu */
    class ClickListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if (e.isPopupTrigger())
                Pop(e);
        }
        public void mouseReleased(MouseEvent e){
            if (e.isPopupTrigger())
                Pop(e);
        }
        private void Pop(MouseEvent e) {
            PopupMenu menu = new PopupMenu();
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }//end ClickListener

    /* initialize CMSC350Project4 */
    public static void main(String[] args) {
        new CMSC350Project4();
    }//end main

}//end CMSC350Project3
