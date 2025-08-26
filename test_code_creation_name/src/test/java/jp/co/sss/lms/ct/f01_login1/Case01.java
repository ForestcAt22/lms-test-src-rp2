package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.closeDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.createDriver;
import static jp.co.sss.lms.ct.util.WebDriverUtils.webDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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
				"C:\\\\Users\\\\user\\\\git\\\\lms-test-src-rp2\\\\test_code_creation_name\\\\evidence\\\\CT_F01_C01_T01_top_screen.png"));
	}
}