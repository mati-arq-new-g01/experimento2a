##Configuración experimento2:


1. Clonar repositorio
   git clone https://github.com/mati-arq-new-g01/experimento2.git

2. Ir a la raiz del directorio donde se descargaron los fuentes y ejecutar y ejecutar el comando
   vagrant up


3. Subir instancia de Mosquitto

	docker run --name mosquitto -p 1883:1883 -itd matiang01/mosquitto

	docker exec -it mosquitto bash
  	
	mosquitto &

	exit
	
   
3. Subir instancia de kafka
	
	docker run --name kafka -p 2181:2181 -p 9092:9092 -v /home/vagrant/kafka:/home/kafka -itd matiang01/java
		
	docker exec -it kafka bash
 
	cd /home/kafka/zookeeper-3.3.6
		
	bin/zkServer.sh start conf/zoo.cfg 
	
	cd /home/kafka/kafka_2.9.2-0.8.2.2
	
	bin/kafka-server-start.sh config/server.properties &
	
	bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1  --partitions 1 --topic iotdogs
	
	cd /home/kafka/kafka_2.9.2-0.8.2.2/libs
	
	java -jar  bridgekafka.jar tcp://172.17.0.2:1883 localhost:2181 &

4. Subir instancia de storm (importante: Zookeeper debe estar corriendo)

	cd /home/kafka/apache-storm-0.10.0/
	
	bin/storm nimbus &
	
	bin/storm supervisor &
	
	cd /home/kafka/storm-topology
	
	java -jar temperature-storm.jar
	
	
5. Dispositivos IoT

java -jar  iotGateway.jar
	
Conectar dispositivo	

  	
##Validaciones:

cd /home/kafka/kafka_2.9.2-0.8.2.2/

bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1  --partitions 1 --topic iotsmart

bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic iotsmart --from-beginning

bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic iotcats --from-beginning

echo "date:01-04-2016;c:23;f:45" | bin/kafka-console-producer.sh --broker-list localhost:9092 --topic iotdogs

cd /home/kafka/kafka_2.9.2-0.8.2.2/libs

java -jar kafkapub.jar localhost:2181 test "hi"

java -jar  bridgekafka.jar tcp://172.17.0.3:1883 localhost:2181

bin/storm jar storm-wordcount.jar co.edu.uniandes.matiang01.storm.WordCountTopology WordCount -c nimbus.host=localhost
	
bin/storm jar /home/kafka/apache-storm-0.10.0/examples/storm-starter/storm-starter-topologies-0.10.0.jar storm.starter.RollingTopWords 
	