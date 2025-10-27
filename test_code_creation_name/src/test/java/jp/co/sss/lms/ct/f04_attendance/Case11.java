package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.openqa.selenium.support.ui.Select;

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

		// アラートが出ているか確認して処理
		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept(); // OKボタンを押す
		} catch (NoAlertPresentException e) {
			// アラートがなければ何もしない
		}

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
	void test05() throws InterruptedException {
		//テーブルのtdody要素を取得する
		WebElement tbody = webDriver.findElement(By.tagName("tbody"));
		//trタグを取得する
		List<WebElement> trTags = tbody.findElements(By.tagName("tr"));

		// yyyy年MM月dd日 の部分だけを抜き出す正規表現
		Pattern datePattern = Pattern.compile("(\\d{4}年\\d{2}月\\d{2}日)");

		//日付の比較の準備
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		LocalDate today = LocalDate.now();//今日の日付

		for (WebElement tr : trTags) {
			List<WebElement> tdTags = tr.findElements(By.tagName("td"));
			Matcher matcher = datePattern.matcher(tdTags.get(0).getText());
			if (matcher.find()) {
				String dateString = matcher.group(1);
				System.out.println(dateString);

				// 文字列をLocalDateに変換
				LocalDate date = LocalDate.parse(dateString, formatter);

				// 日付を比較して本日とそれ以前の日付には「定時」ボタンをクリックする
				if (date.isBefore(today) || date.equals(today)) {
					WebElement button = tdTags.get(2).findElement(By.tagName("button"));
					//画面を少し下にずらしてクリック
					scrollBy("50");
					button.click();

					//一秒待つ
					Thread.sleep(1000);
				}
			}

		}

		/*最初の日付の出勤が9:00 退勤が18:00 であるかを確認する*/
		//要素を取得
		WebElement startHour = webDriver.findElement(By.xpath("//select[@id='startHour0']"));
		WebElement startMinute = webDriver.findElement(By.xpath("//select[@id='startMinute0']"));
		WebElement endHour = webDriver.findElement(By.xpath("//select[@id='endHour0']"));
		WebElement endMinute = webDriver.findElement(By.xpath("//select[@id='endMinute0']"));

		// Selectクラスを使って操作
		Select selectStartHour = new Select(startHour);
		Select selectStartMinute = new Select(startMinute);
		Select selectEndHour = new Select(endHour);
		Select selectEndMinute = new Select(endMinute);

		// 現在選択されているオプションのテキストを取得
		String startHourStr = selectStartHour.getFirstSelectedOption().getText();
		String startMinuteStr = selectStartMinute.getFirstSelectedOption().getText();
		String endHourStr = selectEndHour.getFirstSelectedOption().getText();
		String endMinuteStr = selectEndMinute.getFirstSelectedOption().getText();

		assertEquals("09", startHourStr);
		assertEquals("00", startMinuteStr);
		assertEquals("18", endHourStr);
		assertEquals("00", endMinuteStr);

		//クリックできるように画面をスクロールする
		scrollTo("1000");

		//「更新」ボタンを押下して勤怠管理画面に遷移する
		WebElement update = webDriver.findElement(By.name("complete"));
		update.click();

		//一秒待つ
		Thread.sleep(1000);

		// アラートが出ているか確認して処理
		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept(); // OKボタンを押す
		} catch (NoAlertPresentException e) {
			// アラートがなければ何もしない
		}

		//正しい表示画面に遷移しているかを確認
		WebElement attendanceManagement = webDriver.findElement(By.tagName("h2"));
		assertEquals("勤怠管理", attendanceManagement.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

	}

}
