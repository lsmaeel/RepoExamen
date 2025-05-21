package PaqueteBanco;

import java.io.Serializable;

/**
 *
 * @author Ismael
 */


abstract public class Cuenta implements Serializable, operable{

    private String iban;
    private String titular;
    private double saldo;
    private String dni;

    public Cuenta(String iban, String titular, String dni) {
        this.setIban(iban);
        this.setTitular(titular);
        this.setSaldo(0);
        this.setDni(dni);
    }

    public Cuenta() {
        this.setIban("");
        this.setTitular("");
        this.setSaldo(0);
        this.setDni("");
    }

    @Override
    public String toString() {       
        return String.format("%-24s %-30s %9s %10.2f", this.getIban(), this.getTitular(), this.getDni(), this.getSaldo());
    }

	@Override
	public String getDni() {
		return dni;
	}

	@Override
	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public double getSaldo() {
		return saldo;
	}

	@Override
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String getTitular() {
		return titular;
	}

	@Override
	public void setTitular(String titular) {
		this.titular = titular;
	}

	@Override
	public String getIban() {
		return iban;
	}

	@Override
	public void setIban(String iban) {
		this.iban = iban;
	}

}
