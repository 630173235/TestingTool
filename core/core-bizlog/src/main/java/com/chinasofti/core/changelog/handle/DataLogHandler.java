package com.chinasofti.core.changelog.handle;

import java.util.List;

import com.chinasofti.core.changelog.entity.DataLog;

public interface DataLogHandler {

	void log( List<DataLog> dataLog );
}
