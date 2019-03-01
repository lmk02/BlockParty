package de.leonkoth.blockparty.web.server;

import org.eclipse.jetty.util.log.Logger;

/**
 * Created by Leon on 19.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */
public class JettyLogger implements Logger {
    @Override
    public String getName() {
        return "BlockParty";
    }

    @Override
    public void warn(String s, Object... objects) {

    }

    @Override
    public void warn(Throwable throwable) {

    }

    @Override
    public void warn(String s, Throwable throwable) {

    }

    @Override
    public void info(String s, Object... objects) {

    }

    @Override
    public void info(Throwable throwable) {

    }

    @Override
    public void info(String s, Throwable throwable) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void setDebugEnabled(boolean b) {

    }

    @Override
    public void debug(String s, Object... objects) {

    }

    @Override
    public void debug(String s, long l) {

    }

    @Override
    public void debug(Throwable throwable) {

    }

    @Override
    public void debug(String s, Throwable throwable) {

    }

    @Override
    public Logger getLogger(String s) {
        return this;
    }

    @Override
    public void ignore(Throwable throwable) {

    }
}
