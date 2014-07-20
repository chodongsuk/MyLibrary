package cm.android.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 */
public class LogConfig {

    public static void test() {
        configLogback(Level.OFF, new File("/sdcard"));
        // BasicLogcatConfigurator.configureDefaultContext();

        Logger log = LoggerFactory.getLogger(LogConfig.class);
        log.error("gggg1,{},{}", "ggggg2", "ggggg3");
    }

    private static volatile boolean configFlag = false;

    public static void configLogback(Level level, File logDir) {
        if (configFlag) {
            return;
        }
        configFlag = true;

        // add the newly created appenders to the root logger;
        // qualify Logger to disambiguate from org.slf4j.Logger
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(Logger.ROOT_LOGGER_NAME);

        // reset the default context (which may already have been initialized)
        // since we want to reconfigure it
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();

        if (logDir != null) {
            setFileMode(root, lc, logDir);
        }

        setLogcatMode(root, lc);

        // Level:OFF
        root.setLevel(level);
    }

    private static void setFileMode(ch.qos.logback.classic.Logger root,
                                    LoggerContext lc, File logDir) {
        // setup FileAppender
        PatternLayoutEncoder encoder1 = new PatternLayoutEncoder();
        encoder1.setContext(lc);

        encoder1.setPattern("%date %-5level [%thread:%logger:%method:%line] - %msg%n");
        encoder1.start();

        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<ILoggingEvent>();
        fileAppender.setContext(lc);
        fileAppender.setFile(logDir.getAbsolutePath() + File.separator
                + "logback.log");
        fileAppender.setEncoder(encoder1);

        TimeBasedRollingPolicy<ILoggingEvent> policy = new TimeBasedRollingPolicy<ILoggingEvent>();
        policy.setContext(lc);
        policy.setMaxHistory(7);
        policy.setFileNamePattern(logDir.getAbsolutePath() + File.separator
                + "logback.%d{yyyy-MM-dd}.log.gz");
        policy.setParent(fileAppender);
        policy.start();

        fileAppender.setRollingPolicy(policy);
        fileAppender.start();

        root.addAppender(fileAppender);
    }

    private static void setLogcatMode(ch.qos.logback.classic.Logger root,
                                      LoggerContext lc) {
        // setup LogcatAppender
        PatternLayoutEncoder encoder2 = new PatternLayoutEncoder();
        encoder2.setContext(lc);
        encoder2.setPattern("[%thread:%logger:%method:%line] %msg%n");
        encoder2.start();

        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(lc);
        logcatAppender.setEncoder(encoder2);
        logcatAppender.start();

        root.addAppender(logcatAppender);
    }
}
