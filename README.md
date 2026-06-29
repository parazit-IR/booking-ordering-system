docker-compose -p hooshmand \
-f common.yml \
-f zookeeper.yml \
-f kafka_cluster.yml \
-f init_kafka.yml \
-f keycloak.yml \
up

docker-compose -p hooshmand \
-f common.yml \
-f zookeeper.yml \
-f kafka_cluster.yml \
-f init_kafka.yml \
-f keycloak.yml \
down -v

docker network inspect hooshmand-shipping-system