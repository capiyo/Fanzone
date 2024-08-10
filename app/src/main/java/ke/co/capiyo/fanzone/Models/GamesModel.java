package ke.co.capiyo.fanzone.Models;

public class GamesModel {
    private String  homeTeam;
    private String awayTeam;
    private String progress;
	private String gameId;
	private String score;
	private String counter;
	
	private String date;
	private String time;
    private  String lastComment;
    private String  gUsername;
    private  String   gClub;




    public GamesModel() {

    }

    public  GamesModel(String counter,String homeTeam,String date,String time,String gameId,String lastComment,String gUsername, String gClub,
	String awayTeam, String progress
					 ) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.lastComment=lastComment;
        this.gUsername=gUsername;
        this.gClub=gClub;
        this.progress= progress;
		this.gameId=gameId;
		this.date=date;
		this.time=time;
		this.counter=counter;
		


    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String getCounter() {
        return counter;
    }
	public void setCounter(String counter) {
        this.counter=counter;
    }
	
	
	public String getTime() {
        return time;
    }
	public String getGameId() {
        return gameId;
    }
	public String getDate() {
        return date;
    }
	public void setTime(String time) {
        this.time=time;
    }
	public void setDate(String date) {
        this.date=date;
    }
	public void setGameId(String gameId) {
        this.gameId=gameId;
    }


    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam =awayTeam;
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
