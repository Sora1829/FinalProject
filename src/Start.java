import javax.swing.*;
import java.io.*;

public class Start {
    public static Character player = new Character();
    public static Display d = new Display();
    public static void main(String[] args){
        File saveLocation = new File(Save.getSaveLocation());
        if (saveLocation.exists()){
            System.out.println("Save location already exists!");
        } else {
            boolean f = saveLocation.mkdirs(); //attempt to create dir returning boolean (true means it was made)
            if(f) {
                System.out.println("Directory Created");
            } else {
                JOptionPane.showMessageDialog(null, "Error in creating save file directory! Access is probably denied",
                        "Error", JOptionPane.ERROR_MESSAGE); //if an error occurred, say so.
            }
        }

        String[] options = {"Start New", "Load Existing"};
        int selection = JOptionPane.showOptionDialog(null, "Welcome to " + Save.getGameName() + ". Select an option:",
                Save.getGameName(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (selection == -1){ //if user clicked x
            System.exit(3);
        }
        if (selection == 0){ //start new
            JFileChooser selectFile = new JFileChooser(saveLocation); //save dialog opens in saves folder
            int selectValue = selectFile.showDialog(null, "Enter name of new save folder");
            if (selectValue != JFileChooser.APPROVE_OPTION){ //if user selects x or cancel
                main(args);
                System.exit(2);
            }
            Save.setSaveLocation(selectFile.getSelectedFile());
            File saveFile = selectFile.getSelectedFile(); //saveFile = save file location
            File map = new File(saveFile + "\\map");
            map.mkdirs(); //make the map folder
            Save.setMapLocation(); //set map folder location to newly created map folder

            //class selection
            String[] classes = player.getClasses();
            int classChoice;
            do {
                classChoice = JOptionPane.showOptionDialog(null, "Choose your class.\nThey do more damage with their " +
                                "respective weapons", "Class selection", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, classes, null);
            }while(classChoice==-1);
            player.setPlayerClass(classChoice);
            Save.initMap();
            player.save();
        }
        else if (selection == 1){ //load existing
            if (!player.load()){ //if user cancelled or clicked x
                main(args);
                System.exit(2);
            }
        }
        if (player.getHitPoints() <= 0){
            if (JOptionPane.showConfirmDialog(null, "Yo, you died. That's irreversible!", "Death is permanent!",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "I see. You don't give up. I'll give you another chance...",
                        "ORLLY", JOptionPane.PLAIN_MESSAGE);
                player.addHp(player.getMaxHp());
            } else {
                System.exit(10);
            }
        }
        while (true) {
            Movement.movementMain();
        }
    }
}
