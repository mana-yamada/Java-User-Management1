package addUser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addUserCheck")
public class addUserCheck extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//リクエストパラメータの取得
		String strUserId = request.getParameter("userid"); //チェックする際にint型へ変換
		String password  = request.getParameter("password");
		String name 	 = request.getParameter("name");
		String mail      = request.getParameter("mail");
		String tel 		 = request.getParameter("tel");

		//取得したリクエストパラメータをチェック⇒ //エラー文づくり
		String errorMsg = "<h1>" + "エラー！" + "</h1>";
		try {
			//userIdのチェック
			if(strUserId.length() == 0 || strUserId == null) {
				errorMsg = "<p>・ユーザーIDが入力されていません</p>";
			}
			int userId = Integer.parseInt(strUserId);
			//入力されたuserIdは既に登録されているかSQLのデータチェック
			driverConnect(userId ,errorMsg);

			//passwordチェック
			if(password.length() == 0 || password == null ) {
				errorMsg = "<p>・パスワードが入力されていません。</p>";
			}
			if(password.length() < 8 || password.length() > 20) {
				errorMsg += "<p>" +  "・パスワードが8文字以上20文字以内で正しく入力されていません。" + "</p>";
				//throw new MismatchStringException ("error");
			}
			//nameチェック
			if( name.length() == 0 || name == null) {
				errorMsg = "<p>・名前が入力されていません。 </p>";
			}
			if(name.length() > 50) {
				errorMsg += "<p>" + "・名前が長すぎです。(50文字以内)" + "</p>";
				//throw new MismatchStringException("error");
			}
			//mailチェック
			if( mail.length() == 0 || mail  == null) {
				errorMsg = "<p>メールアドレスが入力されていません。</p>";
			}
			if(mail.length() > 50) {
				errorMsg += "<p>" + "・メールアドレスが長すぎです。(50文字以内)"  + "</p>";
				//throw new MismatchStringException("error");
			}
			//telチェック
			if( tel.length() == 0 || tel == null) {
				errorMsg = "<p>・電話番号が入力されていません。</p>";
			}
			if(tel.length() > 15) {
				errorMsg += "<p>" + "・電話番号が長すぎです。(15文字以内)" + "</p>";
				//throw new MismatchStringException("error");
			}
		}catch(Exception e) {

		}
	}


	//動作が正常時の出力文づくり


	//ユーザーIDチェック
	//MySQLドライバーへの接続
	public void driverConnect(int userId , String errorMsg) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			readDB(userId , errorMsg);
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
	}
	//プロパティファイルから接続先のデータを読み込み
	public void readDB(int userId , String errorMsg) {
		try {
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-User-Management1\\myapp\\usersystem\\MySQLdocs.properties");
			Properties p = new Properties();
			p.load(fr);
			final String URL = p.getProperty("url");
			final String USERNAME = p.getProperty("userName");
			final String PASS = p.getProperty("pass");
			fr.close();
			connection(URL, USERNAME, PASS, userId, errorMsg);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//DB接続及びSQL操作・DB切断
	public void connection(String URL, String USERNAME, String PASS, int userId, String errorMsg) {
		//DBへの接続
		Connection con = null;

		try {
			con = DriverManager.getConnection(URL, USERNAME, PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//SQL操作 //新規登録前の検索
			checkUserId( con ,  pstmt , rs , userId, errorMsg);
		}catch(SQLException e){
			e.printStackTrace();
			//ロールバック
			try {
				con.rollback();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}finally {
			//DB接続を切断
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e3) {
					e3.printStackTrace();
				}
			}
		}
	}
	//ユーザーIDのチェック
	public void checkUserId(Connection con , PreparedStatement pstmt ,ResultSet rs , int userId, String errorMsg) throws SQLException{
		//SQL送信処理
		//ひな型
		pstmt = con.prepareStatement("SELECT * FROM users WHERE userId = ?");
		//ひな型に値を流し込み
		pstmt.setInt(1, userId);
		//検索系SQL文を自動組み立て、送信
		rs = pstmt.executeQuery();
		//結果票の処理 //既にユーザーが登録されていたらエラー文を出す
		if(rs.next()) {
			errorMsg += ("<p>" +  "・入力したユーザーはユーザー登録されています" + "</p>");
		}else {
			//System.out.println("入力したユーザーはユーザー登録されていません。新規登録をしてください");
		}
		//後片付け
		rs.close();
		pstmt.close();
		//送信済みの処理要求の確定（コミット）
		con.commit();
	}

}
