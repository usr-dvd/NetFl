import java.util.Date;

public class User {
    private String userName;
    private String password;
    private int subscription;
    private final Date date = new Date();
    private final TvSerial[] viewedTvSeries = new TvSerial[Const.VIEWED_SE_ARR_LEN];
    private final Episode[] viewedEpisodes = new Episode[Const.VIEWED_EP_ARR_LEN];
    private final TvSerial[] endedWatch = new TvSerial[Const.ENDED_WATCH_SE_ARR_LEN];



    public Date endOfSubscription(){
        Date endDate = date;
        endDate.setHours(subscription);
        return endDate;
    }

    public Date getDate() {
        return date;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        boolean isSafePass = IsSafePass(password);
        if (isSafePass){
            this.password = password;
        }
        else {
            System.out.println("The password isn't safe");
        }
    }
    
    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public boolean IsSafePass(String password){
        boolean isSafe;
        String abc = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        boolean containsLet=false, containsNum=false;

        for (int i = 0; i < password.length(); i++){
            for (int j = 0; j < abc.length(); j++){
                if (password.charAt(i) == abc.charAt(j) || password.charAt(i) == Character.toUpperCase(abc.charAt(j))) {
                    containsLet = true;
                    break;
                }
            }
        }
        for (int i = 0; i < password.length(); i++){
            for (int j = 0; j < digits.length(); j++){
                if (password.charAt(i) == digits.charAt(j)) {
                    containsNum = true;
                    break;
                }
            }
        }

        isSafe = password.length() >= 6 && containsLet && containsNum;
        return isSafe;
    }

    public int  findLastIndex(Episode[] currentEpisodes) {
        int last = -1;
        for (int i = viewedEpisodes.length-1; i >= 0 ; i--) {
            for (int j = 0; j < currentEpisodes.length; j++){
                if (currentEpisodes[j] != null && currentEpisodes[j]==viewedEpisodes[i]){
                    last=j;
                    return last;
                }
            }
        }
        return last;
    }

    public void addToViewed(TvSerial serial, int episodeIndex){
        if (serial.getEpisodes()[episodeIndex-1]==null){
            System.out.println("There is no such episode");
        }
        else {
            for (int i = 0; i < viewedEpisodes.length; i++) {
                if (viewedEpisodes[i]==null){
                    viewedEpisodes[i]=serial.getEpisodes()[episodeIndex-1];
                    break;
                }
            }
            System.out.println("You watched " + serial.getEpisodes()[episodeIndex-1].getName());

            boolean isViewed = false;
            for (TvSerial viewedTvSery : viewedTvSeries) {
                if (viewedTvSery == serial) {
                    isViewed = true;
                    break;
                }
            }
            if (!isViewed) {
                for (int i = 0; i < viewedTvSeries.length; i++) {
                    if (viewedTvSeries[i]==null) {
                        viewedTvSeries[i]=serial;
                        break;
                    }
                }
            }
        }
    }
    
    public void addToEnded(TvSerial serial) {
        int epCounter = 0;
        for (int i = 0; i < serial.getEpisodes().length; i++) {
            if(serial.getEpisodes()[i] != null) {
                epCounter++;
            }
        }
        boolean isEnded = true;
        boolean[]  allViewedEp = new boolean[epCounter];
        int watchedCount = 0;
        for (int i = 0; i < serial.getEpisodes().length; i++) {
            Episode episode = serial.getEpisodes()[i];
            if (episode != null) {
                for (int j = 0; j < viewedEpisodes.length; j++) {
                    if (viewedEpisodes[j] != null) {
                        if (episode == viewedEpisodes[j]) {
                            allViewedEp[watchedCount] = true;
                            watchedCount++;
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < allViewedEp.length; i++) {
            if (!allViewedEp[i]) {
                isEnded = false;
                break;
            }
        }
        if (isEnded) {
            for (int i = 0; i < endedWatch.length; i++) {
                if (endedWatch[i] == null) {
                    endedWatch[i] = serial;
                    break;
                }
            }
        }
    }
    
    public void printTotalWatchedEp() {
        int counter = 0;
        for (int i = 0; i < viewedEpisodes.length; i++) {
            if (viewedEpisodes[i] != null) {
                counter++;
            }
        }
        System.out.println("Total Episodes watched: " + counter);
    }

    public int countTotalWatchedSe() {
        int counter = 0;
        for (int i = 0; i < viewedTvSeries.length; i++) {
            if (viewedTvSeries[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    public void printEndedWatchSe() {
        int endedCounter = 0;
        for (int i = 0; i < endedWatch.length; i++) {
            if (endedWatch[i] != null) {
                endedCounter++;
            }
        }
        System.out.println("Ended to watch: " + endedCounter);
    }

    public void printWatchedSe() {
        for (int i = 0; i < viewedTvSeries.length; i++) {
            if (viewedTvSeries[i] != null) {
                TvSerial current = viewedTvSeries[i];
                System.out.println("Watched serial: " + current.getName());
                printLastEp(findLastIndex(current.getEpisodes()), current);

            }
        }

    }

    public void printLastEp(int last, TvSerial serial) {
        if (last >= 0) {
            System.out.println("Last episode watched: " + serial.getEpisodes()[last].getName());
        }
    }


    public int getSubscription() {
        return subscription;
    }

    public TvSerial[] getViewedTvSeries() {
        return viewedTvSeries;
    }

    public Episode[] getViewedEpisodes() {
        return viewedEpisodes;
    }

    public TvSerial[] getEndedWatch() {
        return endedWatch;
    }
}
