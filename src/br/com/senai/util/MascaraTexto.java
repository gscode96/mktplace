
package br.com.senai.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MascaraTexto extends PlainDocument {
	
//Classe criada para tratar tipos de entradas dos usuarios
	public enum TipoEntrada {
		//declara como enuns os tipos de entradas
		numerointeiro, numerodecimal, nome, titulo, data, endereco,hora;
	};
	
	//cria duas variaveis para definir a qtd de caracter e o tipo de entrada
	private int qtdCaractere;
	private TipoEntrada tpEntrada;

	//construtor
	public MascaraTexto(int qtdCaractere, TipoEntrada tpEntrada) {
		this.qtdCaractere = qtdCaractere;
		this.tpEntrada = tpEntrada;
	}

	//sobreescreve o metodo insertString do java
	@Override
	public void insertString(int i, String string, AttributeSet a) throws BadLocationException {

		if (string == null || getLength() == qtdCaractere) {
			return;
		}
		int totalCarac = getLength() + string.length();
		//realiza o regex conformete o tipo da entrada
		//regex altera a string
		String regex = "";
		switch (tpEntrada) {
		case numerointeiro:
			regex = "[^0-9]";
			break;
		case numerodecimal:
			regex = "[^0-9,.]";
			break;
		case nome:
			regex = "[^\\p{IsLatin} ]";
			break;
		case titulo:
			regex = "[^\\p{IsLatin}@.\\-_][^0-9]";
			break;
		case data:
			regex = "[^0-9/]";
			break;
		case endereco:
			regex = "[^\\p{IsLatin} ][^0-9]";
			break;
		case hora:
			regex = "[^0-9:]";
			break;
		}
		string = string.replaceAll(regex, "");

		if (totalCarac <= qtdCaractere) {
			super.insertString(i, string, a);
		} else {
			String nova = string.substring(0, qtdCaractere);
			super.insertString(i, nova, a);
		}
	}

}
