package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.closeDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.createDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.visibilityTimeout;
import static jp.co.sss.lms.ct.util.WebDriverUtils.webDriver;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F02_C05_T01_top_screen.png"));

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws IOException {
		//トップページへアクセス
		webDriver.get("http://localhost:8080/lms");
		//URLでログイン画面へ遷移か検証
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());
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
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F02_C05_T02_login_screen.png"));

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() throws IOException {
		//ドロップダウンリストをクリック
		webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[1]/li[4]/a")).click();
		//「ヘルプ」リンクをクリック
		webDriver.findElement(By.xpath("//*[@id=\"nav-content\"]/ul[1]/li[4]/ul/li[4]/a")).click();
		//ヘルプ画面のURLを検証
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());
		//よくある質問の要素の表示の検証
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div[2]/p/a")).isDisplayed());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F02_C05_T03_help_screen.png"));

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws IOException {
		// 「よくある質問」リンクをクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div[2]/p/a")).click();
		// ウィンドウの操作を取得
		ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
		// 2番目のタブに切り替え
		webDriver.switchTo().window(tabs.get(1));
		// よくある質問画面のURLを検証
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());
		// 期待する要素表示の検証
		assertTrue(webDriver.findElement(By.xpath("//*[@id=\"main\"]/h2")).isDisplayed());

		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F02_C05_T04_faq_screen.png"));

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() throws IOException {
		//キーワード検索
		webDriver.findElement(By.id("form")).sendKeys("研修", Keys.ENTER);
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/form/fieldset/div[2]/div/input[1]")).click();

		visibilityTimeout(By.id("form"), 20);
		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F02_C05_T05_faq_keyword_screen.png"));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() throws IOException {
		//「クリア」ボタンをクリック
		webDriver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/form/fieldset/div[2]/div/input[2]")).click();
		//スクリーンショットを取得して保存
		File file = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(
				"C:\\\\\\\\Users\\\\\\\\user\\\\\\\\git\\\\\\\\lms-test-src-rp2\\\\\\\\test_code_creation_name\\\\\\\\evidence\\\\\\\\CT_F02_C05_T06_faq_keyword_clear_screen.png"));
	}

}
