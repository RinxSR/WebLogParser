<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>LogParserResultPage</title>
</head>
<body>
<div style="text-align: left;">
    <h1>Результат работы парсера</h1>
    <table border=1 width="50%" height="100%">
        <tr>
            <td width="50%" height="10%"><h2>Текст до обработки</h2></td>
            <td width="50%" height="10%"><h2>Текст после обработки</h2></td>
        </tr>
        <tr>
            <td width="50%" height="90%"><pre>${requestScope.sourceText}</pre></td>
            <td width="50%" height="90%"><pre>${requestScope.handledText}</pre></td>
        </tr>
    </table>
</div>
<br/>
<form action="index.jsp" >
    <button type="submit">Обработать еще один файл</button>
</form>
</body>
</html>