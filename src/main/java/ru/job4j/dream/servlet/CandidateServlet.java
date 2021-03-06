package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CandidateServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req,
                      HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("last", new ArrayList<>(PsqlStore.instOf().findLastCandidates()));
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().saveCandidate(new Candidate(
                Integer.parseInt(req.getParameter("id")),
                req.getParameter("name"),
                Timestamp.valueOf(LocalDateTime.now()),
                Integer.parseInt(req.getParameter("city"))));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
