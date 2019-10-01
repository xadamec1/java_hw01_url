package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.SmartUrl;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jakub Cechacek
 */

public class SmartUrlTest {

    @Test
    public void shouldBeEqualUrls() {
        SoftAssertions softly = new SoftAssertions();
        SmartUrl expected = new Url("http://domain.com/articles/technology/25-moon-landing?order=asc&read=true");
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing/?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?read=true&order=asc" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com:80/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/./25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/public/../25-moon-landing?order=asc&read=true" , expected);
        softly.assertAll();
    }

    @Test
    public void shouldNotBeEqualUrls() {
        SoftAssertions softly = new SoftAssertions();
        SmartUrl expected = new Url("http://domain.com/articles/technology/25-moon-landing?order=asc&read=true");
        assertNotEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?order=asc&read=true&pg=2" , expected);
        assertNotEqualUrl(softly, "http://domain.com/articles/technology/25-moon-landing#foo?order=asc&read=true" , expected);
        assertNotEqualUrl(softly,"http://domain.com/articles/25-moon-landing?order=asc&read=true" , expected);
        assertNotEqualUrl(softly,"http://domain.com:8080/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        softly.assertAll();
    }

    @Test
    public void shouldStripTrailingSlash() {
        SoftAssertions softly = new SoftAssertions();
        assertNoTrailingSlash(softly, "http://domain.com/", "http://domain.com");
        assertNoTrailingSlash(softly, "http://domain.com/articles/", "http://domain.com/articles");
        assertNoTrailingSlash(softly, "http://domain.com/articles", "http://domain.com/articles");
        assertNoTrailingSlash(softly, "http://domain.com/articles?order=desc", "http://domain.com/articles?order=desc");
        softly.assertAll();
    }

    @Test
    public void shouldNotHavePath() {
        SoftAssertions softly = new SoftAssertions();
        assertNullPath(softly, "http://domain.com");
        assertNullPath(softly, "http://domain.com/");
        assertNullPath(softly, "http://domain.com?");
        assertNullPath(softly, "http://domain.com/articles/../");
        assertNullPath(softly, "http://domain.com/articles/../..");
        assertNullPath(softly, "http://domain.com?order=asc");
        softly.assertAll();
    }

    @Test
    public void shouldNotHaveQuery() {
        SoftAssertions softly = new SoftAssertions();
        assertNullQuery(softly, "http://domain.com");
        assertNullQuery(softly, "http://domain.com?");
        assertNullQuery(softly, "http://domain.com/articles/");
        assertNullQuery(softly, "http://domain.com/articles?");
        softly.assertAll();
    }

    @Test
    public void shouldHaveNormalizedQuery() {
        SoftAssertions softly = new SoftAssertions();
        SmartUrl u = new Url("http://domain.com/articles?foo=bar&buz=qux&c&order=desc");
        assertQuery(softly, "http://domain.com/articles?c&foo=bar&order=desc&buz=qux", u.getQuery());
        assertQuery(softly, "http://domain.com/articles?buz=qux&c&foo=bar&order=desc", u.getQuery());
        softly.assertAll();
    }

    @Test
    public void shouldHaveDefaultPort() {
        SoftAssertions softly = new SoftAssertions();
        assertDefaultPort(softly, "http://domain.com/articles", 80);
        assertDefaultPort(softly, "https://domain.com/articles", 443);
        assertDefaultPort(softly, "ftp://domain.com/files", 21);
        assertDefaultPort(softly, "sftp://domain.com/files", 22);
        assertDefaultPort(softly, "ssh://127.0.0.1", 22);
    }

    private void assertDefaultPort(SoftAssertions assertions, String url, int port) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getPort()).isEqualTo(port);
    }

    private void assertEqualUrl(SoftAssertions assertions, String actual, SmartUrl expected) {
        SmartUrl u = new Url(actual);
        assertions.assertThat(u.isSameAs(expected)).isTrue();
    }

    private void assertNotEqualUrl(SoftAssertions assertions, String actual, SmartUrl expected) {
        SmartUrl u = new Url(actual);
        assertions.assertThat(u.isSameAs(expected)).isFalse();
    }

    private void assertQuery(SoftAssertions assertions, String url, String query) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getQuery()).isEqualTo(query);
    }

    private void assertNullPath(SoftAssertions assertions, String url) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getPath()).isNull();
    }

    private void assertNullQuery(SoftAssertions assertions, String url) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getQuery()).isNull();
    }

    private void assertNoTrailingSlash(SoftAssertions assertions, String url, String expected) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getAsString()).isEqualTo(expected);
    }
}
