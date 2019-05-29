package com.qexz.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.qexz.alibaba.easyexcel.test.QuestionUpload;
import com.qexz.alibaba.easyexcel.test.listen.ExcelListener;
import com.qexz.alibaba.easyexcel.test.model.QuestionModel;
import com.qexz.alibaba.easyexcel.test.util.FileUtil;
import com.qexz.dto.AjaxResult;
import com.qexz.model.Contest;
import com.qexz.model.Question;
import com.qexz.service.QuestionService;
import com.qexz.service.SubjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.FieldPosition;
import java.util.List;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    private static Log LOG = LogFactory.getLog(QuestionController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private SubjectService subjectService;

    QuestionUpload questionUpload = new QuestionUpload();
    StringBuffer stringBuffer = new StringBuffer();

    //添加题目
    @RequestMapping(value="/api/addQuestion", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addQuestion(@RequestBody Question question) {
        AjaxResult ajaxResult = new AjaxResult();
        int questionId = questionService.addQuestion(question);
        return new AjaxResult().setData(questionId);
    }

    //更新题目信息
    @RequestMapping(value="/api/updateQuestion", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateQuestion(@RequestBody Question question) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = questionService.updateQuestion(question);
        return new AjaxResult().setData(result);
    }

    //删除题目信息
    @DeleteMapping("/api/deleteQuestion/{id}")
    public AjaxResult deleteContest(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = questionService.deleteQuestion(id);
        
        return new AjaxResult().setData(result);
    }

    //校验批量导入的题目
    public StringBuffer checkoutQuestions(InputStream inputStream) throws IOException {
        AjaxResult ajaxResult = new AjaxResult();
//        System.out.println(filePath);
//        InputStream inputStream = FileUtil.getResourcesFileInputStream(filePath);
        stringBuffer.delete(0,stringBuffer.length());
//        questionUpload.init();
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 1, QuestionModel.class));
        QuestionUpload.setI(0);
        for (Object o:data) {
            stringBuffer.append(questionUpload.checkoutQuestion(o));
        }
        if (stringBuffer != null && !stringBuffer.equals("") && stringBuffer.length() > 0) {
            System.out.println("报错信息：");
            System.out.println(stringBuffer);
            return stringBuffer;
        } else {
//            stringBuffer.append("校验通过");
            System.out.println("校验通过");
            return stringBuffer;
        }
//        inputStream.close();
//        return new AjaxResult().setData(stringBuffer);
    }
    //批量导入题目
    public AjaxResult insertQuestions(InputStream inputStream,int contest_id) throws IOException {
        AjaxResult ajaxResult = new AjaxResult();
//        InputStream inputStream = FileUtil.getResourcesFileInputStream(filePath);
        stringBuffer.delete(0,stringBuffer.length());
        questionUpload.init();
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 1, QuestionModel.class));
        for (Object o:data) {
            QuestionUpload.setQuestionService(questionService);
            QuestionUpload.setSubjectService(subjectService);
            stringBuffer.append(questionUpload.insertQuestion(o,contest_id));
        }
        inputStream.close();
        return new AjaxResult().setData(stringBuffer);
    }
}
