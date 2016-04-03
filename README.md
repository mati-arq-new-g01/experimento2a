##Configuración experimento2:


1. Clonar repositorio
   git clone https://github.com/mati-arq-new-g01/experimento2.git

2. Ir a la raiz del directorio donde se descargaron los fuentes y ejecutar y ejecutar el comando
   vagrant up


3. Subir instancia de Mosquitto (172.17.0.2)

	docker run --name mosquitto -p 1883:1883 -itd matiang01/mosquitto
	
	docker exec -it mosquitto bash
  	
	mosquitto &

	exit
	
4. Subir instancia de mongo (172.17.0.3)

	docker run --name realtimedb -h realtimedb -itd matiang01/mongo

	docker exec -it realtimedb bash

	mongod &
	
	mongo
	
	db.createCollection("temperature");
	
	exit
   
5. Subir instancia de kafka (172.17.0.4)
	
	docker run --name kafka -p 2181:2181 -p 8080:8080 -p 9092:9092 -v /home/vagrant/kafka:/home/kafka -itd matiang01/java
		
	docker exec -it kafka bash
 
	cd /home/kafka/zookeeper-3.3.6
		
	bin/zkServer.sh start conf/zoo.cfg 
	
	cd /home/kafka/kafka_2.9.2-0.8.2.2
	
	bin/kafka-server-start.sh config/server.properties &
	
	bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1  --partitions 1 --topic iotdogs
	
	cd /home/kafka/standalone/
	
	java -jar iot-framework.jar --bridge start bridge_config.properties

6. Subir instancia de storm (importante: Zookeeper debe estar corriendo)

	cd /home/kafka/apache-storm-0.10.0/
	
	bin/storm nimbus &
	
	bin/storm supervisor &
	
	bin/storm ui &
	
	cd /home/kafka/standalone/
	
	java -jar iot-framework.jar --storm temperature default_config.properties &
	
	
5. Dispositivos IoT
	
   Conectar dispositivos (tcp://localhost:1883)

  	
##Validaciones:

1. Validar topico en Kafka (abrir un putty independiente)

	docker exec -it kafka bash
	
	cd /home/kafka/kafka_2.9.2-0.8.2.2/
	
	bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic iotdogs --from-beginning

2. Validar bd mongo (abrir un putty independiente)

	docker exec -it realtimedb bash

	mongo

	db.temperature.find();



	