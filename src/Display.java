import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class Display {
    private boolean isMapOpen = false;
    public boolean update = false;


    public String getMapString(int centerX, int centerY){
        final int MAP_SIZE = 5; //from center
        final String CITY = "⊞";
        final String NOTHING = "⊟";
        final String NOT_GENNED = "⊠";
        final String PLAYER = "⊡";
        StringBuilder map = new StringBuilder();
        map.append("<html><span style='font-size:20px'>");
        for (int j = 0; j < MAP_SIZE * 2 + 1; j++) {
            for (int i = 0; i < MAP_SIZE * 2 + 1; i++) {
                try {
                    if (centerX + MAP_SIZE - i == Movement.getX() && centerY + MAP_SIZE - j == Movement.getY()){
                        map.append(PLAYER);
                    } else if (Save.getMapAtPos(centerX + MAP_SIZE - i, centerY + MAP_SIZE - j)[0][0].equals("City")) {
                        map.append(CITY);
                    } else if (Save.getMapAtPos(centerX + MAP_SIZE - i, centerY + MAP_SIZE - j)[0][0].equals("NULL")) {
                        map.append(NOTHING);
                    } else if (Save.getMapAtPos(centerX + MAP_SIZE - i, centerY + MAP_SIZE - j)[0][0].equals("BOSS")) {
                        map.append(NOT_GENNED);
                    }
                } catch (NullPointerException e) {
                    map.append(NOT_GENNED);
                }
            }
            String s10 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            switch (j){
                case 3:
                    map.append(s10 + "Key:");
                    break;
                case 4:
                    map.append(s10 + "<span style='font-size:10px'>Player: <span style='font-size:20px'>" + PLAYER +
                            "<span style='font-size:10px'> (" + Movement.getX() + ", " + Movement.getY() +
                            ")<span style='font-size:20px'>");
                    break;
                case 5:
                    map.append(s10 + "<span style='font-size:10px'>City: <span style='font-size:20px'>" + CITY);
                    break;
                case 6:
                    map.append(s10 + "<span style='font-size:10px'>Nothing: <span style='font-size:20px'>" + NOTHING);
                    break;
                case 7:
                    map.append(s10 + "<span style='font-size:10px'>Not Generated: <span style='font-size:20px'>" + NOT_GENNED);
                    break;

            }
            map.append("<br/>");
        }
        map.append("</html>");
        return map.toString();
    }

    public void displayMap(final int[] x, final int[] y){

        String north = "▲";
        String south = "▼";
        String east = "▶";
        String west = "◀";

        String mapOption;
        System.out.println("Map shown: " + isMapOpen);

        if (isMapOpen){
            mapOption = "Close or Edit Map";
        } else {
            mapOption = "Keep Map Open";
        }

        Object[] options = {north, south, west, east, "Continue", mapOption};
        int choice = JOptionPane.showOptionDialog(null, getMapString(x[0], y[0]), "Map", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, null);
        switch (choice){
            case 0:
                y[0] += 1;
                displayMap(x, y);
                break;
            case 1:
                y[0] -= 1;
                displayMap(x, y);
                break;
            case 2:
                x[0] += 1;
                displayMap(x, y);
                break;
            case 3:
                x[0] -= 1;
                displayMap(x, y);
                break;
            case 5:
                if (!isMapOpen){
                    isMapOpen = true;
                    constantMap();
                } else {
                    AtomicBoolean sleep = new AtomicBoolean(false);
                    JFrame f = new JFrame();
                    JPanel p = new JPanel(new GridLayout(0, 1));
                    JLabel l = new JLabel("Press ok once you have the map in the position you want it");
                    JButton ok = new JButton("Ok");
                    ok.addActionListener(e -> {
                        f.dispose();
                        sleep.set(false);
                    });
                    Dimension d = new Dimension(50, 20);
                    Dimension d2 = new Dimension(200, 20);
                    ok.setSize(d);
                    ok.setPreferredSize(d);
                    l.setMinimumSize(d2);
                    p.add(l);
                    p.add(ok);
                    f.add(p);
                    f.pack();
                    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    f.setTitle("Lock Map");
                    f.setSize(350, 100);
                    f.setVisible(true);
                    sleep.set(true);

                    while (sleep.get()){
                        try {
                            Thread.sleep(1);
                            if (!f.isShowing()){
                                sleep.set(false);
                            }
                        } catch (InterruptedException e){
                            System.out.println("Sleep caused: " + e);
                        }
                    }
                }

        }
    }

    public synchronized void constantMap(){
        System.out.println("Map open: " + isMapOpen);

        JFrame dialog = new JFrame();
        JLabel map = new JLabel(getMapString(Movement.getX(), Movement.getY()));
        dialog.add(map);


        dialog.setTitle("Map");
        dialog.setResizable(true);
        dialog.setSize(500, 500);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        AtomicBoolean sleep = new AtomicBoolean(false);
        JFrame f = new JFrame();
        JPanel p = new JPanel(new GridLayout(0, 1));
        JLabel l = new JLabel("Press ok once you have the map in the position you want it");
        JButton ok = new JButton("Ok");
        ok.addActionListener(e -> {
            f.dispose();
            sleep.set(false);
        });
        Dimension d = new Dimension(50, 20);
        Dimension d2 = new Dimension(200, 20);
        ok.setSize(d);
        ok.setPreferredSize(d);
        l.setMinimumSize(d2);
        p.add(l);
        p.add(ok);
        f.add(p);
        f.pack();
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setTitle("Lock Map");
        f.setSize(350, 100);
        f.setVisible(true);
        sleep.set(true);

        while (sleep.get()){
            try {
                Thread.sleep(1);
                if (!f.isShowing()){
                    sleep.set(false);
                }
            } catch (InterruptedException e){
                System.out.println("Sleep caused: " + e);
            }
        }

        Thread one = new Thread(() -> {
            while (isMapOpen) {
                if (!dialog.isDisplayable()){
                    isMapOpen = false;
                    System.out.println("Map closed");
                } else if (update) {
                    map.setText(getMapString(Movement.getX(), Movement.getY()));
                    dialog.setContentPane(map);
                    update = false;
                }
            }
        });

        one.start();
    }
}
