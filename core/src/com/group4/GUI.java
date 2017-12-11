package com.group4;

/**
 * Created by Tobias on 27/11/2017.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class GUI extends JFrame{

    private int highlight;



    //This method is for the constant show of the arraylist during the game.
    public GUI(ArrayList<String> strings, ArrayList<Integer> integers) {
        //needs to work with the game interface to show the two arraylists.
        JPanel pnl = new JPanel(new BorderLayout());

        String[] temp = new String[10];
        for (int i=1; i<=10; i++)
            temp[i-1]= Integer.toString(i);
        JList<Integer> list1 = new JList(temp);
        JList<String> list2 = new JList(strings.toArray());
        JList<Integer> list3 = new JList(integers.toArray());

        pnl.add(list1,BorderLayout.WEST);
        pnl.add(list2,BorderLayout.CENTER);
        pnl.add(list3,BorderLayout.EAST);

    }


    //this method is for when the request is from the menu (flag = false) or
    //if it's when the game ends (flag = true)
    public GUI(ArrayList<String> strings, ArrayList<Integer> integers, Boolean flag, int k) {
        super("HighScores");
        highlight = k;
        JPanel container = new JPanel(new BorderLayout());
        JScrollPane jsp = new JScrollPane(container);


        String[] temp1 = new String[strings.size()];
        String[] temp2 = new String[strings.size()];
        String[] temp3 = new String[strings.size()];
        for (int i=0; i<temp1.length; i++) {
            temp1[i] = Integer.toString(i + 1 );
            temp2[i] = strings.get(i);
            temp3[i] = Integer.toString(integers.get(i));
        }
        JList<String> list1 = new JList(temp1);
        JList<String> list2 = new JList(temp2);
        JList<String> list3 = new JList(temp3);


        if (flag) {
            JButton button1 = new JButton("Play Again");
            JButton button2 = new JButton("Change Player");
            JButton button3 = new JButton("Quit");


            JPanel botPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

            botPnl.add(button1);
            botPnl.add(button2);
            botPnl.add(button3);

            container.add(botPnl,BorderLayout.SOUTH);

            button1.setVisible(true);
            button2.setVisible(true);
            button3.setVisible(true);

            button2.addActionListener(new Change());
            button3.addActionListener(new Close());

        }


//        JLabel title = new JLabel("HIGHSCORE");
//
//        container.add(title, BorderLayout.NORTH);


        list1.setCellRenderer(new aListCellRenderer());
        list2.setCellRenderer(new aListCellRenderer());
        list3.setCellRenderer(new aListCellRenderer());

        DefaultListCellRenderer renderer1 = (DefaultListCellRenderer) list1.getCellRenderer();
        renderer1.setHorizontalAlignment(SwingConstants.LEFT);
        DefaultListCellRenderer renderer2 = (DefaultListCellRenderer) list2.getCellRenderer();
        renderer2.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultListCellRenderer renderer3 = (DefaultListCellRenderer) list3.getCellRenderer();
        renderer3.setHorizontalAlignment(SwingConstants.RIGHT);


        list1.setFont(new Font("Rockwell",Font.BOLD,20));
        list2.setFont(new Font("Rockwell",Font.BOLD,20));
        list3.setFont(new Font("Rockwell",Font.BOLD,20));
        container.add(list1,BorderLayout.WEST);
        container.add(list2,BorderLayout.CENTER);
        container.add(list3,BorderLayout.EAST);

        setLayout(new BorderLayout());

        ImageIcon img = new ImageIcon("unnamed.png");
        setIconImage(img.getImage());

        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(jsp);

    }



    private class aListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setOpaque(isSelected); // Highlight only when selected
            label.setOpaque(true); // Highlight always
            if (index%2 == 0) { // I faked a match for the second index, put you matching condition here.
                label.setBackground(new Color(220,220,220));
                label.setEnabled(true);
            }
            if (index == highlight) {
                label.setBackground(Color.YELLOW);
                label.setEnabled(true);
            }
            return label;
        }
    }

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class Change implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame();
            String s = (String) JOptionPane.showInputDialog(frame, "Who is the new player?", "Change Player", JOptionPane.PLAIN_MESSAGE);


            if ((s != null) && (s.length() > 0)) {
                return;
            }

        }
    }
}