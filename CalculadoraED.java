/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package calculator;

import java.util.ArrayList;
import pilas.PilaA;
import pilas.PilaADT;

/**
 *
 * @author neto-
 */
public class CalculadoraED extends javax.swing.JFrame {

    boolean bandera = false;
    boolean bandera2 = false;

    /**
     * Regresa el número de elementos de una pila dada.
     *
     * @param pila Pila de la cual se quiere conocer su número de elementos.
     * @return - int: El número de elementos en la pila.
     */
    public static <T> int numEle(PilaADT<T> pila) {
        int contador = 0;
        ArrayList<T> apoyo = new ArrayList();
        while (!pila.isEmpty()) {
            apoyo.add(pila.pop());
            contador++;
        }
        int i = apoyo.size() - 1;
        while (i >= 0) { //lo regresa a su pos original pa q la pila no se vea afectada
            pila.push(apoyo.get(i));
            i--;
        }
        return contador;
    }

    /**
     * Determina si un carácter dado es un número o un punto decimal.
     *
     * @param car Caracter a evaluar.
     * @return <ul>
     * <li>true: si el carácter es un número o un punto decimal.
     * <li>false: si el carácter no es un número o punto decimal.
     * </ul>
     */
    public static boolean esNum(Character car) {
        boolean res = false;
        if (car == '0' || car == '1' || car == '2' || car == '3' || car == '4' || car == '5' || car == '6' || car == '7' || car == '8' || car == '9' || car == '.') {
            res = true;
        }
        return res;
    }

    /**
     * Determina si un carácter dado es un operador (excepto -).
     *
     * @param car Caracter a evaluar.
     * @return <ul>
     * <li>true: si el carácter es un operador.
     * <li>false: si el carácter no es un operador.
     * </ul>
     */
    public static boolean esSigno(Character car) { //no incluye el -
        boolean res = false;
        if (car == '+' || car == '*' || car == '/' || car == '^') {
            res = true;
        }
        return res;
    }

    /**
     * Determina si una expresión matemática tiene una correcta sintaxis.
     *
     * @param cad Expresión a evaluar.
     * @return <ul>
     * <li>true: si la expresión tiene sintaxis correcta.
     * <li>false: si la expresión tiene sintaxis incorrecta.
     * </ul>
     */
    public static boolean malSignos(String cad) {
        boolean res = false;
        PilaA<Character> p = new PilaA();
        int i = 0;
        if (!esSigno(cad.charAt(cad.length() - 1)) && cad.charAt(cad.length() - 1) != '-') { //si el ultimo elemento no es signo
            while (i < cad.length()) {
                if (esSigno(cad.charAt(i))) { //es signo (excepto el menos
                    try {
                        p.pop();
                    } catch (Exception e) {
                        break;
                    }
                } else if (cad.charAt(i) == '-') {
                    if (i != 0 && cad.charAt(i - 1) != '(') {
                        if (cad.charAt(i + 1) == ')') { //(3-)
                            break;
                        }
                        if (esSigno(cad.charAt(i - 1)) || cad.charAt(i - 1) == '-') {// 3*-2 o 3--2
                            break;
                        } else if (esSigno(cad.charAt(+1)) || cad.charAt(i + 1) == '-') {// 3-*2   o 3--2
                            break;
                        } else {
                            if (esNum(cad.charAt(i - 1))) {//funge como operador
                                try {
                                    p.pop();
                                } catch (Exception e) {
                                    break;
                                }
                            }
                        }

                    }

                } else if (cad.charAt(i) == '(') {
                    if (!p.isEmpty()) {//la expersion esta bien antes de '(' si la pila esta vacía
                        break;

                    }
                } else if (cad.charAt(i) == ')') {
                    if (numEle(p) != 1) {//la expersion esta bien al salir del parentesis si la pila tiene 1 elemento
                        break;
                    }
                } else { //si es num, punto decimal
                    if (i == 0) { //push el numero o decimal en la pos 0
                        p.push(cad.charAt(i));
                    } else if (esSigno(cad.charAt(i - 1)) || cad.charAt(i - 1) == ')' || cad.charAt(i - 1) == '(' || cad.charAt(i - 1) == '-') {  //pa q sirva con nums de varios digitos
                        p.push(cad.charAt(i));
                    }
                }
                i++;
            }

        }
        if (i == cad.length() && numEle(p) == 1) {
            res = true;
        }

        return res;
    }

    /**
     * Determina si una cadena cuenta con una cantidad y acomodo adecuado de
     * paréntesis.
     *
     * @param cad Cadena a evaluar
     * @return <ul>
     * <li>true: si la expresión tiene paréntesis adecuados.
     * <li>false: si la expresión tiene no paréntesis adecuados.
     * </ul>
     */
    public static boolean parentesisBal(String cad) {
        PilaA<Character> p = new PilaA();
        int i = 0;
        boolean res = false;
        while (i < cad.length()) {
            if (cad.charAt(i) == '(') {
                p.push(cad.charAt(i)); //me lo puedo ahorrar y decirle push('A') o cualquier caracter
            }
            if (cad.charAt(i) == ')') {
                try {
                    p.pop(); //lanza la exception hecha en clase 
                } catch (Exception e) { //si lanza exception, pa q no me pare el programa y c vea feo
                    break;
                }
            }
            i++;
        }
        if (i == cad.length() && p.isEmpty()) {
            res = true;
        }

        return res;
    }

