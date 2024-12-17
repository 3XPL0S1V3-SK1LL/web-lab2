package org.ifmo.ru.lab2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet(name = "Controller", value = "/Controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем параметры запроса
        String x = req.getParameter("X");
        String y = req.getParameter("Y");
        String r = req.getParameter("R");

        // Проверяем наличие параметров
        if (x != null && y != null && r != null) {
            System.out.println("------------------Forward to AreaCheckServlet-------------------------");
            // Перенаправляем к AreaCheckServlet
            req.getRequestDispatcher("/AreaCheckServlet").forward(req, resp);
        } else {
            System.out.println("------------------Forward to Index.jsp-------------------------");
            // Перенаправляем на index.jsp
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
