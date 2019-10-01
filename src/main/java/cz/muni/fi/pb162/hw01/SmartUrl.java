package cz.muni.fi.pb162.hw01;

/**
 * @author Jakub Cechacek
 *
 *
 *
 *
 */
public interface SmartUrl {
    /**
     * @return host part of url
     */
    String getHost();

    /**
     * @return protocol part of url
     */
    String getProtocol();

    /**
     * @return port
     */
    int getPort();

    /**
     *  Path part of URL in canonical form. This means no trailing slashes and resolved relative segments
     * @return path part of url or null
     */
    String getPath();

    /**
     * Query parameters are the part of URL after <code>?</code>
     * Returned the same query string for all equal URLs
     *
     * E.g. "foo=bar&buz=qux" is return for
     *  http://domain.com:80/articles?foo=bar&buz=qux
     *  http://domain.com:80/articles?buz=qux&foo=bar
     *
     * @return query part of url or null
     */
    String getQuery();

    /**
     * @return fragment part of url or null
     */
    String getFragment();

    /**
     * Returns canonical representation of URL.
     *
     * A canonical URL fulfills the following criteria
     * <ul>
     *  <li>Port is omitted if it is the default port for given protocol</li>
     *  <li>No trailing slashes</li>
     *  <li>Relative path segments referring to current or parent directory are resolved</li>
     *  <li>Order of query parameters is fixed</li>
     * </ul>
     *
     * @return String representation of this URL in it's canonical form or null if URL is invalid
     */
    String getAsString();

    /**
     * @return String representation of this URL as originally initialized
     */
    String getAsRawString();

    /**
     * Compares two URLs for equality. URLs are equal if their canonical form is the same.
     *
     * @param url another URL
     * @return true if this and the other URL are equal, false otherwise
     */
    boolean isSameAs(SmartUrl url);

    /**
     * @see #isSameAs(String)
     * @param url another URL
     * @return true if this and the other URL are equal, false otherwise
     */
    boolean isSameAs(String url);
}
