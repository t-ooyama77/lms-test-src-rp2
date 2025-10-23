package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//トップページに遷移
		goTo("http://localhost:8080/lms");

		//正しい表示画面に遷移しているかを確認
		WebElement login = webDriver.findElement(By.tagName("h2"));
		assertEquals("ログイン", login.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//ログインIDを入力する
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		loginId.clear(); // 既存の値を消す（任意）
		loginId.sendKeys("StudentAA02"); //初回ログイン済みのログインID

		//ログインIDを入力する
		WebElement password = webDriver.findElement(By.name("password"));
		password.clear(); // 既存の値を消す（任意）
		password.sendKeys("studentA02"); //初回ログイン済みのpassword

		//ログインボタンを押下
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[type='submit']"));
		loginButton.click();

		//正しい表示画面に遷移しているかを確認
		WebElement loginSuccess = webDriver.findElement(By.tagName("small"));
		assertEquals("ようこそ受講生ＡＡ２さん", loginSuccess.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//「機能」リンクをクリックしてドロップダウンメニューを表示
		WebElement function = webDriver.findElement(By.linkText("機能"));
		function.click();

		//表示されたドロップダウンメニューから「ヘルプ」リンクをクリック
		WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));
		helpLink.click();

		//クリック後に「ヘルプ画面」に遷移しているかを確認
		WebElement transitionHelp = webDriver.findElement(By.tagName("h2"));
		assertEquals("ヘルプ", transitionHelp.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//「よくある質問」リンクをクリック
		WebElement FAQ = webDriver.findElement(By.linkText("よくある質問"));
		FAQ.click();

		//「よくある質問画面」は新しいタブで開くため、タブを移動する
		Object[] windowHandles = webDriver.getWindowHandles().toArray();//ブラウザで開いているタブの情報を取得
		webDriver.switchTo().window((String) windowHandles[1]);//新しいタブに切り替えている

		//クリック後に「よくある質問画面」に遷移しているかを確認
		WebElement transitionFAQ = webDriver.findElement(By.tagName("h2"));
		assertEquals("よくある質問", transitionFAQ.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//キーワード検索の入力フォームにキーワードを（キャンセル）を入力する
		WebElement keyword = webDriver.findElement(By.name("keyword"));
		keyword.clear(); //これまでの入力内容を消去する
		keyword.sendKeys("キャンセル");

		//「カテゴリー検索」の下に表示された【研修関係】のリンクをクリックする
		WebElement categorySearch = webDriver.findElement(By.linkText("【研修関係】"));
		categorySearch.click();

		//「カテゴリー検索」リンクを押下後に検索結果の質問が表示されているかを確認
		WebElement showQuestion = webDriver.findElement(By.className("text-primary"));
		assertEquals("Q.", showQuestion.getText());

		//検索結果が見えるように画面を下にスクロールする
		scrollTo("165");

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//「検索結果」に表示されている質問をクリック
		WebElement question = webDriver.findElement(By.className("text-primary"));
		question.click();

		//クリック後に質問に対する回答が表示されているかを確認
		WebElement showAnswer = webDriver.findElement(By.className("text-warning"));
		assertEquals("A.", showAnswer.getText());

		//検索結果が見えるように画面を下にスクロールする
		scrollTo("300");

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

}
