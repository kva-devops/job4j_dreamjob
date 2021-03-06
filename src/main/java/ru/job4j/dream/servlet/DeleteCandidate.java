package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCandidate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                          HttpServletResponse resp) throws IOException, ServletException {
        PsqlStore.instOf().deleteCandidate(Integer.valueOf(req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
