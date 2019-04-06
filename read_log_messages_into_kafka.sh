#!/usr/bin/env bash
class_name='com.cleverfishsoftware.utils.messagebuillder.RunKafkaMessageSender'
jar_name='target/log-message-builder-1.0-SNAPSHOT.jar'
log_file_name="$PWD/logs/log-message-builder.log"

mvn clean install && java -cp $jar_name $class_name $log_file_name
