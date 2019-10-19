package user_management_1;

import java.util.Scanner;

import user_management_2.ToolongStringException;

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
		System.out.println("0:プログラムを終了する");

		System.out.println("0～5までの数字で上記の操作を選んでください");

			//0から5までの整数を入力してもらう
			String strSelexct = scanner.nextLine();
		try {
			int sn =Integer.parseInt(strSelexct);

			//0未満,5より大きい数字が入力されたら、例外を投げる
			if(sn < 0 || sn > 5) {
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

	//6.プログラムを終了する
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
