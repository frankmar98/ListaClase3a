import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Version con interfaz grafica de usuario del programa de sacar de la lista de clase
 * Frank Martinez Moreno 1AMT B
 * @author fmm
 *
 */
public class ListaClase3a {
	
	/**
	 * Inicia ventana, widgets, lista y contiene eventos que gestionan la salida
	 * @param args
	 */

	public static void main(String[] args) {
		String listaInicial[] = { "Alberto Ortega", "Alberto Pajares", "Aless", "Carlos", "Dani", "Frank", "Miguel",
				"Pablo", "Paulina", "Wilson" }; // lista inicial de la clase, luego se pueden añadir o quitar (la
												// dificutad de este programa)
		JFrame fr = new JFrame("Sacar de la lista, IES Paloma");
		JButton b1 = new JButton("Sacar alumno");
		JButton b2 = new JButton("Resetear");
		JTextArea ta1 = new JTextArea();
		JTextArea ta2 = new JTextArea();
		JScrollPane scroll1 = new JScrollPane(ta1); // alumnos por salir
		JScrollPane scroll2 = new JScrollPane(ta2); // alumnos que han salido
		fr.setSize(625, 640);
		b1.setBounds(5, 255, 290, 90);
		b2.setBounds(305, 255, 290, 90);
		scroll1.setBounds(5, 5, 590, 240);
		scroll2.setBounds(5, 355, 590, 240);
		fr.add(b1);
		fr.add(b2);
		fr.add(scroll1);
		fr.add(scroll2);
		fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		fr.setLayout(null);
		fr.setVisible(true);

		ta1.setText(convertir(listaInicial)); //inicializar con la listaInicial
		ta2.setText("");

		b2.addActionListener(new ActionListener() { // resetear
			public void actionPerformed(ActionEvent e) {
				ta1.setText(convertir(listaInicial));
				ta2.setText("");
			}
		});

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean haSalido[];
				String listaActual[], listaSacados[];
				listaActual = ta1.getText().split("\n");
				if (ta2.getText().equals("")) {
					for (String alumno : listaActual) {
						ta2.append("\n ");
					}
				}
				listaSacados = ta2.getText().split("\n");
				haSalido = new boolean[listaActual.length];
				for (int i = 0; i < listaActual.length; i++) {
					if (listaActual[i].isBlank()) {
						haSalido[i] = true;
					}
				}
				haSalido = turno(haSalido, listaActual);
				listaSacados = actualizarSacados(haSalido, listaActual, listaSacados);
				ta1.setText(convertir(actualizarRestantes(haSalido, listaActual)));
				ta2.setText(convertir(listaSacados));
				if (hanSalidoTodos(haSalido)) {
					ta1.setText(convertir(listaInicial));
					ta2.setText("");
				}
			}
		});

	}
	
	/**
	 * Convierte el array de la lista de clase a una cadena para insertar en el TextArea
	 * @param lista
	 * @return
	 */

	private static String convertir(String lista[]) {
		String aux = "";
		for (String alumno : lista) {
			if (alumno != null) {
				aux += alumno + "\n";
			} else {
				aux += "\n";
			}
		}
		return aux;
	}
	
	/**
	 * Borra de la lista alumnos que han salido
	 * @param haSalido
	 * @param lista
	 * @return
	 */

	private static String[] actualizarRestantes(boolean haSalido[], String lista[]) {
		int n = lista.length;
		for (int i = 0; i < n; i++) {
			if (haSalido[i]) {
				lista[i] = " ";
			}
		}
		return lista;
	}
	
	/**
	 * Añade a la lista alumnos que han salido
	 * @param haSalido
	 * @param listaTotal
	 * @param listaSacados
	 * @return
	 */

	private static String[] actualizarSacados(boolean haSalido[], String listaTotal[], String listaSacados[]) {
		int n = listaTotal.length;
		for (int i = 0; i < n; i++) {
			if (haSalido[i] && listaSacados[i].isBlank()) {
				listaSacados[i] = listaTotal[i];
			}
		}
		return listaSacados;
	}
	
	/**
	 * Saca aleatoriamente un alumno que no haya salido
	 * @param haSalido
	 * @param lista
	 * @return
	 */

	private static boolean[] turno(boolean haSalido[], String lista[]) {
		int num, n = lista.length;
		do {
			num = (int) (Math.random() * n);
		} while (haSalido[num]);
		haSalido[num] = true;
		JOptionPane.showMessageDialog(null, "Ha salido: " + lista[num]);
		return haSalido;
	}
	
	/**
	 * Comprueba si han salido todos los alumnos
	 * @param haSalido
	 * @return
	 */

	private static boolean hanSalidoTodos(boolean haSalido[]) {
		boolean hanSalido = false;
		int contador = 0, n = haSalido.length;
		for (int i = 0; i < n; i++) {
			if (haSalido[i]) {
				contador++;
			}
		}
		if (contador >= n) {
			hanSalido = true;
			JOptionPane.showMessageDialog(null, "Ha salido toda la clase, vuelta a empezar");
		}
		return hanSalido;
	}
}