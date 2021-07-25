package Basic;
/**
 * unit of all time is 10 seconds
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public abstract class Account {
	
	private String pin;
	
	protected int accNo;
	protected String name;
	protected double balance;
	protected String fileName;

	public abstract void withdraw(double amt); //to withdraw amount 'amt' from the account
	public abstract void deposit(double amt); //to deposit amount 'amt' to the account
	protected abstract double calculateInterest(Calendar c1, Calendar c2); //to calculate interest for time between c1 and c2
	
	public Account(int accNo, String name) {
		this.accNo = accNo;
		this.name = name;
		pin="0000";
	}
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	private int type;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAccNo() {
		return accNo;
	}

	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// used to change the pin
	//includes validations of old and new
	public void changePin(String newPIN, String oldPIN) {
		if (pin.equals(oldPIN)) {
			if (newPIN.length()==4) {
				for (int i=0;i<newPIN.length();i++) {
					if (!Character.isDigit(newPIN.charAt(i))) {
						System.out.println("PIN unchanged due to inappropriate value");
						return;
					}
				}
				pin = newPIN;
				System.out.println("PIN has been updated");
			} else {
				System.out.println("PIN unchanged due to inappropriate value");
			}
		} else {
			System.out.println("Wrong PIN");
		}
	}
	//adds the interest and prints the list of transactions of the account
	public void printTransactions() {
		deposit(0);
		System.out.println("\n\n**************************************************");
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String s;
			System.out.println(toString());
			br.readLine();br.readLine();br.readLine();
			while ((s=br.readLine())!= null) {
				System.out.println(s);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("**************************************************\n\n");
	}
	// used to empty the text file where the account details are stored
	public void empty() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
