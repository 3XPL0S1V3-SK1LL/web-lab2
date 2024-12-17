package org.ifmo.ru.lab2;

import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpSession;
import org.ifmo.ru.lab2.classes.TableInformation;
import org.ifmo.ru.lab2.classes.Line;
import org.ifmo.ru.lab2.classes.TableInformationManager;

@WebServlet("/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("------------------Started processing req-------------------------");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String xParam = request.getParameter("X");
        String yParam = request.getParameter("Y");
        String rParam = request.getParameter("R");

        BigDecimal x, y, r;
        try {
            x = new BigDecimal(xParam);
            y = new BigDecimal(yParam);
            r = new BigDecimal(rParam);
        } catch (NumberFormatException e) {
            System.out.println("------------------ caught NumberFormatException -------------------------");
            System.out.println("x: " + xParam + ", y: " + yParam + ", r: " + rParam);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Parameters must be numeric\"}");
            return;
        }

        long startTime = System.nanoTime();
        boolean isInside = checkHit(x, y, r);
        long executionTime = System.nanoTime() - startTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String currentTime = LocalDateTime.now().format(formatter);

        Line line = new Line();
        line.setX(x);
        line.setY(y);
        line.setR(r);
        line.setHit(isInside);
        line.setExecutionTime(executionTime / 1000);
        line.setTime(currentTime);
        System.out.println("------------------parameters set-------------------------");

        // Добавление в сессию
        HttpSession session = request.getSession();
        TableInformation tableInformation = TableInformationManager.getTableInformation(session);
        tableInformation.getLines().add(line);
        System.out.println("------------------Added to Session-------------------------");


        JsonObject object = new JsonObject();
        object.addProperty("x", x.toString());
        object.addProperty("y", y.toString());
        object.addProperty("r", r.toString());
        object.addProperty("hit", isInside);
        object.addProperty("executionTime", executionTime / 1000);
        object.addProperty("currentTime", currentTime);
        System.out.println("------------------Added to JSON-------------------------");
        System.out.println(object.toString());
        response.getWriter().write(object.toString());
        System.out.println("------------------wrote-------------------------");

    }
    private boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                x.compareTo(r.divide(BigDecimal.valueOf(2))) <= 0 && y.compareTo(r) <= 0) {
            return true;
        }
        // Проверка внутри круга
        else if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0 &&
                x.multiply(x).add(y.multiply(y)).compareTo(r.divide(BigDecimal.valueOf(2)).pow(2)) <= 0) {
            return true;
        }
        // Проверка в третьей области
        else if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) <= 0 &&
                y.compareTo(r.divide(BigDecimal.valueOf(2)).negate()) >= 0 && x.compareTo(r.divide(BigDecimal.valueOf(2))) <= 0 &&
                y.compareTo(x.subtract(r.divide(BigDecimal.valueOf(2)))) >= 0) {
            return true;
        }
        return false;
    }

//    private boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
//        if (x >= 0 && y >= 0 && x <= r/2 && y <= r) return true;
//         else if (x <= 0 && y <= 0 && x * x + y * y <= Math.pow(r/2, 2)) return true;
//         else if (x >= 0 && y <= 0 && y >= -r/2 && x <= r / 2 && y >= x - r/2) return true;
//        return false;
//    }
}
