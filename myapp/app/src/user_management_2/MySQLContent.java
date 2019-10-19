package user_management_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLContent {
	//1.が送信されたときの処理 新規登録
	public static void newUser(int userid, String username, String usermail, String phone, String password) {
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
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO user VALUE(?,?,?,?,?)");
			pstmt.setInt(1, userid);
			pstmt.setString(2, username);
			pstmt.setString(3, usermail);
			pstmt.setString(4, phone);
			pstmt.setString(5, password);
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

	//2.が送信されたときの処理 指定したユーザーを表示
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
									+ "電話番号" + " "
									+ "パスワード" + "");
				System.out.println(rs.getInt("userId") + " "
								 + rs.getString("name") + " "
								 + rs.getString("mailAddress") + " "
								 + rs.getString("tel")
								 + "パスワード" + "*********");
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
						+ "電話番号" + " "
						+"パスワード" + "");
				System.out.println(rs.getInt("userId") + " "
					    + rs.getString("name") + " "
					    + rs.getString("mailAddress") + " "
					    + rs.getString("tel") + "  "
					    + "***********");
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
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM user WHERE userID = ?");
			pstmt.setInt(1, del);
			int r = pstmt.executeUpdate();
			if(r != 0) {
				System.out.println(del +"さんのデータは正しく削除されました");
			}else {
				System.out.println(del + "さんのデータは正しく削除されませんでした");
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
				System.out.println("全データは正しく削除されませんでした");
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
}
