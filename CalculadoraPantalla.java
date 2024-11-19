package Calculadora;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.event.*;

public class CalculadoraPantalla extends JFrame {

    // Creamos una variable global para el JTextArea
    private JTextArea areaTexto;

    public CalculadoraPantalla() {
        super("Calculadora"); // nombre de la pestaña

        JPanel pantallaSecundaria = new JPanel(); // crea la pantalla donde se escribe texto
        pantallaSecundaria.setPreferredSize(new Dimension(0, 100)); // Indica las dimensiones por defecto, solo el
                                                                    // height es utilizado
        pantallaSecundaria.setBackground(Color.LIGHT_GRAY); // color de pantalla
        pantallaSecundaria.setLayout(new BorderLayout()); // El borde de layout

        areaTexto = new JTextArea(); // permite ingresar texto
        areaTexto.setLineWrap(true); // controla la longitud del texto cambiando a una segunda línea en caso de ser
                                     // necesario
        areaTexto.setWrapStyleWord(true); // controla que el texto no sea cortado a la mitad
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14)); // La fuente en el espacio de texto

        // Filtrar entradas desde el teclado
        areaTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                // Permitimos solo los números del teclado numérico derecho y los operadores
                if (!(key >= '0' && key <= '9') && !isNumericKeypad(key) && key != KeyEvent.VK_BACK_SPACE
                        && !isOperator(key)) { // comprueba que no intente escribir valores inválidos
                    e.consume(); // Si la tecla no es válida bloquea su escritura
                }
            }

            private boolean isNumericKeypad(char key) { // comprueba que pertezca al teclado numerico derecho
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

        add(pantallaSecundaria, BorderLayout.NORTH); // Posiciona el recuadro escribible arriba de todo

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // obtiene la resolución de pantalla
        int width = screenSize.width / 4; // En ancho editado
        int height = 600; // es el alto preestablecido

        setSize(width, height); // Indica el ancho y alto del recuadro
        setLocationRelativeTo(null); // es para setear a null la localización relativa

        JPanel mainPanel = new JPanel(new GridLayout(5, 4)); // Crea el panel para los botones de 5 lineas con 4 botones
                                                             // cada uno
        mainPanel.setBackground(Color.LIGHT_GRAY); // color del panel
        add(mainPanel, BorderLayout.CENTER); // añade el panel centrado a la pestaña

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
            areaTexto.setText(""); // El contenido escrito en el panel pasa a ser ""
        } else { // En caso contrario
            areaTexto.append(texto); // Se añade el contenido del botón
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraPantalla ventana = new CalculadoraPantalla();
            ventana.setVisible(true);
        });
    }
}
