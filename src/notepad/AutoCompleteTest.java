package notepad;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.Vector;

public class AutoCompleteTest extends JFrame{
    JTextField city = new JTextField(10);
    String enteredName = null;
    String[] cities = {"java","advanced java",
            "notedpad","python","php","jackpot","javelin","javascript","jasmine","the jack barry show","","jacket"};
    JList list = new JList();
    JScrollPane pane = new JScrollPane();
    ResultWindow r = new ResultWindow();
//------------------------------------------------------------------------------
    public static void main(String[] args) {
       new AutoCompleteTest();
    }
//------------------------------------------------------------------------------
    public AutoCompleteTest(){
        setLayout(new java.awt.FlowLayout());
        setVisible(true);
        add(city);
//      add(pane);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        city.addKeyListener(new TextHandler());
    }
      private void actionExit() {
        dispose();
    }
//------------------------------------------------------------------------------
    public void initiateSearch(String lookFor){
       /* Vector<String> matches = new Vector<>();
        lookFor = lookFor.toLowerCase();
        for(String each : cities){
            if(each.contains(lookFor)){
                matches.add(each);
                System.out.println("Match: " + each);
            }
        }
        this.repaint();

        if(matches.size()!=0){
            list.setListData(matches);
            r.searchResult = list;
            r.pane = pane;
            r.initiateDisplay();
        }else{
            matches.add("No Match Found");
            list.setListData(matches);
            r.searchResult = list;
            r.pane = pane;
            r.initiateDisplay();
        }*/
    int newWidth = AutoCompleteTest.this.getSize().width;        
    list.setPreferredSize(new Dimension(newWidth, list.getPreferredSize().height));

        Vector<String> matches = new Vector<String>();
        lookFor = lookFor.toLowerCase();
        for(String each : cities){
            if(each.contains(lookFor)){
                matches.add(each);
                System.out.println("Match: " + each);
            }
        }
        this.repaint();

        if(matches.size()!=0){
            list.setListData(matches);
            r.searchResult = list;
            r.pane = pane;
            r.initiateDisplay();
        }else{
            matches.add("No Match Found");
            list.setListData(matches);
            r.searchResult = list;
            r.pane = pane;
            r.initiateDisplay();
        }

    }
//------------------------------------------------------------------------------
    public class ResultWindow extends JWindow{
        public JScrollPane pane;
        public JList searchResult;
//------------------------------------------------------------------------------
        public ResultWindow(){

        }
//------------------------------------------------------------------------------
        public void initiateDisplay(){
           /* pane.setViewportView(searchResult);
            add(pane);
            pack();
            this.setLocation(AutoCompleteTest.this.getX() + 2, 
                    AutoCompleteTest.this.getY()+
                    AutoCompleteTest.this.getHeight());

//          this.setPreferredSize(city.getPreferredSize());
            this.setVisible(true);*/
        this.pane.setViewportView(this.searchResult);
        this.add(this.pane);
        this.pack();
        //this.setLocation(AutoCompleteTest.this.getX() + 2, AutoCompleteTest.this.getY()
          //      + AutoCompleteTest.this.getHeight());
this.setLocation(AutoCompleteTest.this.getX() + 2, AutoCompleteTest.this.getY()
          + AutoCompleteTest.this.getHeight());
        int padding = 5;
        int height = this.searchResult.getModel().getSize()
                * AutoCompleteTest.this.city.getSize().height;
        int windowWidth = AutoCompleteTest.this.getSize().width;

        this.setSize(windowWidth, height);
        this.setVisible(true);
        }
    }
//------------------------------------------------------------------------------

    class TextHandler implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e){

        }

        @Override
        public void keyPressed(KeyEvent e){
            if(r.isVisible()){
                r.setVisible(false);
            }
            if(e.getKeyChar() == '\n'){
                initiateSearch(city.getText());
            }
        }

        @Override
        public void keyReleased(KeyEvent e){

        }
    }
//------------------------------------------------------------------------------
}