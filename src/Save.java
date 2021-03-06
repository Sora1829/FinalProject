import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Save {
    private static String gameName = "Journeyman's Quest";
    private static String saveLocation = "C:\\ProgramData\\" + gameName + "\\saves";
    private static String mapLocation = saveLocation + "\\map";


    public static void saveToFile(Object[][][] details){
        JFileChooser selectFile = new JFileChooser(saveLocation); //save dialog opens in saves folder
        String sF = selectFile.getCurrentDirectory().toString();
        File saveFile = new File(sF + "\\save"); //saveFile = save file location
        try { //tries saving file
            saveFile.createNewFile();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e, "Exception", JOptionPane.ERROR_MESSAGE);
        }
        try { //tries writing to file
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(saveFile));
            os.writeObject(details); //insert name of var to write here
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e, "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static Object[][][] loadFromFile(){
        JFileChooser selectFile = new JFileChooser(saveLocation); //load dialog opens in saves folder
        selectFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (selectFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //only continue if open is selected
                try {
                    String sF = selectFile.getSelectedFile().toString();
                    File file = new File(sF);
                    setSaveLocation(file);
                    setMapLocation();
                    File saveFile = new File(sF + "\\save"); //saveFile = save file location
                    try { //tries reading and returning file as Object[][][]
                        ObjectInputStream is = new ObjectInputStream(new FileInputStream(saveFile));
                        return (Object[][][]) is.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        JOptionPane.showMessageDialog(null, e, "Exception ", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Loading cancelled: " + e);
                }
            }
        System.out.println("Error, user probably cancelled.");
        return null;
    }
    public static String getSaveLocation(){
        return saveLocation;
    }
    public static void setSaveLocation(File s){
        saveLocation = s.toString();
        System.out.println(saveLocation + " is new location for saves");
    }
    public static String getGameName() {
        return gameName;
    }
    public static void setMapLocation(){
        mapLocation = saveLocation + "\\map";
        System.out.println(mapLocation + " is location for map");
    }

    public static String getMapLocation(){
        return mapLocation;
    }

    public static void setMapAtPos(int x, int y, String s, Object[] cityDetails){
        Object[][] city = {{s}, cityDetails};
        File map = new File(mapLocation + "\\x" + x + "y" + y);
        try { //tries creating file
            map.createNewFile();
        }catch (IOException e){
            System.out.println("Exception in creating map file:\n" + e);
        }
        try { //tries writing to file
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(map));
            os.writeObject(city); //insert name of var to write here
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }

    public static Object[][] getMapAtPos(int x, int y){
        File map = new File(mapLocation + "\\x" + x + "y" + y);
        try { //tries reading and returning file as Object[]
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(map));
            return (Object[][])is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //System.out.println("Exception: " + e);
        }
        return null;
    }

    public static void initMap(){
        Save.setMapAtPos(0, 0, "NULL", null);
        for (int i = 0; i < (100 * 10); i += 100){
            for (int j = 0; j < (100 * 10); j += 100){
                int randomX = (int)(Math.random() * 10);
                int randomY = (int)(Math.random() * 10);
                if (Math.round((Math.random())) == 1)
                    randomX *= -1;
                if ((Math.round(Math.random())) == 1)
                    randomY *= -1;
                if (i == 0 && j == 0){
                    System.out.println("0, 0 dragon not generated");
                } else {
                    Save.setMapAtPos(i + randomX, j + randomY, "BOSS", null);
                }
            }
            for (int j = 0; j > -(100 * 10); j -= 100){
                int randomX = (int)(Math.random() * 10);
                int randomY = (int)(Math.random() * 10);
                if (Math.round((Math.random())) == 1)
                    randomX *= -1;
                if ((Math.round(Math.random())) == 1)
                    randomY *= -1;
                if (i == 0 && j == 0){
                    System.out.println("0, 0 dragon not generated");
                } else {
                    Save.setMapAtPos(i + randomX, j + randomY, "BOSS", null);
                }
            }
        }
        for (int i = 0; i > -(100 * 10); i -= 100){
            for (int j = 0; j < (100 * 10); j += 100){
                int randomX = (int)(Math.random() * 10);
                int randomY = (int)(Math.random() * 10);
                if (Math.round((Math.random())) == 1)
                    randomX *= -1;
                if ((Math.round(Math.random())) == 1)
                    randomY *= -1;
                if (i == 0 && j == 0){
                    System.out.println("0, 0 dragon not generated");
                } else {
                    Save.setMapAtPos(i + randomX, j + randomY, "BOSS", null);
                }
            }
            for (int j = 0; j > -(100 * 10); j -= 100){
                int randomY = (int)(Math.random() * 10);
                int randomX = (int)(Math.random() * 10);
                if (Math.round((Math.random())) == 1)
                    randomX *= -1;
                if ((Math.round(Math.random())) == 1)
                    randomY *= -1;
                if (i == 0 && j == 0){
                    System.out.println("0, 0 dragon not generated");
                } else {
                    Save.setMapAtPos(i + randomX, j + randomY, "BOSS", null);
                }
            }
        }
    }

}
