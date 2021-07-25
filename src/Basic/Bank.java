package Basic;

import java.util.HashMap;


public class Bank { 

	public static final int STARTING_ACCOUNT_NO = 1000;
	
	private HashMap<Integer, Account> accounts;
	private String name;
	private String location;
	
	public Bank(String name, String location) {
		this.name = name;
		this.location = location;
		accounts = new HashMap<Integer, Account>();
	}

	public void setAccounts(HashMap<Integer, Account> accounts) {
		this.accounts = accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	//to get the basic details of the bank
	@Override
	public String toString() {
		String s ="";
		s = s+"\nName of Bank\t\t: "+this.name;
		s = s+"\nNumber of Accounts\t: "+accounts.size();
		return s;
	}
	//to create accounts of certain types
	public Account createAccount(String name,  int type, double amt) {
		Account acc=null;
		int accno=STARTING_ACCOUNT_NO;
		for (;accounts.containsKey(accno);accno++);
		switch (type) {
		case 1:
			acc = new SavingAccount(accno, name, location);
			accounts.put(acc.getAccNo(), acc);	
			break;
		case 2:
			acc = new CurrentAccount(accno, name, location);
			accounts.put(acc.getAccNo(), acc);	
			break;
		case 3:
			acc = new FixedDeposit(accno, name, amt, location);
			accounts.put(acc.getAccNo(), acc);			
		}
		return acc;
	}
	//used to delete the account
	public boolean deleteAccount(int accountNo) {
		if (accounts.containsKey(accountNo)){
			accounts.get(accountNo).empty();
			accounts.remove(accountNo);
			return true;
		}
		return false;
	}
	public Account getAccount(int accNo) {		
		return accounts.get(accNo);
	}
	
}
