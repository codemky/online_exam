package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.Account;
import com.qexz.model.Grade;
import com.qexz.model.Question;
import com.qexz.service.GradeService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/grade")
public class GradeController {

    private static Log LOG = LogFactory.getLog(GradeController.class);

    @Autowired
    private GradeService gradeService;
    @Autowired
    private QuestionService questionService;

    /**
     * Author: laizhouhao 16:33 2019/5/23
     * @param grade
     * @return AjaxResult
     * @apiNote: 自动批改试卷并且计算分数
     */
    @RequestMapping(value="/api/submitContest", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult submitContest(HttpServletRequest request, @RequestBody Grade grade) {
        AjaxResult ajaxResult = new AjaxResult();
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //分割答题记录，以字符“”“”‘_~_’分割，所以QexzConst.SPLIT_CHAR的值为_~_
        List<String> answerStrs = Arrays.asList(grade.getAnswerJson().split(QexzConst.SPLIT_CHAR));
        //记录客观题的分数
        int autoResult = 0;
        //获取某个考试的所有题目
        List<Question> questions = questionService.getQuestionsByContestId(grade.getContestId());

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            // 类型为0,1,2的题目种类分别是 单选题 多选题 填空题
            if (question.getQuestionType() <= 2 && question.getAnswer()
                    .equals(answerStrs.get(i))) {
                autoResult += question.getScore();
            }
        }
        grade.setStudentId(currentAccount.getId());
        grade.setResult(autoResult);
        grade.setAutoResult(autoResult);
        grade.setManulResult(0);
        grade.setState(1);
        int gradeId = gradeService.addGrade(grade);
        return new AjaxResult().setData(gradeId);
    }

//    //完成批改试卷
//    @RequestMapping(value="/api/finishGrade", method= RequestMethod.POST)
//    @ResponseBody
//    public AjaxResult finishGrade(@RequestBody Grade grade) {
//        AjaxResult ajaxResult = new AjaxResult();
//        grade.setResult(grade.getAutoResult()+grade.getManulResult());
//        grade.setFinishTime(new Date());
//        grade.setState(1);
//        boolean result = gradeService.updateGrade(grade);
//        return new AjaxResult().setData(result);
//    }
}
