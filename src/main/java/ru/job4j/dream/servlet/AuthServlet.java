package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User buffUser = PsqlStore.instOf().findByEmail(email);
        if (buffUser != null
            && buffUser.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", buffUser);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Wrong login or password. Try again.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