    /**
     * Determina si una si una expresión matemática cuenta con una correcta
     * sintaxis y un uso adecuado de paréntesis.
     *
     * @param cad Cadena a evaluar
     * @return <ul>
     * <li>true: si la expresión cumple con ambas condiciones.
     * <li>false: si la expresión no cumple al menos una de las dos condiciones.
     * </ul>
     */
    public static boolean estaBien(String cad) {
        boolean res = false;
        Integer n = 0;

        if (malSignos(cad) && parentesisBal(cad)) {
            res = true;
        }
        return res;
    }

    /**
     * Creates new form CalculadoraED
     */
    public CalculadoraED() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_escribeOperacion = new javax.swing.JTextField();
        jButton_siete = new javax.swing.JButton();
        jButton_nueve = new javax.swing.JButton();
        jButton_cuatro = new javax.swing.JButton();
        jButton_ocho = new javax.swing.JButton();
        jButton_seis = new javax.swing.JButton();
        jButton_uno = new javax.swing.JButton();
        jButton_dos = new javax.swing.JButton();
        jButton_tres = new javax.swing.JButton();
        jButton_cero = new javax.swing.JButton();
        jButton_puntoDecimal = new javax.swing.JButton();
        jButton_signoIgual = new javax.swing.JButton();
        jButton_dividir = new javax.swing.JButton();
        jButton_multiplicar = new javax.swing.JButton();
        jButton_signoMenos = new javax.swing.JButton();
        jButton_signoMas = new javax.swing.JButton();
        jButton_signoPotencia = new javax.swing.JButton();
        jLabel_dameOperacion = new javax.swing.JLabel();
        jButton_AC = new javax.swing.JButton();
        jButton_Del = new javax.swing.JButton();
        jButton_parAbre = new javax.swing.JButton();
        jButton_parCierra = new javax.swing.JButton();
        jButton_cinco1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField_escribeOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_escribeOperacionActionPerformed(evt);
            }
        });

        jButton_siete.setText("7");
        jButton_siete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sieteActionPerformed(evt);
            }
        });

        jButton_nueve.setText("9");
        jButton_nueve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_nueveActionPerformed(evt);
            }
        });

        jButton_cuatro.setText("4");
        jButton_cuatro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cuatroActionPerformed(evt);
            }
        });

        jButton_ocho.setText("8");
        jButton_ocho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ochoActionPerformed(evt);
            }
        });

        jButton_seis.setText("6");
        jButton_seis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_seisActionPerformed(evt);
            }
        });

        jButton_uno.setText("1");
        jButton_uno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_unoActionPerformed(evt);
            }
        });

        jButton_dos.setText("2");
        jButton_dos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_dosActionPerformed(evt);
            }
        });

        jButton_tres.setText("3");
        jButton_tres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_tresActionPerformed(evt);
            }
        });

        jButton_cero.setText("0");
        jButton_cero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ceroActionPerformed(evt);
            }
        });

        jButton_puntoDecimal.setText(".");
        jButton_puntoDecimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_puntoDecimalActionPerformed(evt);
            }
        });

        jButton_signoIgual.setText("=");
        jButton_signoIgual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_signoIgualActionPerformed(evt);
            }
        });

        jButton_dividir.setText("/");
        jButton_dividir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_dividirActionPerformed(evt);
            }
        });

        jButton_multiplicar.setText("x");
        jButton_multiplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_multiplicarActionPerformed(evt);
            }
        });

        jButton_signoMenos.setText("-");
        jButton_signoMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_signoMenosActionPerformed(evt);
            }
        });

        jButton_signoMas.setText("+");
        jButton_signoMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_signoMasActionPerformed(evt);
            }
        });

        jButton_signoPotencia.setText("^");
        jButton_signoPotencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_signoPotenciaActionPerformed(evt);
            }
        });

        jButton_AC.setText("AC");
        jButton_AC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ACActionPerformed(evt);
            }
        });

        jButton_Del.setText("DEL");
        jButton_Del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DelActionPerformed(evt);
            }
        });

        jButton_parAbre.setText("(");
        jButton_parAbre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_parAbreActionPerformed(evt);
            }
        });

        jButton_parCierra.setText(")");
        jButton_parCierra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_parCierraActionPerformed(evt);
            }
        });

        jButton_cinco1.setText("5");
        jButton_cinco1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cinco1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_dameOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton_cero, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_puntoDecimal, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_uno, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_dos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton_cuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_cinco1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_siete, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_ocho, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_seis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_tres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_signoPotencia, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jButton_nueve, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton_dividir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_multiplicar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_signoMenos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_signoMas, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_signoIgual, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jTextField_escribeOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_AC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Del, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_parCierra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_parAbre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel_dameOperacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_escribeOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_nueve, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_dividir, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(jButton_siete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_ocho, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_multiplicar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                            .addComponent(jButton_cuatro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_cinco1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_seis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_signoMenos, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jButton_uno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_dos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_tres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_signoPotencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_cero, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(jButton_puntoDecimal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_signoMas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_signoIgual, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton_AC, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_Del, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_parAbre, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_parCierra, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                        .addGap(115, 115, 115))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_escribeOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_escribeOperacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_escribeOperacionActionPerformed

    private void jButton_nueveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_nueveActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "9");
    }//GEN-LAST:event_jButton_nueveActionPerformed

    private void jButton_signoPotenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_signoPotenciaActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }bandera2 = true;
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "^");    }//GEN-LAST:event_jButton_signoPotenciaActionPerformed

    private void jButton_sieteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sieteActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "7");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_sieteActionPerformed

    private void jButton_seisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_seisActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "6");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_seisActionPerformed

    private void jButton_ochoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ochoActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "8");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_ochoActionPerformed

    private void jButton_cuatroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cuatroActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "4");    }//GEN-LAST:event_jButton_cuatroActionPerformed

    private void jButton_tresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_tresActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "3");    }//GEN-LAST:event_jButton_tresActionPerformed

    private void jButton_dosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_dosActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "2");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_dosActionPerformed

    private void jButton_unoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_unoActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "1");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_unoActionPerformed

    private void jButton_ceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ceroActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "0");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_ceroActionPerformed

    private void jButton_puntoDecimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_puntoDecimalActionPerformed
      
            if (bandera) {
                jTextField_escribeOperacion.setText("");
                bandera = false;
            }
            jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + ".");
            bandera2 = true;
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_puntoDecimalActionPerformed

    private void jButton_dividirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_dividirActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        bandera2 = true;
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "/");    }//GEN-LAST:event_jButton_dividirActionPerformed

    private void jButton_multiplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_multiplicarActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }bandera2 = true;
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "*");    }//GEN-LAST:event_jButton_multiplicarActionPerformed

    private void jButton_signoMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_signoMenosActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }bandera2 = true;
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "-");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_signoMenosActionPerformed

    private void jButton_signoMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_signoMasActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }bandera2 = true;
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "+");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_signoMasActionPerformed

    private void jButton_signoIgualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_signoIgualActionPerformed
        boolean res;

        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        Calculadora c = new Calculadora();

        String exp = jTextField_escribeOperacion.getText();
        if (exp.length() > 0) {
            res = estaBien(exp);
            if (res) {
                jTextField_escribeOperacion.setText(c.resuelveExpresion(c.infixToPostfix(exp)));
            } else {
                jTextField_escribeOperacion.setText("Sintax error");
                bandera = true;
            }
        }
    }//GEN-LAST:event_jButton_signoIgualActionPerformed

    private void jButton_ACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ACActionPerformed
        jTextField_escribeOperacion.setText("");jTextField_escribeOperacion.setText("");    }//GEN-LAST:event_jButton_ACActionPerformed

    private void jButton_DelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DelActionPerformed
        String cad;
        cad = jTextField_escribeOperacion.getText();
        if (cad.length() > 0) {
            jTextField_escribeOperacion.setText(cad.substring(0, cad.length() - 1));
        }
    }//GEN-LAST:event_jButton_DelActionPerformed

    private void jButton_parAbreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_parAbreActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "(");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_parAbreActionPerformed

    private void jButton_parCierraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_parCierraActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + ")");        // TODO add your handling code here:

    }//GEN-LAST:event_jButton_parCierraActionPerformed

    private void jButton_cinco1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cinco1ActionPerformed
        if (bandera) {
            jTextField_escribeOperacion.setText("");
            bandera = false;
        }
        jTextField_escribeOperacion.setText(jTextField_escribeOperacion.getText() + "5");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_cinco1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalculadoraED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculadoraED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculadoraED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculadoraED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalculadoraED().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_AC;
    private javax.swing.JButton jButton_Del;
    private javax.swing.JButton jButton_cero;
    private javax.swing.JButton jButton_cinco1;
    private javax.swing.JButton jButton_cuatro;
    private javax.swing.JButton jButton_dividir;
    private javax.swing.JButton jButton_dos;
    private javax.swing.JButton jButton_multiplicar;
    private javax.swing.JButton jButton_nueve;
    private javax.swing.JButton jButton_ocho;
    private javax.swing.JButton jButton_parAbre;
    private javax.swing.JButton jButton_parCierra;
    private javax.swing.JButton jButton_puntoDecimal;
    private javax.swing.JButton jButton_seis;
    private javax.swing.JButton jButton_siete;
    private javax.swing.JButton jButton_signoIgual;
    private javax.swing.JButton jButton_signoMas;
    private javax.swing.JButton jButton_signoMenos;
    private javax.swing.JButton jButton_signoPotencia;
    private javax.swing.JButton jButton_tres;
    private javax.swing.JButton jButton_uno;
    private javax.swing.JLabel jLabel_dameOperacion;
    private javax.swing.JTextField jTextField_escribeOperacion;
    // End of variables declaration//GEN-END:variables
}
