package laba6;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RenameServlet extends HttpServlet {
    private String text ="Foo Fighters is an American rock band, formed in Seattle, Washington, in 1994." +"\n"+
            "It was founded by Nirvana drummer Dave Grohl as a one-man project following the dissolution of Nirvana after the suicide of Kurt Cobain." +"\n"+
            "The group got its name from the UFOs and various aerial phenomena that were reported by Allied aircraft pilots in World War II, which were known collectively as \"foo fighters\"." +"\n"+
            "microcomputer revolution of the 1970s and 1980s, along with Apple co-founder Steve Wozniak.  Jobs and Wozniak co-founded Apple in 1976 to sell Wozniak's" +"\n"+
            "The band began with performances in Portland, Oregon. Goldsmith quit during the recording of the group's second album, The Colour and the Shape (1997)," +"\n"+
            "when most of the drum parts were re-recorded by Grohl himself. Smear's departure followed soon afterward, though he would appear as a guest with the band frequently starting in 2006," +"\n"+
            "and would rejoin as an official full-time member in 2011.";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String choice=request.getParameter("Choice");
        String oldString = "band";
        String newString = choice;
        text = text.replaceAll(oldString,newString);
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lab6</title>");
            out.println("</head>");
            out.println("<h1 align='center'><font face = 'Times New Roman'><b>Changed text</b></font><br></h1>");
            out.println("<body>");
            out.println("<span style=\"  \">" + text + "</span><br></br>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        request.setAttribute("text", text);
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("text", text);
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
