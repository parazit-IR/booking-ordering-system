docker-compose -p booking \
-f common.yml \
-f zookeeper.yml \
-f kafka_cluster.yml \
-f init_kafka.yml \
up

docker-compose -p booking \
-f common.yml \
-f zookeeper.yml \
-f kafka_cluster.yml \
-f init_kafka.yml \
down -v

docker network inspect hooshmand-shipping-system