package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.closeDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.createDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.visibilityTimeout;
import static jp.co.sss.lms.ct.util.WebDriverUtils.webDriver;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(日報) 正常系")
public class Case08 {

	/** 修正後の報告内容 */
	private static final String UPDATED_REPORT = "明日は雨予報なので傘を持っていく。";
	private static String reportDays;

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
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C08_T01_top_screen.png"));
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
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C08_T02_login_screen.png"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03「提出済」ステータスの研修日の「詳細」ボタンを押下し、セクション詳細画面に遷移する。")
	void test03() throws IOException {
		//修正するレポートの日付をのテキストを取得し、変数に保存
		WebElement reportRow = webDriver
				.findElement(By.xpath("(//tr[td/span[text()='提出済み']])[1]"));
		reportDays = reportRow
				.findElement(By.xpath("./td")).getText();

		//取得した日付の情報をコンソールで確認
		System.out.println("修正対象レポートの日付：" + reportDays);

		//「詳細」ボタンをクリック
		reportRow.findElement(By.xpath("./td/form/input[@value='詳細']")).click();

		//URLへ正しく遷移しているか検証
		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());
		//スクリーンショットを取得して保存
		File file2 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file2, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C08_T03_ssection_detail_screen.png"));
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出済み週報を確認する」ボタンを押下し、レポート登録画面に遷移する。")
	void test04() throws IOException {
		//提出済み日報を確認するをクリック
		webDriver.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']")).click();
		//URLへ正しく遷移しているかの検証
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());
		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C08_T04__report_regit.png"));
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() throws IOException {
		//報告レポートの入力フォームを空欄にする
		WebElement textarea = webDriver.findElement(By.id("content_0"));
		textarea.clear();

		//報告レポートのフォームに修正した報告文を入力
		textarea.sendKeys(UPDATED_REPORT);

		//「日報を提出する」ボタンをクリック
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		visibilityTimeout(By.id("contents"), 10);
		//URLへ正しく遷移することの検証
		assertTrue(webDriver.getCurrentUrl().startsWith("http://localhost:8080/lms/section/detail"));
		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C08_T05_correct_report_section_screen.png"));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクから、ユーザー詳細画面に遷移")
	void test06() throws IOException {

		//「ようこそ○○さん」リンクをクリック
		webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[2]/li[2]/a")).click();

		//URLへ正しく遷移することの検証
		assertEquals("http://localhost:8080/lms/user/detail", webDriver.getCurrentUrl());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C08_T06_user_details_screen.png"));
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下し、レポート詳細画面で修正内容が反映される")
	void test07() throws IOException {
		// test03で保存した日付情報を使って、該当レポートの行を特定
		WebElement detailButton = webDriver.findElement(
				By.xpath("//tr[td[contains(text(),'" + reportDays + "')]]/td/form/input[@value='詳細']"));

		// ボタンをクリックしてレポート詳細画面に遷移
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailButton);

		// URLが正しく遷移したことを検証
		assertEquals("http://localhost:8080/lms/report/detail", webDriver.getCurrentUrl());

		// 修正内容が画面に表示されていることを検証
		assertTrue(webDriver.getPageSource().contains(UPDATED_REPORT));
		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C08_T07_report_reflect_screen.png"));
	}
}