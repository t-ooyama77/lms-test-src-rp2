package jp.co.sss.lms.ct.f04_attendance;

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
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//「勤怠」リンクをクリックする
		WebElement attendance = webDriver.findElement(By.linkText("勤怠"));
		attendance.click();

		//正しい表示画面に遷移しているかを確認
		WebElement attendanceManagement = webDriver.findElement(By.tagName("h2"));
		assertEquals("勤怠管理", attendanceManagement.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		//「勤怠情報を直接編集する」リンクをクリックする
		WebElement attendanceEdit = webDriver.findElement(By.linkText("勤怠情報を直接編集する"));
		attendanceEdit.click();

		//正しい表示画面に遷移しているかを確認
		WebElement attendanceChange = webDriver.findElement(By.tagName("h2"));
		assertEquals("勤怠管理", attendanceChange.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		// TODO ここに追加
	}

}
