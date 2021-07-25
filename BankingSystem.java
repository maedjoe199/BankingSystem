package banking_system;

import java.io.File;
import java.util.Scanner;


/**
 * @author Edwin Joseph
 *
 * This is a banking program which has the basic functions of a bank
 * accounts may be created with specific account number and any account name
 * accounts can be created of types Saving, Current and Fixed Deposit
 * amount may be deposited into the account
 * amount may be withdrawn, transactions may be viewed and PIN may be changed using the account pin
 *
 */
public class BankingSystem {

	private static Scanner in;
	private static String licenceKey = "1234";

	public static int getChoice(Scanner in, int range) {
		int a = in.nextInt();
		in.nextLine();
		if (a<1 || a>range) {
			return getChoice(in, range);
		} else {
			return a;
		}
	}

	// provides as a window for the functioning of all banking processes
	public static void main(String[] args) {
		/*if (args.length != 1) {
			System.out.println("!!Warning. No license key specified while starting. Aborting the program..");
			return;
		}*/

		/*if (!args[0].equals(licenceKey)) {
			System.out.println("Incorrect licence Key...Aborting the program..");
			return;
		}	 */
		Bank bank = null;
		String dirLocation = null;
		try {
			File file = new File("C:\\BankingSystem");
			if(!file.exists()){
				file.mkdir();
			}
			int noOfFiles = file.listFiles().length;
			dirLocation = "C:\\BankingSystem\\Accounts"+(noOfFiles+1);
			new File(dirLocation).mkdirs();
			bank = new Bank("Royal Bank", dirLocation);
		} catch (Exception e) {
			System.out.println("Error occured while creating directory" + dirLocation);
			return;
		}

		in = new Scanner(System.in);
		Account acc=null;
		int i = 1;
		while(true) {
			int choice = 2;
			if (acc == null) {
				System.out.println("\n\n\n");
				System.out.println("Token number : "+i);
				System.out.println("Enter 1 for creating account");
				System.out.println("Enter 2 for using an existing account");
				choice = getChoice(in, 2);
				System.out.println("\n\n");
				acc=null;
			}

			switch(choice) {
			case 1:
				System.out.println("Enter the name of the account holder");
				String name = in.nextLine().toUpperCase();
				System.out.println("\n\n");
				System.out.println("Enter 1 for Saving Account");
				System.out.println("\t-Interest per second\t"+SavingAccount.RATE);
				System.out.println("\t-Minimum Balance\t"+SavingAccount.MIN_BALANCE);
				System.out.println("Enter 2 for Current Account");
				System.out.println("\t-Interest per second\t"+CurrentAccount.RATE);
				System.out.println("\t-Minimum Balance\t"+CurrentAccount.MIN_BALANCE);
				System.out.println("Enter 3 for Fixed Deposit ");
				System.out.println("\t-Interest per second\t"+FixedDeposit.RATE);
				System.out.println("\t-Maturation Time\t"+FixedDeposit.MATURATION_TIME+" sec");
				int type=getChoice(in, 3);
				switch (type) {
				case 1:
					acc = bank.createAccount(name, 1, 0);
					System.out.println("\n\n");
					System.out.println("Account type\tSavings Account");
					break;
				case 2:
					acc = bank.createAccount(name, 2, 0);
					System.out.println("\n\n");
					System.out.println("Account type\tCurent Account");
					break;
				case 3:
					System.out.println("Enter the amount for fixed deposit");
					double amt = Double.parseDouble(in.nextLine());
					System.out.println("\n\n");
					acc = bank.createAccount(name, 3, amt);
					System.out.println("Account type\tFixedDeposit Account");
					break;
				}
				System.out.println(acc.toString());
				System.out.println("Account PIN\t: "+acc.getPin());
				System.out.println("\n\n");
			case 2:
				if (acc==null) {
					System.out.println("Enter the account number");
					int accNo =  Integer.parseInt(in.nextLine());
					acc=bank.getAccount(accNo);
					if (acc==null) {
						System.out.println("Account does not exist");
						i++;
						break;
					} else {
						System.out.println("Account name\t: "+acc.getName());
					}
				}
				int activity = 0;
				System.out.println("\n\n");
				switch (acc.getType()) {
				case 1:
				case 2:
					System.out.println("Enter 1 to change PIN");
					System.out.println("Enter 2 to view complete transactions");
					System.out.println("Enter 3 to close account");
					System.out.println("Enter 4 to log off from account");
					System.out.println("Enter 5 to deposit");
					System.out.println("Enter 6 to withdraw");
					activity=getChoice(in, 6);
					break;
				case 3:
					System.out.println("Enter 1 to change PIN");
					System.out.println("Enter 2 to view complete transactions");
					System.out.println("Enter 3 to close account");
					System.out.println("Enter 4 to switch account");
					activity=getChoice(in, 4);
				}
				String pin=null;
				switch (activity) {
				case 1:
					System.out.println("Enter the old 4-digit PIN");
					pin = in.nextLine();
					System.out.println("Enter the new 4-digit PIN");
					String n1 = in.nextLine();
					System.out.println("Re-enter the new 4-digit PIN");
					String n2 = in.nextLine();
					if(n1.equals(n2)) {
						acc.changePin(n1, pin);
					} else {
						System.out.println("Re-entered different PIN\nPIN unchanged");
					}
					break;
				case 2:
					System.out.println("Enter the 4-digit PIN");
					pin = in.nextLine();
					if (pin.equals(acc.getPin())) {
						acc.printTransactions();
					} else {
						System.out.println("Wrong PIN");
					}
					break;
				case 3:
					System.out.println("Enter the 4-digit PIN");
					pin = in.nextLine();
					if (pin.equals(acc.getPin())) {
						System.out.println("Enter 1 to confirm deletion");
						System.out.println("Enter 2 to cancel deletion");
						int delete=Integer.parseInt(in.nextLine());
						switch (delete) {
						case 1:
							if (bank.deleteAccount(acc.getAccNo())) {
								System.out.println("Account deleted");
								acc = null;
								i++;
								System.out.println("Press 1 to continue");
								System.out.println("Press 2 to exit...");
								int exit = getChoice(in, 2);
								if (exit==2) {
									return;
								}
							} else {
								System.out.println("Account not deleted");
							}
							break;
						case 2:
							System.out.println("Account not deleted");
						}
					} else {
						System.out.println("Wrong PIN");
					}
					break;
				case 4:
					System.out.println("Switching Account...\n\n");
					acc = null;
					i++;
					System.out.println("Press 1 to ShutDown the Banking System...");
					System.out.println("Press 2 for next customer...");
					int exit = getChoice(in, 2);
					if (exit==1) {
						System.out.println("Thank you for using Banking Systems...\nShutting Down..");
						return;
					}
					break;
				case 5:
					System.out.println("Enter the amount");
					double deposit = Double.parseDouble(in.nextLine());
					acc.deposit(deposit);
					break;
				case 6:
					System.out.println("Enter the 4-digit PIN");
					pin = in.nextLine();
					if (pin.equals(acc.getPin())) {
						System.out.println("Enter the amount");
						double withdraw = Double.parseDouble(in.nextLine());
						acc.withdraw(withdraw);
					} else {
						System.out.println("Wrong PIN");
					}
					break;
				}
			}
		}
	}

}
