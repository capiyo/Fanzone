package ke.co.capiyo.fanzone.Models;

public class PendingModel {
    private String homeTeam;
    private String awayTeam;
    private String progress;
    private String gameId;
    private String score;
    private String myTeam;
    private String starterId;
    private String finisherId;
    private String betId;
    private String myAmount;
    private String hisAmount;
    private  String lastComment;
    private String  gUsername;
    private  String   gClub;



    public PendingModel() {

    }

    public PendingModel(String betId, String myAmount, String hisAmount, String starterId, String finisherId, String homeTeam, String myTeam, String gameId,
                        String awayTeam, String progress
    ) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.progress = progress;
        this.gameId = gameId;
        this.myTeam = myTeam;
        this.starterId = starterId;
        this.finisherId = finisherId;
        this.lastComment=lastComment;
        this.gUsername=gUsername;
        this.gClub=gClub;
        this.progress= progress;
        this.betId = betId;
        this.myAmount = myAmount;
        this.hisAmount = hisAmount;


    }
    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public String getgUsername() {
        return gUsername;
    }

    public void setgUsername(String gUsername) {
        this.gUsername = gUsername;
    }

    public String getgClub() {
        return gClub;
    }

    public void setgClub(String gClub) {
        this.gClub = gClub;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getHisAmount() {
        return hisAmount;
    }

    public void setHisAmount(String hisAmount) {
        this.hisAmount = hisAmount;
    }

    public String getMyAmount() {
        return myAmount;
    }

    public void setMyAmount(String myAmount) {
        this.myAmount = myAmount;
    }

    public String getFinisherId() {
        return finisherId;
    }

    public void setFinisherId(String finisherId) {
        this.finisherId = finisherId;
    }

    public String getStarterId() {
        return starterId;
    }

    public void setStarterId(String starterId) {
        this.starterId = starterId;
    }


    public String getMyTeam() {
        return myTeam;
    }

    public void setMyTeam(String myTeam) {
        this.myTeam = myTeam;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }


    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }


    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }


}
