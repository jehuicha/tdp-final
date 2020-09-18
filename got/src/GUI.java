package got;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
	
	private Logica logica;
	private JPanel contentPane;
	
	private JTextPane textPane;
	private JTextPane directorio; 
	private JButton botonCargar;
	private JButton botonComenzar;
	private JComboBox<String> comboIdioma;
	private JLabel lblIdioma;
	private JLabel lblDirInvalid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
		cargarProperties();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		directorio = new JTextPane();
		directorio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		directorio.setBounds(30, 16, 390, 36);
		contentPane.add(directorio);
		
		botonCargar = new JButton("");
		botonCargar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		botonCargar.setBounds(30, 63, 208, 36);
		contentPane.add(botonCargar);
		
		botonComenzar = new JButton("");
		botonComenzar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		botonComenzar.setBounds(30, 115, 150, 36);
		contentPane.add(botonComenzar);
		
		comboIdioma = new JComboBox<String>();
		comboIdioma.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboIdioma.setBounds(458, 63, 105, 36);
		contentPane.add(comboIdioma);
		
		lblIdioma = new JLabel("");
		lblIdioma.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblIdioma.setBounds(458, 26, 105, 26);
		contentPane.add(lblIdioma);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textPane.setVisible(false);
		textPane.setEditable(false);
		textPane.setBounds(30, 182, 463, 146);
		contentPane.add(textPane);
		
		lblDirInvalid = new JLabel((String) null);
		lblDirInvalid.setVisible(false);
		lblDirInvalid.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDirInvalid.setBounds(253, 68, 167, 31);
		contentPane.add(lblDirInvalid);
		this.setTitle("Winter is Coming");
		
		setearTxt("config-ES.properties");
		
		//listeners
		comboIdioma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = comboIdioma.getSelectedItem().toString();
				if ((s.equals("Español") || s.equals("Spanish")))
					setearTxt("config-ES.properties");
				else 
					setearTxt("config-EN.properties");
			}
		});
		botonComenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logica.comenzar();
			}
		});
		botonCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblDirInvalid.setVisible(false);
				textPane.setVisible(false);
				String dir = directorio.getText();
				logica.cargarDirectorio(dir);
			}
		});
		
		logica = new Logica(this);
	}
	
	public void dirInvalid() {
		lblDirInvalid.setVisible(true);
	}
	
	public void resultado(LinkedList<Palabra> lista, int count) {
		String calculo ="";
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		for(int i=0 ; i< lista.size() ; i++) {
			float j = lista.get(i).getCantidad();
			float ff = (j/count)*100;
			calculo = calculo +" "+lista.get(i).getPalabra()+"  "+df.format(ff)+"% \n";
		}
		textPane.setText(calculo);
		textPane.setVisible(true);
	}
	
	private void cargarProperties(){
		try (	OutputStream output = new FileOutputStream("config-ES.properties")){
			Properties prop = new Properties();
			
			prop.setProperty("keyIngresar", "Ingrese el directorio");
			prop.setProperty("keyCargar", "Cargar Directorio");
            prop.setProperty("keyComenzar", "Comenzar");
            prop.setProperty("keyDirInvalid", "Directorio Invalido");
            prop.setProperty("keyIdioma", "Idioma");
            prop.setProperty("keyEspañol", "Español");
            prop.setProperty("keyIngles", "Ingles");
            
            prop.store(output, null);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (	OutputStream output = new FileOutputStream("config-EN.properties")){
			Properties prop = new Properties();
			
			prop.setProperty("keyIngresar", "Enter directory");
			prop.setProperty("keyCargar", "Load Directory");
            prop.setProperty("keyComenzar", "Start");
            prop.setProperty("keyDirInvalid", "Invalid Directory");
            prop.setProperty("keyIdioma", "Language");
            prop.setProperty("keyEspañol", "Spanish");
            prop.setProperty("keyIngles", "Inglish");
            
            prop.store(output, null);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setearTxt(String path){
		try (InputStream input = new FileInputStream(path)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            directorio.setText(prop.getProperty("keyIngresar"));
            botonCargar.setText(prop.getProperty("keyCargar"));
    		botonComenzar.setText(prop.getProperty("keyComenzar"));
    		lblIdioma.setText(prop.getProperty("keyIdioma"));
    		lblDirInvalid.setText(prop.getProperty("keyDirInvalid"));
    		comboIdioma.setModel(new DefaultComboBoxModel(new String[] {prop.getProperty("keyEspañol"), prop.getProperty("keyIngles")}));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
