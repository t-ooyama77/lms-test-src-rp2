package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//週報が提出済みのセクションの「詳細」ボタンを押下する(上から2番目の「詳細」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='詳細']"));
		if (detailButtons.size() > 1) {
			detailButtons.get(1).click();
		}

		//セクション詳細画面に遷移しているかを確認
		WebElement section = webDriver.findElement(By.linkText("コース詳細"));//コース詳細画面へのリンク
		assertEquals("コース詳細", section.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//クリックできるように画面をスクロールする
		scrollTo("500");
		//「提出済み週報【デモ】を確認する」ボタンを押下してレポート登録画面に遷移する
		WebElement submit = webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']"));
		submit.click();

		//レポート登録画面に遷移しているかを確認
		WebElement report = webDriver.findElement(By.tagName("h2"));
		String reportTitleString = report.getText();
		String reportTitle = reportTitleString.split(" ")[0];
		assertEquals("週報【デモ】", reportTitle);

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() throws Exception {
		//入力できるように画面をスクロールする
		scrollTo("500");
		//週報の「一週間の振り返り」欄の入力内容をを修正する
		WebElement reportInput = webDriver.findElement(By.name("contentArray[2]"));
		reportInput.clear();
		reportInput.sendKeys("毎日予習復習を行ってがんばります！");

		//「提出する」ボタンを押下してセクション詳細画面に遷移する
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		//セクション詳細画面に遷移したことを確認する
		WebElement section = webDriver.findElement(By.tagName("h2"));
		String sectionTitleString = section.getText();
		String sectionTitle = sectionTitleString.split(" ")[0];
		assertEquals("アルゴリズム、フローチャート", sectionTitle);

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		//ユーザー名のリンクからユーザー詳細画面に遷移する
		WebElement userName = webDriver.findElement(By.xpath("//a[small[text()='ようこそ受講生ＡＡ２さん']]"));
		userName.click();

		//ユーザー詳細画面に遷移しているかを確認する
		WebElement userDetail = webDriver.findElement(By.tagName("h2"));
		assertEquals("ユーザー詳細", userDetail.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() throws InterruptedException {
		//クリックできるように画面をスクロールする
		scrollTo("1000");
		//週報の「詳細」ボタンを押下する(上から4番目の「詳細」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='詳細']"));
		detailButtons.get(3).click();

		//一秒待つ
		Thread.sleep(1000);

		//表示された週報の内容が修正した内容になっているかを確認する
		List<WebElement> tables = webDriver.findElements(By.tagName("table"));
		List<WebElement> tds = tables.get(2).findElements(By.tagName("td"));
		WebElement weeklyReview = tds.get(2);

		assertEquals("毎日予習復習を行ってがんばります！", weeklyReview.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

}
