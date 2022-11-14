package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK);
//        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        //[response-header]
        response.setHeader("Content-Type", "text/plain;charset=utf8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //캐쉬에 대한 것, 현재는 캐시를 완전 무효화
        response.setHeader("Pragma", "no-cache"); //http강의에서 과거버전까지 캐쉬를 무효화
        response.setHeader("my-header", "hello"); //내가 원하는 임의의 헤더를 생성

        //[Header 편의 메서드]
        content(response);
        cookie(response);
        redirect(response);

        //[message body]
        PrintWriter writer = response.getWriter();
        writer.println("ok");

    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8"); //content에 대한 type.1
        response.setContentType("text/plain"); //type.2-1
        response.setCharacterEncoding("utf-8"); //type.2-2
        //response.setContentLength(2); //임의로 적으면 적은 값이 나감.(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie); //여기에 쿠기를 넣으면 (//response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");)과 같은 효과.
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html

//        response.setStatus(HttpServletResponse.SC_FOUND); //302
//        response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
