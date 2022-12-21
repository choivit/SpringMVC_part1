package hello.servlet.web.frontController.v3;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/front-controller/v3/members/new-form
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    //미리 저장을 해놓고 부르면 꺼내서 쓸 수 있게 됨.
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("FrontControllerServletV1.service");
        //상단의 로그 같은 경우는 console에다가 찍는 게 맞으나 여기서는 그냥 시스템에 찍음.

        //해당 주소를 입력 하면 request.getRequestURI에 들어와서 뽑아내면
        String requestURI = request.getRequestURI();

        //미리 넣어뒀던 주소 값에서 동일 한 것을 찾아 연결해줌.
        ControllerV3 controller = controllerMap.get(requestURI);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap을 넘겨줘야 함.

//        Map<String, String> paramMap = new HashMap<>();
//        request.getParameterNames().asIterator()
//                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
//
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();//논리이름 new-form
        ///WEB-INF/views/new-form.jsp
        MyView view = viewResolver(viewName);

        //모델에 있는 걸 request에 넣어주는 과정
        view.render(mv.getModel(), request, response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
