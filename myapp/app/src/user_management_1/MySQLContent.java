package user_management_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MySQLContent {
	//1.新規登録をするためのSQL操作
	public static void newUser(int userid, String username, String usermail, String phone) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
		//phpMyAdminで作成したデータベースへ接続
		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL,USERNAME,PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//SQL送信処理
			//いずれは最初の操作指示のコマンドで打った番号によって替える
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO user VALUE(?,?,?,?)");
			pstmt.setInt(1, userid);
			pstmt.setString(2, username);
			pstmt.setString(3, usermail);
			pstmt.setString(4, phone);
			int r = pstmt.executeUpdate();
			if(r != 0) {
				System.out.println(r + "件のユーザーを登録しました");
			}else {
				System.out.println("正しくユーザー登録されませんでした");
			}
			//後片付け
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e) {
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

	//2.1人のユーザーを指定して登録したデータを見るためのSQL操作
	public static void showUser(int index) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
		//phpMyAdminで作成したデータベースへ接続
		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL,USERNAME,PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);

			//SQL送信処理
			//いずれは最初の操作指示のコマンドで打った番号によって替える

			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
			pstmt.setInt(1, index);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				System.out.println("ユーザーID" + " "
									+ "氏名" + " "
									+ "メールアドレス" + " "
									+ "電話番号" + " " );
				System.out.println(rs.getInt("userId") + " "
								 + rs.getString("name") + " "
								 + rs.getString("mailAddress") + " "
								 + rs.getString("tel") );
			}else {
				System.out.println("入力されたIDのデータはありません");
			}
			//後片付け
			rs.close();
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e) {
			//ロールバック
			e.printStackTrace();
			try {
				con.rollback();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		finally {
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

	//3.登録した全員のデータを見るためのSQL操作

	//3.が送信されたときの処理 全ユーザーを表示
	public static void showAllUser() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
		//phpMyAdminで作成したデータベースへ接続
		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL,USERNAME,PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);

			//SQL送信処理
			//いずれは最初の操作指示のコマンドで打った番号によって替える
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user ");
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				System.out.println("ユーザーID" + " "
						+ "氏名" + " "
						+ "メールアドレス" + ""
						+ "電話番号" + " " );
				System.out.println(rs.getInt("userId") + " "
					    + rs.getString("name") + " "
					    + rs.getString("mailAddress") + " "
					    + rs.getString("tel") );
			}
			if(!(rs.next())) {
				System.out.println("データベースにデータがありません");
			}
			//後片付け
			rs.close();
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e) {
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

	//4.が選択されたときに呼ぶメソッド 選択したユーザーのデータを削除する

	//4.選択したユーザーのデータを削除するためのSQL操作
	public static void delUser(int del) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
		//phpMyAdminで作成したデータベースへ接続
		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL,USERNAME,PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);

			//SQL送信処理
			//いずれは最初の操作指示のコマンドで打った番号によって替える

			//データを検索し、データの有無によって条件分岐
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
			pstmt.setInt(1, del);
			ResultSet rs = pstmt.executeQuery();
			//検索データがあるならデータを削除
			if(rs.next()) {
				pstmt = con.prepareStatement("DELETE FROM user WHERE userID = ?");
				pstmt.setInt(1, del);
				int r = pstmt.executeUpdate();
				if(r != 0) {
					System.out.println(del +"さんのデータは正しく削除されました");
				}
			}else {
				//検索データがないならデータがないことをコマンドに送る
				System.out.println("入力されたIDのデータはありません");
			}

			//後片付け
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e) {
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

	//5.全員のデータを削除するためのSQL操作

	//5.が選択されたときに呼ぶメソッド 全ユーザーを削除する
	public static void delAllUser() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
		//phpMyAdminで作成したデータベースへ接続
		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL,USERNAME,PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);

			//SQL送信処理
			//いずれは最初の操作指示のコマンドで打った番号によって替える
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM user");

			int r = pstmt.executeUpdate();
			if(r != 0) {
				System.out.println("全データは正しく削除されました");
			}else {
				System.out.println("データベースにデータがありません");

			}
			//後片付け
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();
		}catch(SQLException e) {
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

	//6.クレジットカードの番号登録をするためのSQL操作
	public static void newCredit(int index2) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}

		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS = "";
		Connection con = null;
		try {
			//DBへ接続
			con = DriverManager.getConnection(URL,USERNAME,PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//指定したユーザーIDのデータがあるかを確認
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
			pstmt.setInt(1, index2);
			//入力したユーザーIDがユーザー情報に既にある場合の処理
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("ユーザーID" + " "
									+ "氏名" + " "
									+ "メールアドレス" + " "
									+ "電話番号" + " " );
				System.out.println(rs.getInt("userId") + " "
								 + rs.getString("name") + " "
								 + rs.getString("mailAddress") + " "
								 + rs.getString("tel") );

				Scanner scanner = new Scanner(System.in);
				//クレジットカードの番号を入力させる
				System.out.println("クレジットカードの番号を入力してください。(16ケタ)");
				String strCredit = scanner.nextLine();

				//有効期限年を入力させる
				System.out.println("クレジットカードの有効期限(年)を入力してください(2ケタ）");
				System.out.println("例： 2021年⇒21");
				String strYear = scanner.nextLine();

				//有効期限月を入力させる
				System.out.println("クレジットカードの有効期限(月)を入力してください(2ケタ）");
				System.out.println("例： 1月⇒01 11月⇒11");
				String strMonth = scanner.nextLine();

				//creditテーブルへアクセスする、データベースに格納する
				pstmt = con.prepareStatement("INSERT INTO credit VALUES (?,?,?,?)");
				pstmt.setInt(1, index2);
				pstmt.setString(2, strCredit);
				pstmt.setString(3, strYear);
				pstmt.setString(4, strMonth);
				//SQL文を自動組み立て、送信
				int r = pstmt.executeUpdate();
				if(r != 0) {
					System.out.println("入力されたユーザーIDのクレジットカード情報は正しく登録されました");
				}else {
					System.out.println("入力されたIDのクレジットカード情報は正しく登録されませんでした");
				}
				//後片付け
				scanner.close();
				pstmt.close();
				//送信済みの処理要求の確定（コミット）
				con.commit();
			}else {
				//ユーザー情報が登録されていない場合の処理
				System.out.println("入力されたIDのデータはありません");
			}
		}catch(SQLException e) {
			//ロールバック
			e.printStackTrace();
			try {
				con.rollback();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		finally {
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

	//7.クレジットカードを登録している人のユーザーID,ユーザー名、クレジットカードの情報を表示するSQL操作
	public static void showCredit() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}

		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS = "";
		Connection con = null;
		try {
			//DBへ接続
			con = DriverManager.getConnection(URL,USERNAME,PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);
			//userテーブルとcreditテーブルを結合
			//userテーブルから抽出するもの：ユーザーID、userテーブルのユーザー名
			//creditテーブルから抽出するもの：クレジット番号、有効期限年、有効期限月
			PreparedStatement pstmt = con.prepareStatement("SELECT credit.userId, user.name, creditNum, year, month FROM credit JOIN user ON credit.userId = user.userId");
			//入力したユーザーIDがユーザー情報に既にある場合の処理
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println("ユーザーID" + " "
									+ "氏名" + " "
									+"クレジットカード番号" + " "
								    + "有効期限年" + " "
								    + "有効期限月" + " " );
				System.out.println(rs.getInt("user.userId") + " "
								 + rs.getString("user.name") + " "
								 + rs.getString("creditNum")
								 + rs.getString("year") + "/"
								 + rs.getString("month"));
			}
			if(!(rs.next())){
				//クレジットカードの情報を誰も登録していない場合の処理
				System.out.println("入力されたIDのデータはありません");
			}
			//後片付け
			rs.close();
			pstmt.close();
			//送信済みの処理要求の確定（コミット）
			con.commit();


		}catch(SQLException e) {
			//ロールバック
			e.printStackTrace();
			try {
				con.rollback();
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		finally {
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

}
