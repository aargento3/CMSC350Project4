import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;


/**
 * @author AArgento
 * @date 23 April 2017
 * @class CMSC350
 * @purpose Provide three methods: Create, Sort, DepthFirstSearch
 */


class DirectedGraph {

    private static ArrayList <Integer>[] adjacencies;
    private static HashMap<String,Integer> verticies = new HashMap <>();
    private static int numberOfClasses = 0;

    /* initialize the graph each time a new file is read in. */
    static boolean Create(String file) {
        try {
            Scanner scanner = new Scanner(new File(file));

            while(scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] classes = s.split(" ");
                for(String whatClass : classes) {
                    final Integer put = verticies.put(whatClass, whatClass.charAt(whatClass.length() - 1) - 'A');
                }
            }

            switch (numberOfClasses = verticies.size()) {
            }

            adjacencies = new ArrayList[numberOfClasses];

            scanner = new Scanner(new File(file));

            while(scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] classes = s.split("[ \n]");
                adjacencies[verticies.get(classes[0])] = new ArrayList<>();

                int i = 1;

                if (i < classes.length) {
                    do {
                        final boolean add = adjacencies[verticies.get(classes[0])].add(verticies.get(classes[i]));
                        i++;
                    } while (i < classes.length);
                }
            }
            return true;
        }
        catch (FileNotFoundException ex) {
            return false;
        }
    }//end Create

    /* generate a topological order. calls DepthFirstSearch */
    static ArrayList <String> Sort(String source) {
        boolean s [] = new boolean [numberOfClasses];
        boolean discovered [] = new boolean [numberOfClasses];

        Stack <Integer> stack = new Stack<>();

        DepthFirstSearch(s, discovered, stack, verticies.get(source));

        if(stack.size()>0 && stack.get(stack.size()-1)!=-1) {
            ArrayList<String> orderOfClass=new ArrayList<>();

            while(!stack.empty()) {
                char whatClass = (char) (65 + stack.pop());
                final boolean add = orderOfClass.add("Class" + whatClass);
            }
            return orderOfClass;
        }
        return null;
    }//end Sort
    
    /* implements the pseudocode required by assignment */
    private static void DepthFirstSearch(boolean s[], boolean discovered[], Stack <Integer> stack, int i) {

        if(s[i]) {
            final CycleException cycleException = new CycleException();
        } else if(discovered[i])
            return;

        s[i] = true;

        if(adjacencies[i]!=null) for (Integer k : adjacencies[i])
            DepthFirstSearch(s, discovered, stack, k);

        discovered[i] = true;
        stack.add(i);
    }//end DepthFirstSearch

}//end DirectedGraph