import java.util.*;

class Transaction {
    String type;
    double amount;
    String receiver;
    Date date;

    Transaction(String type, double amount, String receiver) {
        this.type = type;
        this.amount = amount;
        this.receiver = receiver;
        this.date = new Date();
    }

    public String toString() {
        if(receiver != null)
            return type + " ₹" + amount + " -> " + receiver + " | " + date;
        else
            return type + " ₹" + amount + " | " + date;
    }
}

class Account {
    String username;
    String password;
    String accountNumber;
    double balance;

    ArrayList<Transaction> transactions = new ArrayList<>();

    Account(String username, String password, String accountNumber) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, null));
        System.out.println("Deposit Successful");
    }

    void withdraw(double amount) {

        if(amount > balance){
            System.out.println("Insufficient Balance");
            return;
        }

        balance -= amount;
        transactions.add(new Transaction("Withdraw", amount, null));
        System.out.println("Withdraw Successful");
    }

    void transfer(Account receiver, double amount){

        if(amount > balance){
            System.out.println("Insufficient Balance");
            return;
        }

        balance -= amount;
        receiver.balance += amount;

        transactions.add(new Transaction("Transfer", amount, receiver.accountNumber));

        System.out.println("Transfer Successful to Account: " + receiver.accountNumber);
    }

    void showTransactions(){

        if(transactions.isEmpty()){
            System.out.println("No Transactions Yet");
            return;
        }

        for(Transaction t : transactions){
            System.out.println(t);
        }
    }
}

public class BankSystem{

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Account> accounts = new ArrayList<>();


    static String generateAccountNumber(){
        Random r = new Random();
        return "AC" + (100000 + r.nextInt(900000));
    }


    static Account login(){

        System.out.print("Username: ");
        String user = sc.next();

        System.out.print("Password: ");
        String pass = sc.next();

        for(Account acc : accounts){
            if(acc.username.equals(user) && acc.password.equals(pass)){
                return acc;
            }
        }

        System.out.println("Invalid Login");
        return null;
    }


    static Account findAccount(String accNo){

        for(Account acc : accounts){
            if(acc.accountNumber.equals(accNo)){
                return acc;
            }
        }

        return null;
    }


    public static void main(String[] args) {

        while(true){

            System.out.println("\n===== BANK SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            if(choice == 1){

                System.out.print("Enter Username: ");
                String user = sc.next();

                System.out.print("Enter Password: ");
                String pass = sc.next();

                String accNo = generateAccountNumber();

                Account newAcc = new Account(user, pass, accNo);

                accounts.add(newAcc);

                System.out.println("Account Created Successfully");
                System.out.println("Account Number: " + accNo);

            }

            else if(choice == 2){

                Account current = login();

                if(current == null)
                    continue;

                while(true){

                    System.out.println("\n===== DASHBOARD =====");
                    System.out.println("Account Number: " + current.accountNumber);
                    System.out.println("Balance: ₹" + current.balance);

                    System.out.println("\n1 Deposit");
                    System.out.println("2 Withdraw");
                    System.out.println("3 Transfer");
                    System.out.println("4 Transaction History");
                    System.out.println("5 Logout");

                    int ch = sc.nextInt();

                    if(ch == 1){

                        System.out.print("Enter Amount: ");
                        double amt = sc.nextDouble();

                        current.deposit(amt);
                    }

                    else if(ch == 2){

                        System.out.print("Enter Amount: ");
                        double amt = sc.nextDouble();

                        current.withdraw(amt);
                    }

                    else if(ch == 3){

                        System.out.print("Enter Receiver Account Number: ");
                        String accNo = sc.next();

                        Account receiver = findAccount(accNo);

                        if(receiver == null){
                            System.out.println("Account Not Found");
                            continue;
                        }

                        System.out.print("Enter Amount: ");
                        double amt = sc.nextDouble();

                        current.transfer(receiver, amt);
                    }

                    else if(ch == 4){

                        current.showTransactions();
                    }

                    else if(ch == 5){

                        System.out.println("Logged Out");
                        break;
                    }

                }

            }

            else if(choice == 3){

                System.out.println("Thank You");
                break;
            }
        }
    }
}
