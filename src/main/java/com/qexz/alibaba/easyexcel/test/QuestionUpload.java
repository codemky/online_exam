package com.qexz.alibaba.easyexcel.test;

import com.qexz.model.Question;
import com.qexz.model.Subject;
import com.qexz.service.ContestService;
import com.qexz.service.QuestionService;
import com.qexz.service.SubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author chenenru
 * @ClassName QuestionUpload
 * @Description
 * @Date 2019/5/25 10:16
 * @Version 1.0
 **/
@Component
public class QuestionUpload {
    @Autowired
    private QuestionService Qquestion;
    @Autowired
    private SubjectService Ssubject;

    private static  QuestionService questionService;
    private static  SubjectService subjectService;

    @PostConstruct
    public void init(){
        questionService = this.Qquestion;
        subjectService = this.Ssubject;
        i=0;
    }
    Object o =new Object();
    StringBuffer s = new StringBuffer();
    Question question = new Question();
//    List<Subject> subjects = new ArrayList<>();
    static int i=0;
//    int flag=0;

    public static void setI(int i) {
        QuestionUpload.i = i;
    }

    public static void setQuestionService(QuestionService questionService) {
        QuestionUpload.questionService = questionService;
    }

    public static void setSubjectService(SubjectService subjectService) {
        QuestionUpload.subjectService = subjectService;
    }

