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

/**
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
		loginId.sendKeys("StudentAB03"); //初回ログインしていないユーザーのログインID

		//ログインIDを入力する
		WebElement password = webDriver.findElement(By.name("password"));
		password.clear(); // 既存の値を消す（任意）
		password.sendKeys("StudentAB03"); //初回ログインしていないユーザーのpassword

		//ログインボタンを押下
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[type='submit']"));
		loginButton.click();

		//利用規約画面に遷移しているかを確認
		WebElement loginSuccess = webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2"));
		assertEquals("利用規約", loginSuccess.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
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
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		//現在のパスワードに正確なパスワードを入力
		WebElement currentPassword = webDriver.findElement(By.xpath("//input[@name='currentPassword']"));
		currentPassword.sendKeys("StudentAB03");

		// 「変更パスワード」に新しいパスワードを入力
		WebElement password = webDriver.findElement(By.xpath("//input[@name='password']"));
		password.sendKeys("studentAB03");

		//「確認パスワード」に変更パスワードと同じパスワードを入力する
		WebElement passwordConfirm = webDriver.findElement(By.xpath("//input[@name='passwordConfirm']"));
		passwordConfirm.sendKeys("studentAB03");

		//「変更」ボタンをクリックする
		scrollBy("500");
		WebElement passwordChangeButton = webDriver.findElement(By.xpath("//button[@type='submit']"));
		passwordChangeButton.click();

		//変更確認ダイアログで「変更」ボタンをクリックする
		visibilityTimeout(By.xpath("//button[@id='upd-btn']"), 2);
		WebElement changeconfirmDialog = webDriver.findElement(By.xpath("//button[@id='upd-btn']"));
		changeconfirmDialog.click();//変更確認ダイアログで「変更」ボタンをクリックする

		//コース詳細画面に遷移していることを確認する
		WebElement courseDetail = webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/ol/li"));

		assertEquals("コース詳細", courseDetail.getAttribute("textContent"));

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

}
