package banking_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class FixedDeposit extends Account {
	
	public static final double RATE = 0.012;
	public static final int MATURATION_TIME = 15;
	private Calendar old;
	private String details;
	
	
	public FixedDeposit(int accNo, String name, double amount, String location) {
		super(accNo, name);
		super.setType(3);
		this.balance=amount;
		fileName = location+"\\AccountNo"+accNo+".txt";
		try {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			old = Calendar.getInstance();
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(name);
			bw.write("\n"+accNo+"\n");
			bw.write("FixedDeposit Account\n");
			bw.write("\n\t\t\tBalance\t\t\tInterest\n");
			bw.write("Deposit\t\t"+df.format(amount)+"\n");
			details = sdf.format(old.getTime())+"\t"+df.format(balance)+"\t\t\t"+df.format(0);
			bw.write(details+"\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//used to calculate interest
	public double calculateInterest(Calendar first, Calendar now) {
		long time = (now.getTimeInMillis()-first.getTimeInMillis())/10000;
		if (time>MATURATION_TIME) {
			old = Calendar.getInstance();
		}
		return balance*time*time/MATURATION_TIME*RATE;
	}

	//returns string containing the basic details of the account
	// includes account number, account name, account balance
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String s = "Account number\t: "+this.accNo;
		s = s+"\nName\t\t: "+this.name;
		s = s+"\nAccount Balance\t: "+df.format(this.balance);
		return s;
	}
	
	@Override
	public void withdraw(double amt) {	
		//no functionality of method
	}

	//used to deposit amount and log the details
	@Override
	public void deposit(double amt) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss");
		DecimalFormat df = new DecimalFormat("#,##0.00");
		Calendar old= Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		Scanner sc = new Scanner(details);
		String prev = sc.next();
		sc.close();
		try {
			old.setTime(sdf.parse(prev));
			now.setTime(sdf.parse(sdf.format(Calendar.getInstance().getTime())));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		double interest = calculateInterest(old , now);
		old = now;
		this.balance+=amt+interest;
		details = sdf.format(now.getTime())+"\t"+df.format(balance)+"\t\t\t"+df.format(interest);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
			bw.write("Deposit\t\t"+df.format(amt));
			bw.write("\n"+details+"\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
