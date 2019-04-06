#!/usr/bin/env bash
class_name='com.cleverfishsoftware.utils.messagebuillder.RunLogMessageBuilderToFile'
jar_name='target/log-message-builder-1.0-SNAPSHOT.jar'
records_to_write='10000'
log_file_name="$PWD/logs/log-message-builder.log"

mvn clean install && java -cp $jar_name $class_name $records_to_write \
 && cat $log_file_name
