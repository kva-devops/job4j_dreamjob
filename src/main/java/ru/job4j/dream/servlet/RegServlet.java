package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (PsqlStore.instOf().findByEmail(email) == null) {
            PsqlStore.instOf().saveUser(new User(0, name, email, password));
        } else {
            req.setAttribute("error", "This email address is already registered.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
