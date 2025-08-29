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

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	private static final String INPUT_REPORT = "今日はとても良い天気でした。";

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
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C07_T01_top_screen.png"));

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
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C07_T02_login_screen.png"));

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws IOException {
		//ページを下にスクロール（必要な時に利用
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,400);");
		//「未提出」ステータスの「詳細」ボタンをクリック
		webDriver.findElement(By.xpath("//tr[td/span[text()='未提出']]/td/form/input[@value='詳細']"))
				.click();
		// 画面がセクション詳細画面に遷移するまで待機
		visibilityTimeout(By.id("sectionDetail"), 10);

		//URLへ正常に遷移することの検証
		String currentPath = webDriver.getCurrentUrl().split("\\?")[0];
		assertEquals("http://localhost:8080/lms/section/detail", currentPath);

		//「日報を提出する」のボタンが表示されていることの検証
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[5]"))
				.isDisplayed());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C07_T03_report_detail.png"));

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws IOException {
		//「日報を提出する」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"sectionDetail\"]/table/tbody/tr[2]/td/form/input[5]")).click();
		//URLへ正常に遷移することの検証
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());
		//報告内容の入力欄が表示されていることの確認
		assertTrue(webDriver.findElement(By.id("content_0")).isDisplayed());
		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C07_T04_report_regit.png"));

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() throws IOException {
		//報告レポートのフォームに入力
		webDriver.findElement(By.id("content_0")).sendKeys(INPUT_REPORT);
		//「提出する」ボタンをクリック
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();
		// 提出後、セクション詳細画面に戻るまで待機
		visibilityTimeout(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"), 10);

		// URLへ正しく遷移することの検証
		assertTrue(webDriver.getCurrentUrl().startsWith("http://localhost:8080/lms/section/detail"));

		//「確認する」ボタンに代わったことの検証
		assertTrue(webDriver.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"))
				.isDisplayed());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F03_C07_T05_report_submitted.png"));
	}
}