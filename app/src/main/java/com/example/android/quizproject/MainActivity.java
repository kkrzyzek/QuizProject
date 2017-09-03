package com.example.android.quizproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int finalScore = 0; //initial value for final quiz score shown in summary

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * FOR All! This method is called when button "Check Answers" is clicked
     * @param       view = main view
     */
    public void checkAllAnswers(View view) {
        finalScore = 0;

        boolean answerQuestion1 = false;
        boolean check1Quest1 = checkSingleCheckBox(R.id.jupiter_checkbox_view);
        boolean check2Quest1 = checkSingleCheckBox(R.id.mars_checkbox_view);
        boolean check3Quest1 = checkSingleCheckBox(R.id.earth_checkbox_view);
        boolean check4Quest1 = checkSingleCheckBox(R.id.venus_checkbox_view);

        if (!check1Quest1 && !check2Quest1 && !check3Quest1 && check4Quest1) {
            finalScore = scoreCounter(finalScore);
            answerQuestion1 = true;
        }

        boolean answerQuestion2 = false;
        boolean check1Quest2 = checkSingleCheckBox(R.id.nasa_checkbox_view);
        boolean check2Quest2 = checkSingleCheckBox(R.id.spacex_checkbox_view);
        boolean check3Quest2 = checkSingleCheckBox(R.id.nvidia_checkbox_view);
        boolean check4Quest2 = checkSingleCheckBox(R.id.astrotech_checkbox_view);

        if (check1Quest2 && check2Quest2 && !check3Quest2 && check4Quest2) {
            finalScore = scoreCounter(finalScore);
            answerQuestion2 = true;
        }

        boolean answerQuestion3 = false;
        boolean check1Quest3 = checkSingleCheckBox(R.id.moon_checkbox_view);
        boolean check2Quest3 = checkSingleCheckBox(R.id.sun_checkbox_view);
        boolean check3Quest3 = checkSingleCheckBox(R.id.comet_checkbox_view);
        boolean check4Quest3 = checkSingleCheckBox(R.id.asteroid_checkbox_view);

        if (check1Quest3 && !check2Quest3 && !check3Quest3 && !check4Quest3) {
            finalScore = scoreCounter(finalScore);
            answerQuestion3 = true;
        }

        boolean answerQuestion4 = false;
        boolean check1Quest4 = checkSingleCheckBox(R.id.yes_checkbox_view);
        boolean check2Quest4 = checkSingleCheckBox(R.id.no_checkbox_view);

        if (check1Quest4 && !check2Quest4) {
            finalScore = scoreCounter(finalScore);
            answerQuestion4 = true;
        }

        String question1Summary = createQuestionSummary(answerQuestion1, 1, getResources().getString(R.string.venus));
        String question2Summary = createQuestionSummary(answerQuestion2, 2, getResources().getString(R.string.nasa_spacex_astrotech));
        String question3Summary = createQuestionSummary(answerQuestion3, 3, getResources().getString(R.string.moon));
        String question4Summary = createQuestionSummary(answerQuestion4, 4, getResources().getString(R.string.yes));

        String quizSummary = createQuizSummary(finalScore);

        displayQuestionSummary(question1Summary, answerQuestion1, R.id.question1_summary);
        displayQuestionSummary(question2Summary, answerQuestion2, R.id.question2_summary);
        displayQuestionSummary(question3Summary, answerQuestion3, R.id.question3_summary);
        displayQuestionSummary(question4Summary, answerQuestion4, R.id.question4_summary);
        displayQuizSummary(quizSummary);

    }

    /**
     * FOR ALL! Methods called by clicking 'Hint' button for all questions
     * @param       view = main view
     */
    public void hintMessage1(View view) {
        makeAToast(R.string.question_1_hint);
    }
    public void hintMessage2(View view) {
        makeAToast(R.string.question_2_hint);
    }
    public void hintMessage3(View view) {
        makeAToast(R.string.question_3_hint);
    }
    public void hintMessage4(View view) {
        makeAToast(R.string.question_4_hint);
    }

    /**
     * Displays question summary colored text
     * @param       questionSummaryText = text to display
     */
    private void displayQuestionSummary(String questionSummaryText, boolean answer, int textViewId) {
        TextView questionSummary = (TextView) findViewById(textViewId);
        questionSummary.setText(questionSummaryText);
        if (answer) {
            questionSummary.setTextColor(getResources().getColor(R.color.correctAnswer));
        }else {
            questionSummary.setTextColor(getResources().getColor(R.color.wrongAnswer));
        }
    }

    /**
     * Displays quiz summary normal text
     * @param quizSummaryText = text to display
     */
    private void displayQuizSummary (String quizSummaryText) {
        TextView quizSummary = (TextView) findViewById(R.id.quiz_summary);
        quizSummary.setText(quizSummaryText);
    }

    /**
     * Checks single checkbox value
     * @param checkBoxId    int
     * @return              true/false
     */
    private boolean checkSingleCheckBox (int checkBoxId) {
        CheckBox checkbox = (CheckBox) findViewById(checkBoxId);
        return checkbox.isChecked();
    }

    /**
     * This method is called when button 'Mail Summary' is clicked
     * @param      view = main view
     */
    public void sendMailAction(View view) {
        String name = getPlayerName();
        String message = createShortQuizSummary(finalScore, name);
        String mailto = "mailto:" + getPlayerMail() +
                "?cc=" + "" +
                "&subject=" + Uri.encode(name + getResources().getString(R.string.score_in_quiz)) +
                "&body=" + Uri.encode(message);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
        }

    }

    /**
     * Creates summary string
     * @param       score = int
     * @return      quizSummary
     */
    private String createQuizSummary(int score) {
        String quizSummary = getResources().getString(R.string.your_score_is) + score + getResources().getString(R.string.points);

        if(score == 4) {
            quizSummary += "\n" + getResources().getString(R.string.wow);
        }else if(score == 3) {
            quizSummary += "\n" + getResources().getString(R.string.you_did_pretty_good);
        }else if (score == 2) {
            quizSummary += "\n" + getResources().getString(R.string.you_can_do_better);
        }

        return quizSummary;
    }

    /**
     * Creates single question summary
     * @param       answerCorrect = true/false
     * @param       questionNumber = int
     * @param       rightAnswer = correct answer
     * @return      questionSummary
     */
    private String createQuestionSummary(boolean answerCorrect, int questionNumber, String rightAnswer) {
        String answerStatementQuestion = answerStatement(answerCorrect);
        String questionSummary = getResources().getString(R.string.correct_answer_to_question) + questionNumber + getResources().getString(R.string.is) + rightAnswer + "\n";
        questionSummary += getResources().getString(R.string.your_answer_is) + answerStatementQuestion + "\n";
        return questionSummary;
    }
    /**
     * Creates short quiz summary for mail intent
     * @param       score = int
     * @param       name = player name
     * @return      shortQuizSummary
     */
    private String createShortQuizSummary(int score, String name) {
        String shortQuizSummary = name + getResources().getString(R.string.scored);
        shortQuizSummary += score;
        shortQuizSummary += getResources().getString(R.string.points);
        return shortQuizSummary;
    }

    /**
     * Gets player name from EditText View
     * @return      playerName
     */
    private String getPlayerName() {
        EditText name = (EditText) findViewById(R.id.name_edittext_view);
        return name.getText().toString();
    }

    /**
     * Gets player mail address from EditText View
     * @return      playerMail
     */
    private String getPlayerMail() {
        EditText mail = (EditText) findViewById(R.id.mail_edittext_view);
        return mail.getText().toString();
    }

    /**
     * Creates answer statement for createQuizSummary method
     * @param       answer = true/false
     * @return      answerStatement
     */
    String answerStatement(boolean answer) {
        String answerStatement = getResources().getString(R.string.wrong);
        if (answer) {
            answerStatement = getResources().getString(R.string.correct);
        }
        return answerStatement;
    }

    /**
     * Counts points earned by user for all answers
     * @param       result - int
     * @return      result
     */
    private int scoreCounter(int result) {
            result +=1;
        return result;
        }

    /**
     * Creates and shows Toast message
     * @param       stringQuestionId = string resource id
     */
    private void makeAToast(int stringQuestionId) {
        Toast hint = Toast.makeText(this, getResources().getString(stringQuestionId), Toast.LENGTH_SHORT);
        hint.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        hint.show();
    }

    /**
     * Creates intent to map for question 4
     * @param view = main view
     */
    public void intentForQuestion4 (View view){
        Intent question4 = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.pl/maps/place/Cape+Canaveral+Air+Force+Station/@28.4880013,-80.5730528,1691m/data=!3m1!1e3!4m5!3m4!1s0x88e0a4e74e6a8abb:0x2a16683cb4a44f!8m2!3d28.4886723!4d-80.5728241"));
        startActivity(question4);
    }

}