<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー新規登録</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>

<%--入力フォームの作成 --%>
<form action = "/addUserCheck" method = "post">
<p>ユーザーID：<input type ="text" name ="userId"><p>
<p>パスワード(8文字以上20文字以内)：<input type = "text" name = "password"></p>
<p>名前(50文字以内)：<input type = "text" name = "name"></p>
<p>メールアドレス(50文字以内):<input type ="text" name = "mail"></p>
<p>電話番号(15文字以内)<input type = "text" name = "tel"></p>
<%--//入力内容を確認するページへ移動(addUserCheck.jsp) --%>
<input type = "submit" value ="入力内容を確認">
</form>
<%--//TOP画面へ戻る --%>
<input type = "submit" value = "TOPへ戻る">
</body>
</html>



