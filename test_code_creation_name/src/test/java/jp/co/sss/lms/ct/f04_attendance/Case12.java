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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import jp.co.sss.lms.ct.util.ConstantsMessages;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出勤の（時）と（分）のいずれかが空白")
	void test05() {
		//一番上の出勤時間の(時)の欄を空白にする
		WebElement startHour = webDriver.findElement(By.xpath("//select[@id='startHour0']"));
		Select selectStartHour = new Select(startHour);
		selectStartHour.selectByValue("");

		//クリックできるように画面をスクロールする
		scrollTo("1000");

		//更新するボタンを押下する
		WebElement update = webDriver.findElement(By.name("complete"));
		update.click();

		//ポップアップが表示されたら「OK」ボタンを押す
		Alert alert = webDriver.switchTo().alert();
		alert.accept(); // OKボタンを押す

		//正しいエラーメッセージが表示されているかを確認
		WebElement errorMessage = webDriver.findElement(By.className("error"));
		assertEquals("* 出勤時間が正しく入力されていません。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//一番上の出勤時間の(時)の欄(エラー状態)に「09」を入力する
		WebElement startHourError = webDriver.findElement(By.xpath("//select[@id='startHour0']"));
		Select selectStartHourError = new Select(startHourError);
		selectStartHourError.selectByValue("9");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：退勤の（時）と（分）のいずれかが空白")
	void test06() {
		//一番上の退勤時間の(時)の欄を空白にする
		WebElement endHour = webDriver.findElement(By.xpath("//select[@id='endHour0']"));
		Select selectEndHour = new Select(endHour);
		selectEndHour.selectByValue("");

		//クリックできるように画面をスクロールする
		scrollTo("1000");

		//更新するボタンを押下する
		WebElement update = webDriver.findElement(By.name("complete"));
		update.click();

		//ポップアップが表示されたら「OK」ボタンを押す
		Alert alert = webDriver.switchTo().alert();
		alert.accept(); // OKボタンを押す

		//正しいエラーメッセージが表示されているかを確認
		WebElement errorMessage = webDriver.findElement(By.className("error"));
		assertEquals("* 退勤時間が正しく入力されていません。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//一番上の退勤時間の(時)の欄(エラー状態)に「18」を入力する
		WebElement endHourError = webDriver.findElement(By.xpath("//select[@id='endHour0']"));
		Select selectEndHourError = new Select(endHourError);
		selectEndHourError.selectByValue("18");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test07() {
		//一番上の出勤時間の(時)と(分)の欄を空白にする
		WebElement startHour = webDriver.findElement(By.xpath("//select[@id='startHour0']"));//時
		Select selectStartHour = new Select(startHour);
		selectStartHour.selectByValue("");

		WebElement startMinute = webDriver.findElement(By.xpath("//select[@id='startMinute0']"));//分
		Select selectStartMinute = new Select(startMinute);
		selectStartMinute.selectByValue("");

		//クリックできるように画面をスクロールする
		scrollTo("1000");

		//更新するボタンを押下する
		WebElement update = webDriver.findElement(By.name("complete"));
		update.click();

		//ポップアップが表示されたら「OK」ボタンを押す
		Alert alert = webDriver.switchTo().alert();
		alert.accept(); // OKボタンを押す

		//正しいエラーメッセージが表示されているかを確認
		WebElement errorMessage = webDriver.findElement(By.className("error"));
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//一番上の出勤時間の(時)の欄(エラー状態)に「09」を入力する
		WebElement startHourError = webDriver.findElement(By.xpath("//select[@id='startHour0']"));
		Select selectStartHourError = new Select(startHourError);
		selectStartHourError.selectByValue("9");

		//一番上の出勤時間の(分)の欄(エラー状態)に「00」を入力する
		WebElement startMinuteError = webDriver.findElement(By.xpath("//select[@id='startMinute0']"));
		Select selectStartMinuteError = new Select(startMinuteError);
		selectStartMinuteError.selectByValue("0");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test08() {
		//一番上の出勤時間の(時)に20,(分)に00を入力する(退勤時間は18：00が入力済み)
		WebElement startHour = webDriver.findElement(By.xpath("//select[@id='startHour0']"));//時
		Select selectStartHour = new Select(startHour);
		selectStartHour.selectByValue("20");

		WebElement startMinute = webDriver.findElement(By.xpath("//select[@id='startMinute0']"));//分
		Select selectStartMinute = new Select(startMinute);
		selectStartMinute.selectByValue("0");

		//クリックできるように画面をスクロールする
		scrollTo("1000");

		//更新するボタンを押下する
		WebElement update = webDriver.findElement(By.name("complete"));
		update.click();

		//ポップアップが表示されたら「OK」ボタンを押す
		Alert alert = webDriver.switchTo().alert();
		alert.accept(); // OKボタンを押す

		//正しいエラーメッセージが表示されているかを確認
		WebElement errorMessage = webDriver.findElement(By.className("error"));
		assertEquals("* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//一番上の出勤時間の(時)の欄(エラー状態)に「09」を入力する
		WebElement startHourError = webDriver.findElement(By.xpath("//select[@id='startHour0']"));
		Select selectStartHourError = new Select(startHourError);
		selectStartHourError.selectByValue("9");

		//一番上の出勤時間の(分)の欄(エラー状態)に「00」を入力する
		WebElement startMinuteError = webDriver.findElement(By.xpath("//select[@id='startMinute0']"));
		Select selectStartMinuteError = new Select(startMinuteError);
		selectStartMinuteError.selectByValue("0");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test09() {
		//一番上の出勤時間の(時)に13,(分)に00を入力する(退勤時間は18：00が入力済み)
		WebElement startHour = webDriver.findElement(By.xpath("//select[@id='startHour0']"));//時
		Select selectStartHour = new Select(startHour);
		selectStartHour.selectByValue("13");

		WebElement startMinute = webDriver.findElement(By.xpath("//select[@id='startMinute0']"));//分
		Select selectStartMinute = new Select(startMinute);
		selectStartMinute.selectByValue("0");

		//「中抜け時間」に「6時間」を入力する
		WebElement blankTime = webDriver.findElement(By.xpath("//select[@name='attendanceList[0].blankTime']"));
		Select selectBlankTime = new Select(blankTime);
		selectBlankTime.selectByValue("360");

		//クリックできるように画面をスクロールする
		scrollTo("1000");

		//更新するボタンを押下する
		WebElement update = webDriver.findElement(By.name("complete"));
		update.click();

		//ポップアップが表示されたら「OK」ボタンを押す
		Alert alert = webDriver.switchTo().alert();
		alert.accept(); // OKボタンを押す

		//正しいエラーメッセージが表示されているかを確認
		WebElement errorMessage = webDriver.findElement(By.className("error"));
		assertEquals("* 中抜け時間が勤務時間を超えています。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});

		//一番上の出勤時間の(時)の欄(エラー状態)に「09」を入力する
		WebElement startHourError = webDriver.findElement(By.xpath("//select[@id='startHour0']"));
		Select selectStartHourError = new Select(startHourError);
		selectStartHourError.selectByValue("9");

		//一番上の出勤時間の(分)の欄(エラー状態)に「00」を入力する
		WebElement startMinuteError = webDriver.findElement(By.xpath("//select[@id='startMinute0']"));
		Select selectStartMinuteError = new Select(startMinuteError);
		selectStartMinuteError.selectByValue("0");

		//「中抜け時間」を空欄にする
		WebElement blankTimeError = webDriver.findElement(By.xpath("//select[@name='attendanceList[0].blankTime']"));
		Select selectBlankTimeError = new Select(blankTimeError);
		selectBlankTimeError.selectByValue("");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正してエラー表示：備考が100文字超")
	void test10() {
		//「備考」欄に101文字を入力する
		WebElement notes = webDriver.findElement(By.xpath("//input[@name='attendanceList[0].note']"));
		notes.clear();
		notes.sendKeys(ConstantsMessages.len101);

		//クリックできるように画面をスクロールする
		scrollTo("1000");

		//更新するボタンを押下する
		WebElement update = webDriver.findElement(By.name("complete"));
		update.click();

		//ポップアップが表示されたら「OK」ボタンを押す
		Alert alert = webDriver.switchTo().alert();
		alert.accept(); // OKボタンを押す

		//正しいエラーメッセージが表示されているかを確認
		WebElement errorMessage = webDriver.findElement(By.className("error"));
		assertEquals("* 備考の長さが最大値(100)を超えています。", errorMessage.getText());

		//画面をキャプチャして保存する
		getEvidence(new Object() {
		});
	}

}
