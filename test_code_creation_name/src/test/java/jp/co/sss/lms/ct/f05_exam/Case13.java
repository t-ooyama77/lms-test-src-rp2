package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//「試験有」になっているセクションの「詳細」ボタンを押下する
		WebElement tbody = webDriver.findElement(By.tagName("tbody"));
		List<WebElement> trTags = tbody.findElements(By.tagName("tr"));

		for (WebElement tr : trTags) {
			List<WebElement> tdTags = tr.findElements(By.tagName("td"));
			String exam = tdTags.get(3).getText();
			if (exam.equals("試験有")) {
				WebElement detailButton = tdTags.get(4).findElement(By.xpath(".//input[@value='詳細']"));
				detailButton.click();
			}
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
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		//「本日の試験」欄の「詳細」ボタンを押下する
		WebElement detailButton = webDriver.findElement(By.xpath("//input[@value='詳細']"));
		detailButton.click();

		//試験開始画面に遷移しているかを確認する
		WebElement exam = webDriver.findElement(By.xpath("//li[@class='active']"));
		assertEquals("試験", exam.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		//「本日の試験」欄の「試験を開始する」ボタンを押下する
		WebElement startExam = webDriver.findElement(By.xpath("//input[@value='試験を開始する']"));
		startExam.click();

		//試験問題画面に遷移しているかを確認する
		WebElement exam = webDriver.findElement(By.xpath("//div[@class='panel-heading']"));
		assertEquals("第1問 【】", exam.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() throws Exception {
		//画面を下にスクロールする
		scrollTo("4500");

		//何も入力せずに「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移する
		WebElement examConfirm = webDriver.findElement(By.xpath("//input[@type='submit']"));
		examConfirm.click();

		//試験回答確認画面に遷移しているかを確認する
		WebElement answerConfirm = webDriver.findElement(By.xpath("//h2//small"));
		String text = answerConfirm.getText();
		String totalAnswers = text.split("：")[0];
		assertEquals("回答数", totalAnswers);

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		//画面を下にスクロールする
		scrollTo("4500");

		//「回答を送信する」ボタンを押下
		WebElement submitAnswer = webDriver.findElement(By.xpath("//button[@id='sendButton']"));
		submitAnswer.click();

		//「回答を送信しますか？」のアラートが出るのでエンターキーを押す
		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept(); // OKボタンを押す
		} catch (NoAlertPresentException e) {
			// アラートがなければ何もしない
		}

		//試験結果画面に遷移しているかを確認する
		WebElement examResult = webDriver.findElement(By.xpath("//h2//small"));
		String text = examResult.getText();
		String examResultText = text.split("：")[0];
		assertEquals("あなたのスコア", examResultText);

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		//画面を下にスクロールする
		scrollTo("5000");

		//「戻る」ボタンを押下する
		WebElement back = webDriver.findElement(By.xpath("//input[@type='submit']"));
		back.click();

		//試験開始画面のtable要素を取得
		List<WebElement> tables = webDriver.findElements(By.tagName("table"));
		//2番目のテーブルのtbody要素を取得
		WebElement tbody = tables.get(1).findElement(By.tagName("tbody"));
		//trタグをすべて取得
		List<WebElement> trTags = tbody.findElements(By.tagName("tr"));
		//最後に生成されたtrタグ(最新の試験結果情報)のすべてのtdタグを取得する
		List<WebElement> tdTags = trTags.get(trTags.size() - 1).findElements(By.tagName("td"));
		//最新の試験結果の日時を取得する
		String newExamResultDate = tdTags.get(2).getText();

		//この試験が今行われたものかを確認する(分単位まで照合する)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分ss秒");//フォーマット
		LocalDateTime newExamResultDateTime = LocalDateTime.parse(newExamResultDate, formatter);//LocalDateTime型に変換

		// 現在時刻を取得し、分単位に切り捨て
		LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
		LocalDateTime newExamResultDateMinute = newExamResultDateTime.truncatedTo(ChronoUnit.MINUTES);

		// 分単位で一致するかを検証
		assertEquals(now, newExamResultDateMinute);

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}
}
