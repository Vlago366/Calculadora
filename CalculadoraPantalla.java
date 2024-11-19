package Calculadora;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CalculadoraPantalla extends JFrame {

    // Constructor para configurar la pantalla
    public CalculadoraPantalla() {
        super("Calculadora");

        // Crear el panel secundario
        JPanel pantallaSecundaria = new JPanel();
        pantallaSecundaria.setPreferredSize(new Dimension(60000, 100)); // Tamaño: ancho dinámico, alto fijo
        pantallaSecundaria.setBackground(Color.LIGHT_GRAY);
        pantallaSecundaria.setLayout(new BorderLayout()); // Diseño para expandir contenido

        // Crear un campo de texto grande (JTextArea con scroll)
        JTextArea areaTexto = new JTextArea();
        areaTexto.setLineWrap(true); // Ajuste de líneas
        areaTexto.setWrapStyleWord(true); // Ajuste por palabra
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente personalizada

        // Agregar scroll al JTextArea
        JScrollPane scrollTexto = new JScrollPane(areaTexto);
        pantallaSecundaria.add(scrollTexto, BorderLayout.CENTER);

        add(pantallaSecundaria, BorderLayout.NORTH);


        // Configurar tamaño inicial (mitad de mitad de pantalla y 600px de altura)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 4; // Mitad de mitad del ancho de la pantalla
        int height = 600; // Altura fija
        setSize(width, height);
        setLocationRelativeTo(null); // Centrar la ventana
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        setLayout(new GridLayout(5,4));
        add(new Button("1"));
        add(new Button("2"));
        add(new Button("3"));
        add(new Button("/"));
        add(new Button("4"));
        add(new Button("5"));
        add(new Button("6"));
        add(new Button("*"));
        add(new Button("7"));
        add(new Button("8"));
        add(new Button("9"));
        add(new Button("-"));
        add(new Button("0"));
        add(new Button("+"));
        add(new Button("="));
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraPantalla ventana = new CalculadoraPantalla();
            ventana.setVisible(true);
        });
    }
}

