FROM fluent/fluentd:v1.12-1

USER root
RUN apk add --no-cache --update --virtual .build-deps \
        sudo build-base ruby-dev \
 && sudo gem install fluent-plugin-elasticsearch \
 && sudo gem install fluent-plugin-record-reformer \
 && sudo gem install fluent-plugin-kubernetes_metadata_filter \
 && sudo gem install fluent-plugin-prometheus \
 && sudo gem install fluent-plugin-grafana-loki \
 && sudo gem install fluent-plugin-multi-format-parser \
 && sudo gem install fluent-plugin-grok-parser \
 && sudo gem install fluent-plugin-concat \
 && sudo gem install fluent-plugin-flatten-hash \
 && sudo gem install fluent-plugin-detect-exceptions \
 && sudo gem sources --clear-all \
 && apk del .build-deps \
 && rm -rf /var/cache/apk/*

USER fluent