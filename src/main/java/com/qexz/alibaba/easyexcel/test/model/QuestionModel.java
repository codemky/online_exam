package com.qexz.alibaba.easyexcel.test.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @Author chenenru
 * @ClassName QuestionModel
 * @Description
 * @Date 2019/5/24 21:14
 * @Version 1.0
 **/
public class QuestionModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    //题目的标题
    private String questiontitle;
    @ExcelProperty(index = 1)
    //题目的内容
    private String questioncontent;
    @ExcelProperty(index = 2)
    //题目类型
    private String questiontype;
    @ExcelProperty(index = 3)
    //选项a
    private String qurstionoptionA;
    @ExcelProperty(index = 4)
    //选项b
    private String qurstionoptionB;
    @ExcelProperty(index = 5)
    //选项c
    private String qurstionoptionC;
    @ExcelProperty(index = 6)
    //选项d
    private String qurstionoptionD;
    @ExcelProperty(index = 7)
    //答案
    private String questionanswer;
    @ExcelProperty(index = 8)
    //答案解析
    private String questionparse;
    @ExcelProperty(index = 9)
    //学科
    private String questionsubject;
    @ExcelProperty(index = 10)
    //题目分值
    private Integer questionscore;
    @ExcelProperty(index = 11)
    //题目难度
    private Integer questiondifficulty;

    public QuestionModel() {
    }

    public QuestionModel(String questiontitle, String questioncontent, String questiontype, String qurstionoptionA, String qurstionoptionB, String qurstionoptionC, String qurstionoptionD, String questionanswer, String questionparse, String questionsubject, Integer questionscore, Integer questiondifficulty) {
        this.questiontitle = questiontitle;
        this.questioncontent = questioncontent;
        this.questiontype = questiontype;
        this.qurstionoptionA = qurstionoptionA;
        this.qurstionoptionB = qurstionoptionB;
        this.qurstionoptionC = qurstionoptionC;
        this.qurstionoptionD = qurstionoptionD;
        this.questionanswer = questionanswer;
        this.questionparse = questionparse;
        this.questionsubject = questionsubject;
        this.questionscore = questionscore;
        this.questiondifficulty = questiondifficulty;
    }

    public String getQuestiontitle() {
        return questiontitle;
    }

    public void setQuestiontitle(String questiontitle) {
        this.questiontitle = questiontitle;
    }

    public String getQuestioncontent() {
        return questioncontent;
    }

    public void setQuestioncontent(String questioncontent) {
        this.questioncontent = questioncontent;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public String getQurstionoptionA() {
        return qurstionoptionA;
    }

    public void setQurstionoptionA(String qurstionoptionA) {
        this.qurstionoptionA = qurstionoptionA;
    }

    public String getQurstionoptionB() {
        return qurstionoptionB;
    }

    public void setQurstionoptionB(String qurstionoptionB) {
        this.qurstionoptionB = qurstionoptionB;
    }

    public String getQurstionoptionC() {
        return qurstionoptionC;
    }

    public void setQurstionoptionC(String qurstionoptionC) {
        this.qurstionoptionC = qurstionoptionC;
    }

    public String getQurstionoptionD() {
        return qurstionoptionD;
    }

    public void setQurstionoptionD(String qurstionoptionD) {
        this.qurstionoptionD = qurstionoptionD;
    }

    public String getQuestionanswer() {
        return questionanswer;
    }

    public void setQuestionanswer(String questionanswer) {
        this.questionanswer = questionanswer;
    }

    public String getQuestionparse() {
        return questionparse;
    }

    public void setQuestionparse(String questionparse) {
        this.questionparse = questionparse;
    }

    public String getQuestionsubject() {
        return questionsubject;
    }

    public void setQuestionsubject(String questionsubject) {
        this.questionsubject = questionsubject;
    }

    public Integer getQuestionscore() {
        return questionscore;
    }

    public void setQuestionscore(Integer questionscore) {
        this.questionscore = questionscore;
    }

    public Integer getQuestiondifficulty() {
        return questiondifficulty;
    }

    public void setQuestiondifficulty(Integer questiondifficulty) {
        this.questiondifficulty = questiondifficulty;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "questiontitle='" + questiontitle + '\'' +
                ", questioncontent='" + questioncontent + '\'' +
                ", questiontype='" + questiontype + '\'' +
                ", qurstionoptionA='" + qurstionoptionA + '\'' +
                ", qurstionoptionB='" + qurstionoptionB + '\'' +
                ", qurstionoptionC='" + qurstionoptionC + '\'' +
                ", qurstionoptionD='" + qurstionoptionD + '\'' +
                ", questionanswer='" + questionanswer + '\'' +
                ", questionparse='" + questionparse + '\'' +
                ", questionsubject='" + questionsubject + '\'' +
                ", questionscore=" + questionscore +
                ", questiondifficulty=" + questiondifficulty +
                '}';
    }
}
