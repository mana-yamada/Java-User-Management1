package user_management_2;
//varcher(○)と指定されているのに指定した文字数より長い文字列が入力されたときに投げる例外クラス
public class ToolongStringException extends Exception {

	//エラーメッセージを受け取るコンストラクタ
	public ToolongStringException(String msg) {
		super(msg);
	}
}
