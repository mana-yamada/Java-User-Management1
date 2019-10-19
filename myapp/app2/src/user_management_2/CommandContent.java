package user_management_2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Properties;
import java.util.Scanner;

public class CommandContent {
    //Scanner初期化
	Scanner scanner = new Scanner(System.in);


	//プログラムで操作したいことを選択してもらう
	public void select() {
		//ユーザーの操作したい内容を選んでもらう
		System.out.println("           ");
		System.out.println("1:新規登録");
		System.out.println("2:ユーザーIDを指定してデータを表示");
		System.out.println("3:全ユーザーのデータを表示");
		System.out.println("4:ユーザーIDを指定してデータを削除");
		System.out.println("5:全ユーザーのデータを削除");
		System.out.println("6:クレジットカードの新規登録");
		System.out.println("7:ユーザーIDを指定してクレジットカードのデータを表示");
		System.out.println("0:プログラムを終了する");
		System.out.println("         ");
		System.out.println("0～7までの数字で上記の操作を選んでください");

		//0から5までの整数を入力してもらう
		String strSelect = scanner.nextLine();
		int select =Integer.parseInt(strSelect);

		//MySQLドライバーへの接続
		driverConnect(select);
	}

	//select()メソッドで入力してもらった番号に合わせて別々のメソッドを呼び出す

