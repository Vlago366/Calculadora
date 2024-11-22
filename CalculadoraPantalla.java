package Calculadora;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CalculadoraPantalla extends JFrame {

    // Creamos una variable global para el JTextArea
    private JTextArea areaTexto;

    public CalculadoraPantalla() {
        super("Calculadora"); // nombre de la pestaña

        // Panel principal con diseño nulo para controlar manualmente el tamaño
        JPanel pantallaSecundaria = new JPanel(); // crea la pantalla donde se escribe texto
        pantallaSecundaria.setBackground(Color.LIGHT_GRAY); // color de pantalla
        pantallaSecundaria.setLayout(new BorderLayout()); // El borde de layout
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        pantallaSecundaria.setPreferredSize(new Dimension(380, 500)); // Tamaño fijo
        pantallaSecundaria.setBounds(10, 10, d.width / 2, 100); // posición y tamaño fijo de la pantalla

        areaTexto = new JTextArea(); // permite ingresar texto
        areaTexto.setLineWrap(true); // controla la longitud del texto cambiando a una segunda línea en caso de ser
                                     // necesario
        areaTexto.setWrapStyleWord(true); // controla que el texto no sea cortado a la mitad
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14)); // La fuente en el espacio de texto
        areaTexto.setEditable(false); // Evita que el usuario escriba directamente en el área de texto

        // Filtrar entradas desde el teclado
        areaTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();

                // Permitir solo los números del teclado numérico derecho y los operadores
                if (!(Character.isDigit(key) || isNumericKeypad(key) || isOperator(key)
                        || key == KeyEvent.VK_BACK_SPACE)) {
                    e.consume(); // Si la tecla no es válida, la bloqueamos
                }
            }

            // Verifica si el carácter pertenece al teclado numérico derecho
            private boolean isNumericKeypad(char key) {
                return key == KeyEvent.VK_NUMPAD0 ||
                        key == KeyEvent.VK_NUMPAD1 ||
                        key == KeyEvent.VK_NUMPAD2 ||
                        key == KeyEvent.VK_NUMPAD3 ||
                        key == KeyEvent.VK_NUMPAD4 ||
                        key == KeyEvent.VK_NUMPAD5 ||
                        key == KeyEvent.VK_NUMPAD6 ||
                        key == KeyEvent.VK_NUMPAD7 ||
                        key == KeyEvent.VK_NUMPAD8 ||
                        key == KeyEvent.VK_NUMPAD9;
            }

            // Verifica si el carácter es un operador válido
            private boolean isOperator(char key) {
                return key == '+' || key == '-' || key == '*' || key == '/';
            }
        });

        JScrollPane scrollTexto = new JScrollPane(areaTexto); // ayuda a corregir el cambio de lineas al escribir en la
                                                              // calculadora
        pantallaSecundaria.add(scrollTexto, BorderLayout.CENTER); // agrega la capacidad de escribir y la central al
                                                                  // panel secundario donde se escribe los números

        add(pantallaSecundaria); // Añade el panel de la pantalla al JFrame

        setSize((d.width / 2), 600); // Indica el ancho y alto del recuadro
        setLocationRelativeTo(null); // Mantiene la ventana siempre centrada
        setLayout(null); // Layout nulo, para tener control manual de los tamaños

        // Panel para los botones
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 4)); // Crea el panel para los botones de 5 lineas con 4 botones
        mainPanel.setBackground(Color.LIGHT_GRAY); // color del panel
        mainPanel.setBounds(10, 120,(d.width / 2), 500); // Ubica el panel de los botones debajo de la pantalla
        add(mainPanel); // Añade el panel de los botones al JFrame

        String[] botones = {
                "1", "2", "3", "/", "4", "5", "6", "*",
                "7", "8", "9", "-", "0", "+", "=", "C"
        }; // Son los nombres de los botones

        for (String texto : botones) { // Para el array con los botones mientras haya
            Button button = new Button(texto); // Se crea un boton con el contenido de dicha posición
            button.setFont(new Font("Arial", Font.PLAIN, 20)); // Se describe la fuente y el tamaño
            button.setBackground(Color.WHITE); // El color del boton
            button.addActionListener(new ActionListener() { // Al pulsar el boton
                @Override
                public void actionPerformed(ActionEvent e) {
                    manejarAccionBoton(texto); // Llama al método que escribe el contenido del boton
                }
            });
            mainPanel.add(button); // Añade el boton al panel
        }
    }

    private void manejarAccionBoton(String texto) {
        if (texto.equals("C")) { // Si se presiona el boton C
            areaTexto.setText(""); // Borra el contenido del JTextArea
        } else if (texto.equals("=")) {
            manejarLaOperacion(); // Llamada para manejar la operación (si es necesario implementarla)
        } else {
            areaTexto.append(texto); // Añade el texto del botón presionado
        }
    }

    public void manejarLaOperacion() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraPantalla ventana = new CalculadoraPantalla();
            ventana.setVisible(true);
        });
    }
}
