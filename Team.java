/**
 * Created by PaulN on 3/13/2017.
 */
public class Team {
    String name = "";
    int score = 0;

    Team( String teamName ) {
        this.name = teamName.toUpperCase();
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase() ;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void scored (int addScore) {
        this.score += addScore;
    }
}
