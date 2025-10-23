package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//日報が未提出のセクションの「詳細」ボタンを押下する
		WebElement detail = webDriver
				.findElement(By.xpath("//span[text()='未提出']/ancestor::div//input[@type='submit']"));
		detail.click();

		//セクション詳細画面に遷移しているかを確認
		WebElement section = webDriver.findElement(By.linkText("コース詳細"));//コース詳細画面へのリンク
		assertEquals("コース詳細", section.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//「提出する」ボタンを押下してレポート登録画面に遷移する
		WebElement submit = webDriver.findElement(By.xpath("//input[@value='日報【デモ】を提出する']"));
		submit.click();

		//レポート登録画面に遷移しているかを確認
		WebElement report = webDriver.findElement(By.tagName("legend"));
		assertEquals("報告レポート", report.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//報告内容を入力する
		WebElement reportInput = webDriver.findElement(By.tagName("textarea"));
		reportInput.clear();
		reportInput.sendKeys("日報を提出します。");

		//「提出する」ボタンを押下してセクション詳細画面に遷移する
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		//セクション詳細画面に遷移しているかを確認する

		//「日報を提出する」ボタンの表記が「提出済み日報を確認する」に変化しているかを確認する
		WebElement buttonChange = webDriver.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"));
		assertEquals("提出済み日報【デモ】を確認する", buttonChange.getAttribute("value"));

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}
}