	//MySQLドライバーへの接続
	public void driverConnect(int select) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			readDB(select);
		}catch(ClassNotFoundException e) {
			System.out.println("ドライバーが正しくセットされていません");
		}
	}

	//プロパティファイルから接続先のデータを読み込み
	public void readDB(int select) {
		try {
			Reader fr = new FileReader("C:\\Users\\mana-koba\\Java-User-Management1\\myapp\\app2\\MySQLdocs.properties");
			Properties p = new Properties();
			p.load(fr);
			final String URL = p.getProperty("url");
			final String USERNAME = p.getProperty("userName");
			final String PASS = p.getProperty("pass");
			fr.close();
			connection(URL, USERNAME, PASS, select);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//DB接続及びSQL操作・DB切断
	public void connection(String URL, String USERNAME, String PASS,int select) {
		//DBへの接続
		Connection con = null;
		//↑ここまでを別のメソッドにする
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASS);
			//自動コミットモードの解除
			con.setAutoCommit(false);
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			//
//			//選んでもらった内容に合わせてSQL文を組み立てる所からコミットするところ部分を別々のメソッドにして条件分岐
			switch(select) {
			case 1:
				do1(con, pstmt, rs);
				break;
			case 2:
				do2(con, pstmt, rs);
				break;
			case 3:
				do3(con, pstmt, rs);
				break;
			case 4:
				do4(con, pstmt, rs);
				break;
			case 5:
				do5(con, pstmt, rs);
				break;
			case 6:
				do6(con,pstmt, rs);
				break;
			case 7:
				do7(con, pstmt, rs);
				break;
			case 0:
				do0();
				break;
			}
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
					//最初の操作に戻る
					select();
				}catch(SQLException e3) {
					e3.printStackTrace();
				}
			}
		}
	}

	//1.新規登録をするSQLの処理
	public void do1(Connection con, PreparedStatement pstmt,ResultSet rs) throws SQLException {
			System.out.println("新規登録をします");

			try {
				//ユーザーIDの入力
				System.out.println("ユーザーID：0以上の整数で正しく入力してください");
				String strId = scanner.nextLine();
		        System.out.println(strId);
				int userId = Integer.parseInt(strId);

			    //Integerにしたものがマイナス値だったら例外を投げる

				//Passwordの入力
				System.out.println("パスワード：8文字以上20文字以内 半角英数字でお願いします");
				String password = scanner.nextLine();
				System.out.println("*************");


				//ユーザー名の入力
				System.out.println("ユーザー名");
				String username = scanner.nextLine();
				System.out.println(username);
				if(username.length() > 50) {
					throw new ToolongStringException ("正しくデータが入力されません");
				}
				//メールアドレスの入力
				System.out.println("メールアドレス");
				String usermail = scanner.nextLine();
				System.out.println(usermail);
				if(usermail.length() > 50) {
					throw new ToolongStringException ("正しくデータが入力されません");
				}
				//電話番号の入力
				System.out.println("電話番号");
				String phone = scanner.nextLine();
				System.out.println(phone);
				if(phone.length() > 15) {
					throw new ToolongStringException ("正しくデータが入力されません");
				}

				//入力した情報を表示する⇒これで本当に登録してよいかを確認できるうようにいずれはしたい。
				System.out.println("あなたの登録した情報");
				System.out.println("ユーザーID:" + userId);
				System.out.println("ユーザー名:" + username);
				System.out.println("メールアドレス:" + usermail);
				System.out.println("電話番号:" + phone);
				System.out.println("パスワード" + "**************");
				//SQL送信処理
				pstmt = con.prepareStatement("INSERT INTO user VALUE(?,?,?,?,?)");
				pstmt.setInt(1, userId);
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
				rs.close();
				pstmt.close();
				//送信済みの処理要求の確定（コミット）
				con.commit();
			}catch(ToolongStringException e) {
				e.printStackTrace();
			}catch(SQLIntegrityConstraintViolationException e) {
				//今回はIDが被っていることを知らせるため
				System.out.println("入力されたIDは既に登録されています");
				e.printStackTrace();
			}
	}

	//2.1人を選んで登録したデータを見る
	public void do2(Connection con ,PreparedStatement pstmt , ResultSet rs ) throws SQLException {

		//ユーザーIDの入力
		System.out.println("ユーザーID：0以上の整数で正しく入力してください");
		String strId = scanner.nextLine();
        System.out.println(strId);
		int userId = Integer.parseInt(strId);

		//SQL送信処理
		pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
		pstmt.setInt(1,userId);
		rs = pstmt.executeQuery();
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
	}

	//3.登録した全員のデータを見る
	public void do3(Connection con,PreparedStatement pstmt, ResultSet rs) throws SQLException {
		//SQL送信処理
		pstmt = con.prepareStatement("SELECT * FROM user ");
		rs = pstmt.executeQuery();
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
	}

	//4.選択したユーザーのデータを削除する ユーザー入力の操作は条件分岐のところで入力させる
	public void do4(Connection con,PreparedStatement pstmt, ResultSet rs) throws SQLException {

		//ユーザーIDの入力
		System.out.println("ユーザーID：0以上の整数で正しく入力してください");
		String strId = scanner.nextLine();
        System.out.println(strId);
		int userId = Integer.parseInt(strId);

		//SQL送信処理
		pstmt = con.prepareStatement("DELETE FROM user WHERE userID = ?");
		pstmt.setInt(1, userId);
		int r = pstmt.executeUpdate();
		if(r != 0) {
			System.out.println(userId +"さんのデータは正しく削除されました");
		}else {
			System.out.println(userId + "さんのデータは正しく削除されませんでした");
		}
		//後片付け
		pstmt.close();
		//送信済みの処理要求の確定（コミット）
		con.commit();
	}

	//5.全員のデータを削除する
	public void do5(Connection con,PreparedStatement pstmt, ResultSet rs) throws SQLException {
		//SQL送信処理
		pstmt = con.prepareStatement("DELETE FROM user");
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
	}

	//6:クレジットカードの新規登録
	public void do6(Connection con,PreparedStatement pstmt, ResultSet rs) throws SQLException {
		//ユーザーIDの入力
		System.out.println("ユーザーID：0以上の整数で正しく入力してください");
		String strId = scanner.nextLine();
	    System.out.println(strId);
		int userId = Integer.parseInt(strId);

		//SQL送信処理
		//ひな型
		pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
		//ひな型に値を流し込み
		pstmt.setInt(1, userId);
		//検索系SQL文を自動組み立て、送信
		rs = pstmt.executeQuery();
		//結果票の処理
		if(rs.next()) {
			System.out.println("入力したユーザーはユーザー登録されていました。");
			try {
				//クレジットカード番号の入力
				System.out.println("クレジットカード番号：16ケタの番号を正しく入力してください。");
				String credit = scanner.nextLine();
		        System.out.println(credit);
				 //Integerにしたものがマイナス値だったら例外を投げる
		      	if(!(credit.length() == 16) ) {
					throw new ArithmeticException("0未満のが入力されています");
				}
		      	//クレジットカード会社の入力
		      	System.out.println("クレジットカードの会社を入力してください。");
		      	String creditCP = scanner.nextLine();
		      	System.out.println(creditCP);
		    	if(creditCP.length() > 16 ) {
					throw new ArithmeticException("0未満のが入力されています");
				}

				//有効期限年の入力
				System.out.println("有効期限年を入力してください 例：2021年⇒21");
				String year = scanner.nextLine();
		        System.out.println(year);
				 //Integerにしたものがマイナス値だったら例外を投げる
				if(!(year.length() == 2) ) {
					throw new ArithmeticException("0未満のが入力されています");
				}
				//有効期限月の入力
				System.out.println("有効期限月を入力してください  例：10月⇒10 1月⇒01");
				String month = scanner.nextLine();
		        System.out.println(month);
		        if(!(month.length() == 2) ) {
					throw new ArithmeticException("0未満のが入力されています");
				}
		        //SQL操作でクレジットカードの情報を格納する
		        //ひな型
		        pstmt = con.prepareStatement("INSERT INTO credit VALUES(?,?,?,?,?)");
		        //ひな型に流し込む
		        pstmt.setInt(1, userId);
		        pstmt.setString(2, credit);
		        pstmt.setString(3, creditCP);
		        pstmt.setString(4, year);
		        pstmt.setString(5, month);
		        //更新系SQLの自動組み立て、送信
		        int r = pstmt.executeUpdate();
		        if(r != 0) {
		        	System.out.println("お客様のクレジットカードを登録しました。");
		        }else {
		        	System.out.println("お客様のクレジットカードは正しく登録されませんでした。");
		        }
		        //後片付け
				rs.close();
				pstmt.close();
				//送信済みの処理要求の確定（コミット）
				con.commit();
				 //Integerにしたものがマイナス値だったら例外を投げる
				if(!(month.length() == 2)) {
					throw new ArithmeticException("0未満のが入力されています");
				}
			}catch(ArithmeticException e) {
				System.out.println("データが正しく入力されていません。");
				select();
			}catch(NumberFormatException e) {

			}
			catch(SQLIntegrityConstraintViolationException e) {
				//今回はカードがが被っていることを知らせるため
				System.out.println("入力されたカードは既に登録されています");
				e.printStackTrace();
			}
		}else {
			System.out.println("入力したユーザーはユーザー登録されていません。新規登録をしてください");
		}
	}

	//7：ユーザーIDを指定してクレジットカードのデータを表示
	public void do7(Connection con,PreparedStatement pstmt, ResultSet rs) throws SQLException {
			//ユーザーIDの入力
			System.out.println("ユーザーID：0以上の整数で正しく入力してください");
			String strId = scanner.nextLine();
		    System.out.println(strId);
			int userId = Integer.parseInt(strId);
			//SQL送信処理
			//ひな型
			pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
			//ひな型に値を流し込み
			pstmt.setInt(1, userId);
			//検索系SQL文を自動組み立て、送信
			rs = pstmt.executeQuery();
			//結果票の処理
			if(rs.next()) {
				System.out.println("入力したユーザーはユーザー登録されていました。");
				try {
					//クレジットカード番号の入力
					System.out.println("クレジットカード番号：16ケタの番号を正しく入力してください。");
					String credit = scanner.nextLine();
			        System.out.println(credit);
					 //Integerにしたものがマイナス値だったら例外を投げる
					if(!(credit.length() == 16) ) {
						throw new ArithmeticException("0未満のが入力されています");
					}

					//7:ユーザーIDを指定してクレジットカードのデータを表示
					pstmt = con.prepareStatement("SELECT credit.userId, user.name, creditNum, creditCP , year, month FROM credit JOIN user ON credit.userId = user.userId WHERE creditNum = ?" );
					pstmt.setString(1,credit);
					//検索系SQL文を自動組み立て、送信
					rs = pstmt.executeQuery();
					//登録情報の確認
					if(rs.next()) {
						System.out.println("");
						System.out.println("ユーザーID" + " "
								+ "氏名" + " "
								+ "クレジットカード番号" + " "
								+ "有効期限(月/年）" + " " );
						System.out.println(rs.getInt("credit.userId") + " "
							 + rs.getString("user.name") + " "
							 + rs.getString("creditNum") + " "
							 + rs.getString("creditCP") + " "
							 + rs.getString("year") + "/" + rs.getString("month") );
					}else {
						System.out.println("入力したカード番号のクレジットカードは登録されていません。");
					}
				}catch(ArithmeticException e) {
					System.out.println("データが正しく入力されていません。");
					select();
				}
			}else {
				System.out.println("入力したユーザーはユーザー登録されていません。新規登録をしてください");
			}
	}

	//0.プログラムを終了する
	public static void do0() {
		System.out.println("プログラムを終了します。おつかれさまでした。");
	}


	//データが正しく入力されていないときに呼び出しユーザーへ送るエラー文
	public void wrongInput() {
		System.out.println("もう一度最初から操作をやり直してください。");
		System.out.println("           ");
		//最初の操作に戻る
		select();
	}

}
