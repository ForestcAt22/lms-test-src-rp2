package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.closeDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.createDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.visibilityTimeout;
import static jp.co.sss.lms.ct.util.WebDriverUtils.webDriver;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	void test01() throws IOException {
		//トップページへアクセス
		webDriver.get("http://localhost:8080/lms");
		//URLでログイン画面へ遷移か検証
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		//ログインID、パスワード、ログインボタン検証
		assertTrue(webDriver.findElement(By.id("loginId")).isDisplayed());
		assertTrue(webDriver.findElement(By.id("password")).isDisplayed());
		assertTrue(webDriver.findElement(By.xpath("//input[@value='ログイン']")).isDisplayed());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C10_T01_top_screen.png"));
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws IOException {
		//DBに登録されているユーザ情報を入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudenTAA01");

		//ログインボタンをクリック
		webDriver.findElement(By.xpath("//input[@value='ログイン']")).click();

		//コース詳細画面の要素が表示されるまで待機
		visibilityTimeout(By.className("clearfix"), 10);

		//URLへ正常に遷移することの検証
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		//コース詳細画面の要素を確認
		assertTrue(webDriver.findElement(By.id("contents")).isDisplayed());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C10_T02_login_screen.png"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() throws IOException {
		//「勤怠」ボタンをクリック
		webDriver.findElement(By.xpath("//a[contains(text(),'勤怠')]")).click();

		//過去日の勤怠未入力アラート処理
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.alertIsPresent());
			webDriver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
		}
		// 勤怠管理画面の要素が表示されるまで待機
		visibilityTimeout(By.id("contents"), 10);

		//URLへ正しく遷移することの検証
		assertEquals("http://localhost:8080/lms/attendance/detail", webDriver.getCurrentUrl());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C10_T03_attendance_detail_screen.png"));

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() throws IOException {
		//「出勤」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value='出勤']")).click();

		//確認アラートの処理
		webDriver.switchTo().alert().accept();

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C10_T04_start_time_screen.png"));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() throws IOException {
		//「退勤」ボタンをクリック
		webDriver.findElement(By.xpath("//input[@value='退勤']")).click();

		//確認アラートの処理
		webDriver.switchTo().alert().accept();

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C10_T05_end_time_screen.png"));
	}
}