<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>LogParser</title>
</head>
<body>
<div style="text-align: left;">
  <h1>Парсер лог-файлов</h1>
  <form method="post" action="log-parser" enctype="multipart/form-data">
    <h4>Выберите файл для обработки:</h4> <br/>
    <input type="file" name="file"/><br/>
    <br /> <input type="submit" value="Обработать" name="обработать"/>
  </form>
</div>
</body>
</html>