    /**
     * Author: chenenru 11:33 2019/5/25
     * @param o
     * @return s
     * @apiNote: 校验题目
     */
    public StringBuffer checkoutQuestion(Object o){
        //init();
        this.o = o;
        s.delete(0, s.length());
        String questiontitle = (String) getFieldValueByName("questiontitle", o);
        String questioncontent = (String) getFieldValueByName("questioncontent", o);
        String questiontype = (String) getFieldValueByName("questiontype", o);
        String qurstionoptionA = (String) getFieldValueByName("qurstionoptionA", o);
        String qurstionoptionB = (String) getFieldValueByName("qurstionoptionB", o);
        String qurstionoptionC = (String) getFieldValueByName("qurstionoptionC", o);
        String qurstionoptionD = (String) getFieldValueByName("qurstionoptionD", o);
        String questionanswer = (String) getFieldValueByName("questionanswer", o);
        String questionparse = (String) getFieldValueByName("questionparse", o);
        String questionsubject = (String) getFieldValueByName("questionsubject", o);
        Integer questionscore = (Integer) getFieldValueByName("questionscore", o);
        Integer questiondifficulty = (Integer) getFieldValueByName("questiondifficulty", o);

        System.out.println(questiontitle + "@1@" + questioncontent + "@2@" + questiontype + "@3@" + qurstionoptionA + "@4@"
        + qurstionoptionB + "@5@"+ qurstionoptionC + "@6@"+ qurstionoptionD + "@7@"+ questionanswer + "@8@"+ questionparse + "@9@"
                + questionsubject + "@10@"+ questionscore + "@11@"+ questiondifficulty);

        i++;
        if (questiontitle==null||questiontitle.equals("")){
            s.append("第"+i+"行第1列不能为空，请检查\n");
        }
        if (questioncontent==null||questioncontent.equals("")){
            s.append("第"+i+"行第2列不能为空，请检查\n");
        }
        if (questiontype==null||questiontype.equals("")){
            s.append("第"+i+"行第3列不能为空，请检查\n");
        }else if (!questiontype.equals("单项选择题")&&!questiontype.equals("多项选择题")
                &&!questiontype.equals("填空题")&&!questiontype.equals("编程题")){
            s.append("第"+i+"行第3列题目类型设置不正确，请检查\n");
        }
        if (questiontype.equals("单项选择题")||questiontype.equals("多项选择题")){
            if (qurstionoptionA==null||qurstionoptionA.equals("")){
                s.append("第"+i+"行第4列不能为空，请检查\n");
            }
            if (qurstionoptionB==null||qurstionoptionB.equals("")){
                s.append("第"+i+"行第5列不能为空，请检查\n");
            }
            if (qurstionoptionC==null||qurstionoptionC.equals("")){
                s.append("第"+i+"行第6列不能为空，请检查\n");
            }
            if (qurstionoptionD==null||qurstionoptionD.equals("")){
                s.append("第"+i+"行第7列不能为空，请检查\n");
            }
        }
        if (questionanswer==null||questionanswer.equals("")){
            s.append("第"+i+"行第8列的答案不能为空，请检查\n");
        }else if (questiontype.equals("单项选择题")||questiontype.equals("多项选择题")){
            questionanswer = questionanswer.toUpperCase();
            //System.out.println("------->"+questionanswer);
            int a = StringUtils.countMatches(questionanswer, "A");
            int b = StringUtils.countMatches(questionanswer, "B");
            int c = StringUtils.countMatches(questionanswer, "C");
            int d = StringUtils.countMatches(questionanswer, "D");
            if (a>1||b>1||c>1||d>1){
                s.append("第"+i+"行第8列的答案不能有重复的选项不正确，请检查\n");
            }
        }
        if (questionparse==null||questionparse.equals("")){
            s.append("第"+i+"行第9列不能为空，请检查\n");
        }

        if (questionsubject==null||questionsubject.equals("")){
            s.append("第"+i+"行第10列不能为空，请检查\n");
        }
        else {
            /*List<Subject> subjects = subjectService.getSubjects();
            System.out.println("-------------->"+subjectService.getSubjects().toString());
            for (Subject s: subjects) {
                if (questionsubject.equals(s.getName())){
                    flag=1;
                    break;
                }
            }*/
            Subject subjectBySubjectName = subjectService.getSubjectBySubjectName(questionsubject);
            if (subjectBySubjectName == null ||subjectBySubjectName.equals("")) {
                s.append("第"+i+"行第10列的课程不正确，请检查\n");
            }
        }
        if(questionscore==null||questionscore.equals("")){
            s.append("第"+i+"行第11列的题目分值不能为空，请检查\n");
        }else if (questionscore.intValue()<1||questionscore.intValue()>100){
            s.append("第"+i+"行第11列题目分值设置不正确，请检查\n");
        }

        if (questiondifficulty==null||questiondifficulty.equals("")){
            s.append("第"+i+"行第12列的难度系数不能为空，请检查\n");
        }else if (questiondifficulty.intValue()<1||questiondifficulty.intValue()>5){
            s.append("第"+i+"行第12列难度系数设置不正确，请检查\n");
        }
        return s;
    }
    /**
     * Author: chenenru 11:33 2019/5/25
     * @param o,contest_id
     * @return s
     * @apiNote: 批量导入题目
     */
    public StringBuffer insertQuestion(Object o,int contest_id){
        //init();
        this.o = o;
        s.delete(0, s.length());
        String questiontitle = (String) getFieldValueByName("questiontitle", o);
        String questioncontent = (String) getFieldValueByName("questioncontent", o);
        String questiontype = (String) getFieldValueByName("questiontype", o);
        String qurstionoptionA = (String) getFieldValueByName("qurstionoptionA", o);
        String qurstionoptionB = (String) getFieldValueByName("qurstionoptionB", o);
        String qurstionoptionC = (String) getFieldValueByName("qurstionoptionC", o);
        String qurstionoptionD = (String) getFieldValueByName("qurstionoptionD", o);
        String questionanswer = (String) getFieldValueByName("questionanswer", o);
        String questionparse = (String) getFieldValueByName("questionparse", o);
        String questionsubject = (String) getFieldValueByName("questionsubject", o);
        Integer questionscore = (Integer) getFieldValueByName("questionscore", o);
        Integer questiondifficulty = (Integer) getFieldValueByName("questiondifficulty", o);
        question.setTitle(questiontitle);
        question.setContent(questioncontent);
        if (questiontype.equals("单项选择题")){
            question.setQuestionType(0);
            questionanswer = questionanswer.toUpperCase();
        }else if (questiontype.equals("多项选择题")){
            question.setQuestionType(1);
            questionanswer = questionanswer.toUpperCase();
        }else if (questiontype.equals("填空题")){
            question.setQuestionType(2);
        }else if (questiontype.equals("编程题")){
            question.setQuestionType(3);
        }
        question.setOptionA(qurstionoptionA);
        question.setOptionB(qurstionoptionB);
        question.setOptionC(qurstionoptionC);
        question.setOptionD(qurstionoptionD);

        question.setAnswer(questionanswer);
        question.setParse(questionparse);
        /*List<Subject> subjects = subjectService.getSubjects();
        for (Subject s: subjects) {
            if (s.getName().equals(questionsubject)){
                question.setSubjectId(s.getId());
            }
        }*/
        Subject subjectBySubjectName = subjectService.getSubjectBySubjectName(questionsubject);
        question.setSubjectId(subjectBySubjectName.getId());
        question.setContestId(contest_id);
        question.setCreateTime(new Date());
        question.setScore(questionscore);
        question.setDifficulty(questiondifficulty);
        question.setState(0);
        int i = questionService.addQuestion(question);
        if (i>0){
            s.append("插入成功\n");
        }else{
            s.append("插入失败\n");
        }
        return s;
    }
    /**
     * 根据属性名获取属性值
     */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
