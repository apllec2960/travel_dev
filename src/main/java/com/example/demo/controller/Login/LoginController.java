package com.example.demo.controller.Login;

import com.example.demo.service.LoginService;
import com.example.demo.util.HttpUtil;
import com.example.demo.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static com.example.demo.util.HttpUtil.getClientIP;
import static com.example.demo.util.HttpUtil.makeHashToJsonModelAndView;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 로그인 화면 이동
     * 페이지 처리 없음
     * request:
     * response :
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/")
    public String loginIntro(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

        log.debug("LoginController userLoginIntro START");
        try {
            if(session.getAttribute("loginId") == null) {
                return "login/login";
            }else {
                model.addAttribute("loginId",session.getAttribute("loginId"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("LoginController userLoginIntro END");
        return "home";
    }

    /**
     * <b>로그인(DB사용 x)</b>
     * 페이지 처리 없음
     * request:
     * response : msgCode, msgDesc
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/login/login.do")
    @ResponseBody
    public ModelAndView login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        log.debug("LoginController login START");

        HashMap<String, Object> paramMap = HttpUtil.getParameterMap(request);
        HashMap<String, Object> map = new HashMap<String, Object>();

        try {
            String ipAddresss = getClientIP(request);

            log.debug("======================== user Info START ==========================");
            log.debug("loginId          : "+ paramMap.get("loginId"));
            log.debug("sessionTime       : "+ipAddresss);
            log.debug("======================== user Info END ==========================");

            session.setAttribute("loginIp", ipAddresss);
            session.setAttribute("loginId", paramMap.get("loginId"));
            session.setMaxInactiveInterval(1800);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {

        }

        log.debug("LoginController login END");

        return makeHashToJsonModelAndView(map);
    }


    /**
     * <b>로그인(DB사용)</b>
     * 페이지 처리 없음
     * request:
     * response : msgCode, msgDesc
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/login/login2.do")
    @ResponseBody
    public HashMap<String, Object> login2(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        log.debug("LoginController login START");

        HashMap<String, Object> paramMap = HttpUtil.getParameterMap(request);
        HashMap<String, Object> map = new HashMap<String, Object>();

        try {
            String ipAddresss = getClientIP(request);
            paramMap.put("lastLoginIp", ipAddresss);
            paramMap.put("sessionId", session.getId());
            paramMap.put("request", request.getServletPath());

            map = loginService.login(paramMap);

            if((Constants.OK).equals(String.valueOf(map.get("msgCode")))) {
                int sessionTime = Integer.parseInt(String.valueOf(map.get("sessionTime"))) * 60;

                log.debug("======================== user Info START ==========================");
                log.debug("loginId          : "+map.get("loginId"));
                log.debug("sessionTime       : "+sessionTime);
                log.debug("======================== user Info END ==========================");

                log.debug("session     : "+session);

                session.setAttribute("loginId", map.get("loginId"));

                session.setMaxInactiveInterval(sessionTime);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(Constants.OK == map.get("msgCode")) {

            }else{
                map.put("msgCode", Constants.FAIL);
                map.put("success", false);
                if(null == map.get("msgDesc") || ("").equals(map.get("msgDesc"))) {
                    map.put("msgDesc","정상적으로 수행되지 않았습니다. 관리자에게 문의하시기 바랍니다.");
                }
            }
        }

        log.debug("LoginController login END");

        return map;
    }

    /**
     * <b>로그아웃</b>
     * request:
     * response : msgCode, msgDesc
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/login/logout.do")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        log.debug("LoginController logout START");

        HashMap<String, Object> paramMap = HttpUtil.getParameterMap(request);
        HashMap<String, Object> map = new HashMap<String, Object>();

        try {
            log.debug("Session Clear1");

            session.invalidate();

            log.debug("Session Clear2");
        }catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("LoginController logout END");

        return "/login/login";
    }

}
