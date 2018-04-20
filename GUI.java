//importere deviser biblioteker fra bla. java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;

//extender JPanel for at benytte Jpanel implementeringer.
//Actionlistener interfacet implementeres for at kunne modtage action handlinger
public class GUI extends JPanel implements ActionListener{

    //radius bruges til bestemme radius på cirklerne
    int radius =  10;
    // Konstruere JMenuItem objekterne som senere skal kontrollere radiusen på cirklen.
    private JMenuItem item1 = new JMenuItem("1");
    private JMenuItem item2 = new JMenuItem("2");
    private JMenuItem item3 = new JMenuItem("3");
    private JMenuItem item4 = new JMenuItem("4");
    private JMenuItem item5 = new JMenuItem("5");
    //Konstruere JSliders som benyttes til at styre farve parametre
    private JSlider sliderR = new JSlider();
    private JSlider sliderB = new JSlider();
    private JSlider sliderG = new JSlider();
    //Konstruere JLabel som benyttes til at beskrive JSliders
    JLabel labelr = new JLabel("Rød");
    JLabel labelg = new JLabel("Grøn");
    JLabel labelb = new JLabel("Blå");
    //Color en funktion fra Jpanel - benyttes til at give cirklerne farve.
    Color color = Color.RED;


// Konstrurer et Spinner element, som skal benyttes til styre item variablerne og derved radiussen for cirklerne
// - SpinnerNumberModel(value, min, max, step); - sætter min til 0 og max til 5.

    JSpinner spinner = new JSpinner(new SpinnerNumberModel(0,0,5,1));

    //main metoden laver en instants af GUI klassen og starter metoden setGui().
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setGui();

    }

