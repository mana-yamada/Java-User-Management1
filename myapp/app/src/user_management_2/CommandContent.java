package user_management_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CommandContent {
    Scanner scanner = new Scanner(System.in);


	//プログラムで操作したいことを選択してもらう
	public void select() {
		System.out.println("           ");
		System.out.println("1:新規登録");
		System.out.println("2:ユーザーIDを指定してデータを表示");
		System.out.println("3:全ユーザーのデータを表示");
		System.out.println("4:ユーザーIDを指定してデータを削除");
		System.out.println("5:全ユーザーのデータを削除");
		System.out.println("6:クレジットカードの新規登録");
		System.out.println("7:ユーザーIDを指定してクレジットカードのデータを表示");
		System.out.println("0:プログラムを終了する");

		System.out.println("0～5までの数字で上記の操作を選んでください");

			//0から5までの整数を入力してもらう
			String strSelexct = scanner.nextLine();
		try {
			int sn =Integer.parseInt(strSelexct);

			//0未満,5より大きい数字が入力されたら、例外を投げる
			if(sn < 0 || sn > 7) {
				throw new ArithmeticException("0未満,6以上の数字が入力されています");
			}
			//入力した番号に応じてメソッドを呼び出すbranchメソッドを呼び出す
			branch(sn);
			//もし0未満,6以上の数字、文字が入力されていたら・・・
			//改めてプログラムで操作したいことをselect()メソッドで選択してもらう
		}catch(ArithmeticException e) {
			//0未満,6以上の数字が入力されていたら
			minus();
		}catch(NumberFormatException e) {
			//マイナスの数字が入力されていたら
			missNumber();
		}
	}

	//select()メソッドで入力してもらった番号に合わせて別々のメソッドを呼び出す
	public void branch(int sn) {
		//0か1を入力した、もしくはデータが何かしら入っている場合
			switch(sn) {
			case 1:
				//1.新規登録を行うメソッドを呼び出す
				do1();
				break;
			case 2:
				//2:ユーザーIDを指定してデータを表示するメソッドを呼び出す
				do2();
				break;
			case 3:
				//3:全ユーザーのデータを表示するメソッドを呼び出す
				do3();
				break;
			case 4:
				//4:ユーザーIDを指定してデータを削除するメソッドを呼び出す
				do4();
				break;
			case 5:
				//5:全ユーザーのデータを削除するメソッドを呼び出す
				do5();
				break;
			case 6:
				//6:クレジットカードの新規登録
				do6();
				break;
			case 7:
				//7：ユーザーIDを指定してクレジットカードのデータを表示
				do7();
				break;
			case 0:
				//0.プログラムを終了するためのメソッドを呼び出す
				do0();
				break;
			}
	}

	//1.新規登録
	public void do1() {
		try {
			//ユーザーIDの入力
			System.out.println("ユーザーID：0以上の整数で正しく入力してください");
			String strId = scanner.nextLine();
	        System.out.println(strId);
			int userid = Integer.parseInt(strId);
			 //Integerにしたものがマイナス値だったら例外を投げる
			if(userid < 0 ) {
				throw new ArithmeticException("0未満のが入力されています");
			}

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

			//パスワードの入力
			System.out.println("パスワード");
			String password = scanner.nextLine();
			System.out.println("*************");
			if(password.length() < 8 || password.length() > 20) {
				throw new ToolongStringException ("正しくデータが入力されません");
			}

			//入力した情報を表示する⇒これで本当に登録してよいかを確認できるうようにいずれはしたい。
			System.out.println("あなたの登録した情報");
			System.out.println("ユーザーID:" + userid);
			System.out.println("ユーザー名:" + username);
			System.out.println("メールアドレス:" + usermail);
			System.out.println("電話番号:" + phone);
			System.out.println("パスワード" + "**************");

			//1.ユーザーの新規登録をするために必要なSQLの操作をするメソッドを呼び出す
			MySQLContent.newUser(userid, username, usermail, phone, password);

			//最初の操作に戻る
			select();
		}catch(Exception e) {
			System.out.println("データが正しく入力されていません。");
			wrongInput();
		}
	}

	//2.1人を選んで登録したデータを見る
	public void do2() {
		System.out.println("ユーザーIDを入力ください。");
		try {
			String strIndex = scanner.nextLine();
			int index = Integer.parseInt(strIndex);
			if(index < 0) {
				throw new Exception("マイナスの文字が入力");
			}
			//SQLの操作
			MySQLContent.showUser(index);
			//最初の操作に戻る
			select();
		}catch(Exception e) {
			System.out.println("正しくユーザーIDが入力されません");
			wrongInput();
		}
	}

	//3.登録した全員のデータを見る
	public void do3() {
		//SQLの操作
		MySQLContent.showAllUser();
		//最初の操作に戻る
		select();
	}

	//4.選択したユーザーのデータを削除する
	public void do4() {
		System.out.println("削除するユーザーのユーザーIDを指定してください");

		try {
		//何番目に削除したいデータがあるか入力してもらう
		String strDel = scanner.nextLine();
		int del = Integer.parseInt(strDel);

		//SQLの操作
		MySQLContent.delUser(del);

		//最初の操作に戻る
		select();

		//投げた例外をキャッチ
		}catch(ArrayIndexOutOfBoundsException e) {
			//マイナスの数字が入力されていた時のメソッドの呼び出し
			minus();

		}catch(IndexOutOfBoundsException e) {
			//最初の操作に戻る
			select();

		}catch(NumberFormatException e) {
			//文字を入力したら、NumberFormatExceptionが出た為こちらで例外をキャッチ
			//数字を打ってほしいのでもう一度最初から操作をやり直してもらう
			missNumber();
		}
	}

	//5.全員のデータを削除する
	public void do5() {
		//SQLの操作
		MySQLContent.delAllUser();
		//最初の操作に戻る
		select();
	}

	//6:クレジットカードの新規登録
	public void do6() {

				//ユーザーIDの入力
				System.out.println("ユーザーID：0以上の整数で正しく入力してください");
				String strId = scanner.nextLine();
		        System.out.println(strId);
				int userid = Integer.parseInt(strId);
				 //Integerにしたものがマイナス値だったら例外を投げる
				if(userid < 0 ) {
					throw new ArithmeticException("0未満のが入力されています");
				}

				//パスワードの入力
				System.out.println("パスワード");
				String password = scanner.nextLine();
				System.out.println("*************");
				if(password.length() < 8 || password.length() > 20) {
					throw new ArithmeticException ("正しくデータが入力されません");
				}

				try {
					//MySQL上にユーザーがあるか確認
					Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(ClassNotFoundException  e) {
					e.printStackTrace();
				}

				final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
				final String USERNAME = "root";
				final String PASS ="";

				Connection con = null;
				try {
					con = DriverManager.getConnection(URL, USERNAME, PASS);
					//SQL送信
					//ひな型
					PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
					//ひな型に値を流し込み
					pstmt.setInt(1, userid);
					//検索系SQL文を自動組み立て、送信
					ResultSet rs = pstmt.executeQuery();
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

					        //SQL操作でクレジットカードの情報を格納する
					        //ひな型
					        pstmt = con.prepareStatement("INSERT INTO credit VALUES(?,?,?,?)");
					        //ひな型に流し込む
					        pstmt.setInt(1, userid);
					        pstmt.setString(2, credit);
					        pstmt.setString(3, year);
					        pstmt.setString(4, month);
					        //更新系SQLの自動組み立て、送信
					        int r = pstmt.executeUpdate();
					        if(r != 0) {
					        	System.out.println("お客様のクレジットカードを登録しました。");
					        }else {
					        	System.out.println("お客様のクレジットカードは正しく登録されませんでした。");
					        }

							 //Integerにしたものがマイナス値だったら例外を投げる
							if(!(month.length() == 2)) {
								throw new ArithmeticException("0未満のが入力されています");
							}
						}catch(ArithmeticException e) {
							System.out.println("データが正しく入力されていません。");
							select();
						}catch(NumberFormatException e) {
							//マイナスの数字が入力されていたら
							missNumber();
						}
					}else {
						System.out.println("入力したユーザーはユーザー登録されていません。新規登録をしてください");
					}
				}catch(SQLException e){
					e.printStackTrace();
				}finally {
					if(con != null) {
						try {
							con.close();
							//最初の操作に戻る
							select();
						}catch(SQLException e) {
							e.printStackTrace();
						}
					}
				}
	}

	//7:ユーザーIDを指定してクレジットカードのデータを表示
	//7：ユーザーIDを指定してクレジットカードのデータを表示
	public void do7() {
		//ユーザーIDの入力⇒ユーザー情報があるかチェック
		System.out.println("ユーザーID：0以上の整数で正しく入力してください");
		String strId = scanner.nextLine();
        System.out.println(strId);
		int userid = Integer.parseInt(strId);
		 //Integerにしたものがマイナス値だったら例外を投げる
		if(userid < 0 ) {
			throw new ArithmeticException("0未満のが入力されています");
		}

		try {
			//MySQL上にユーザーがあるか確認
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException  e) {
			e.printStackTrace();
		}
		final String URL = "jdbc:mysql://127.0.0.1:3306/cloneamazon?serverTimezone=JST";
		final String USERNAME = "root";
		final String PASS ="";
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASS);
			//SQL送信
			//ひな型
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user WHERE userId = ?");
			//ひな型に値を流し込み
			pstmt.setInt(1, userid);
			//検索系SQL文を自動組み立て、送信
			ResultSet rs = pstmt.executeQuery();
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
					pstmt = con.prepareStatement("SELECT credit.userId, user.name, creditNum, year, month FROM credit JOIN user ON credit.userId = user.userId WHERE creditNum = ?" );
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
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
					//最初の操作に戻る
					select();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//0.プログラムを終了する

	//0.プログラムを終了する
	public static void do0() {
		System.out.println("プログラムを終了します。おつかれさまでした。");
	}

	//マイナスの数字が入力されていた時に呼び出しユーザーへ送るエラー文
	public void minus() {
		//ユーザーIDを登録する部分でマイナスの数字が入力されていたら、最初の指示選択コマンドを呼び出す
		System.out.println("           ");
		System.out.println("マイナスの文字が入力されています。");
		System.out.println("もう一度最初から操作をやり直してください。");
		System.out.println("           ");
		//最初の操作に戻る
		select();
	}

	//文字を入力してほしくない部分で文字が入力されたら呼び出しユーザーへ送るエラー文
	public void missNumber() {
		System.out.println("           ");
		System.out.println("文字が入力されています。");
		System.out.println("もう一度最初から操作をやり直してください。");
		System.out.println("           ");
		//最初の操作に戻る
		select();
	}
	//データが正しく入力されていないときに呼び出しユーザーへ送るエラー文
	public void wrongInput() {
		System.out.println("もう一度最初から操作をやり直してください。");
		System.out.println("           ");
		//最初の操作に戻る
		select();
	}

}
