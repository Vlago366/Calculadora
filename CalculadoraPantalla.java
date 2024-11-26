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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class CalculadoraPantalla extends JFrame {

    // Creamos una variable global para el JTextArea
    private JTextArea areaTexto;
    private boolean tecladoOn = true;

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

        JScrollPane scrollTexto = new JScrollPane(areaTexto); // ayuda a corregir el cambio de lineas al escribir en la

        if(tecladoOn){
            activarTeclado();
        }
        
        pantallaSecundaria.add(scrollTexto, BorderLayout.CENTER); // panel secundario donde se escribe los números

        add(pantallaSecundaria); // Añade el panel de la pantalla al JFrame
        setResizable(false);
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
        String texto = areaTexto.getText(); // Obtener el texto actual en el JTextArea
    
        if (texto.isEmpty()) { //Si no hay nada escrito
            return; // retorna
        }
    
        try {
            texto = calcularOperacion(texto, "*/"); // comprueba primero si hay multiplicacion o división y actualiza el contenido del texto
    
            texto = calcularOperacion(texto, "+-"); // Luego hace las sumas y restas y actualiza el valor de texto
    
            areaTexto.setText(texto); // Escribe el texto en la pantalla
        } catch (Exception e) {
            areaTexto.setText("Error"); // Si peta en el proceso
        }
    }
    
    private String calcularOperacion(String texto, String operadores) {
        String regex = "(-?\\d+(?:\\.\\d+)?)([" + operadores + "])(-?\\d+(?:\\.\\d+)?)"; //Expresion para encontrar operadores

        // Crear un patrón de expresión regular
        Pattern pattern = Pattern.compile(regex); // crea un patron que contiene * y /
        Matcher matcher = pattern.matcher(texto); // busca el patron en el siguiente texto

        while (matcher.find()) { // el metodo find de la clase match devuelve true si encuentra el patrón buscado

            double num1 = Double.parseDouble(matcher.group(1)); // El grupo 1 contiene los numeros antes del operador
            String operador = matcher.group(2); // El grupo dos contiene el operador
            double num2 = Double.parseDouble(matcher.group(3)); // El grupo tres contiene el numero que se va a operar

            double resultado = 0; // variable que contiene el resultado
            switch (operador) { // Segun el operador hará una operación u otra
                case "*":
                    resultado = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("División por cero"); // en caso de intentar dividir por cero
                    }
                    resultado = num1 / num2;
                    break;
                case "+":
                    resultado = num1 + num2;
                    break;
                case "-":
                    resultado = num1 - num2;
                    break;
            }

            // Reemplazar la operación con el resultado
            texto = texto.substring(0, matcher.start()) + resultado + texto.substring(matcher.end()); //Obtiene la posicion de donde comienza la operacion y donde termina y la reemplaza por el resultado
            
            matcher = pattern.matcher(texto); // vuelve a buscar el patrón en el texto
        }

        return texto; // Devolver el texto con el resultado final
    }

    public void activarTeclado() {
        areaTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                
                // Permitimos solo los números del teclado numérico, las teclas de 0-9, y los operadores
                if (!isValidKey(keyCode)) {
                    e.consume(); // Si la tecla no es válida, la bloqueamos
                }
            }
    
            // Verifica si la tecla es válida (números, operadores y retroceso)
            private boolean isValidKey(int keyCode) {
                return (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) // Teclas de 0-9
                        || (keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) // Teclas del teclado numérico
                        || keyCode == KeyEvent.VK_BACK_SPACE // Permite la tecla de retroceso
                        || isOperatorKey(keyCode); // Permite los operadores
            }
    
            // Verifica si el código de la tecla es un operador válido
            private boolean isOperatorKey(int keyCode) {
                return keyCode == KeyEvent.VK_PLUS || keyCode == KeyEvent.VK_MINUS
                        || keyCode == KeyEvent.VK_MULTIPLY || keyCode == KeyEvent.VK_DIVIDE;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraPantalla ventana = new CalculadoraPantalla();
            ventana.setVisible(true);
        });
    }
}
