package nablarch.core.log;

import nablarch.core.log.basic.LogContext;
import nablarch.core.log.basic.LogLevel;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class NablarchLoggerTest {

	public static class NablarchLoggerEnableTest {

		static class IsEnabledTestParameter {
			public String name;
			public boolean expected;
			// stringParameter => name|expected
			static List<IsEnabledTestParameter> of(String ...stringParameters) {
				List<IsEnabledTestParameter> parameters = new ArrayList<IsEnabledTestParameter>();
				for (String stringParameter : stringParameters) {
					String[] params = stringParameter.split("\\|");
					IsEnabledTestParameter isEnabledTestParameter = new IsEnabledTestParameter();
					isEnabledTestParameter.name = params[0].trim();
					isEnabledTestParameter.expected = Boolean.valueOf(params[1].trim());
					parameters.add(isEnabledTestParameter);
				}
				return parameters;
			}
		}

		interface IsEnabled extends Predicate<Logger> {}

		private void isEnabledTest(IsEnabled invoker, String... parameters) {
			for (IsEnabledTestParameter parameter : IsEnabledTestParameter.of(parameters)) {
				final org.slf4j.Logger logger = LoggerFactory.getLogger(parameter.name);
				assertEquals(parameter.expected, invoker.test(logger));
			}
		}

		@Test
		public void isErrorEnabled() {
			IsEnabled invoker = Logger::isErrorEnabled;
			isEnabledTest(invoker,
					"error | true",
					"warn  | true",
					"info  | true",
					"debug | true",
					"trace | true");
		}

		@Test
		public void isWarnEnabled() {
			IsEnabled invoker = Logger::isWarnEnabled;
			isEnabledTest(invoker,
					"error | false",
					"warn  | true",
					"info  | true",
					"debug | true",
					"trace | true");
		}

		@Test
		public void isInfoEnabled() {
			IsEnabled invoker = Logger::isInfoEnabled;
			isEnabledTest(invoker,
					"error | false",
					"warn  | false",
					"info  | true",
					"debug | true",
					"trace | true");
		}

		@Test
		public void isDebugEnabled() {
			IsEnabled invoker = Logger::isDebugEnabled;
			isEnabledTest(invoker,
					"error | false",
					"warn  | false",
					"info  | false",
					"debug | true",
					"trace | true");
		}

		@Test
		public void isTraceEnabled() {
			IsEnabled invoker = Logger::isTraceEnabled;
			isEnabledTest(invoker,
					"error | false",
					"warn  | false",
					"info  | false",
					"debug | false",
					"trace | true");
		}

		private void isEnabledForLevelTest(Level level, IsEnabled expected){
			IsEnabled invoker = logger -> logger.isEnabledForLevel(level);
			final org.slf4j.Logger testLogger = LoggerFactory.getLogger(level.toString());

			assertEquals(invoker.test(testLogger), expected.test(testLogger));
		}

		@Test
		public void isEnabledForLevelError() { isEnabledForLevelTest(Level.ERROR, Logger::isErrorEnabled); }

		@Test
		public void isEnabledForLevelWarn() { isEnabledForLevelTest(Level.WARN, Logger::isWarnEnabled); }

		@Test
		public void isEnabledForLevelInfo() { isEnabledForLevelTest(Level.INFO, Logger::isInfoEnabled);	}

		@Test
		public void isEnabledForLevelDebug() { isEnabledForLevelTest(Level.DEBUG, Logger::isDebugEnabled); }

		@Test
		public void isEnabledForLevelTrace() { isEnabledForLevelTest(Level.TRACE, Logger::isTraceEnabled); }
	}

	public static abstract class Base {

		private final LogLevel logLevel;

		protected Logger logger;

		public Base(final LogLevel logLevel) {
			this.logLevel = logLevel;
		}

		@Before
		public void init() {
			LoggerManager.get(""); //ロガー初期化のログを捨てるため、ここで一度Nablachのロガーを取得しておく
			MockLogWriter.init();
			logger = LoggerFactory.getLogger("test");
		}

		protected LogContext expected(final String message, final Throwable error) {
			return new LogContext("TEST", logLevel, message, error);
		}
	}

	public static class ErrorTest extends Base {

		public ErrorTest() {
			super(LogLevel.ERROR);
		}

		@Test
		public void error() {
			logger.error("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		public void error_with_1_argument() {
			logger.error("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		public void error_with_2_arguments() {
			logger.error("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		public void error_with_1_argument_and_error() {
			logger.error("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		public void error_with_3_arguments() {
			logger.error("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		public void error_with_2_arguments_and_error() {
			logger.error("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		public void error_with_error() {
			logger.error("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	public static class WarnTest extends Base {

		public WarnTest() {
			super(LogLevel.WARN);
		}

		@Test
		public void warn() {
			logger.warn("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		public void warn_with_1_argument() {
			logger.warn("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		public void warn_with_2_arguments() {
			logger.warn("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		public void warn_with_1_argument_and_warn() {
			logger.warn("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		public void warn_with_3_arguments() {
			logger.warn("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		public void warn_with_2_arguments_and_warn() {
			logger.warn("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		public void warn_with_warn() {
			logger.warn("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	public static class InfoTest extends Base {

		public InfoTest() {
			super(LogLevel.INFO);
		}

		@Test
		public void info() {
			logger.info("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		public void info_with_1_argument() {
			logger.info("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		public void info_with_2_arguments() {
			logger.info("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		public void info_with_1_argument_and_error() {
			logger.info("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		public void info_with_3_arguments() {
			logger.info("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		public void info_with_2_arguments_and_error() {
			logger.info("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		public void info_with_info() {
			logger.info("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	public static class DebugTest extends Base {

		public DebugTest() {
			super(LogLevel.DEBUG);
		}

		@Test
		public void debug() {
			logger.debug("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		public void debug_with_1_argument() {
			logger.debug("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		public void debug_with_2_arguments() {
			logger.debug("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		public void debug_with_1_argument_and_error() {
			logger.debug("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		public void debug_with_3_arguments() {
			logger.debug("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		public void debug_with_2_arguments_and_error() {
			logger.debug("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		public void debug_with_debug() {
			logger.debug("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	public static class TraceTest extends Base {

		public TraceTest() {
			super(LogLevel.TRACE);
		}

		@Test
		public void trace() {
			logger.trace("test");
			MockLogWriter.assertLog(expected("test", null));
		}

		@Test
		public void trace_with_1_argument() {
			logger.trace("test {}", 1);
			MockLogWriter.assertLog(expected("test 1", null));
		}

		@Test
		public void trace_with_2_arguments() {
			logger.trace("test {} {}", 1, 2);
			MockLogWriter.assertLog(expected("test 1 2", null));
		}

		@Test
		public void trace_with_1_argument_and_error() {
			logger.trace("test {}", 1, new MockException());
			MockLogWriter.assertLog(expected("test 1", new MockException()));
		}

		@Test
		public void trace_with_3_arguments() {
			logger.trace("test {} {} {}", 1, 2, 3);
			MockLogWriter.assertLog(expected("test 1 2 3", null));
		}

		@Test
		public void trace_with_2_arguments_and_error() {
			logger.trace("test {} {}", 1, 2, new MockException());
			MockLogWriter.assertLog(expected("test 1 2", new MockException()));
		}

		@Test
		public void trace_with_trace() {
			logger.trace("test", new MockException());
			MockLogWriter.assertLog(expected("test", new MockException()));
		}
	}

	public static class NablarchLoggerlogLevelDoNotMatchTest {
		@Test
		public void logLevelDoNotMatch() {
			LoggerManager.get(""); //ロガー初期化のログを捨てるため、ここで一度Nablachのロガーを取得しておく
			MockLogWriter.init();
			Logger logger = LoggerFactory.getLogger("fatal");
			logger.error("");
			logger.error("", 1);
			logger.error("", 1, 2);
			logger.error("", 1, 2, 3);
			logger.error("", new MockException());
			logger.error("", 1, new MockException());
			logger.error("", 1, 2, new MockException());
			logger.warn("");
			logger.warn("", 1);
			logger.warn("", 1, 2);
			logger.warn("", 1, 2, 3);
			logger.warn("", new MockException());
			logger.warn("", 1, new MockException());
			logger.warn("", 1, 2, new MockException());
			logger.info("");
			logger.info("", 1);
			logger.info("", 1, 2);
			logger.info("", 1, 2, 3);
			logger.info("", new MockException());
			logger.info("", 1, new MockException());
			logger.info("", 1, 2, new MockException());
			logger.debug("");
			logger.debug("", 1);
			logger.debug("", 1, 2);
			logger.debug("", 1, 2, 3);
			logger.debug("", new MockException());
			logger.debug("", 1, new MockException());
			logger.debug("", 1, 2, new MockException());
			logger.trace("");
			logger.trace("", 1);
			logger.trace("", 1, 2);
			logger.trace("", 1, 2, 3);
			logger.trace("", new MockException());
			logger.trace("", 1, new MockException());
			logger.trace("", 1, 2, new MockException());
			assertTrue(MockLogWriter.getQueue().isEmpty());
		}
	}
}
