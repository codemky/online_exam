package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.*;
import com.qexz.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private static Log LOG = LogFactory.getLog(DefaultController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private GradeService gradeService;

    /**
     * 首页
     */
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/home";
    }

    /**
     * 在线考试列表页
     */
    @RequestMapping(value="/contest/index", method= RequestMethod.GET)
    public String contestIndex(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        contestService.updateStateToStart();
        contestService.updateStateToEnd();
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);


        Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
        model.addAttribute(QexzConst.DATA, data);
//        List<Integer> integers = new ArrayList<>();

        if(currentAccount != null){
            Map<String ,Object> existCommit = new HashMap<>();
            Object contests = data.get("contests");
            List<Integer> integers = new ArrayList<>();
            if( contests !=null ){
                List<Contest> list = (List<Contest>) contests;
                for(Contest contest : list){
                    boolean isExist = false;
                    List<Grade> gradesByContestId = gradeService.getGradesByContestId(contest.getId());
                    for( Grade grade : gradesByContestId ){
                        if(grade.getStudentId() == currentAccount.getId())
                            isExist = true;
                    }
                    if(isExist)
                        integers.add(1);
                    else
                        integers.add(0);
                }
            }

            model.addAttribute("commit",existCommit);
            model.addAttribute("fuck",integers);
        }
        return "/contest/index";
    }

    /**
     * 在线考试页
     */
    @RequestMapping(value="/contest/{contestId}", method= RequestMethod.GET)
    public String contestDetail(HttpServletRequest request,
                               @PathVariable("contestId") int contestId,
                               Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        List<Grade> gradesByContestId = gradeService.getGradesByContestId(contestId);
        //检验考生是否已考试，进行拦截
        for(Grade grade : gradesByContestId)
            if(grade.getStudentId() == currentAccount.getId())
                return "redirect:/contest/index";

        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        Contest contest = contestService.getContestById(contestId);
        if (currentAccount == null || contest.getStartTime().getTime() > System.currentTimeMillis()
                || contest.getEndTime().getTime() < System.currentTimeMillis() ) {
            return "redirect:/contest/index";
        }
        List<Question> questions = questionService.getQuestionsByContestId(contestId);
        for (Question question : questions) {
            question.setAnswer("");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("contest", contest);
        data.put("questions", questions);
        model.addAttribute(QexzConst.DATA, data);
        return "/contest/detail";
    }

    /**
     * 获取服务器端时间,防止用户篡改客户端时间提前参与考试
     *
     * @return 时间的json数据
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult time() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new AjaxResult().setData(localDateTime);
    }

    /**
     * 测试分布式一致性session
     * @param session
     * @return
     */
    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return new AjaxResult().setData(session.getId());
    }
}
