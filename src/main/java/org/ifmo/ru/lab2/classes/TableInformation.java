package org.ifmo.ru.lab2.classes;

import java.util.ArrayList;
import java.util.List;

public class TableInformation {
    private List<Line> lines;

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
    public List<Line> getLines() {
        if (lines == null) setLines(new ArrayList<>());
        return lines;
    }
}
