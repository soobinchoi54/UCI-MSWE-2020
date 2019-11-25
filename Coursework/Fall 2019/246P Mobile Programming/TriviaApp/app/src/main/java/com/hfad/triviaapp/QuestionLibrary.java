package com.hfad.triviaapp;

public class QuestionLibrary {

    private String mQuestions [] = {
            "Which color is NOT a primary color?"
    };

    private String mChoices [][] = {
            {"Red", "Green", "Blue", "Yellow"}
    };

    private String mCorrectAnswers[] = {"Green"};

    public String getQuestion(int a) {
        String question = mQuestions [a];
        return question;
    }

    public String getChoice1(int a) {
        String choice1 = mChoices[a][0];
        return choice1;
    }
    public String getChoice2(int a) {
        String choice2 = mChoices[a][1];
        return choice2;
    }
    public String getChoice3(int a) {
        String choice3 = mChoices[a][2];
        return choice3;
    }
    public String getChoice4(int a) {
        String choice4 = mChoices[a][3];
        return choice4;
    }

    public String getCorrectAnswer(int a ){
        String answer = mCorrectAnswers[a];
        return answer;
    }

    public String fQuestion = "What color does K represent in CMYK?";

    public static String fAnswer = "Black";

    public static int score =0;

    public static int pass = 0;
    public static int fail = 0;

}
