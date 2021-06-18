public class TvSerial extends Content {

    private Episode[] episodes = new Episode[Const.EPISODES_ARR_LEN];


    public TvSerial(String name) {
        super(name);
    }

    public Episode[] getEpisodes() {
        return episodes;
    }

    public void printEpisodes(){
        for (int i = 0; i < episodes.length; i++){
            if (episodes[i] != null) {
                episodes[i].getRealiseDate().setHours(Const.EP_RLS_HOURS+i*Const.EP_RLS_DH);
                System.out.println(
                        "Episode " + (i+1) + " name: " + episodes[i].getName()+
                                ", Description: " + episodes[i].getDescription()+
                                ", Realise Date: " + episodes[i].getRealiseDate()
                        );
            }
        }
    }

    public void setEpisodes(Episode[] episodes) {
        this.episodes = episodes;
    }

}
