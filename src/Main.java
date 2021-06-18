import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Users users = new Users();
        Scanner intScanner = new Scanner(System.in);
        Scanner lnScanner = new Scanner(System.in);
        TvSerial[] tvSerials = new TvSerial[Const.SERIALS_ARR_LEN];
        fillArray(tvSerials, "Star Way");
        fillArray(tvSerials, "Perfect Crime");
        fillArray(tvSerials, "Neighbors");
        fillEpisodes(tvSerials, "Star Way","SW_Ep_");
        fillEpisodes(tvSerials, "Perfect Crime", "PC_Ep_");
        fillEpisodes(tvSerials, "Neighbors", "Ne_Ep_");

        User user;
        boolean running = true;

        while (running) {
            int userChoice=0;
            try {

                System.out.println(
                        "1) Create new account\n" +
                        "2) Sign into existing account\n" +
                        "3) End the program "
                );
                System.out.print("Enter the option: ");
                userChoice = intScanner.nextInt();
            } catch (InputMismatchException e) {
                    intScanner.nextLine();
                    System.out.println("Enter only digits 1, 2");
                }
                String userName;
                String password="";
                switch (userChoice) {
                    case Const.MENU_OPT_1:
                        if (!users.checkIfFull()) {
                            System.out.print("Enter the user Name: ");
                            userName = lnScanner.nextLine();
                            while (users.findUser(userName,false)) {
                                System.out.println("The user name already exists in the system choose another");
                                System.out.print("Enter the user Name: ");
                                userName = lnScanner.nextLine();
                            }
                            user = new User();
                            while (!user.IsSafePass(password)) {
                                System.out.print("Enter the safe password (at least 6 symbols, at least 1 eng. letter, at least 1 number): ");
                                password = lnScanner.nextLine();
                                user.setPassword(password);
                            }
                            user.setSubscription(Const.USR_END_SUBSCRIPTION_HOURS);
                            user.setUserName(userName);
                            users.addUser(user);
                        }
                        else {
                            System.out.println("The array of users is full need to increase Const.USERS_ARR_LEN");
                        }

                        break;
                    case Const.MENU_OPT_2:
                        if (users.checkIfEmpty()){
                            System.out.println("There is no users in the system");
                        }
                        else {
                            System.out.print("Enter the user name: ");
                            userName=lnScanner.nextLine();
                            System.out.print("Enter the password: ");
                            password = lnScanner.nextLine();
                            if (!users.findUser(userName, true) || !users.getUser().getPassword().equals(password)){

                                System.out.println("Wrong user name or password");
                            }
                            else {
                                user = users.getUser();
                                do {
                                    System.out.println(
                                            "1) Show a list of all TV series\n" +
                                            "2) Show a list of TV series that started watching\n"+
                                            "3) Show subscription details\n"+
                                            "4) Select TV serial to watch\n"+
                                            "5) Log out");
                                    try {
                                        System.out.print("Enter the option: ");
                                        userChoice = intScanner.nextInt();
                                    switch (userChoice) {
                                        case Const.SUB_MENU_OPT_1:
                                            printTvSerials(tvSerials);
                                            break;
                                        case Const.SUB_MENU_OPT_2:
                                            if (user.countTotalWatchedSe() == 0) {
                                                System.out.println("You didn't watch any tv serial yet");
                                            }
                                            else {
                                                user.printWatchedSe();
                                            }
                                            break;
                                        case Const.SUB_MENU_OPT_3:
                                            System.out.println("User name: " + user.getUserName());
                                            System.out.println("Date of account creation:  " + user.getDate());
                                            System.out.println("Subscription expiration date: " + user.endOfSubscription());
                                            System.out.println("Total watched serials: " + user.countTotalWatchedSe());
                                            user.printEndedWatchSe();
                                            user.printTotalWatchedEp();
                                            break;
                                        case Const.SUB_MENU_OPT_4:
                                            String tvSerialName;
                                            System.out.print("Enter the TV Serial name: ");
                                            tvSerialName = lnScanner.nextLine();
                                            if (checkIfExist(tvSerialName, tvSerials)) {
                                               TvSerial current = tvSerials[findTvSerialIndex(tvSerialName, tvSerials)];
                                               current.printEpisodes();
                                               user.printLastEp(user.findLastIndex(current.getEpisodes()), current);
                                               System.out.println("enter the episode number you want to watch");
                                               try {

                                                   int episodeIndex = intScanner.nextInt();
                                                   user.addToViewed(current, episodeIndex);
                                                   user.addToEnded(current);
                                               }
                                               catch (InputMismatchException e){
                                                   intScanner.nextLine();
                                                   System.out.println("Enter only digits");
                                               }

                                            }
                                            break;
                                        case Const.SUB_MENU_OPT_5:
                                            break;
                                        default:
                                            System.out.println("Unknown option");
                                        }
                                    }
                                    catch (InputMismatchException e) {
                                        intScanner.nextLine();
                                        System.out.println("Enter only digits 1, 2, 3, 4, 5");
                                    }


                                } while (userChoice != 5);


                            }


                        }
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Unknown option");
                }
            }
        }
        public static void printTvSerials(TvSerial[] tvSerials){
            for (int i = 0; i < tvSerials.length; i++) {
                if (tvSerials[i] != null) {
                    System.out.println((i+1)+"; "+tvSerials[i].getName());
                }
            }
        }

        public static boolean checkIfExist(String tvSerialName, TvSerial[] tvSerials) {
            boolean exist = false;
            for (int i = 0; i < tvSerials.length; i++) {
                if (tvSerials[i] != null && tvSerials[i].getName().equals(tvSerialName)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                System.out.println("The tv serial with this name doesn't exist");
            }
            return exist;
        }

        public static int findTvSerialIndex(String tvSerialName, TvSerial[] tvSerials){
                int i=0;
                while (i < tvSerials.length) {
                    if (tvSerials[i].getName().equals(tvSerialName)){
                        break;
                    }
                    i++;
                }
                return i;
        }

        public static void fillArray(TvSerial[] tvSerials, String serialName) {
            for (int i = 0; i < tvSerials.length; i++) {
                if (tvSerials[i] == null) {
                    TvSerial serial = new TvSerial(serialName);
                    tvSerials[i] = serial;
                    break;
                }
            }
        }

    public static void fillEpisodes(TvSerial[] tvSerials, String serialName, String epSubName) {
        for (int i = 0; i < tvSerials.length; i++) {
            if (tvSerials[i] != null) {
                TvSerial serial = tvSerials[i];
                if (serial.getName().equals(serialName)) {
                    for (int j = 0; j < 3; j++) {
                        if(serial.getEpisodes()[j] == null) {
                            String index = String.valueOf(j+1);
                            Episode episode = new Episode(epSubName.concat(index), "***short description of episode***");
                            serial.getEpisodes()[j]=episode;
                        }
                    }
                }
            }
        }
    }
}

