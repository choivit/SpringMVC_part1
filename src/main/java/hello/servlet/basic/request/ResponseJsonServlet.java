package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type: application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        HelloData helloData = new HelloData();
        helloData.setUsername("kim");
        helloData.setAge(20);

        //{"username":"kim", "age":"20"}의 형식으로 바꿀 것임.
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);
    }

    //application/json은 utf-8 형식을 사용하도록 정의 되어 있음. 그래서 스펙이서 charset=utf-8과 같은 추가 파라미터 미제공.
    //따라서 Content-Type에 utf-8을 추가하여 보내는 것은 의미 없는 행위.
    //그러나 response.getWriter()는 추가 파라미터는 자동으로 추가 함. 따라서 response.getOutputStream()으로 출력하면 해당 문제 없음.
}
