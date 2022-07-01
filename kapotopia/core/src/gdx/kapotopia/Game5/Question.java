package gdx.kapotopia.Game5;

public class Question {

    public String choix1;
    public String choix2;
    public String choix3;
    public String choix4;
    public String question;
    public int goodAnswer;

    public Question(String choix1, String choix2, String choix3, String choix4,String question, int goodAnswer)
    {
        this.choix1= choix1;
        this.choix2= choix2;
        this.choix3= choix3;
        this.choix4 = choix4;
        this.goodAnswer = goodAnswer;
        this.question = question;

    }
}
