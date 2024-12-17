package org.ifmo.ru.lab2.classes;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class TableInformationManager {
    private List<Line> lines = new ArrayList<>();
    public static TableInformation getTableInformation(HttpSession session) {
        TableInformation tableInformation = (TableInformation) session.getAttribute("tableInformation");
        if (tableInformation == null) {
            tableInformation = new TableInformation();
            tableInformation.setLines(new ArrayList<>());
            session.setAttribute("tableInformation", tableInformation);
        }
        return tableInformation;
    }

}
