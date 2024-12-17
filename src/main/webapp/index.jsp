<%@ page import="org.ifmo.ru.lab2.classes.TableInformationManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.ifmo.ru.lab2.classes.Line" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Веб 2 лаба</title>
    <link rel="stylesheet" href="styles/buttonsStyles.css">
    <link rel="stylesheet" href="styles/SVGStyles.css">
    <link rel="stylesheet" href="styles/GeneralStyles.css">
</head>
<body>
<jsp:useBean id="tableInformation" class="org.ifmo.ru.lab2.classes.TableInformation" scope="session"/>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        <%
                    List<Line> lines = TableInformationManager.getTableInformation(session).getLines();
            if (lines != null && !lines.isEmpty()) {
                for (Line line : lines) {
                    BigDecimal x = line.getX();
                    BigDecimal y = line.getY();
                    BigDecimal r = line.getR();
                    String isHit = line.isHit() ? "Yes" : "No";
                    long executionTime = line.getExecutionTime();
                    String time = line.getTime();
        %>
        addRowToTable(<%= x %>, <%= y %>, <%= r %>, "<%= isHit %>", <%= executionTime %>, "<%= time %>");
        addDot(<%= x %>, <%= y %>, <%= r %>);
        console.log(<%= x %>, <%= y %>, <%= r %>, "<%= isHit %>", <%= executionTime %>, "<%= time %>")
        <%
                }
            }
        %>
    });
</script>


<header lang="ru">
    Козьяков Арсений Дмитриевич группа P3214 вариант 408815
</header>
<div class="main-container">
    <!-- Форма -->
    <div class="form-container">
        <form id="myForm">
            <div class="X">
                <label for="xSelect">
                    Enter X:
                    <select id="xSelect" name="X">
                        <option value="">Select X</option>
                        <option value="-5">-5</option>
                        <option value="-4">-4</option>
                        <option value="-3">-3</option>
                        <option value="-2">-2</option>
                        <option value="-1">-1</option>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                </label>
                <div id="xError" style="color: red; display: none;">X must be an integer between -5 and 3.</div>
            </div>

            <div id="yButton">
                <label>
                    Enter Y:
                    <input id="y" type="text" placeholder="must be between (-3;5)" name="Y">
                </label>
                <div id="yError" style="color: red; font-size: 12px; display: none;">Y must be a number between -3 and 5.</div>
            </div>

            <div class="R">
                <label for="R">
                    Enter R:
                    <input id="R" type="text" placeholder="must be between (1;4)" name="R">
                </label>
                <div id="rError" style="color: red; display: none;">R must be a number between 1 and 4.</div>
            </div>

            <input class="SendButton" type="button" value="now or never">
        </form>
        <div id="formError" style="color: red; display: none;"></div>

    </div>

    <!-- График -->
    <div class="graph-container">
        <svg id="graph" width="300" height="300" viewBox="-150 -150 300 300" xmlns="http://www.w3.org/2000/svg">
            <!-- Сетка и оси -->
            <line x1="-150" y1="0" x2="150" y2="0" stroke="black"></line>
            <line x1="0" y1="-150" x2="0" y2="150" stroke="black"></line>
            <text x="135" y="-5" font-size="12">x</text>
            <text x="-10" y="-135" font-size="12">y</text>

            <line x1="-100" y1="-5" x2="-100" y2="5" stroke="black"></line>
            <line x1="-50" y1="-5" x2="-50" y2="5" stroke="black"></line>
            <line x1="50" y1="-5" x2="50" y2="5" stroke="black"></line>
            <line x1="100" y1="-5" x2="100" y2="5" stroke="black"></line>

            <line x1="-5" y1="-100" x2="5" y2="-100" stroke="black"></line>
            <line x1="-5" y1="-50" x2="5" y2="-50" stroke="black"></line>
            <line x1="-5" y1="50" x2="5" y2="50" stroke="black"></line>
            <line x1="-5" y1="100" x2="5" y2="100" stroke="black"></line>

            <text x="-110" y="15" font-size="12">-R</text>
            <text x="-60" y="15" font-size="12">-R/2</text>
            <text x="40" y="15" font-size="12">R/2</text>
            <text x="90" y="15" font-size="12">R</text>

            <text x="5" y="105" font-size="12">-R</text>
            <text x="5" y="55" font-size="12">-R/2</text>
            <text x="5" y="-45" font-size="12">R/2</text>
            <text x="5" y="-95" font-size="12">R</text>

            <!-- Прямоугольник -->
            <rect x="0" y="-100" width="50" height="100" fill="blue" fill-opacity="0.5"></rect>
            <!-- Четверть круга -->
            <path d="M -50 0 A 50 50 0 0 0 0 50 L 0 0 Z" fill="blue" fill-opacity="0.5"></path>
            <!-- Треугольник -->
            <polygon points="0,0 50,0 0,50" fill="blue" fill-opacity="0.5"></polygon>
        </svg>
        <br>
        <br>
    </div>
    <div class = "table-container">
        <table id="resultsTable">
            <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Hit</th>
                <th>Execution Time</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <br>
    <br>
    <div id="graphWarning" style="color: yellow; display: none;"></div>
</div>

<div id="error" style="color: red; display: none;"> </div>
<button class="clear">Очистить график</button>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/InteractionsWithSVG.js"></script>
<script src="js/ActionsWithButtons.js"></script>
</body>
</html>
