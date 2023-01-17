package hello.servlet.web.frontController.v4.controller;

import hello.servlet.web.frontController.v4.ControllerV4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paraMap, Map<String, Object> model) {

        // ModelView를 직접 만들 필요가 없음.
        return "new-form";
    }
}
