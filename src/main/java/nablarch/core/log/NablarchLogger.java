package nablarch.core.log;

import org.slf4j.event.Level;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

import nablarch.core.log.Logger;

/**
 * SLF4Jのロガーを実装したクラス。
 *
 * ログ出力をNablarchのロガーに委譲する。
 *
 * @author Kiyohito Itoh
 */
public class NablarchLogger extends MarkerIgnoringBase {

	/** Nablarchのロガー */
	private final Logger logger;

	/**
	 * コンストラクタ。
	 * @param logger Nablarchのロガー
	 */
	public NablarchLogger(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean isEnabledForLevel(Level level) {
		return switch (level) {
			case ERROR -> isErrorEnabled();
			case WARN -> isWarnEnabled();
			case INFO -> isInfoEnabled();
			case DEBUG -> isDebugEnabled();
			case TRACE -> isTraceEnabled();
		};
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public void trace(final String msg) {
		if (logger.isTraceEnabled()) {
			logger.logTrace(msg);
		}
	}

	@Override
	public void trace(final String format, final Object arg) {
		if (logger.isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			logger.logTrace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void trace(final String format, final Object arg1, final Object arg2) {
		if (logger.isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			logger.logTrace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void trace(final String format, final Object... arguments) {
		if (logger.isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			logger.logTrace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void trace(final String msg, final Throwable t) {
		if (logger.isTraceEnabled()) {
			logger.logTrace(msg, t);
		}
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(final String msg) {
		if (logger.isDebugEnabled()) {
			logger.logDebug(msg);
		}
	}

	@Override
	public void debug(final String format, final Object arg) {
		if (logger.isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			logger.logDebug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void debug(final String format, final Object arg1, final Object arg2) {
		if (logger.isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			logger.logDebug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void debug(final String format, final Object... arguments) {
		if (logger.isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			logger.logDebug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void debug(final String msg, final Throwable t) {
		if (logger.isDebugEnabled()) {
			logger.logDebug(msg, t);
		}
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public void info(final String msg) {
		if (logger.isInfoEnabled()) {
			logger.logInfo(msg);
		}
	}

	@Override
	public void info(final String format, final Object arg) {
		if (logger.isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			logger.logInfo(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void info(final String format, final Object arg1, final Object arg2) {
		if (logger.isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			logger.logInfo(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void info(final String format, final Object... arguments) {
		if (logger.isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			logger.logInfo(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void info(final String msg, final Throwable t) {
		if (logger.isInfoEnabled()) {
			logger.logInfo(msg, t);
		}
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public void warn(final String msg) {
		if (logger.isWarnEnabled()) {
			logger.logWarn(msg);
		}
	}

	@Override
	public void warn(final String format, final Object arg) {
		if (logger.isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			logger.logWarn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void warn(final String format, final Object arg1, final Object arg2) {
		if (logger.isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			logger.logWarn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void warn(final String format, final Object... arguments) {
		if (logger.isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			logger.logWarn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void warn(final String msg, final Throwable t) {
		if (logger.isWarnEnabled()) {
			logger.logWarn(msg, t);
		}
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	@Override
	public void error(final String msg) {
		if (logger.isErrorEnabled()) {
			logger.logError(msg);
		}
	}

	@Override
	public void error(final String format, final Object arg) {
		if (logger.isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			logger.logError(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void error(final String format, final Object arg1, final Object arg2) {
		if (logger.isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			logger.logError(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void error(final String format, final Object... arguments) {
		if (logger.isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			logger.logError(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void error(final String msg, final Throwable t) {
		if (logger.isErrorEnabled()) {
			logger.logError(msg, t);
		}
	}
}
