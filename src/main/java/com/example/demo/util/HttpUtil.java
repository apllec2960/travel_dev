package com.example.demo.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;


public class HttpUtil {
    public static ModelAndView makeHashToJsonModelAndView(
            final HashMap<String, Object> map) {
        Gson gson = new Gson();
        ModelAndView mnv = new ModelAndView();
        mnv.setViewName("notification");
        mnv.addObject("message", gson.toJson(map));
        return mnv;
    }

    public static void sendResponceToJson(HttpServletResponse response,
                                          final HashMap<String, Object> map) {
        response.setContentType("application/json; charset=utf-8");

        Gson gson = new Gson();
        try {
            response.getWriter().write(gson.toJson(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Object> getParameterMap(
            HttpServletRequest request) {
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        Enumeration<?> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            // Parameter가 배열일 경우
            if (parameters.length > 1) {
                parameterMap.put(paramName, parameters);
                // Parameter가 배열이 아닌 경우
            } else {
                try {
                    parameters[0] = parameters[0].replaceAll("%", "%25");
                    parameterMap.put(paramName, URLDecoder.decode(parameters[0],"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return parameterMap;
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
