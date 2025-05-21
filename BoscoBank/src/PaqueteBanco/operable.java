package PaqueteBanco;

public interface operable {

	void ingresar(double cantidad);

	void retirar(double cantidad);

	String toString();

	String getDni();

	void setDni(String dni);

	double getSaldo();

	void setSaldo(double saldo);

	String getTitular();

	void setTitular(String titular);

	String getIban();

	void setIban(String iban);

}