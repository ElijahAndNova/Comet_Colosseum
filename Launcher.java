import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Launcher{

    public static JFrame frame;
    public static Container cp;
    public static float r = .7f;
    public static float g = 0f;
    public static float b = 0f;
    public static boolean r_mode = true;
    public static boolean g_mode = true;
    public static boolean b_mode = true;
    public static JTextField username_textfield;
    public static JPasswordField password_textfield;
    public static int screen_width = 960;//java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    public static JLabel title;

    
    public static void main(String args[]){
	new Colosseum();
	/*
	assembleGUI(screen_width);
	frame.getContentPane().setBackground(new Color(0,0,0));
	//changeGUIBackground(.0001f);

	JLabel astronaut = new JLabel();
        astronaut.setSize(screen_width,534);
        astronaut.setLocation(0,0);
        astronaut.setVisible(true);
        ImageIcon imageIcon = new ImageIcon("Images/showdown.png");
        Image image = imageIcon.getImage(); // transform it                                                                                                                                               
        Image newimg = image.getScaledInstance(screen_width, 534,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way                                                                               
        imageIcon = new ImageIcon(newimg);
        astronaut.setIcon(imageIcon);
        frame.getContentPane().add(astronaut);


	frame.repaint();
	/*while (true){
	    changeGUIBackground(.000001f);
	    frame.repaint();
	
	    }*/
    }

    public static void showFrame(){
    
	frame.setVisible(true);
	
    }

    public static void changeGUIBackground(float speed_multiplier){	
	if (r_mode) r += .001f*speed_multiplier;
	else r -= .0001f*speed_multiplier;
	if (r >= .999) r_mode = false;
	else if (r <= .65) r_mode = true;
	if (g_mode) g += .001f*speed_multiplier;
        else g-= .0001f*speed_multiplier;
	if (g >= .999) g_mode = false;
	else if(g <= .65) g_mode= true;
	if (b_mode) b += .001f*speed_multiplier;
        else b-= .001f*speed_multiplier;
	if (b >= .5) b_mode = false;
	else if(b <= 001) b_mode= true;
	frame.getContentPane().setBackground(new Color(r,g,b));
	//System.out.println("R = " + r + "G = " +  g +"B = " + b);
    }

    public static void assembleGUI(int screen_width){	
	frame = new JFrame("Side Swiped");
	frame.setSize(screen_width,534);
	frame.setAlwaysOnTop(true);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setUndecorated(true);
	frame.setLayout(null);
	//frame.setOpacity(.75f);
	frame.setVisible(true);

	title = new JLabel("Comet Colosseum");
	title.setLocation(screen_width/2-100, 30);
	title.setFont(new Font("Verdana", Font.ITALIC, 60));
	title.setForeground(new Color(r,g,b));
	title.setSize(700, 80);
	frame.getContentPane().add(title);	


	/*	JLabel astronaut2 = new JLabel();
        astronaut2.setSize(150,200);
        astronaut2.setLocation(170,50);
        astronaut2.setVisible(true);
        ImageIcon imageIcon2 = new ImageIcon("Images/Astronaut2.png");
        Image image2 = imageIcon2.getImage(); // transform it                                                                                                                                               
        Image newimg2 = image2.getScaledInstance(170, 180,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way                                                                                        
        imageIcon2 = new ImageIcon(newimg2);
        astronaut2.setIcon(imageIcon2);
        frame.getContentPane().add(astronaut2);

	JLabel astronaut3 = new JLabel();
        astronaut3.setSize(150,200);
        astronaut3.setLocation(screen_width-370,50);
        astronaut3.setVisible(true);
        ImageIcon imageIcon3 = new ImageIcon("Images/Astronaut3.png");
        Image image3 = imageIcon3.getImage(); // transform it                                                                                                                                           \
                                                                                                                                                                                                        
        Image newimg3 = image3.getScaledInstance(105, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way                                                                                                                                                                                                                                                                  
        imageIcon3 = new ImageIcon(newimg3);
        astronaut3.setIcon(imageIcon3);
        frame.getContentPane().add(astronaut3);
	*/
	username_textfield = new JTextField("Nova");
	username_textfield.setSize(200,20);
	username_textfield.setBackground(Color.white);
	username_textfield.setForeground(new Color(r,g,b));
	username_textfield.setVisible(true);
	username_textfield.setLocation(screen_width-350+100, 130);
	frame.getContentPane().add(username_textfield);

	password_textfield = new JPasswordField("meowcow521");
        password_textfield.setSize(200,20);
	password_textfield.setBackground(Color.white);
	password_textfield.setForeground(new Color(r,g,b));
        password_textfield.setLocation(screen_width-350+100, 160);
        frame.getContentPane().add(password_textfield);

	JLabel username_label = new JLabel("Username");
	username_label.setFont(new Font("Verdana", Font.BOLD,15));
	username_label.setForeground(new Color(r,g,b));
	username_label.setSize(100, 20);
	username_label.setLocation(screen_width-450+100, 130);
	frame.getContentPane().add(username_label);

	JLabel password_label = new JLabel("Password");
        password_label.setFont(new Font("Verdana", Font.BOLD, 15));
        password_label.setSize(100, 20);
	password_label.setForeground(new Color(r,g,b));
        password_label.setLocation(screen_width-450+100, 160);
        frame.getContentPane().add(password_label);

	ImageIcon exit_icon = new ImageIcon("Images/exit.png");
	JButton exit = new JButton(exit_icon);
	exit.setSize(100,60);
	exit.setLocation(screen_width-125, 450);
	exit.setOpaque(false);
	exit.setContentAreaFilled(false);
	exit.setBorderPainted(false);
	exit.addActionListener(new ActionListener(){ 
		public void actionPerformed(ActionEvent e) { 
		    System.exit(1);
		} 
	    });
	frame.getContentPane().add(exit);

	ImageIcon login_icon = new ImageIcon("Images/login.png");
        JButton login = new JButton(login_icon);
	login.setSize(100,60);
        login.setLocation(screen_width-225, 450);
        login.setOpaque(false);
        login.setContentAreaFilled(false);
	login.setBorderPainted(false);
        login.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
		    new Colosseum();
                }
            });
        frame.getContentPane().add(login);
    }
}