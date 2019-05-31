package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.model.*;
import com.qexz.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "manage")
public class ManageController {

    private static Log LOG = LogFactory.getLog(ManageController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    /**
     * 管理员登录页
     */
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);

        if (currentAccount == null) {
            return "/manage/manage-login";
        } else {
            return "redirect:/manage/contest/list";
        }
    }

    /**
     * 用户管理
     */
    @RequestMapping(value="/account/list", method= RequestMethod.GET)
    public String accountList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            return "/error/404";
        } else {
            Map<String, Object> data = accountService.getAccounts(page, QexzConst.accountPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-accountList";
        }
    }

    /**
     * 考试管理
     */
    @RequestMapping(value="/contest/list", method= RequestMethod.GET)
    public String contestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        contestService.updateStateToStart();
        contestService.updateStateToEnd();
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
            List<Subject> subjects = subjectService.getSubjects();
            data.put("subjects", subjects);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-contestBoard";
        }
    }

    /**
     * 考试管理-查看试题
     */
    @RequestMapping(value="/contest/{contestId}/problems", method= RequestMethod.GET)
    public String contestProblemList(HttpServletRequest request,
                                     @PathVariable("contestId") Integer contestId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            List<Question> questions = questionService.getQuestionsByContestId(contestId);
            Contest contest = contestService.getContestById(contestId);
            data.put("questionsSize", questions.size());
            data.put("questions", questions);
            data.put("contest", contest);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-editContestProblem";
        }
    }

    /**
     * 成绩管理-考试列表
     */
    @RequestMapping(value="/result/contest/list", method= RequestMethod.GET)
    public String resultContestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            contestService.updateStateToStart();
            contestService.updateStateToEnd();
            Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
            List<Subject> subjects = subjectService.getSubjects();
            data.put("subjects", subjects);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-resultContestBoard";
        }
    }

    /**
     * 成绩管理-考试列表-学生成绩列表
     */
    @RequestMapping(value="/result/contest/{contestId}/list", method= RequestMethod.GET)
    public String resultStudentList(HttpServletRequest request,
                                    @PathVariable("contestId") int contestId,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            List<Grade> grades = gradeService.getGradesByContestId(contestId);
            Contest contest = contestService.getContestById(contestId);
            List<Account> students = new ArrayList<>();
            for( Grade  grade : grades){
                students.add(accountService.getAccountById(grade.getStudentId()));
            }

            data.put("grades", grades);
            data.put("contest", contest);
//            data.put("students", students);
            model.addAttribute("students",students);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-resultStudentBoard";
        }
    }

    /**
     * 课程管理
     */
    @RequestMapping(value="/subject/list", method= RequestMethod.GET)
    public String subjectList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = subjectService.getSubjects(page, QexzConst.subjectPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-subjectBoard";
        }
    }


}
