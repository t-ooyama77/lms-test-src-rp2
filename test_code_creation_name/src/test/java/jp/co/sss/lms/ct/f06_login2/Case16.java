package jp.co.sss.lms.ct.f06_login2;

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

import jp.co.sss.lms.ct.util.ConstantsMessages;

/**
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		//ログインIDを入力する
		WebElement loginId = webDriver.findElement(By.name("loginId"));
		loginId.clear(); // 既存の値を消す（任意）
		loginId.sendKeys("StudentAB04"); //初回ログインしていないユーザーのログインID

		//ログインIDを入力する
		WebElement password = webDriver.findElement(By.name("password"));
		password.clear(); // 既存の値を消す（任意）
		password.sendKeys("StudentAB04"); //初回ログインしていないユーザーのpassword

		//ログインボタンを押下
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[type='submit']"));
		loginButton.click();

		//利用規約画面に遷移しているかを確認
		WebElement loginSuccess = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2"));
		assertEquals("利用規約", loginSuccess.getText());

		//画面をキャプチャして保存する
		//getEvidence(new Object() {
		//});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		//下にスクロールする
		scrollBy("500");
		//「同意します」チェックボックスにチェックを入れる(クリックする)
		WebElement checkbox = webDriver.findElement(By.xpath("//input[@type='checkbox']"));
		checkbox.click();

		//「次へ」ボタンを押下する
		WebElement next = webDriver.findElement(By.xpath("//button[@type='submit']"));
		next.click();

		//パスワード変更画面に遷移しているかを確認する
		WebElement passwordChange = webDriver.findElement(By.tagName("h2"));
		assertEquals("パスワード変更", passwordChange.getText());

		//画面をキャプチャして保存する
		//getEvidence(new Object() {
		//});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 現在のパスワード、変更パスワードを未入力で「変更」ボタン押下")
	void test04() throws Exception {
		//「変更」ボタンをクリックする
		WebElement passwordChangeButton = webDriver.findElement(By.xpath("//button[@type='submit']"));
		passwordChangeButton.click();

		//変更確認ダイアログで「変更」ボタンをクリックする
		visibilityTimeout(By.xpath("//button[@id='upd-btn']"), 2);
		WebElement changeconfirmDialog = webDriver.findElement(By.xpath("//button[@id='upd-btn']"));
		changeconfirmDialog.click();

		Thread.sleep(2000);

		//「現在のパスワード」のエラーメッセージを取得
		WebElement currentPasswordError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[1]/div/ul/li/span"));

		//「変更パスワード」のエラーメッセージを取得
		WebElement passwordError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		String actual = passwordError.getText().replaceAll("\\s+", "");
		String text = actual;
		int start = text.indexOf("パスワードは必須です。");
		String extracted = "";
		if (start != -1) {
			extracted = text.substring(start, start + "パスワードは必須です。".length());
		}

		int startSplit = text.indexOf("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。");
		String extractedSplit = "";
		if (startSplit != -1) {
			extractedSplit = text.substring(startSplit,
					startSplit + "「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。".length());
		}

		//「確認パスワード」のエラーメッセージを取得
		WebElement passwordConfirmError = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[3]/div/ul/li/span"));

		//エラーメッセージを照合する
		assertEquals("現在のパスワードは必須です。", currentPasswordError.getText());
		assertEquals("パスワードは必須です。", extracted);
		assertEquals("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。", extractedSplit);
		assertEquals("確認パスワードは必須です。", passwordConfirmError.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		// 「変更パスワードのに21文字入力する」
		WebElement input21 = webDriver.findElement(By.xpath("//input[@name='password']"));
		input21.sendKeys(ConstantsMessages.len21);

		//「変更」ボタンをクリックする
		scrollBy("500");
		WebElement passwordChangeButton = webDriver.findElement(By.xpath("//button[@type='submit']"));
		passwordChangeButton.click();

		//変更確認ダイアログで「変更」ボタンをクリックする
		visibilityTimeout(By.xpath("//button[@id='upd-btn']"), 2);
		WebElement changeconfirmDialog = webDriver.findElement(By.xpath("//button[@id='upd-btn']"));
		changeconfirmDialog.click();

		//エラーメッセージを取得
		WebElement errorMessage = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertEquals("パスワードの長さが最大値(20)を超えています。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		// 「変更パスワード」にポリシーに合わない変更パスワードを入力
		WebElement notPolicyPasword = webDriver.findElement(By.xpath("//input[@name='password']"));
		notPolicyPasword.sendKeys("studentaaa");

		//「変更」ボタンをクリックする
		scrollBy("500");
		WebElement passwordChangeButton = webDriver.findElement(By.xpath("//button[@type='submit']"));
		passwordChangeButton.click();

		//変更確認ダイアログで「変更」ボタンをクリックする
		visibilityTimeout(By.xpath("//button[@id='upd-btn']"), 2);
		WebElement changeconfirmDialog = webDriver.findElement(By.xpath("//button[@id='upd-btn']"));
		changeconfirmDialog.click();

		//エラーメッセージを取得
		WebElement errorMessage = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertEquals("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、"
				+ "数字を含めた8～20文字を入力してください。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		//現在のパスワードに正確なパスワードを入力
		WebElement currentPassword = webDriver.findElement(By.xpath("//input[@name='currentPassword']"));
		currentPassword.sendKeys("StudentAB04");

		// 「変更パスワード」に新しいパスワードを入力
		WebElement password = webDriver.findElement(By.xpath("//input[@name='password']"));
		password.sendKeys("studentAB04");

		//「確認パスワード」に変更パスワードとは違うパスワードを入力する
		WebElement passwordConfirm = webDriver.findElement(By.xpath("//input[@name='passwordConfirm']"));
		passwordConfirm.sendKeys("studentB10");

		//「変更」ボタンをクリックする
		scrollBy("500");
		WebElement passwordChangeButton = webDriver.findElement(By.xpath("//button[@type='submit']"));
		passwordChangeButton.click();

		//変更確認ダイアログで「変更」ボタンをクリックする
		visibilityTimeout(By.xpath("//button[@id='upd-btn']"), 2);
		WebElement changeconfirmDialog = webDriver.findElement(By.xpath("//button[@id='upd-btn']"));
		changeconfirmDialog.click();//変更確認ダイアログで「変更」ボタンをクリックする

		//エラーメッセージを取得
		//WebElement errorMessage = webDriver.findElement(By.xpath("//span[contains(@class, 'error')]"));
		visibilityTimeout(By.className("error"), 2);
		WebElement errorMessage = webDriver
				.findElement(By.xpath("//*[@id='upd-form']/div[1]/fieldset/div[2]/div/ul/li/span"));
		assertEquals("パスワードと確認パスワードが一致しません。", errorMessage.getAttribute("textContent"));

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

}
