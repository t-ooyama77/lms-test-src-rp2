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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
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
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//クリックできるように画面をスクロールする
		scrollTo("1000");
		//週報の「詳細」ボタンを押下する(上から4番目の「修正する」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='修正する']"));
		detailButtons.get(3).click();

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() throws InterruptedException {
		//「学習項目」を未入力の状態にする
		WebElement learning = webDriver.findElement(By.name("intFieldNameArray[0]"));
		learning.clear();

		//クリックできるように画面をスクロールする
		scrollTo("500");

		//「提出する」ボタンをクリック
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		WebElement errorMessage = webDriver.findElement(By.xpath("//p/span[text()='* 理解度を入力した場合は、学習項目は必須です。']"));

		assertEquals("* 理解度を入力した場合は、学習項目は必須です。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//ユーザー詳細画面に戻る
		webDriver.navigate().back();
		webDriver.navigate().back();
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		//クリックできるように画面をスクロールする
		scrollTo("1000");
		//週報の「詳細」ボタンを押下する(上から4番目の「修正する」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='修正する']"));
		detailButtons.get(3).click();

		//「理解度」の項目のドロップダウンリストを取得する
		WebElement levelOfUnderstanding = webDriver.findElement(By.name("intFieldValueArray[0]"));
		Select selectLevelOfUnderstanding = new Select(levelOfUnderstanding);

		//ドロップダウンリストの最初の要素である「空白」を選択する
		selectLevelOfUnderstanding.selectByIndex(0);

		//クリックできるように画面をスクロールする
		scrollTo("500");

		//「提出する」ボタンをクリック
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		//表示されたエラー情報が正しいかを確認
		WebElement errorMessage = webDriver.findElement(By.xpath("//p/span[text()='* 学習項目を入力した場合は、理解度は必須です。']"));
		assertEquals("* 学習項目を入力した場合は、理解度は必須です。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//ユーザー詳細画面に戻る
		webDriver.navigate().back();
		webDriver.navigate().back();
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//クリックできるように画面をスクロールする
		scrollTo("1000");
		//週報の「詳細」ボタンを押下する(上から4番目の「修正する」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='修正する']"));
		detailButtons.get(3).click();

		//「目標の達成度」の項目に数値以外を入力する
		WebElement goalAttainmentLevel = webDriver.findElement(By.name("contentArray[0]"));
		goalAttainmentLevel.clear();
		goalAttainmentLevel.sendKeys("aaa");

		//クリックできるように画面をスクロールする
		scrollTo("500");

		//「提出する」ボタンをクリック
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		//表示されたエラー情報が正しいかを確認
		//エラー情報が出ていない

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//ユーザー詳細画面に戻る
		webDriver.navigate().back();
		webDriver.navigate().back();
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//クリックできるように画面をスクロールする
		scrollTo("1000");
		//週報の「詳細」ボタンを押下する(上から4番目の「修正する」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='修正する']"));
		detailButtons.get(3).click();

		//「目標の達成度」の項目に1～10の範囲以外の数値を入力する
		WebElement goalAttainmentLevel = webDriver.findElement(By.name("contentArray[0]"));
		goalAttainmentLevel.clear();
		goalAttainmentLevel.sendKeys("11");

		//クリックできるように画面をスクロールする
		scrollTo("500");

		//「提出する」ボタンをクリック
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		//表示されたエラー情報が正しいかを確認
		//エラー情報が出ていない

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//ユーザー詳細画面に戻る
		webDriver.navigate().back();
		webDriver.navigate().back();
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//クリックできるように画面をスクロールする
		scrollTo("1000");
		//週報の「詳細」ボタンを押下する(上から4番目の「修正する」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='修正する']"));
		detailButtons.get(3).click();

		//「所感」の項目を未入力にする
		WebElement impression = webDriver.findElement(By.name("contentArray[1]"));
		impression.clear();

		//クリックできるように画面をスクロールする
		scrollTo("500");

		//「提出する」ボタンをクリック
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		//表示されたエラー情報が正しいかを確認
		//エラー情報が出ていない

		//「所感」の項目が表示されるように画面をスクロールする
		scrollBy("1000");

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//ユーザー詳細画面に戻る
		webDriver.navigate().back();
		webDriver.navigate().back();
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() throws Exception {
		//クリックできるように画面をスクロールする
		scrollTo("1000");
		//週報の「詳細」ボタンを押下する(上から4番目の「修正する」ボタンを押す)
		List<WebElement> detailButtons = webDriver.findElements(By.xpath("//input[@value='修正する']"));
		detailButtons.get(3).click();

		//「一週間の振り返り」の項目に2001文字以上の文章を入力する
		WebElement weeklyReview = webDriver.findElement(By.name("contentArray[2]"));
		weeklyReview.clear();

		weeklyReview.sendKeys(
				"ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");

		weeklyReview.sendKeys(
				"ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");

		weeklyReview.sendKeys(
				"ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");

		weeklyReview.sendKeys(
				"ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ");

		weeklyReview.sendKeys(
				"ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ\r\n"
						+ "ああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああああい"
						+ Keys.ENTER);

		//「提出する」ボタンをクリック
		WebElement reportSubmit = webDriver.findElement(By.className("btn-primary"));
		reportSubmit.click();

		//一秒待つ
		Thread.sleep(1000);

		//画面を下にスクロールする
		scrollTo("2000");

		//表示されたエラー情報が正しいかを確認
		//エラー情報が出ていない

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

	}

}
