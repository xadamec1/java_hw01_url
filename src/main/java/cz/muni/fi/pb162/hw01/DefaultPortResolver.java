package cz.muni.fi.pb162.hw01;

/**
 * @author Jakub Cechacek
 *
 * Resolves port numbers based on {@link Protocols} enum
 */
public class DefaultPortResolver implements  PortResolver {
    @Override
    public int getPort(String schema) {
        Protocols protocol =  Protocols.valueOf(schema.toUpperCase());
        return protocol.port;
    }
}