//SetGui er en klasse som står for at tildele JFrame elementer, herunder elementer som Knapper, Sliders, Spinners og Actionlisteneres, som reagere på input.
    public void setGui() {
        // konstruerer JFrame, JButton, JMenuBar og JMenu.

        GUI gui = new GUI();
        Circle cirkel = new Circle();
        JFrame frame = new JFrame("Aflevering");
        JButton btn1 = new JButton("Vælg farve");
        JMenuBar strl = new JMenuBar();
        JMenu strll = new JMenu("Størrelse");
        //tilføjer JMenu til strl JMenubaren
        strl.add(strll);

        //Konstruere et actionHandler objekt
        ActionHandler handler = new ActionHandler();
        //tildeleter JButton"Vælg en farve" actionHandler - så input kan sendes videre til ActionPerformed()
        btn1.addActionListener(handler);

         //Tildeler Items i JMenu strl en ActionListener. - giver et kald til ActionPerformed().
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        item4.addActionListener(this);
        item5.addActionListener(this);



//sætter min og max værdi for sliders
        sliderR.setMaximum(255);
        sliderR.setMinimum(0);
        sliderG.setMaximum(255);
        sliderG.setMinimum(0);
        sliderB.setMaximum(255);
        sliderB.setMinimum(0);

        //Sliders bliver tildelt en ChangeListener, som reagere hvis brugeren manipulere med sliders

        sliderR.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //Sliders R manipulere med den røde farve og via getValue() bliver sliders værdien indsat i color
                //Color modtager (int,int,int) som parametre

                color = new Color(sliderR.getValue(),sliderG.getValue(),sliderB.getValue());
                System.out.println(sliderR.getValue());
            }
        });
        //Det samme sker for de andre sliders
        sliderG.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                color = new Color(sliderR.getValue(),sliderG.getValue(),sliderB.getValue());
                System.out.println(sliderG.getValue());

            }
        });
        sliderB.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                color = new Color(sliderR.getValue(),sliderG.getValue(),sliderB.getValue());
                System.out.println(sliderB.getValue());
            }

        });

        //JMenu strll får tildelt de 5 JMenu elementer. - som senere skal bruges til at bestemme radius på cirklerne
        strll.add(item1);
        strll.add(item2);
        strll.add(item3);
        strll.add(item4);
        strll.add(item5);

        //Fjerne focus fra spinner
        spinner.setFocusable(false);
        //Tilføjer en changelistener til spinneren

        spinner.addChangeListener(new ChangeListener() {
            //Changelisteren modtager et kald af actionPerformed om hvilken spinner værdi som er blevet valgt.
            //radius bliver derved beregnet af spinnerens værdi gange 10. Så hvis spineren f.eks. vælger 40, bliver radiues 40
            @Override
            public void stateChanged(ChangeEvent e) {
                radius = (Integer)spinner.getValue()*10 ;
            }
        });


        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        //Konstuere et nyt BorderLayout, som skal indeholde JButton, JSpinner og et JPanel.

        frame.setLayout(new BorderLayout());

        //konsturere et nyt JPanel med 6 rækker og 1 colonne, som skal oplagre JSliders og JLabels
        JPanel panel = new JPanel(new GridLayout(6,1));

        //tildeler Panel, Jlabel og JSliders
        panel.add(labelr);
        panel.add(sliderR);
        panel.add(labelg);
        panel.add(sliderG);
        panel.add(labelb);
        panel.add(sliderB);
        //tildeler frame et borderlayout
        frame.add(btn1,BorderLayout.NORTH);
        frame.add(spinner,BorderLayout.SOUTH);
        //propper panel i BorderLayout
        frame.add(panel, BorderLayout.WEST);


        frame.setJMenuBar(strl);
        //Tildeler frame gui objektet.
        frame.add(gui);
        //Sætter størrelsen på Frame/vinduet
        frame.setSize(600, 600);
        //Gør vinduet synligt
        frame.setVisible(true);

        //Tildeler en mouselisterene på framet. Som står for at indsætte cirkel objekter i ArrayList.
        gui.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                //Får koordinaten fra museklikket
                final Point pos = e.getPoint();
                //beregner centrum af cirklen med PI, for at cirklen bliver tegnet på musens reele position
                final int x = pos.x - radius/2;
                final int y = pos.y - radius/2;
                //Tilføjer et cirkel objekt til circel arrayet, med color(modtaget fra API eller sliders) og radius (modtaget fra sliders)
                //*Fordi FillOval i virkeligheden er en arvart af FillRect
                cirkel.circles.add(new Circle(x, y, color, radius));
                System.out.println(color);
                //repainter
                gui.repaint();

            }
        });
    }
    //Paintcomponent metoden har til opgave at "male" elementerne i circles arrays i den farve, som de indholder.
    public void paintComponent(Graphics g){
         //For lykken - interere igennem hele arrayet.- benytter en generics for loop. (type element: array)
        for (Circle c: Circle.circles) {
            //sætter farven som cirkel objektet indeholder
            g.setColor(c.c);
            //laver en oval(cirkel), med de koordinator og radius som blev indsat tidligere.
            g.fillOval(c.x, c.y,c.r, c.r);
            repaint();
        }
    }
// laver en actionHandler som reagere på værdierne i JMenu elerment. Henholdsvis 1,2,3,4,5.
// - Disse bliver brugt i StateChanged() metoden til at bestemme cirklens radius.
    public void actionPerformed(ActionEvent E){
        if(E.getSource()== item1){
            radius = 10;
            System.out.println(radius);

        }
        else if(E.getSource()== item2){
            radius = 20;
            System.out.println(radius);

        }
        else if(E.getSource()== item3){
             radius = 30;
            System.out.println(radius);

        }
        else if(E.getSource()== item4){
            radius = 40;
            System.out.println(radius);

        }
        else if(E.getSource()== item5){
            radius = 50;
            System.out.println(radius);
        }
    }
    //ActionHandler er en nestedClass, som benytter tilgår color objektet
   public class ActionHandler implements ActionListener {
//Her bruges JColorChooser API'en til at lave "color" om.
        //JColoChooser spytter (int,int,int) ud. - altså RPG paramtre.
        public void actionPerformed(ActionEvent event) {
            color = JColorChooser.showDialog(null, "Vælg en farve", color);
            repaint();
        }
    }
}