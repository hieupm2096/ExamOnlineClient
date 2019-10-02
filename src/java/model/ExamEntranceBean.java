/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Exam;
import entity.ExamQuestion;
import entity.ExamQuestionAnswer;
import entity.ExamStudent;
import entity.ExamStudentAnswer;
import facade.ExamStudentFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oswal
 */
@Named(value = "examEntranceBean")
@SessionScoped
public class ExamEntranceBean implements Serializable {

    @EJB
    private ExamStudentFacade examStudentFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamEntranceBean.class);
    private static final String EXAM_PAGE_REDIRECT = "exam/exam-info?faces-redirect=true";
    private static final String DO_EXAM_PAGE_REDIRECT = "do-exam?faces-redirect=true";
    private static final String EXAM_RESULT_PAGE_REDIRECT = "exam-result?faces-redirect=true";
    private static final String SINGLE_ANSWER_QUESTION_TYPE = "T01";

    private static final int EXTEND_DURATION = 15;

    @Inject
    private AuthenticationBean authenticationBean;

    private String examId;
    private String passcode;

    private ExamStudent examStudent;
    private Exam exam;

    private List<ExamQuestion> questions;

    private String[][] questionAnswer;

    private int numberOfCorrectAnswer;

    /**
     * Verify if exam ID is valid and passcode match
     *
     * @return String to redirect
     */
    public String accessExam() {
        if (examId != null && passcode != null) {
            String studentId = authenticationBean.getLoginStudent().getId();
            examStudent = examStudentFacade.findAvailableExamStudentByExamIdAndStudentId(examId, studentId);
            if (examStudent != null && examStudent.getPasscode() != null && examStudent.getPasscode().equals(passcode)) {
                LOGGER.info("Exam access: " + examStudent.getExam().getId() + "; Student: " + examStudent.getStudent().getName());
                return EXAM_PAGE_REDIRECT;
            } else {
                examStudent = null;
                LOGGER.error("Exam Not Found or passcode not match");
            }
        }
        return null;
    }

    public boolean isAccessedExam() {
        return examStudent != null;
    }

    public String isAccessedExamForwardExam() {
        if (isAccessedExam()) {
            return EXAM_PAGE_REDIRECT;
        }
        return null;
    }

    public boolean isExamStarted() {
        return examStudent.getExam().getStartTime() != null;
    }

    public boolean isExamOngoing() {
        int duration = examStudent.getExam().getDuration();

        Date startTime = examStudent.getExam().getStartTime();
        Date endTime = new Date(startTime.getTime() + ((duration + EXTEND_DURATION) * 60000));
        return isExamStarted() && new Date().before(endTime);
    }

    public boolean isExamDone() {
        return examStudent.getEndTime() != null;
    }

    public String startDoingExam() {
        examStudent.setStartTime(new Date());
        try {
            examStudentFacade.edit(examStudent);
            return DO_EXAM_PAGE_REDIRECT;
        } catch (Exception e) {
            LOGGER.error("Transaction required but is not active");
        }
        return null;
    }

    public boolean isDoingExam() {
        return examStudent.getStartTime() != null && examStudent.getEndTime() == null && isExamOngoing();
    }

    public String isDoingExamForward() {
        if (isDoingExam()) {
            return DO_EXAM_PAGE_REDIRECT;
        }
        return null;
    }
    
    public boolean isDoneExam() {
        return examStudent.getEndTime() != null;
    }
    
    public String isDoneExamForward() {
        if (isDoneExam()) {
            return EXAM_RESULT_PAGE_REDIRECT;
        }
        return null;
    }

    public long getTimeoutDuration() {
        int duration = examStudent.getExam().getDuration();
        Date studentEndTime = new Date(examStudent.getStartTime().getTime() + duration * 60000);
        Date examEndTime = new Date(examStudent.getExam().getStartTime().getTime() + (duration + EXTEND_DURATION) * 60000);
        if (studentEndTime.before(examEndTime)) {
            return (studentEndTime.getTime() - new Date().getTime()) / 1000;
        }
        return (examEndTime.getTime() - new Date().getTime()) / 1000;
    }

    public boolean isQuestionSingleAnswer(ExamQuestion q) {
        return q.getQuestionTypeId().getId().equals(SINGLE_ANSWER_QUESTION_TYPE);
    }

    public void initQuestionAnswer() {
        long seed = Long.parseLong(passcode, 36);
        Random rand = new Random(seed);
        questions = examStudent.getExam().getExamquestionList();
        Collections.shuffle(questions, rand);

        for (ExamQuestion question : questions) {
            List<ExamQuestionAnswer> answers = question.getExamQuestionAnswerList();
            Collections.shuffle(answers, rand);
            question.setExamQuestionAnswerList(answers);
        }

        questionAnswer = new String[questions.size()][5];
    }

    public String finishExam() {
        List<ExamQuestionAnswer> allAnswers = new ArrayList<>();
        for (ExamQuestion question : questions) {
            allAnswers.addAll(question.getExamQuestionAnswerList());
        }
        
        List<ExamQuestionAnswer> examQuestionAnswerList = new ArrayList<>();
        List<ExamStudentAnswer> examStudentAnswerList = new ArrayList<>();

        numberOfCorrectAnswer = 0;

        for (String[] item : questionAnswer) {
            if (item != null && item.length != 0 && item[0] != null) {
                
                ExamQuestionAnswer eqa = findLocalAnswer(item[0], allAnswers);
                ExamQuestion q = eqa.getExamQuestion();
                if (isQuestionSingleAnswer(q)) {
                    ExamStudentAnswer asa = new ExamStudentAnswer();
                    asa.setAnswer(eqa);
                    asa.setExamStudent(examStudent);
                    examStudentAnswerList.add(asa);
                    if (eqa.getIsCorrect()) {
                        numberOfCorrectAnswer++;
                    }
                } else {
                    boolean isAllAnswerCorrect = true;
                    for (String answerId : item) {
                        if (answerId != null) {
                            ExamQuestionAnswer _eqa = findLocalAnswer(answerId, allAnswers);
                            ExamStudentAnswer asa = new ExamStudentAnswer();
                            asa.setAnswer(_eqa);
                            asa.setExamStudent(examStudent);
                            examStudentAnswerList.add(asa);
                            if (!_eqa.getIsCorrect()) {
                                isAllAnswerCorrect = false;
                                break;
                            }
                        }
                    }
                    if (isAllAnswerCorrect) {
                        numberOfCorrectAnswer++;
                    }
                }
            }
        }

        LOGGER.info("Number of correct answer: " + numberOfCorrectAnswer);
        LOGGER.info("Result: " + calculateResult(numberOfCorrectAnswer, questions.size()));

        examStudent.setEndTime(new Date());
        examStudent.setExamStudentAnswerList(examStudentAnswerList);
        examStudent.setResult(calculateResult(numberOfCorrectAnswer, questions.size()));

        examStudentFacade.edit(examStudent);

        return EXAM_RESULT_PAGE_REDIRECT;
    }
    
    public void onExamTimeout() {
        String temp = finishExam();
        if (temp.equals(EXAM_RESULT_PAGE_REDIRECT)) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try {
                String requestContextPath = ec.getRequestContextPath();
                LOGGER.info(requestContextPath);
                ec.redirect(requestContextPath + "/app/exam/exam-result.xhtml");
                
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

    public ExamQuestionAnswer findLocalAnswer(String answerId, List<ExamQuestionAnswer> answerList) {
        for (ExamQuestionAnswer answer : answerList) {
            if (answer.getId().equals(answerId)) {
                return answer;
            }
        }
        return null;
    }

    public int calculateResult(double numberOfCorrectQuestion, double numberOfQuestion) {
        double result = numberOfCorrectQuestion / numberOfQuestion;
        return (int) (result * 100);
    }

    /**
     * Creates a new instance of ExamEntranceBean
     */
    public ExamEntranceBean() {
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public ExamStudent getExamStudent() {
        return examStudent;
    }

    public void setExamStudent(ExamStudent examStudent) {
        this.examStudent = examStudent;
    }

    public List<ExamQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ExamQuestion> questions) {
        this.questions = questions;
    }

    public String[][] getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String[][] questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public int getNumberOfCorrectAnswer() {
        return numberOfCorrectAnswer;
    }

    public void setNumberOfCorrectAnswer(int numberOfCorrectAnswer) {
        this.numberOfCorrectAnswer = numberOfCorrectAnswer;
    }

}
