package cz.muni.fi.pb162.hw01.url;

/**
 * @author Jakub Cechacek
 *
 * Implementation of this interface can be used to resolve port number
 */
public interface PortResolver {

    /**
     * Resolves default port for protocol based on schema
     * @param schema protocol schema
     * @return default port number
     */
    int getPort(String schema);
}
